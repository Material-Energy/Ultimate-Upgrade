package materialenergy.ultimate.upgrade.common.item;

import materialenergy.ultimate.upgrade.common.registry.UUItems;
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
