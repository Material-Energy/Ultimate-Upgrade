package materialenergy.ultimate.upgrade.mixin.runic;

import materialenergy.ultimate.upgrade.api.Rune;
import net.minecraft.enchantment.ProtectionEnchantment;
import net.minecraft.entity.damage.DamageSource;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(ProtectionEnchantment.class)
public abstract class ProtectionRunic{


    @Final
    @Shadow
    public ProtectionEnchantment.Type protectionType;

    @Inject(
            method = "getProtectionAmount",
            at = @At("HEAD"),
            cancellable = true
    )
    public void modifyProtectionAmount(int level, DamageSource source, CallbackInfoReturnable<Integer> cir){
        if(((Rune) this).getRunic()){
            Random random = new Random();
            if(this.protectionType == ProtectionEnchantment.Type.ALL){
                if(random.nextInt((int) (16 - 2 * Math.pow(level, 2))) == 0){
                    cir.setReturnValue(level * 60);
                }
            } else if (this.protectionType == ProtectionEnchantment.Type.EXPLOSION && source.isExplosive()) {
                if(random.nextInt((int) (8 - 2 * Math.pow(level, 2))) == 0){
                    cir.setReturnValue(level * 60);
                }
            } else if (this.protectionType == ProtectionEnchantment.Type.FALL && source.isFromFalling()) {
                if(random.nextInt((int) (8 - 2 * Math.pow(level, 2))) == 0){
                    cir.setReturnValue(level * 60);
                }
            } else if (this.protectionType == ProtectionEnchantment.Type.FIRE && source.isFire()) {
                if(random.nextInt((int) (8 - 2 * Math.pow(level, 2))) == 0){
                    cir.setReturnValue(level * 60);
                }
            } else if (this.protectionType == ProtectionEnchantment.Type.PROJECTILE && source.isProjectile()) {
                if(random.nextInt((int) (8 - 2 * Math.pow(level,2))) == 0){
                    cir.setReturnValue(level * 60);
                }
            }
        }
    }

}
