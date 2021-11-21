package materialenergy.ultimate.upgrade.misc;

import net.minecraft.entity.damage.DamageSource;

public class IncinerateDamageSource extends DamageSource {
    private boolean bypassesArmor;
    private float exhaustion;
    private boolean fire;

    public IncinerateDamageSource(String name) {
        super(name);
    }
    
    @Override
    public IncinerateDamageSource setBypassesArmor() {
        this.bypassesArmor = true;
        this.exhaustion = 0.0F;
        return this;
    }
    
    @Override
    public IncinerateDamageSource setFire() {
        this.fire = true;
        return this;
    }
}
