package materialenergy.ultimate.upgrade.effects;

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
}
