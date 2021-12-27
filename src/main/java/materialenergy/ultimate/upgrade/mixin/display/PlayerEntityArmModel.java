package materialenergy.ultimate.upgrade.mixin.display;

import materialenergy.ultimate.upgrade.registry.UUItems;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntityRenderer.class)
public class PlayerEntityArmModel {
    @Inject(
            method = "getArmPose",
            at = @At("HEAD"),
            cancellable = true
    )
    private static void addCrossbowModel(AbstractClientPlayerEntity player, Hand hand, CallbackInfoReturnable<BipedEntityModel.ArmPose> cir){
        ItemStack itemStack = player.getStackInHand(hand);
        if (itemStack.isOf(UUItems.DRACONIC_CROSSBOW) &&
                !player.handSwinging && CrossbowItem.isCharged(itemStack)
        )
            cir.setReturnValue(BipedEntityModel.ArmPose.CROSSBOW_HOLD);
    }
}
