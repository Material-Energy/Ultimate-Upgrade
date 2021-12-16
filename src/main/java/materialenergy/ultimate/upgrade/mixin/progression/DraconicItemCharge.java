package materialenergy.ultimate.upgrade.mixin.progression;

import materialenergy.ultimate.upgrade.item.DraconicBaseItem;
import materialenergy.ultimate.upgrade.item.DraconicTrident;
import materialenergy.ultimate.upgrade.mixin.api.BeaconLevelGrabber;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BeaconBlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.UUID;

@Mixin(ItemEntity.class)
public abstract class DraconicItemCharge extends Entity {


    private int transformationDelay = 0;

    @Shadow public @Nullable abstract UUID getThrower();

    @Shadow public abstract ItemStack getStack();

    @Shadow protected abstract MoveEffect getMoveEffect();

    public DraconicItemCharge(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(
            method = "tick",
            at = @At("HEAD")
    )
    public void chargeViaBeacon(CallbackInfo ci){
        Item item = this.getStack().getItem();
        if (this.getEntityWorld().getRegistryKey().equals(World.END) &&
                item instanceof DraconicBaseItem ||
                item instanceof DraconicTrident){
            Vec3d pos = this.getPos();
            BlockState block = this.world.getBlockState(new BlockPos(pos).add(0,-1,0));
            BeaconBlockEntity blockEntity = (BeaconBlockEntity) this.world.getBlockEntity(new BlockPos(pos).add(0,-1,0));
            boolean beacon = block.isOf(Blocks.BEACON);
            boolean activated = false;
            if (blockEntity != null) {
                activated = ((BeaconLevelGrabber) blockEntity).getLevel() > 0;
            }
            if (beacon && activated){
                PlayerEntity entity = this.world.getClosestPlayer(this, 10);
                if (entity != null) {
                    int levels = entity.experienceLevel;
                    if (levels >= 15){
                        byte maxed = (byte) (DraconicBaseItem.getEnderEnergy(this.getStack()) + 1);
                        if (this.transformationDelay == 20 && maxed <= 64){
                            this.world.playSound(entity, new BlockPos(pos), SoundEvents.BLOCK_BEACON_ACTIVATE, SoundCategory.BLOCKS, 1.0f, 1.0f);
                            DraconicBaseItem.writeEnderEnergy(this.getStack(), maxed);
                            entity.addExperienceLevels(-1);
                            double d = pos.getX() + 0.55 - (double)(random.nextFloat() * 0.1f);
                            double e = pos.getY() + 0.55 - (double)(random.nextFloat() * 0.1f);
                            double f = pos.getZ() + 0.55 - (double)(random.nextFloat() * 0.1f);
                            double g = 0.4f - (random.nextFloat() + random.nextFloat()) * 0.4f;
                            world.addParticle(ParticleTypes.END_ROD, d + g, e + g, f + g, random.nextGaussian() * 0.005, random.nextGaussian() * 0.005, random.nextGaussian() * 0.005);
                            this.transformationDelay = 0;
                        }
                        this.transformationDelay++;
                    }
                }
            }
        }
    }
}
