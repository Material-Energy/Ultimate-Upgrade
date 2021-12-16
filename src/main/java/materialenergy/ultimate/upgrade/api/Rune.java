package materialenergy.ultimate.upgrade.api;

import net.minecraft.enchantment.Enchantment;

public interface Rune {
    void set(Enchantment enchantment);
    Enchantment getVanilla();
    boolean getRunic();
    
}
