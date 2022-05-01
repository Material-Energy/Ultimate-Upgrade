package materialenergy.ultimate.upgrade.common.api;

import net.minecraft.enchantment.Enchantment;

public interface Rune {
    void set(Enchantment enchantment, int level);
    Enchantment getVanilla();
}
