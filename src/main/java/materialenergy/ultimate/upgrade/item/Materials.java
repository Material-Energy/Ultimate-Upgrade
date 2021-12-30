package materialenergy.ultimate.upgrade.item;

import materialenergy.ultimate.upgrade.registry.UUItems;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;

public class Materials {
    public static class MoltenToolMaterial implements ToolMaterial {
        public static final MoltenToolMaterial INSTANCE = new MoltenToolMaterial();

        @Override
        public int getDurability() {
            return 1024;
        }

        @Override
        public float getMiningSpeedMultiplier() {
            return 8.0F;
        }

        @Override
        public float getAttackDamage() {
            return 4.0F;
        }

        @Override
        public int getMiningLevel() {
            return 3;
        }

        @Override
        public int getEnchantability() {
            return 18;
        }

        @Override
        public Ingredient getRepairIngredient() {
            return Ingredient.ofItems(UUItems.MOLTEN_INGOT);
        }
    }
}
