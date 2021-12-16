package materialenergy.ultimate.upgrade.mixin.progression;

import materialenergy.ultimate.upgrade.registry.UUItems;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientPlayNetworkHandler.class)
public class DraconicTotemDisplay {

    @Inject(
            method = "getActiveTotemOfUndying",
            at = @At("HEAD"),
            cancellable = true
    )
    private static void modifyGetActive(PlayerEntity player, CallbackInfoReturnable<ItemStack> cir) {
        for (Hand hand : Hand.values()) {
            ItemStack itemStack = player.getStackInHand(hand);
            if (!itemStack.isOf(UUItems.DRACONIC_TOTEM)) continue;
            cir.setReturnValue(itemStack);
        }
    }
}
