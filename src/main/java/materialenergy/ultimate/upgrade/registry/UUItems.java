package materialenergy.ultimate.upgrade.registry;

import materialenergy.ultimate.upgrade.UltimateUpgrade;
import materialenergy.ultimate.upgrade.item.*;
import materialenergy.ultimate.upgrade.misc.DraconicCustomDamageHandler;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;

public class UUItems {

    private static Item register(String name, Item item) {
        return Registry.register(Registry.ITEM, new Identifier("ultimateupgrade",name), item);
    }

    public static final Item ULTIMATUM = register("ultimatum",new Ultimatum(UltimatumMat.INSTANCE,0, 0.0F, new Item.Settings().group(UltimateUpgrade.UU_MAIN).fireproof().rarity(Rarity.EPIC)));

    public static final Item MOLTEN_INGOT = register("molten_ingot",new Item(new FabricItemSettings().group(UltimateUpgrade.UU_MAIN).fireproof()));
    public static final Item MOLTEN_SHOVEL = register("molten_shovel",new ShovelItem(MoltenToolMaterial.INSTANCE, 1.5F, -3.0F, new Item.Settings().group(UltimateUpgrade.UU_MAIN).fireproof()));
    public static final Item MOLTEN_SWORD = register("molten_sword",new SwordItem(MoltenToolMaterial.INSTANCE, 3, -2.4F, new Item.Settings().group(UltimateUpgrade.UU_MAIN).fireproof()));
    public static final Item MOLTEN_PICKAXE = register("molten_pickaxe",new MoltenPickaxe(MoltenToolMaterial.INSTANCE, 1, -2.8F, new Item.Settings().group(UltimateUpgrade.UU_MAIN).fireproof()));
    public static final Item MOLTEN_BOW = register("molten_bow",new MoltenBow(new FabricItemSettings().group(UltimateUpgrade.UU_MAIN).fireproof().maxDamage(1024)));

    public static final Item RAW_DRACONIC_CRYSTAL = register("raw_draconic_crystal", new Item(new FabricItemSettings().group(UltimateUpgrade.UU_MAIN)));
    public static final Item DRACONIC_CRYSTAL = register("draconic_crystal", new DraconicBaseItem(new FabricItemSettings().group(UltimateUpgrade.UU_MAIN)));
    public static final Item DRACONIC_TRIDENT = register("draconic_trident", new DraconicTrident(new FabricItemSettings().group(UltimateUpgrade.UU_MAIN).maxDamage(3000).customDamage(new DraconicCustomDamageHandler())));
    public static final Item DRACONIC_TOTEM = register("draconic_totem", new DraconicBaseItem(new FabricItemSettings().group(UltimateUpgrade.UU_MAIN).maxDamage(6).customDamage(new DraconicCustomDamageHandler())));

    public static final Item ENCHANTED_GOLDEN_CARROT = register("enchanted_golden_carrot",new GlowingItem(new FabricItemSettings().group(UltimateUpgrade.UU_MAIN).rarity(Rarity.EPIC).group(ItemGroup.FOOD).food(new FoodComponent.Builder().hunger(12).saturationModifier(1.2F).statusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 2400, 3), 1.0F).alwaysEdible().snack().build())));

    public static void init() {
        //init the items
    }


}
