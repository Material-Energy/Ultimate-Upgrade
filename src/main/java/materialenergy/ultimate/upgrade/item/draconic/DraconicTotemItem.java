package materialenergy.ultimate.upgrade.item.draconic;

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

public class DraconicTotemItem extends DraconicBaseItem{
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
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        if (itemStack.getItem() instanceof DraconicTotemItem){
            int rewind = DraconicTotemItem.getRewind(itemStack);
            if (rewind > 63){
                rewind = 1;
            } else {
                if (Screen.hasAltDown() && Screen.hasShiftDown()) {
                    rewind -= 2;
                } else if (Screen.hasAltDown() && Screen.hasControlDown()) {
                    rewind -= 4;
                } else if (Screen.hasAltDown()) {
                    rewind--;
                } else if (Screen.hasShiftDown()) {
                    rewind += 2;
                } else if (Screen.hasControlDown()) {
                    rewind += 4;
                } else {
                    rewind++;
                }
            }
            DraconicTotemItem.writeRewind(itemStack, rewind);
            user.sendMessage(new TranslatableText("ultimateupgrade.rewind.tooltip").append(String.valueOf(rewind)), true);
            return TypedActionResult.success(itemStack);
        }
        return super.use(world, user, hand);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        int rewind = DraconicTotemItem.getRewind(stack);
        int energy = DraconicBaseItem.getEnderEnergy(stack);
        rewind = Math.min(rewind, energy);
        tooltip.add(new TranslatableText("ultimateupgrade.rewind.tooltip").append(String.valueOf(rewind)).formatted(Formatting.GRAY));
    }
}
