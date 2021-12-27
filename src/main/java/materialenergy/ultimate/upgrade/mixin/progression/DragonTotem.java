package materialenergy.ultimate.upgrade.mixin.progression;

import materialenergy.ultimate.upgrade.item.draconic.DraconicBaseItem;
import materialenergy.ultimate.upgrade.item.draconic.DraconicTotemItem;
import materialenergy.ultimate.upgrade.registry.UUItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ChunkTicketType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.Map;

@Mixin(LivingEntity.class)
public abstract class DragonTotem extends Entity {
    public ArrayList<Map<Vec3d, World>> posList = new ArrayList<>();
    public int timeUntilSave = 0;
    @Shadow public abstract float getMaxHealth();
    @Shadow public abstract void setHealth(float v);
    @Shadow public abstract ItemStack getStackInHand(Hand hand);

    @Shadow public abstract void endCombat();

    public DragonTotem(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(
            method = "tryUseTotem",
            at = @At("HEAD"),
            cancellable = true
    )
    public void modifyTotemUsage(DamageSource source, CallbackInfoReturnable<Boolean> cir){
        ItemStack DRitemStack = null;
        for (Hand hand : Hand.values()) {
            ItemStack DRitemStack2 = this.getStackInHand(hand);
            if (!DRitemStack2.isOf(UUItems.DRACONIC_TOTEM)) continue;
            DRitemStack = DRitemStack2.copy();
            DRitemStack2.damage(1,
                    (LivingEntity)(Object)this,
                    (e) -> e.sendEquipmentBreakStatus(hand.equals(Hand.MAIN_HAND) ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND));
            break;
        }
        if (DRitemStack != null) {
            this.setHealth(this.getMaxHealth());
            int rewindAmount = DraconicTotemItem.getRewind(DRitemStack);
            int enderEnergy = DraconicBaseItem.getEnderEnergy(DRitemStack);
            enderEnergy = Math.min(enderEnergy, rewindAmount);
            Map<Vec3d, World> posToMap = this.getPosAt(
                    enderEnergy != 0 ? enderEnergy : 10
            );
            for (Vec3d posTo : posToMap.keySet() ) {
                this.teleportOutWorld(this, (ServerWorld)posToMap.get(posTo), posTo.x, posTo.y, posTo.z);
            }
            this.world.sendEntityStatus(this, (byte)35);
            cir.setReturnValue(true);
        }
    }

    public Map<Vec3d, World> getPosAt(int secondsBefore) {
        if (secondsBefore < this.posList.size()){
            return this.posList.get(this.posList.size() - secondsBefore - 1);
        } else {
            return this.posList.get(this.posList.size() - 1);
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
