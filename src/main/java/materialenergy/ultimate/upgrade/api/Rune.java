package materialenergy.ultimate.upgrade.api;

import net.minecraft.enchantment.Enchantment;

public interface Rune {
    void set(Enchantment enchantment, int level);
    Enchantment getVanilla();
}
