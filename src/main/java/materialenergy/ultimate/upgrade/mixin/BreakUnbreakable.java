package materialenergy.ultimate.upgrade.mixin;

import materialenergy.ultimate.upgrade.UltimateUpgrade;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(AbstractBlock.class)
public class BreakUnbreakable {
    @ModifyConstant(method = "calcBlockBreakingDelta", constant = @Constant())
    private float BreakBedrock(float num, BlockState state, PlayerEntity player){
        if(player.getMainHandStack().getItem() == UltimateUpgrade.ULTIMATUM) {
            return 1.0F;
        }else{
            return 0.0F;
        }
    }
}
