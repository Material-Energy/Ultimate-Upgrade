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
        boolean bl = processDraconicTridentRecipe(inventory);
        boolean bl2 = processDraconicTotemRecipe(inventory);
        return bl || bl2;
    }

    @Override
    public ItemStack craft(CraftingInventory inventory) {
        ItemStack itemStack;
        byte energy = 0;
        for (int k = 0; k < inventory.size(); ++k) {
            ItemStack stack = inventory.getStack(k);
            if(stack.isOf(UUItems.DRACONIC_CRYSTAL)) energy += stack.getOrCreateNbt().getByte("EnderEnergy");
        }
        itemStack = getDefault(inventory);
        if (!itemStack.isEmpty()) itemStack.getOrCreateNbt().putByte("EnderEnergy", energy);
        return itemStack;
    }

    private ItemStack getDefault(CraftingInventory inventory) {
        if (processDraconicTridentRecipe(inventory)) return UUItems.DRACONIC_TRIDENT.getDefaultStack();
        if (processDraconicTotemRecipe(inventory)) return UUItems.DRACONIC_TOTEM.getDefaultStack();
        else return ItemStack.EMPTY;
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

    public boolean processDraconicTotemRecipe(CraftingInventory inventory){
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
        return recipeComplete == 9;
    }
}
