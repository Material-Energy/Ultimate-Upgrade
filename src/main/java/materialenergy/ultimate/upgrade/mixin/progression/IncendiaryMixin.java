package materialenergy.ultimate.upgrade.mixin.progression;

import materialenergy.ultimate.upgrade.registry.UUDamageSource;
import materialenergy.ultimate.upgrade.registry.UUEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Entity.class)
public abstract class IncendiaryMixin {

    @Redirect(
            method = "baseTick",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;damage(Lnet/minecraft/entity/damage/DamageSource;F)Z")
    )
    public boolean modifyFireDamage(Entity instance, DamageSource source, float amount){
        StatusEffectInstance effectInstance = null;
        if (instance instanceof LivingEntity) {
             effectInstance = ((LivingEntity) instance).getStatusEffect(UUEffects.FIRE_WEAKNESS);
        }
        if (effectInstance != null){
            return instance.damage(UUDamageSource.INCINERATE, amount + effectInstance.getAmplifier());
        } else {
            return instance.damage(source, amount);
        }
    }
}
