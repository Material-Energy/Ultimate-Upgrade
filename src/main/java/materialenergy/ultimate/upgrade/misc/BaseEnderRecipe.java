package materialenergy.ultimate.upgrade.misc;

import materialenergy.ultimate.upgrade.registry.UUItems;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class BaseEnderRecipe extends SpecialCraftingRecipe {
    public BaseEnderRecipe(Identifier id) {
        super(id);
    }

    @Override
    public boolean matches(CraftingInventory inventory, World world) {
        return world.getRegistryKey().equals(World.END);
    }

    @Override
    public ItemStack craft(CraftingInventory inventory) {
        return ItemStack.EMPTY;
    }

    protected byte getEnergy(CraftingInventory inventory) {
        byte energy = 0;
        for (int k = 0; k < inventory.size(); ++k) {
            ItemStack stack = inventory.getStack(k);
            if(stack.isOf(UUItems.DRACONIC_CRYSTAL)) energy += stack.getOrCreateNbt().getByte("EnderEnergy");
        }
        return energy;
    }

    @Override
    public boolean fits(int width, int height) {
        return width * height >= 9;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return null;
    }
}
