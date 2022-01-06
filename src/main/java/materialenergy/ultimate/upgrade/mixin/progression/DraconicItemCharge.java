package materialenergy.ultimate.upgrade.mixin.progression;

import materialenergy.ultimate.upgrade.api.UUtil;
import materialenergy.ultimate.upgrade.item.draconic.DraconicBaseItem;
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
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemEntity.class)
public abstract class DraconicItemCharge extends Entity {


    private int transformationDelay = 0;

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
                item instanceof DraconicBaseItem){
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
                        int maxed = (byte) (DraconicBaseItem.getEnderEnergy(this.getStack()) + 1);
                        if (this.transformationDelay == 20 && maxed <= ((DraconicBaseItem) this.getStack().getItem()).getTotalEnergy()){
                            this.world.playSound(entity, new BlockPos(pos), SoundEvents.BLOCK_BEACON_ACTIVATE, SoundCategory.BLOCKS, 1.0f, 1.0f);
                            DraconicBaseItem.writeEnderEnergy(this.getStack(), (short) maxed);
                            for (int i = 0; i < 20; i++){
                                double d = new BlockPos(pos).getX() + random.nextDouble();
                                double e = new BlockPos(pos).getY() + 0.8;
                                double f = new BlockPos(pos).getZ() + random.nextDouble();
                                world.addParticle(ParticleTypes.END_ROD, d, e, f, 0.0, 0.5, 0.0);
                            }
                            this.transformationDelay = 0;
                        }
                        this.transformationDelay++;
                    }
                }
                double d = new BlockPos(pos).getX() + random.nextDouble();
                double e = new BlockPos(pos).getY() + 0.8;
                double f = new BlockPos(pos).getZ() + random.nextDouble();
                world.addParticle(ParticleTypes.END_ROD, d, e, f, 0.0, 0.4, 0.0);
            }
        }
        if (this.world.getBlockState(this.getBlockPos().add(0,-1,0)).isOf(Blocks.BEACON)){
            int x = this.getBlockX();
            int z = this.getBlockZ();
            if (UUtil.compareVector(this.getVelocity(), new Vec3d(0.5, 0.5, 0.5), true)){
                this.setPos(x + 0.5, this.getPos().y, z + 0.5);
            }
        }
    }
}
