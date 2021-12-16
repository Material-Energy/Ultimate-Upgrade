package materialenergy.ultimate.upgrade.registry;

import materialenergy.ultimate.upgrade.misc.CustomDamageSource;
import net.minecraft.entity.damage.DamageSource;

public class UUDamageSource {
    public static final DamageSource INCINERATE = new CustomDamageSource("incinerate").setBypassesArmor().setFire();
    public static final DamageSource VOID = new CustomDamageSource("void").setBypassesArmor().setOutOfWorld();
}
