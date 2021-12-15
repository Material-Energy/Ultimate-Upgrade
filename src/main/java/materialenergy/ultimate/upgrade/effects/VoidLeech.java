package materialenergy.ultimate.upgrade.effects;

import materialenergy.ultimate.upgrade.registry.UUDamageSource;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;

public class VoidLeech extends StatusEffect {

    public VoidLeech() {
        super(StatusEffectType.HARMFUL, 14981690);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return duration % (10 - amplifier > 0 ? 10 - amplifier : 1) == 0;
    }

    // This method is called when it applies the status effect. We implement custom functionality here.
    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        entity.damage(DamageSource.OUT_OF_WORLD, (float) (amplifier + 1));
    }
}
