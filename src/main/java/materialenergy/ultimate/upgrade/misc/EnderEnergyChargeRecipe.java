package materialenergy.ultimate.upgrade.misc;

import materialenergy.ultimate.upgrade.registry.UUItems;
import materialenergy.ultimate.upgrade.registry.UURecipe;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class EnderEnergyChargeRecipe extends SpecialCraftingRecipe {


    public EnderEnergyChargeRecipe(Identifier id) {
        super(id);
    }

    @Override
    public boolean matches(CraftingInventory inventory, World world) {
        return processDraconicTridentRecipe(inventory);
    }

    @Override
    public ItemStack craft(CraftingInventory inventory) {
        ItemStack itemStack = ItemStack.EMPTY;
        byte energy = 0;
        if(processDraconicTridentRecipe(inventory)){
            for (int k = 0; k < inventory.size(); ++k) {
                if (k == 1 || k == 5){
                    ItemStack stack = inventory.getStack(k);
                    energy += stack.getOrCreateNbt().getByte("EnderEnergy");
                }
            }
            itemStack = UUItems.DRACONIC_TRIDENT.getDefaultStack();
            if(itemStack.hasNbt()) {
                itemStack.getNbt().putByte("EnderEnergy", energy);
            }
        }
        return itemStack;
    }

    @Override
    public boolean fits(int width, int height) {
        return width * height >= 9;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return UURecipe.EE_CHARGE;
    }

    public boolean processDraconicTridentRecipe(CraftingInventory inventory){
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
        return recipeComplete == 5;
    }
}
