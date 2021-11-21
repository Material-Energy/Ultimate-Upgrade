package materialenergy.ultimate.upgrade.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class GlowingItem extends Item {
    public GlowingItem(Settings settings) {
        super(settings);
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return true;
    }
}
