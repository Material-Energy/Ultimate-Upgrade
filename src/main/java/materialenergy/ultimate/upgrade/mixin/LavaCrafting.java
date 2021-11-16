package materialenergy.ultimate.upgrade.mixin;

import materialenergy.ultimate.upgrade.UltimateUpgrade;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemEntity.class)
public abstract class LavaCrafting extends Entity {


    private int timeInLava;

    public LavaCrafting(EntityType<?> type, World world) {
        super(type, world);
    }

    @Shadow
    public abstract ItemStack getStack();


    @Inject(at = @At("HEAD"), method = "tick()V")
    private void tick(CallbackInfo info) {
        if(this.isInLava()){
            this.timeInLava++;
            double d = this.getPos().getX() + random.nextDouble();
            double e = this.getPos().getY() + 1.0D;
            double f = this.getPos().getZ() + random.nextDouble();
            world.addParticle(ParticleTypes.LAVA, d, e, f, 0.0D, 0.0D, 0.0D);
        }
        if(this.getStack().getItem() == Items.IRON_INGOT && this.timeInLava >= 50) {
            double d = this.getPos().getX() + random.nextDouble();
            double e = this.getPos().getY() + 1.0D;
            double f = this.getPos().getZ() + random.nextDouble();
            world.playSound(d, e, f, SoundEvents.BLOCK_LAVA_EXTINGUISH, SoundCategory.BLOCKS, 0.2F + random.nextFloat() * 0.2F, 0.9F + random.nextFloat() * 0.15F, false);
            ItemStack item = new ItemStack(UltimateUpgrade.MOLTEN_INGOT);
            item.setCount(this.getStack().getCount());
            world.spawnEntity(new ItemEntity(world, this.getX(), this.getY(), this.getZ(), item));
            this.discard();
        }
    }
}