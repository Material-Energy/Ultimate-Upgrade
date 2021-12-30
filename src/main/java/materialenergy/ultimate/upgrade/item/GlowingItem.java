package materialenergy.ultimate.upgrade.item;

import materialenergy.ultimate.upgrade.registry.UUItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;

public class GlowingItem extends Item {
    public GlowingItem(Settings settings) {
        super(settings);
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return true;
    }

}
