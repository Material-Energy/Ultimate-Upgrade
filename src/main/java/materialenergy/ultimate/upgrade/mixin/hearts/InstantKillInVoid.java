package materialenergy.ultimate.upgrade.mixin.hearts;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class InstantKillInVoid {

    @Shadow public abstract boolean damage(DamageSource source, float amount);

    @Shadow public abstract float getHealth();

    @Inject(
            method = "tickInVoid",
            at = @At("HEAD"),
            cancellable = true)
    public void killInVoid(CallbackInfo ci){
        this.damage(DamageSource.OUT_OF_WORLD, this.getHealth());
        ci.cancel();
    }
}
