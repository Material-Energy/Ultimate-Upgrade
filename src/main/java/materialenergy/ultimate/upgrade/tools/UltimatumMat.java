package materialenergy.ultimate.upgrade.tools;

import net.minecraft.item.Items;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;

public class UltimatumMat implements ToolMaterial {
        public static final UltimatumMat INSTANCE = new UltimatumMat();


        @Override
        public int getDurability() {
            return 2147483647;
        }

        @Override
        public float getMiningSpeedMultiplier() {
            return Float.MAX_VALUE;
        }

        @Override
        public float getAttackDamage() {
            return 2147483647.0F;
        }

        @Override
        public int getMiningLevel() {
            return 2147483647;
        }

        @Override
        public int getEnchantability() {
            return 2147483647;
        }

        @Override
        public Ingredient getRepairIngredient() {
            return Ingredient.ofItems(Items.BEDROCK);
        }

}
