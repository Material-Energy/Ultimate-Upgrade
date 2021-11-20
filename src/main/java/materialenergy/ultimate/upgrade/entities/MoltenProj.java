package materialenergy.ultimate.upgrade.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

public class MoltenProj extends ArrowEntity {


    public MoltenProj(EntityType entityType, World world) {
        super(entityType, world);
    }

    public MoltenProj(World world, double x, double y, double z){
        super(world,x,y,z);
    }

    public MoltenProj(World world, LivingEntity shooter) {
        super(world, shooter);
    }



    @Override
    public void tick() {
        super.tick();
        if (this.world.isClient) {
            if (this.inGround) {
                if (this.inGroundTime % 5 == 0) {
                    this.spawnParticles(1);
                }
            } else {
                this.spawnParticles(2);
            }
        } else if (this.inGround && this.inGroundTime != 0 && this.inGroundTime >= 600) {
            this.world.sendEntityStatus(this, (byte)0);
        }
    }

    private void spawnParticles(int amount) {
        int i = random.nextInt(256*256*256);
        if (amount > 0) {
            double d = (double)(i >> 16 & 255) / 255.0D;
            double e = (double)(i >> 8 & 255) / 255.0D;
            double f = (double)(i & 255) / 255.0D;

            for(int j = 0; j < amount; ++j) {
                this.world.addParticle(ParticleTypes.FLAME, this.getParticleX(0.5D), this.getRandomBodyY(), this.getParticleZ(0.5D), d, e, f);
            }

        }
    }

    @Override
    protected void onHit(LivingEntity target) {
        super.onHit(target);
        Entity entity = this.getEffectCause();

        world.createExplosion(this.getOwner(),
                target.getX(),
                target.getY(),
                target.getZ(),
                (float)this.getDamage()/2,
                true,
                Explosion.DestructionType.DESTROY);
    }
}
