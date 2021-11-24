package materialenergy.ultimate.upgrade.misc;

import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.ProjectileDamageSource;
import org.jetbrains.annotations.Nullable;

public class CustomDamageSource extends DamageSource {
    public boolean bypassesArmor;
    public float exhaustion;
    public boolean fire;

    public CustomDamageSource(String name) {
        super(name);
    }
    
    @Override
    public CustomDamageSource setBypassesArmor() {
        this.bypassesArmor = true;
        this.exhaustion = 0.0F;
        return this;
    }
    
    @Override
    public CustomDamageSource setFire() {
        this.fire = true;
        return this;
    }

    public static DamageSource trident(Entity trident, @Nullable Entity attacker) {
        return (new ProjectileDamageSource("draconic_trident", trident, attacker)).setProjectile();
    }
}
