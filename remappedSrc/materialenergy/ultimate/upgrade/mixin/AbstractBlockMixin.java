package materialenergy.ultimate.upgrade.mixin;

import materialenergy.ultimate.upgrade.common.registry.UUItems;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(AbstractBlock.class)
public class AbstractBlockMixin {

    /**
     * @author MaterialEnergy
     * @reason modify block breaking
     */
    @Deprecated
    @Overwrite
    public float calcBlockBreakingDelta(BlockState state, PlayerEntity player, BlockView world, BlockPos pos) {
        float f = state.getHardness(world, pos);
        if (f == -1.0f && !player.getMainHandStack().getItem().equals(UUItems.ULTIMATUM)) {
            return 0.0f;
        } else if (f == -1.0f && player.getMainHandStack().getItem().equals(UUItems.ULTIMATUM)){
            player.world.spawnEntity(new ItemEntity(
                    player.world,
                    pos.getX(),
                    pos.getY(),
                    pos.getZ(),
                    new ItemStack(state.getBlock().asItem())
            ));
            return 1.0f;
        } else {
            int i = player.canHarvest(state) ? 30 : 100;
            return player.getBlockBreakingSpeed(state) / f / (float) i;
        }
    }
}
