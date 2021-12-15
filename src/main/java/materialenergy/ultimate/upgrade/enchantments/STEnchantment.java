package materialenergy.ultimate.upgrade.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.SilkTouchEnchantment;
import net.minecraft.entity.EquipmentSlot;

public class STEnchantment extends SilkTouchEnchantment {

    public STEnchantment(Rarity weight, EquipmentSlot... slotTypes) {
        super(weight, slotTypes);
    }
}
