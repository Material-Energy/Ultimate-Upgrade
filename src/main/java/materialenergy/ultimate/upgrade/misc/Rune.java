package materialenergy.ultimate.upgrade.misc;

import net.minecraft.enchantment.Enchantment;

public interface Rune {
    public void set(Enchantment enchantment);
    public Enchantment getVanilla();
    public boolean getRunic();
    
}
