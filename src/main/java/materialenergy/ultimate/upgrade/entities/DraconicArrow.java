package materialenergy.ultimate.upgrade.entities;

import materialenergy.ultimate.upgrade.registry.UUEffects;
import materialenergy.ultimate.upgrade.registry.UUEntities;
import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

public class DraconicArrow extends PersistentProjectileEntity {


    public int energy;

    public DraconicArrow(EntityType<? extends DraconicArrow> entityType, World world) {
        super(entityType, world);
    }

    public DraconicArrow(double x, double y, double z, World world) {
        super(UUEntities.DRACONIC_FLARE, x, y, z, world);
        this.setNoGravity(true);
    }

    public DraconicArrow(LivingEntity owner, World world, int energy) {
        super(UUEntities.DRACONIC_FLARE, owner, world);
        this.setNoGravity(true);
        this.energy = energy;
    }

    @Override
    public void tick() {
        Vec3d velocity = this.getVelocity();
        super.tick();
        this.setVelocity(velocity);
        double velocityX = velocity.x;
        double velocityY = velocity.y;
        double velocityZ = velocity.z;
        for (int i = 0; i < 4; ++i) {
            this.world.addParticle(ParticleTypes.DRAGON_BREATH, this.getX() + velocityX * (double)i / 4.0, this.getY() + velocityY * (double)i / 4.0, this.getZ() + velocityZ * (double)i / 4.0, 0, 0, 0);
        }
        if (this.age > 100){
            this.discard();
        }
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        HitResult.Type type = hitResult.getType();
        if (type == HitResult.Type.ENTITY) {
            this.onEntityHit((EntityHitResult)hitResult);
        } else if (type == HitResult.Type.BLOCK) {
            this.onBlockHit((BlockHitResult)hitResult);
        }
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        Entity entity = entityHitResult.getEntity();
        entity.damage(DamageSource.DRAGON_BREATH, 20.0f);
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        if (!this.world.isClient) {
            List<LivingEntity> list = this.world.getNonSpectatingEntities(LivingEntity.class, this.getBoundingBox().expand(4.0, 4.0, 4.0));
            if (!list.isEmpty()) {
                for (LivingEntity livingEntity : list) {
                    double d = this.squaredDistanceTo(livingEntity);
                    if (!(d < 16.0)) continue;
                    LightningEntity lightning = EntityType.LIGHTNING_BOLT.create(world);
                    assert lightning != null;
                    lightning.setPosition(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ());
                    this.world.spawnEntity(lightning);
                }
            } else {
                LightningEntity lightning = EntityType.LIGHTNING_BOLT.create(world);
                assert lightning != null;
                lightning.setPosition(this.getX(), this.getY(), this.getZ());
                this.world.spawnEntity(lightning);
            }
            this.discard();
        }
    }

    @Override
    protected ItemStack asItemStack() {
        return null;
    }
}
