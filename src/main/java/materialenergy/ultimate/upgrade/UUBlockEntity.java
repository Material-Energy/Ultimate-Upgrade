package materialenergy.ultimate.upgrade;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;

public class UUBlockEntity extends BlockEntity {
    public UUBlockEntity(BlockPos pos, BlockState state) {
        super(UltimateUpgrade.UU_BLOCK_ENTITY, pos, state);
    }
}