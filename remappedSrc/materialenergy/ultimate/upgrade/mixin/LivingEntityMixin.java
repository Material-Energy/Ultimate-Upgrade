package materialenergy.ultimate.upgrade.mixin;

import materialenergy.ultimate.upgrade.common.item.draconic.DraconicBaseItem;
import materialenergy.ultimate.upgrade.common.item.draconic.DraconicTotemItem;
import materialenergy.ultimate.upgrade.common.registry.UUItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTracker;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ChunkTicketType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.Map;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity{


    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    public ArrayList<Map<Vec3d, World>> posList = new ArrayList<>();
    public int timeUntilSave = 0;

    @Shadow public abstract float getMaxHealth();
    @Shadow public abstract ItemStack getStackInHand(Hand hand);
    @Shadow public abstract Iterable<ItemStack> getArmorItems();
    @Shadow public abstract boolean damage(DamageSource source, float amount);
    @Shadow public abstract DamageTracker getDamageTracker();
    @Shadow public abstract void setHealth(float v);
    @Shadow public abstract float getHealth();
    @Shadow public abstract void setAbsorptionAmount(float v);
    @Shadow public abstract float getAbsorptionAmount();
    @Shadow protected abstract float applyEnchantmentsToDamage(DamageSource source, float amount);
    @Shadow protected abstract float applyArmorToDamage(DamageSource source, float amount);

    @Inject(
            method = "tickInVoid",
            at = @At("HEAD"),
            cancellable = true)
    public void killInVoid(CallbackInfo ci){
        this.damage(DamageSource.OUT_OF_WORLD, this.getHealth());
        ci.cancel();
    }

    @Inject(
            method = "applyDamage",
            at = @At("HEAD"),
            cancellable = true
    )
    public void multiply(DamageSource source, float amount, CallbackInfo ci){
        if (source.name.equals("mob") || source.name.equals("player")){
            if (this.isInvulnerableTo(source)) {
                ci.cancel();
            }
            amount *= 2.5;
            amount = this.applyArmorToDamage(source, amount);
            float f = amount = this.applyEnchantmentsToDamage(source, amount);
            amount = Math.max(amount - this.getAbsorptionAmount(), 0.0f);
            this.setAbsorptionAmount(this.getAbsorptionAmount() - (f - amount));
            float g = f - amount;
            if (g > 0.0f && g < 3.4028235E37f && source.getAttacker() instanceof ServerPlayerEntity) {
                ((ServerPlayerEntity)source.getAttacker()).increaseStat(Stats.DAMAGE_DEALT_ABSORBED, Math.round(g * 10.0f));
            }
            if (amount == 0.0f) {
                ci.cancel();
            }
            float h = this.getHealth();
            this.setHealth(h - amount);
            this.getDamageTracker().onDamage(source, h, amount);
            this.setAbsorptionAmount(this.getAbsorptionAmount() - amount);
            this.emitGameEvent(GameEvent.ENTITY_DAMAGED, source.getAttacker());
            ci.cancel();
        }
    }

    @Inject(
            method = "tryUseTotem",
            at = @At("HEAD"),
            cancellable = true
    )
    public void modifyTotemUsage(DamageSource source, CallbackInfoReturnable<Boolean> cir){
        ItemStack draconicTotem = null;
        for (Hand hand : Hand.values()) {
            ItemStack draconicTotem2 = this.getStackInHand(hand);
            if (!draconicTotem2.isOf(UUItems.DRACONIC_TOTEM)) continue;
            draconicTotem = draconicTotem2.copy();
            draconicTotem2.damage(1,
                    (LivingEntity)(Object)this,
                    (e) -> e.sendEquipmentBreakStatus(hand.equals(Hand.MAIN_HAND) ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND));
            break;
        }
        if (draconicTotem != null) {
            int power = DraconicBaseItem.getEnderEnergy(draconicTotem);
            int total = ((DraconicBaseItem) draconicTotem.getItem()).getTotalEnergy();
            this.setHealth(Math.min(this.getMaxHealth() * power/(float)total, 0.01f));
            int rewindAmount = DraconicTotemItem.getRewind(draconicTotem);
            Map<Vec3d, World> posToMap = this.getPosAt(
                    rewindAmount != 0 ? rewindAmount : 10
            );
            this.fallDistance = 0;
            for (Vec3d posTo : posToMap.keySet() ) {
                this.teleportOutWorld(this, (ServerWorld)posToMap.get(posTo), posTo.x, posTo.y, posTo.z);
            }
            this.world.sendEntityStatus(this, (byte)35);
            cir.setReturnValue(true);
        }
    }

    public Map<Vec3d, World> getPosAt(int secondsBefore) {
        int totalIndex = this.posList.size() - 1;
        if (secondsBefore < this.posList.size()){
            return this.posList.get(totalIndex - secondsBefore);
        } else {
            return this.posList.get(totalIndex);
        }
    }

    @Inject(
            method = "tick",
            at = @At("HEAD")
    )
    public void storePos(CallbackInfo ci){
        if (this.getPos().getY() > 0){
            if(this.timeUntilSave == 20 ){
                this.posList.add(0, Map.ofEntries(
                        Map.entry(this.getPos(), this.world)
                ));
                if (this.posList.size() > 20){
                    this.posList.remove(this.posList.size() - 1);
                }
                this.timeUntilSave = 0;
            }
            this.timeUntilSave++;
        }
    }


    private void teleportOutWorld(Entity target, ServerWorld world, double x, double y, double z){
        float f = MathHelper.wrapDegrees(target.getYaw());
        float g = MathHelper.wrapDegrees(target.getPitch());
        if (target instanceof ServerPlayerEntity) {
            ChunkPos chunkPos = new ChunkPos(new BlockPos(x, y, z));
            world.getChunkManager().addTicket(ChunkTicketType.POST_TELEPORT, chunkPos, 1, target.getId());
            target.stopRiding();
            if (((ServerPlayerEntity)target).isSleeping()) {
                ((ServerPlayerEntity)target).wakeUp(true, true);
            }
            if (world == target.world) {
                ((ServerPlayerEntity)target).networkHandler.requestTeleport(x, y, z, f, g);
            } else {
                ((ServerPlayerEntity)target).teleport(world, x, y, z, f, g);
            }
            target.setHeadYaw(f);
            this.world.sendEntityStatus(this, (byte)46);
        } else {
            float chunkPos = MathHelper.clamp(g, -90.0f, 90.0f);
            if (world == target.world) {
                target.refreshPositionAndAngles(x, y, z, f, chunkPos);
                target.setHeadYaw(f);
            } else {
                target.detach();
                Entity entity = target;
                target = entity.getType().create(world);
                if (target != null) {
                    target.copyFrom(entity);
                    target.refreshPositionAndAngles(x, y, z, f, chunkPos);
                    target.setHeadYaw(f);
                    entity.setRemoved(Entity.RemovalReason.CHANGED_DIMENSION);
                    world.onDimensionChanged(target);
                } else {
                    return;
                }
            }
        }
        if (!(target instanceof LivingEntity) || !((LivingEntity)target).isFallFlying()) {
            target.setVelocity(target.getVelocity().multiply(1.0, 0.0, 1.0));
            target.setOnGround(true);
        }
        if (target instanceof PathAwareEntity) {
            ((PathAwareEntity)target).getNavigation().stop();
        }
    }

}
