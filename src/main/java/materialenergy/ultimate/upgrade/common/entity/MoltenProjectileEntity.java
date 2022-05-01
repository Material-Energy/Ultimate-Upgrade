package materialenergy.ultimate.upgrade.common.entity;

import materialenergy.ultimate.upgrade.common.Registries;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;

import java.util.ArrayList;

public class MoltenProjectileEntity extends PersistentProjectileEntity {
    private int recursive = 0;
    private int recursiveTick = 0;

    public MoltenProjectileEntity(EntityType<? extends MoltenProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    public MoltenProjectileEntity(LivingEntity owner, World world) {
        super(Registries.MOLTEN_PROJECTILE_ENTITY_ENTITY_TYPE, owner, world);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.getVelocity().getY() < 0){
            this.recursiveTick++;
            if (recursiveTick > 40 && this.recursive < 4){
                this.recursive++;
                this.recursiveTick = 0;
            }
        }
    }

    protected void onEntityHit(EntityHitResult entityHitResult) { // called on entity hit.
        Entity entity = entityHitResult.getEntity(); // sets a new Entity instance as the EntityHitResult (victim)
        World world = entity.getWorld();
        recursiveHit(world, entity.getBlockPos(), this.recursive);
        this.discard();
    }

    private void recursiveHit(World world, BlockPos pos, int recursive){
        if (recursive < 0) return;
        ArrayList<Entity> entities = (ArrayList<Entity>) world.getOtherEntities(null,
                new Box(
                        pos.subtract(
                                new Vec3i(1,0,1)
                        ),
                        pos.add(
                                new Vec3i(1,0,1)
                        )));
        for (Entity entity1: entities){
            entity1.damage(DamageSource.explosion((LivingEntity) this.getOwner()), 10f);
            world.addParticle((ParticleEffect) Registries.MOLTEN_EXPLOSION, entity1.getX(), entity1.getY(), entity1.getZ(), 0, 0, 0);
            recursiveHit(world, entity1.getBlockPos(), recursive - 1);
        }
    }

    @Override
    protected ItemStack asItemStack() {
        return null;
    }

    protected void onCollision(HitResult hitResult) { // called on collision with a block
        super.onCollision(hitResult);
        if (!this.world.isClient) { // checks if the world is client
            this.kill(); // kills the projectile
        }
    }
}
