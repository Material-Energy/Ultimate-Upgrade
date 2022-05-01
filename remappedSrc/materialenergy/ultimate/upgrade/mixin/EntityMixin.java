package materialenergy.ultimate.upgrade.mixin;

import materialenergy.ultimate.upgrade.common.api.IMultiplier;
import materialenergy.ultimate.upgrade.common.registry.UUEffects;
import materialenergy.ultimate.upgrade.common.registry.UUMisc;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityMixin implements IMultiplier {

    @Shadow private Vec3d velocity;

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
            return instance.damage(UUMisc.INCINERATE, amount + effectInstance.getAmplifier());
        } else {
            return instance.damage(source, amount);
        }
    }

    @Inject(
            method = "getVelocity",
            at = @At("HEAD"),
            cancellable = true
    )
    public void addVelocityMultiplier(CallbackInfoReturnable<Vec3d> cir){
        cir.setReturnValue(this.velocity.multiply(this.velocityMultiplier));
        cir.cancel();
    }

    @Inject(
            method = "setVelocity(Lnet/minecraft/util/math/Vec3d;)V",
            at = @At("HEAD")
    )
    public void setWithMultiplierVec3d(Vec3d velocity, CallbackInfo ci){
        this.velocity = velocity.multiply(1/this.velocityMultiplier);
    }
}
