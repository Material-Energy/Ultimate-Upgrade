package materialenergy.ultimate.upgrade.misc;

import materialenergy.ultimate.upgrade.registry.UUItems;
import materialenergy.ultimate.upgrade.registry.UURecipe;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class DraconicCrossbowRecipe extends BaseEnderRecipe {
    public DraconicCrossbowRecipe(Identifier id) {
        super(id);
    }

    @Override
    public boolean matches(CraftingInventory inventory, World world) {
        int recipeComplete = 0;
        for (int k = 0; k < inventory.size(); ++k) {
            ItemStack itemStack = inventory.getStack(k);
            if (k == 0 && itemStack.getItem().equals(Items.TOTEM_OF_UNDYING)){
                recipeComplete++;
            }
            if (k == 1 && itemStack.getItem().equals(Items.ENDER_PEARL)){
                recipeComplete++;
            }
            if (k == 2 && itemStack.getItem().equals(Items.TOTEM_OF_UNDYING)){
                recipeComplete++;
            }
            if (k == 3 && itemStack.getItem().equals(UUItems.DRACONIC_CRYSTAL)){
                recipeComplete++;
            }
            if (k == 4 && itemStack.getItem().equals(Items.TOTEM_OF_UNDYING)){
                recipeComplete++;
            }
            if (k == 5 && itemStack.getItem().equals(UUItems.DRACONIC_CRYSTAL)){
                recipeComplete++;
            }
            if (k == 6 && itemStack.getItem().equals(Items.TOTEM_OF_UNDYING)){
                recipeComplete++;
            }
            if (k == 7 && itemStack.getItem().equals(Items.RESPAWN_ANCHOR)){
                recipeComplete++;
            }
            if (k == 8 && itemStack.getItem().equals(Items.TOTEM_OF_UNDYING)){
                recipeComplete++;
            }
        }
        return recipeComplete == 9 && super.matches(inventory, world);
    }

    @Override
    public ItemStack craft(CraftingInventory inventory) {
        ItemStack itemStack = UUItems.DRACONIC_CROSSBOW.getDefaultStack();
        itemStack.getOrCreateNbt().putByte("EnderEnergy", getEnergy(inventory));
        return itemStack;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return UURecipe.ENDER_CROSSBOW;
    }
}
