package materialenergy.ultimate.upgrade.misc;

import net.minecraft.entity.damage.DamageSource;

public class UUDamageSource extends DamageSource {
    public boolean bypassesArmor;
    public float exhaustion;
    public boolean fire;

    public UUDamageSource(String name) {
        super(name);
    }
    
    @Override
    public UUDamageSource setBypassesArmor() {
        this.bypassesArmor = true;
        this.exhaustion = 0.0F;
        return this;
    }
    
    @Override
    public UUDamageSource setFire() {
        this.fire = true;
        return this;
    }
}
