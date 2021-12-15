package materialenergy.ultimate.upgrade.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.enchantment.LureEnchantment;
import net.minecraft.entity.EquipmentSlot;

public class LFEnchantment extends LureEnchantment {

    public LFEnchantment(Rarity rarity, EnchantmentTarget enchantmentTarget, EquipmentSlot... equipmentSlots) {
        super(rarity, enchantmentTarget, equipmentSlots);
    }
}
