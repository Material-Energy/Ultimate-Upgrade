package materialenergy.ultimate.upgrade.mixin.hearts;

import net.minecraft.entity.attribute.EntityAttribute;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityAttribute.class)
public class ModifyFallback {

    @Mutable
    @Shadow @Final private double fallback;

    @Inject(method = "<init>", at = @At("TAIL"))
    public void init(String translationKey, double fallback, CallbackInfo ci){
        if (translationKey.equals("attribute.name.generic.max_health")){
            this.fallback = 100.0;
        }
    }
}
