package materialenergy.ultimate.upgrade.common.item.draconic;

import materialenergy.ultimate.upgrade.common.api.MouseWheel;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DraconicTotemItem extends DraconicBaseItem implements MouseWheel {
    public DraconicTotemItem(Settings settings) {
        super(settings);
    }

    @Override
    public ItemStack getDefaultStack() {
        ItemStack itemStack = super.getDefaultStack();
        itemStack.getOrCreateNbt().putByte("EnderEnergy", (byte) 0);
        itemStack.getOrCreateNbt().putByte("RewindAmount", (byte) 1);
        return itemStack;
    }

    @Override
    public int getTotalEnergy() {
        return 64;
    }

    public static byte getRewind(ItemStack stack){
        return stack.getOrCreateNbt().getByte("RewindAmount");
    }

    public static void writeRewind(ItemStack stack, int amount){
        NbtCompound nbtCompound = stack.getOrCreateNbt();
        nbtCompound.putByte("RewindAmount", (byte) amount);
        stack.setNbt(nbtCompound);
    }


    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        int rewind = DraconicTotemItem.getRewind(stack);
        tooltip.add(new TranslatableText("ultimateupgrade.rewind.tooltip").append(String.valueOf(rewind)).formatted(Formatting.GRAY));
    }

    @Override
    public void onMouseScrolled(ItemStack stack, float scrollAmount, PlayerEntity user) {
        if (scrollAmount > 0.0) {
            scrollAmount = 1.0F;
        }
        if (scrollAmount < 0.0) {
            scrollAmount = -1.0F;
        }
        scrollAmount += DraconicTotemItem.getRewind(stack);
        if(scrollAmount <= 0){
            scrollAmount = 1;
        } else if (scrollAmount > 100){
            scrollAmount = 100;
        }
        DraconicTotemItem.writeRewind(stack, (int) scrollAmount);
        user.sendMessage(new TranslatableText("ultimateupgrade.rewind.tooltip").append(String.valueOf(scrollAmount)), true);
    }
}
