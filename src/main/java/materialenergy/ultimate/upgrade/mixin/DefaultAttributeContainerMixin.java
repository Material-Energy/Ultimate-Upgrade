package materialenergy.ultimate.upgrade.mixin;

import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DefaultAttributeContainer.Builder.class)
public abstract class DefaultAttributeContainerMixin {

    @Inject(
            method = "add(Lnet/minecraft/entity/attribute/EntityAttribute;D)Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;",
            at = @At("HEAD"),
            cancellable = true
    )
    public void add(EntityAttribute attribute, double baseValue, CallbackInfoReturnable<DefaultAttributeContainer.Builder> cir){
        EntityAttributeInstance entityAttributeInstance = this.checkedAdd(attribute);
        if (attribute.getTranslationKey().equals("attribute.name.generic.max_health")){
            entityAttributeInstance.setBaseValue(baseValue * 5);
        } else {
            entityAttributeInstance.setBaseValue(baseValue);
        }
        cir.setReturnValue((DefaultAttributeContainer.Builder) (Object) this);
    }

    @Shadow protected abstract EntityAttributeInstance checkedAdd(EntityAttribute attribute);

}
