package materialenergy.ultimate.upgrade.mixin;

import materialenergy.ultimate.upgrade.common.item.draconic.DraconicCrossbowItem;
import materialenergy.ultimate.upgrade.common.item.draconic.DraconicTotemItem;
import materialenergy.ultimate.upgrade.common.item.draconic.DraconicTridentItem;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Enchantment.class)
public class EnchantmentMixin {
    @Shadow
    @Final
    public EnchantmentTarget type;

    @Inject(method = "isAcceptableItem", at = @At(value = "RETURN"), cancellable = true)
    public void isAcceptableItem(ItemStack itemStack, CallbackInfoReturnable<Boolean> cir) {
        if (!cir.getReturnValue() && itemStack.getItem() instanceof DraconicTridentItem && this.type == EnchantmentTarget.TRIDENT) {
            cir.setReturnValue(true);
        }
        if (!cir.getReturnValue() && itemStack.getItem() instanceof DraconicCrossbowItem && this.type == EnchantmentTarget.CROSSBOW) {
            cir.setReturnValue(true);
        }
        if (!cir.getReturnValue() && itemStack.getItem() instanceof DraconicTotemItem && this.type == EnchantmentTarget.BREAKABLE) {
            cir.setReturnValue(true);
        }
    }
}