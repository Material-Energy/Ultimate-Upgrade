package materialenergy.ultimate.upgrade.enchantments;

import net.minecraft.enchantment.KnockbackEnchantment;
import net.minecraft.entity.EquipmentSlot;

public class KBEnchantment extends KnockbackEnchantment {
    public KBEnchantment(Rarity weight, EquipmentSlot... slot) {
        super(weight, slot);
    }
}
