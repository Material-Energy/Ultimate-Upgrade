package materialenergy.ultimate.upgrade.effects;

import materialenergy.ultimate.upgrade.registry.UUDamageSource;
import materialenergy.ultimate.upgrade.registry.UUDamageSource;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.sound.SoundEvents;

import java.util.Objects;

public class FireWeakness extends StatusEffect {
    public FireWeakness() {
        super(StatusEffectType.HARMFUL, 14981690);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        // In our case, we just make it return true so that it applies the status effect every tick.
        return true;
    }

    // This method is called when it applies the status effect. We implement custom functionality here.
    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (entity.getDamageTracker().hasDamage()) {
            if (Objects.requireNonNull(entity.getDamageTracker().getMostRecentDamage()).getDamageSource().isFire() &&
                            entity.getDamageTracker().getTimeSinceLastAttack() <= 20) {
                entity.hurtTime = 0;
                entity.damage(UUDamageSource.INCINERATE, (float) (amplifier + 1));
                entity.playSound(SoundEvents.ENTITY_PLAYER_HURT_ON_FIRE, amplifier + 1, 1.0F);
            }
        }
    }
}
