package materialenergy.ultimate.upgrade.registry;

import materialenergy.ultimate.upgrade.enchantments.RunicEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.util.registry.Registry;

public class UUEnchantments{

    private static final EquipmentSlot[] ALL_ARMOR = new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};

    public static final Enchantment PROTECTION = registerRunic("protection", Enchantment.Rarity.COMMON, EnchantmentTarget.ARMOR, Enchantments.PROTECTION, ALL_ARMOR);
    public static final Enchantment PROJECTILE_PROTECTION = registerRunic("projectile_protection", Enchantment.Rarity.COMMON, EnchantmentTarget.ARMOR, Enchantments.PROJECTILE_PROTECTION, ALL_ARMOR);
    public static final Enchantment BLAST_PROTECTION = registerRunic("blase_protection", Enchantment.Rarity.COMMON, EnchantmentTarget.ARMOR, Enchantments.BLAST_PROTECTION, ALL_ARMOR);
    public static final Enchantment FEATHER_FALLING = registerRunic("feather_falling", Enchantment.Rarity.COMMON, EnchantmentTarget.ARMOR_FEET, Enchantments.FEATHER_FALLING, EquipmentSlot.FEET);
    public static final Enchantment FIRE_ASPECT = registerRunic("fire_aspect", Enchantment.Rarity.RARE, EnchantmentTarget.WEAPON, Enchantments.FIRE_ASPECT, 4, EquipmentSlot.MAINHAND);
    public static final Enchantment THORNS = registerRunic("thorns", Enchantment.Rarity.VERY_RARE, EnchantmentTarget.ARMOR, Enchantments.THORNS, ALL_ARMOR);
    public static final Enchantment UNBREAKING = registerRunic("unbreaking", Enchantment.Rarity.UNCOMMON, EnchantmentTarget.BREAKABLE, Enchantments.UNBREAKING, 1, EquipmentSlot.MAINHAND);

    private static final Enchantment[] ALL_RUNIC = {
            PROTECTION,
            PROJECTILE_PROTECTION,
            BLAST_PROTECTION,
            FEATHER_FALLING,
            FIRE_ASPECT,
            THORNS,
            UNBREAKING
    };


    private static Enchantment register(String name, Enchantment enchantment) {
        return Registry.register(Registry.ENCHANTMENT, Registries.id(name), enchantment);
    }

    private static Enchantment registerRunic(String name, Enchantment.Rarity rarity, EnchantmentTarget target, Enchantment defaultEnchant, EquipmentSlot... equipmentSlots) {
        return UUEnchantments.registerRunic(name, rarity, target, defaultEnchant, 5, equipmentSlots);
    }

    private static Enchantment registerRunic(String name, Enchantment.Rarity rarity, EnchantmentTarget target, Enchantment defaultEnchant, int level, EquipmentSlot... equipmentSlots) {
        RunicEnchantment enchantment = new RunicEnchantment(rarity, target, defaultEnchant, level, equipmentSlots);
        return Registry.register(Registry.ENCHANTMENT, Registries.id(String.format("runic_%s", name)), enchantment);
    }

    public static void init(){

    }

    public static Enchantment getFromId(Enchantment enchantment) {
        for (Enchantment enchantment1: ALL_RUNIC){
            if (enchantment.equals(((RunicEnchantment)enchantment1).getVanilla())){
                return enchantment1;
            }
        }
        return null;
    }
}
