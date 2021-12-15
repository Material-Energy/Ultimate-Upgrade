package materialenergy.ultimate.upgrade.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.UnbreakingEnchantment;
import net.minecraft.entity.EquipmentSlot;

public class UEnchantment extends UnbreakingEnchantment {

    public UEnchantment(Rarity weight, EquipmentSlot... slotTypes) {
        super(weight, slotTypes);
    }
}
