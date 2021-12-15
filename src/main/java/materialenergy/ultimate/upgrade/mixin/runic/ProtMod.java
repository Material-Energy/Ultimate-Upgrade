package materialenergy.ultimate.upgrade.mixin.runic;

import net.minecraft.entity.DamageUtil;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(DamageUtil.class)
public class ProtMod {

    /**
     * @author MaterialEnergy
     * @reason Increase clamp
     */
    @Overwrite
    public static float getInflictedDamage(float damageDealt, float protection) {
        float f = MathHelper.clamp(protection, 0.0f, 25.0f);
        return damageDealt * (1.0f - f / 25.0f);
    }
}
