package materialenergy.ultimate.upgrade.effects;

import materialenergy.ultimate.upgrade.UltimateUpgrade;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;

public class FireWeakness extends StatusEffect {
    public FireWeakness() {
        super(StatusEffectType.BENEFICIAL, 14981690);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        // In our case, we just make it return true so that it applies the status effect every tick.
        return duration % 5 == 0;
    }

    // This method is called when it applies the status effect. We implement custom functionality here.
    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (entity.isOnFire()) {
           entity.damage(UltimateUpgrade.INCINERATE,(float)(amplifier+1));
        }
    }
}
