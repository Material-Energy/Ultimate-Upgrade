package materialenergy.ultimate.upgrade.common.entities;

import materialenergy.ultimate.upgrade.common.api.IMultiplier;
import materialenergy.ultimate.upgrade.common.registry.UUEntities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

public class MoltenProjectileEntity extends PersistentProjectileEntity implements IMultiplier {
    private int timeUntilNextClusterBonus = 0;
    private int recursives = 0;
    public double velocityMultiplier = 0.5;
    private Vec3d velocity;

    public MoltenProjectileEntity(EntityType<? extends MoltenProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    public MoltenProjectileEntity(World world, LivingEntity shooter) {
        super(UUEntities.MOLTEN_ARROW,shooter, world);
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
                for(int i = 0; i < 4; ++i) {
                    this.world.addParticle(ParticleTypes.FLAME, this.getX() + box * (double)i / 4.0D, this.getY() + entity2 * (double)i / 4.0D, this.getZ() + e * (double)i / 4.0D, -box, -entity2 + 0.2D, -e);
                }
            }
        } else if (this.getVelocity().y < 0) {
            this.timeUntilNextClusterBonus++;
            if (this.timeUntilNextClusterBonus > 20 && this.recursives < 5){
                this.timeUntilNextClusterBonus = 0;
                this.recursives++;
            }
        }
    }

    private void spawnParticles() {
        for(int j = 0; j < 1; ++j) {
            world.addParticle(ParticleTypes.FLAME, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
        }

    }


    @Override
    protected void onHit(LivingEntity target) {
        Entity entity = this.getEffectCause();
        entity.damage(DamageSource.mobProjectile(this, (LivingEntity) entity), (float) this.getDamage());
        if (!this.world.isClient) {
            this.discard();
        }
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        world.createExplosion(this.getEffectCause(), this.getX(), this.getY(), this.getZ(), 2.0f, Explosion.DestructionType.DESTROY);
        if (!this.world.isClient) {
            this.discard();
        }
    }

    @Override
    protected ItemStack asItemStack() {
        return new ItemStack(Items.ARROW);
    }

    @Override
    public Vec3d getVelocity() {
        return this.velocity.multiply(this.velocityMultiplier);
    }

    @Override
    public void setVelocity(Vec3d velocity) {
        this.velocity = velocity.multiply(1/this.velocityMultiplier);
    }
}
