package materialenergy.ultimate.upgrade.entities;

import materialenergy.ultimate.upgrade.registry.UUDamageSource;
import materialenergy.ultimate.upgrade.registry.UUEffects;
import materialenergy.ultimate.upgrade.registry.UUEntities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class MoltenProj extends PersistentProjectileEntity {
    private static final TrackedData<Boolean> SUPERCHARGED;

    public MoltenProj(EntityType<? extends MoltenProj> entityType, World world) {
        super(entityType, world);
    }

    public MoltenProj(World world, LivingEntity shooter) {
        super(UUEntities.MOLTEN_ARROW,shooter, world);
        this.setSupercharged(false);
    }

    public void setSupercharged(boolean bool){
        this.dataTracker.set(SUPERCHARGED, bool);
    }

    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(SUPERCHARGED, false);
    }

    public boolean getSupercharged() {
        return this.dataTracker.get(SUPERCHARGED);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.world.isClient) {
            if (this.inGround) {
                if (this.inGroundTime % 5 == 0) {
                    this.spawnParticles();
                }
            } else {
                Vec3d vec3d = this.getVelocity();
                double box = vec3d.x;
                double entity2 = vec3d.y;
                double e = vec3d.z;
                if (this.getSupercharged()) {
                    for(int i = 0; i < 4; ++i) {
                        this.world.addParticle(!this.getSupercharged() ? ParticleTypes.FLAME : ParticleTypes.SOUL_FIRE_FLAME, this.getX() + box * (double)i / 4.0D, this.getY() + entity2 * (double)i / 4.0D, this.getZ() + e * (double)i / 4.0D, -box, -entity2 + 0.2D, -e);
                    }
                }
            }
        } else if (this.inGround && this.inGroundTime != 0 && this.inGroundTime >= 600) {
            this.world.sendEntityStatus(this, (byte)0);
        }
    }

    private void spawnParticles() {
        for(int j = 0; j < 1; ++j) {
            world.addParticle(!this.getSupercharged() ? ParticleTypes.FLAME : ParticleTypes.SOUL_FIRE_FLAME, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
        }

    }

    public void handleStatus(byte status) {
        if (status == 0) {
            for(int j = 0; j < 20; ++j) {
                world.addParticle(!this.getSupercharged() ? ParticleTypes.FLAME : ParticleTypes.SOUL_FIRE_FLAME, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
            }
        } else {
            super.handleStatus(status);
        }

    }

    @Override
    protected void onHit(LivingEntity target) {
        if (this.getSupercharged()) {
            target.damage(UUDamageSource.INCINERATE, (float) this.getDamage() * 2);
        }
        super.onHit(target);
        target.setOnFireFor(5 * (this.getSupercharged() ? 2 : 1));
        target.addStatusEffect(new StatusEffectInstance(UUEffects.FIRE_WEAKNESS, 100, !this.getSupercharged() ? 0:2));
    }

    @Override
    protected ItemStack asItemStack() {
        return new ItemStack(Items.ARROW);
    }

    static {
        SUPERCHARGED = DataTracker.registerData(ArrowEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    }
}
