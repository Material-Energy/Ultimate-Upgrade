package materialenergy.ultimate.upgrade.common.misc;

import materialenergy.ultimate.upgrade.common.registry.UUItems;
import materialenergy.ultimate.upgrade.common.registry.UURecipe;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class DraconicTridentRecipe extends BaseEnderRecipe {


    public DraconicTridentRecipe(Identifier id) {
        super(id);
    }

    @Override
    public boolean matches(CraftingInventory inventory, World world) {
        int recipeComplete = 0;
        for (int k = 0; k < inventory.size(); ++k) {
            ItemStack itemStack = inventory.getStack(k);
            if (k == 1 && itemStack.getItem().equals(UUItems.DRACONIC_CRYSTAL)){
                recipeComplete++;
            } else if (k == 2 && itemStack.getItem().equals(Items.END_CRYSTAL)){
                recipeComplete++;
            } else if (k == 4 && itemStack.getItem().equals(Items.END_ROD)){
                recipeComplete++;
            } else if (k == 5 && itemStack.getItem().equals(UUItems.DRACONIC_CRYSTAL)){
                recipeComplete++;
            } else if (k == 6 && itemStack.getItem().equals(Items.END_ROD)){
                recipeComplete++;
            }
        }
        return recipeComplete == 5 && super.matches(inventory, world);
    }

    @Override
    public ItemStack craft(CraftingInventory inventory) {
        ItemStack itemStack = UUItems.DRACONIC_TRIDENT.getDefaultStack();
        itemStack.getOrCreateNbt().putByte("EnderEnergy", getEnergy(inventory));
        return itemStack;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return UURecipe.ENDER_TRIDENT;
    }

}
