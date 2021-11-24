package materialenergy.ultimate.upgrade.registry;

import materialenergy.ultimate.upgrade.misc.CustomDamageSource;

public class UUDamageSource {
    public static final net.minecraft.entity.damage.DamageSource INCINERATE = new CustomDamageSource("incinerate").setBypassesArmor().setFire();

    public static final net.minecraft.entity.damage.DamageSource SOUL_BURN = new CustomDamageSource("soul_burn").setBypassesArmor().setFire();

}
