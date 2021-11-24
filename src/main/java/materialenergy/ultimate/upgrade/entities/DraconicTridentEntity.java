package materialenergy.ultimate.upgrade.entities;

import materialenergy.ultimate.upgrade.misc.CustomDamageSource;
import materialenergy.ultimate.upgrade.registry.UUEntities;
import materialenergy.ultimate.upgrade.registry.UUItems;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import net.minecraft.world.explosion.ExplosionBehavior;
import org.jetbrains.annotations.Nullable;

public class DraconicTridentEntity extends PersistentProjectileEntity {
    private static final TrackedData<Byte> LOYALTY;
    private static final TrackedData<Boolean> ENCHANTED;
    private static final TrackedData<ItemStack> TRIDENT;
    private ItemStack tridentStack;
    private boolean dealtDamage;
    public int returnTimer;

    public DraconicTridentEntity(EntityType<? extends DraconicTridentEntity> entityType, World world) {
        super(entityType, world);
    }

    public DraconicTridentEntity(World world, LivingEntity owner, ItemStack stack) {
        super(UUEntities.DRACONIC_TRIDENT, owner, world);
        this.tridentStack = new ItemStack(UUItems.DRACONIAN_TRIDENT);
        this.tridentStack = stack.copy();
        LOGGER.debug(this.tridentStack);
        this.dataTracker.set(LOYALTY, (byte)EnchantmentHelper.getLoyalty(stack));
        this.dataTracker.set(ENCHANTED, stack.hasGlint());
        this.dataTracker.set(TRIDENT, stack);
    }

    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(TRIDENT,new ItemStack(UUItems.DRACONIAN_TRIDENT));
        this.dataTracker.startTracking(LOYALTY, (byte)0);
        this.dataTracker.startTracking(ENCHANTED, false);
    }

    public void tick() {
        if (this.inGroundTime > 4) {
            this.dealtDamage = true;
        }

        Entity entity = this.getOwner();
        int i = this.dataTracker.get(LOYALTY);
        if ((this.dealtDamage || this.isNoClip()) && entity != null && EnchantmentHelper.getRiptide(this.dataTracker.get(TRIDENT)) == 0) {
            if (!this.isOwnerAlive()) {
                if (!this.world.isClient && this.pickupType == PickupPermission.ALLOWED) {
                    this.dropStack(this.asItemStack(), 0.1F);
                }

                this.discard();
            } else {
                this.setNoClip(true);
                Vec3d vec3d = entity.getEyePos().subtract(this.getPos());
                i = i * 2 + 1;
                this.setPos(this.getX(), this.getY() + vec3d.y * 0.015D * (double)i, this.getZ());
                if (this.world.isClient) {
                    this.lastRenderY = this.getY();
                }

                double d = 0.05D * (double)i;
                this.setVelocity(this.getVelocity().multiply(0.95D).add(vec3d.normalize().multiply(d)));
                if (this.returnTimer == 0) {
                    this.playSound(SoundEvents.ITEM_TRIDENT_RETURN, 10.0F, 1.0F);
                }

                ++this.returnTimer;
            }
        }

        super.tick();
    }

    private boolean isOwnerAlive() {
        Entity entity = this.getOwner();
        if (entity != null && entity.isAlive()) {
            return !(entity instanceof ServerPlayerEntity) || !entity.isSpectator();
        } else {
            return false;
        }
    }

    protected ItemStack asItemStack() {
        return this.dataTracker.get(TRIDENT);
    }

    public boolean isEnchanted() {
        return this.dataTracker.get(ENCHANTED);
    }

    @Nullable
    protected EntityHitResult getEntityCollision(Vec3d currentPosition, Vec3d nextPosition) {
        return this.dealtDamage ? null : super.getEntityCollision(currentPosition, nextPosition);
    }

    protected void onEntityHit(EntityHitResult entityHitResult) {
        Entity entity = entityHitResult.getEntity();
        float f = 16.0F;
        if (entity instanceof LivingEntity livingEntity && this.tridentStack != null) {
            f += EnchantmentHelper.getAttackDamage(this.tridentStack, livingEntity.getGroup());
        }

        Entity livingEntity = this.getOwner();
        DamageSource damageSource = CustomDamageSource.trident(this, livingEntity == null ? this : livingEntity);
        this.dealtDamage = true;
        SoundEvent soundEvent = SoundEvents.ITEM_TRIDENT_HIT;
        if (entity.damage(damageSource, f)) {
            if (entity.getType() == EntityType.ENDERMAN) {
                return;
            }

            if (entity instanceof LivingEntity livingEntity2) {
                if (livingEntity instanceof LivingEntity) {
                    EnchantmentHelper.onUserDamaged(livingEntity2, livingEntity);
                    EnchantmentHelper.onTargetDamaged((LivingEntity)livingEntity, livingEntity2);
                }

                this.onHit(livingEntity2);
            }
        }

        this.setVelocity(this.getVelocity().multiply(-0.01D, -0.1D, -0.01D));
        float livingEntity2 = 1.0F;
        if (this.world instanceof ServerWorld && this.hasChanneling()) {
            BlockPos blockPos = entity.getBlockPos();
            ExplosionBehavior explosionBehavior = new ExplosionBehavior();
            world.createExplosion(this.getOwner(),DamageSource.DRAGON_BREATH, explosionBehavior, blockPos.getX(),blockPos.getY(),blockPos.getZ(),3.0F,false, Explosion.DestructionType.NONE);
            soundEvent = SoundEvents.ITEM_TRIDENT_THUNDER;
            livingEntity2 = 5.0F;
        }

        this.playSound(soundEvent, livingEntity2, 1.0F);
    }

    public boolean hasChanneling() {
        return EnchantmentHelper.hasChanneling(this.tridentStack);
    }

    protected boolean tryPickup(PlayerEntity player) {
        return super.tryPickup(player) || this.isNoClip() && this.isOwner(player) && player.getInventory().insertStack(this.asItemStack());
    }

    protected SoundEvent getHitSound() {
        return SoundEvents.ITEM_TRIDENT_HIT_GROUND;
    }

    public void onPlayerCollision(PlayerEntity player) {
        if (this.isOwner(player) || this.getOwner() == null) {
            super.onPlayerCollision(player);
        }

    }

    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        if (nbt.contains("Trident", 10)) {
            this.tridentStack = ItemStack.fromNbt(nbt.getCompound("Trident"));
        }

        this.dealtDamage = nbt.getBoolean("DealtDamage");
        this.dataTracker.set(LOYALTY, (byte)EnchantmentHelper.getLoyalty(this.tridentStack));
    }

    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.put("Trident", this.tridentStack.writeNbt(new NbtCompound()));
        nbt.putBoolean("DealtDamage", this.dealtDamage);
    }

    public void age() {
        int i = this.dataTracker.get(LOYALTY);
        if (this.pickupType != PickupPermission.ALLOWED || i <= 0) {
            super.age();
        }

    }

    protected float getDragInWater() {
        return 1.0F;
    }

    public boolean shouldRender(double cameraX, double cameraY, double cameraZ) {
        return true;
    }

    static {
        LOYALTY = DataTracker.registerData(DraconicTridentEntity.class, TrackedDataHandlerRegistry.BYTE);
        ENCHANTED = DataTracker.registerData(DraconicTridentEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
        TRIDENT = DataTracker.registerData(DraconicTridentEntity.class,TrackedDataHandlerRegistry.ITEM_STACK);
    }
}
