package materialenergy.ultimate.upgrade.common.api;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public interface MouseWheel {
    void onMouseScrolled(ItemStack stack, float scrollAmount, PlayerEntity user);
}
