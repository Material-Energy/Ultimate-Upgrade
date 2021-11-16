package materialenergy.ultimate.upgrade.mixin;

import net.minecraft.item.Item;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public abstract class IronFireproof {

    @Shadow @Final
    private boolean fireproof;

    @Shadow public abstract Item asItem();

    @Inject(method = "isFireproof", at = @At("HEAD"), cancellable = true)
    private void FireproofIron(CallbackInfoReturnable<Boolean> cir){
        cir.setReturnValue(this.fireproof || this.asItem().equals(Items.IRON_INGOT));
    }
}
