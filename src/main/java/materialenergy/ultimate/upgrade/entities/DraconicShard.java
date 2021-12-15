package materialenergy.ultimate.upgrade.entities;

import materialenergy.ultimate.upgrade.registry.UUDamageSource;
import materialenergy.ultimate.upgrade.registry.UUEffects;
import materialenergy.ultimate.upgrade.registry.UUEntities;
import net.minecraft.block.HoneyBlock;
import net.minecraft.client.particle.Particle;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

import java.util.Random;

public class DraconicShard extends PersistentProjectileEntity {
    private static final TrackedData<Boolean> OVERFLOW;
    private static final Random random = new Random();
    private int IDL = 0;
    private int life;

    public DraconicShard(EntityType<? extends DraconicShard> entityType, World world) {
        super(entityType, world);
    }

    public DraconicShard(double x, double y, double z, World world, LivingEntity owner) {
        super(UUEntities.DRACONIC_SHARD, x, y, z, world);
        this.setOwner(owner);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(OVERFLOW, random.nextBoolean());
    }

    public static void explosion(double x, double y, double z, World world, LivingEntity owner, int amount, int level) {
        for (int i = 0; i < amount; i++) {
            DraconicShard arrow = new DraconicShard(x,y,z,world,owner);
            arrow.setProperties(owner, (float)random.nextInt(10000)/10000, i*2, 0.2F, (float)random.nextInt(10000)/5000, (float)random.nextInt(10000)/100);
            arrow.setPos(x,y,z);
            arrow.setPierceLevel((byte)30);
            arrow.setImpalingDamageLevel(level);
            world.spawnEntity(arrow);
        }
    }

    public void setImpalingDamageLevel(int level){
        this.IDL = level;
    }


    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        Entity entity = entityHitResult.getEntity();
        entity.damage(UUDamageSource.VOID, 10.0f + this.IDL * 2.5f);
        playSound(this.getHitSound(), this.getActivated() ? 8 : 4, 0);
        if (entity instanceof LivingEntity){
            ((LivingEntity) entity).addStatusEffect(new StatusEffectInstance(UUEffects.VOID_LEECH,20,1));
        }
    }

    public void tick(){
        super.tick();
        if (this.age >= 60 && !world.isClient()){
            this.world.sendEntityStatus(this, (byte) 3);
            this.discard();
        }
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        HitResult.Type type = hitResult.getType();
        if (type == HitResult.Type.ENTITY) {
            this.onEntityHit((EntityHitResult)hitResult);
        } else if (type != HitResult.Type.MISS){
            if (!this.world.isClient) {
                this.world.sendEntityStatus(this, (byte) 3);
                this.discard();
            }
        }
    }

    @Override
    protected SoundEvent getHitSound() {
        return SoundEvents.BLOCK_ANCIENT_DEBRIS_HIT;
    }

    @Override
    protected ItemStack asItemStack() {
        return new ItemStack(Items.AIR);
    }

    public boolean getActivated() {
        return this.dataTracker.get(OVERFLOW);
    }

    static {
        OVERFLOW = DataTracker.registerData(DraconicShard.class, TrackedDataHandlerRegistry.BOOLEAN);
    }
}
