package materialenergy.ultimate.upgrade.registry;

import materialenergy.ultimate.upgrade.enchantments.*;
import materialenergy.ultimate.upgrade.api.Rune;
import net.minecraft.enchantment.*;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.text.Text;
import net.minecraft.util.registry.Registry;

public class UUEnchantments{

    private static final EquipmentSlot[] ALL_ARMOR = new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};

    public static final Enchantment PROTECTION = registerRunic("protection", new ProtectionEnchantment(Enchantment.Rarity.COMMON, ProtectionEnchantment.Type.ALL, ALL_ARMOR));
    public static final Enchantment FIRE_PROTECTION = registerRunic("fire_protection", new ProtectionEnchantment(Enchantment.Rarity.UNCOMMON, ProtectionEnchantment.Type.FIRE, ALL_ARMOR));
    public static final Enchantment FEATHER_FALLING = registerRunic("feather_falling", new ProtectionEnchantment(Enchantment.Rarity.UNCOMMON, ProtectionEnchantment.Type.FALL, ALL_ARMOR));
    public static final Enchantment BLAST_PROTECTION = registerRunic("blast_protection", new ProtectionEnchantment(Enchantment.Rarity.RARE, ProtectionEnchantment.Type.EXPLOSION, ALL_ARMOR));
    public static final Enchantment PROJECTILE_PROTECTION = registerRunic("projectile_protection", new ProtectionEnchantment(Enchantment.Rarity.UNCOMMON, ProtectionEnchantment.Type.PROJECTILE, ALL_ARMOR));
    public static final Enchantment RESPIRATION = registerRunic("respiration", new RespirationEnchantment(Enchantment.Rarity.RARE, ALL_ARMOR));
    public static final Enchantment AQUA_AFFINITY = registerRunic("aqua_affinity", new AquaAffinityEnchantment(Enchantment.Rarity.RARE, ALL_ARMOR));
    public static final Enchantment THORNS = registerRunic("thorns", new ThornsEnchantment(Enchantment.Rarity.VERY_RARE, ALL_ARMOR));
    public static final Enchantment DEPTH_STRIDER = registerRunic("depth_strider", new DepthStriderEnchantment(Enchantment.Rarity.RARE, ALL_ARMOR));
    public static final Enchantment FROST_WALKER = registerRunic("frost_walker", new FrostWalkerEnchantment(Enchantment.Rarity.RARE, EquipmentSlot.FEET));
    public static final Enchantment SOUL_SPEED = registerRunic("soul_speed", new SoulSpeedEnchantment(Enchantment.Rarity.VERY_RARE, EquipmentSlot.FEET));
    public static final Enchantment SHARPNESS = registerRunic("sharpness", new DamageEnchantment(Enchantment.Rarity.COMMON, 0, EquipmentSlot.MAINHAND));
    public static final Enchantment SMITE = registerRunic("smite", new DamageEnchantment(Enchantment.Rarity.UNCOMMON, 1, EquipmentSlot.MAINHAND));
    public static final Enchantment BANE_OF_ARTHROPODS = registerRunic("bane_of_arthropods", new DamageEnchantment(Enchantment.Rarity.UNCOMMON, 2, EquipmentSlot.MAINHAND));
    public static final Enchantment KNOCKBACK = registerRunic("knockback", new KBEnchantment(Enchantment.Rarity.UNCOMMON, EquipmentSlot.MAINHAND));
    public static final Enchantment FIRE_ASPECT = registerRunic("fire_aspect", new FAEnchantment(Enchantment.Rarity.RARE, EquipmentSlot.MAINHAND));
    public static final Enchantment LOOTING = registerRunic("looting", new LEnchantment(Enchantment.Rarity.RARE, EnchantmentTarget.WEAPON, EquipmentSlot.MAINHAND));
    public static final Enchantment SWEEPING = registerRunic("sweeping", new SweepingEnchantment(Enchantment.Rarity.RARE, EquipmentSlot.MAINHAND));
    public static final Enchantment EFFICIENCY = registerRunic("efficiency", new EEnchantment(Enchantment.Rarity.COMMON, EquipmentSlot.MAINHAND));
    public static final Enchantment SILK_TOUCH = registerRunic("silk_touch", new STEnchantment(Enchantment.Rarity.VERY_RARE, EquipmentSlot.MAINHAND));
    public static final Enchantment UNBREAKING = registerRunic("unbreaking", new UEnchantment(Enchantment.Rarity.UNCOMMON, EquipmentSlot.MAINHAND));
    public static final Enchantment FORTUNE = registerRunic("fortune", new LEnchantment(Enchantment.Rarity.RARE, EnchantmentTarget.DIGGER, EquipmentSlot.MAINHAND));
    public static final Enchantment POWER = registerRunic("power", new PowerEnchantment(Enchantment.Rarity.COMMON, EquipmentSlot.MAINHAND));
    public static final Enchantment PUNCH = registerRunic("punch", new PunchEnchantment(Enchantment.Rarity.RARE, EquipmentSlot.MAINHAND));
    public static final Enchantment FLAME = registerRunic("flame", new FlameEnchantment(Enchantment.Rarity.RARE, EquipmentSlot.MAINHAND));
    public static final Enchantment INFINITY = registerRunic("infinity", new InfinityEnchantment(Enchantment.Rarity.VERY_RARE, EquipmentSlot.MAINHAND));
    public static final Enchantment LUCK_OF_THE_SEA = registerRunic("luck_of_the_sea", new LEnchantment(Enchantment.Rarity.RARE, EnchantmentTarget.FISHING_ROD, EquipmentSlot.MAINHAND));
    public static final Enchantment LURE = registerRunic("lure", new LFEnchantment(Enchantment.Rarity.RARE, EnchantmentTarget.FISHING_ROD, EquipmentSlot.MAINHAND));
    public static final Enchantment LOYALTY = registerRunic("loyalty", new LoyaltyEnchantment(Enchantment.Rarity.UNCOMMON, EquipmentSlot.MAINHAND));
    public static final Enchantment IMPALING = registerRunic("impaling", new ImpalingEnchantment(Enchantment.Rarity.RARE, EquipmentSlot.MAINHAND));
    public static final Enchantment RIPTIDE = registerRunic("riptide", new RiptideEnchantment(Enchantment.Rarity.RARE, EquipmentSlot.MAINHAND));
    public static final Enchantment CHANNELING = registerRunic("channeling", new ChannelingEnchantment(Enchantment.Rarity.VERY_RARE, EquipmentSlot.MAINHAND));
    public static final Enchantment MULTISHOT = registerRunic("multishot", new MultishotEnchantment(Enchantment.Rarity.RARE, EquipmentSlot.MAINHAND));
    public static final Enchantment QUICK_CHARGE = registerRunic("quick_charge", new QuickChargeEnchantment(Enchantment.Rarity.UNCOMMON, EquipmentSlot.MAINHAND));
    public static final Enchantment PIERCING = registerRunic("piercing", new PiercingEnchantment(Enchantment.Rarity.COMMON, EquipmentSlot.MAINHAND));
    public static final Enchantment MENDING = registerRunic("mending", new MendingEnchantment(Enchantment.Rarity.RARE, EquipmentSlot.values()));



    private static Enchantment register(String name, Enchantment enchantment) {
        return Registry.register(Registry.ENCHANTMENT, Registries.id(name), enchantment);
    }


    private static Enchantment registerRunic(String name, Enchantment enchantment) {
        ((Rune) enchantment).set(enchantment);
        return Registry.register(Registry.ENCHANTMENT, Registries.id(String.format("runic_%s", name)), enchantment);
    }

    public static void init(){

    }

    public static Enchantment getFromId(Text name) {
        if(name.equals(Enchantments.PROTECTION.getName(Enchantments.PROTECTION.getMaxLevel()))){
            return PROTECTION;
        }
        if(name.equals(Enchantments.FIRE_PROTECTION.getName(Enchantments.FIRE_PROTECTION.getMaxLevel()))){
            return FIRE_PROTECTION;
        }
        if(name.equals(Enchantments.FEATHER_FALLING.getName(Enchantments.FEATHER_FALLING.getMaxLevel()))){
            return FEATHER_FALLING;
        }
        if(name.equals(Enchantments.BLAST_PROTECTION.getName(Enchantments.BLAST_PROTECTION.getMaxLevel()))){
            return BLAST_PROTECTION;
        }
        if(name.equals(Enchantments.PROJECTILE_PROTECTION.getName(Enchantments.PROJECTILE_PROTECTION.getMaxLevel()))){
            return PROJECTILE_PROTECTION;
        }
        if(name.equals(Enchantments.RESPIRATION.getName(Enchantments.RESPIRATION.getMaxLevel()))){
            return RESPIRATION;
        }
        if(name.equals(Enchantments.AQUA_AFFINITY.getName(Enchantments.AQUA_AFFINITY.getMaxLevel()))){
            return AQUA_AFFINITY;
        }
        if(name.equals(Enchantments.THORNS.getName(Enchantments.THORNS.getMaxLevel()))){
            return THORNS;
        }
        if(name.equals(Enchantments.DEPTH_STRIDER.getName(Enchantments.DEPTH_STRIDER.getMaxLevel()))){
            return DEPTH_STRIDER;
        }
        if(name.equals(Enchantments.FROST_WALKER.getName(Enchantments.FROST_WALKER.getMaxLevel()))){
            return FROST_WALKER;
        }
        if(name.equals(Enchantments.SOUL_SPEED.getName(Enchantments.SOUL_SPEED.getMaxLevel()))){
            return SOUL_SPEED;
        }
        if(name.equals(Enchantments.SHARPNESS.getName(Enchantments.SHARPNESS.getMaxLevel()))){
            return SHARPNESS;
        }
        if(name.equals(Enchantments.SMITE.getName(Enchantments.SMITE.getMaxLevel()))){
            return SMITE;
        }
        if(name.equals(Enchantments.BANE_OF_ARTHROPODS.getName(Enchantments.BANE_OF_ARTHROPODS.getMaxLevel()))){
            return BANE_OF_ARTHROPODS;
        }
        if(name.equals(Enchantments.KNOCKBACK.getName(Enchantments.KNOCKBACK.getMaxLevel()))){
            return KNOCKBACK;
        }
        if(name.equals(Enchantments.FIRE_ASPECT.getName(Enchantments.FIRE_ASPECT.getMaxLevel()))){
            return FIRE_ASPECT;
        }
        if(name.equals(Enchantments.LOOTING.getName(Enchantments.LOOTING.getMaxLevel()))){
            return LOOTING;
        }
        if(name.equals(Enchantments.SWEEPING.getName(Enchantments.SWEEPING.getMaxLevel()))){
            return SWEEPING;
        }
        if(name.equals(Enchantments.EFFICIENCY.getName(Enchantments.EFFICIENCY.getMaxLevel()))){
            return EFFICIENCY;
        }
        if(name.equals(Enchantments.SILK_TOUCH.getName(Enchantments.SILK_TOUCH.getMaxLevel()))){
            return SILK_TOUCH;
        }
        if(name.equals(Enchantments.UNBREAKING.getName(Enchantments.UNBREAKING.getMaxLevel()))){
            return UNBREAKING;
        }
        if(name.equals(Enchantments.FORTUNE.getName(Enchantments.FORTUNE.getMaxLevel()))){
            return FORTUNE;
        }
        if(name.equals(Enchantments.POWER.getName(Enchantments.POWER.getMaxLevel()))){
            return POWER;
        }
        if(name.equals(Enchantments.PUNCH.getName(Enchantments.PUNCH.getMaxLevel()))){
            return PUNCH;
        }
        if(name.equals(Enchantments.FLAME.getName(Enchantments.FLAME.getMaxLevel()))){
            return FLAME;
        }
        if(name.equals(Enchantments.INFINITY.getName(Enchantments.INFINITY.getMaxLevel()))){
            return INFINITY;
        }
        if(name.equals(Enchantments.LUCK_OF_THE_SEA.getName(Enchantments.LUCK_OF_THE_SEA.getMaxLevel()))){
            return LUCK_OF_THE_SEA;
        }
        if(name.equals(Enchantments.LURE.getName(Enchantments.LURE.getMaxLevel()))){
            return LURE;
        }
        if(name.equals(Enchantments.LOYALTY.getName(Enchantments.LOYALTY.getMaxLevel()))){
            return LOYALTY;
        }
        if(name.equals(Enchantments.IMPALING.getName(Enchantments.IMPALING.getMaxLevel()))){
            return IMPALING;
        }
        if(name.equals(Enchantments.RIPTIDE.getName(Enchantments.RIPTIDE.getMaxLevel()))){
            return RIPTIDE;
        }
        if(name.equals(Enchantments.CHANNELING.getName(Enchantments.CHANNELING.getMaxLevel()))){
            return CHANNELING;
        }
        if(name.equals(Enchantments.MULTISHOT.getName(Enchantments.MULTISHOT.getMaxLevel()))){
            return MULTISHOT;
        }
        if(name.equals(Enchantments.QUICK_CHARGE.getName(Enchantments.QUICK_CHARGE.getMaxLevel()))){
            return QUICK_CHARGE;
        }
        if(name.equals(Enchantments.PIERCING.getName(Enchantments.PIERCING.getMaxLevel()))){
            return PIERCING;
        }
        if(name.equals(Enchantments.MENDING.getName(Enchantments.MENDING.getMaxLevel()))){
            return MENDING;
        }
        return null;
    }
}
