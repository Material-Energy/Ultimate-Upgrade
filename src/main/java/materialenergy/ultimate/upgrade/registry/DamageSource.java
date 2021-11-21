package materialenergy.ultimate.upgrade.registry;

import materialenergy.ultimate.upgrade.misc.UUDamageSource;

public class DamageSource {
    public static final net.minecraft.entity.damage.DamageSource INCINERATE = new UUDamageSource("incinerate").setBypassesArmor().setFire();

    public static final net.minecraft.entity.damage.DamageSource SOUL_BURN = new UUDamageSource("soul_burn").setBypassesArmor().setFire();

}
