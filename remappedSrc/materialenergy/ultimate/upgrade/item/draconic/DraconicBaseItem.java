package materialenergy.ultimate.upgrade.common.item.draconic;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DraconicBaseItem extends Item {
    public String ENERGY_KEY = "EnderEnergy";

    public DraconicBaseItem(Settings settings) {
        super(settings);
    }

    @Override
    public ItemStack getDefaultStack() {
        return resetEnderCharge(super.getDefaultStack());
    }

    protected ItemStack resetEnderCharge(ItemStack itemStack){
        NbtCompound nbt = new NbtCompound();
        nbt.putShort(ENERGY_KEY, (short) 0);
        itemStack.setNbt(nbt);
        return itemStack;
    }


    @Override
    public boolean hasGlint(ItemStack stack) {
        NbtCompound nbt = stack.getNbt();
        if (nbt != null) {
            return nbt.getShort(ENERGY_KEY) > 0;
        }
        return false;
    }

    @Override
    public void appendStacks(ItemGroup group, DefaultedList<ItemStack> stacks) {
        if (this.isIn(group)) {
            stacks.add(this.getDefaultStack());
        }
    }

    @Override
    public void onCraft(ItemStack stack, World world, PlayerEntity player) {
        NbtCompound nbtCompound = stack.getOrCreateNbt();
        if (nbtCompound.getShort(ENERGY_KEY) == 0) nbtCompound.putShort(ENERGY_KEY, (short) 0);
    }

    public static void writeEnderEnergy(ItemStack stack, short amount){
        NbtCompound nbtCompound = stack.getOrCreateNbt();
        nbtCompound.putShort("EnderEnergy", amount);
        stack.setNbt(nbtCompound);
    }

    public static short getEnderEnergy(ItemStack stack){
        NbtCompound nbtCompound = stack.getOrCreateNbt();
        return nbtCompound.getShort("EnderEnergy");
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        DraconicBaseItem.addTooltip(stack, tooltip);
    }

    public int getTotalEnergy(){
        return 128;
    }

    public static void addTooltip(ItemStack stack, List<Text> tooltip){
        int energy = DraconicBaseItem.getEnderEnergy(stack);
        int total = ((DraconicBaseItem)stack.getItem()).getTotalEnergy();
        int quadrant = total / 4;
        if (energy > -1           && energy <= quadrant    ) tooltip.add(new TranslatableText("ultimateupgrade.enderenergy.tooltip").append(String.valueOf(energy)).formatted(Formatting.GRAY));
        if (energy > quadrant     && energy <= quadrant * 2) tooltip.add(new TranslatableText("ultimateupgrade.enderenergy.tooltip").append(String.valueOf(energy)).formatted(Formatting.AQUA));
        if (energy > quadrant * 2 && energy <= quadrant * 3) tooltip.add(new TranslatableText("ultimateupgrade.enderenergy.tooltip").append(String.valueOf(energy)).formatted(Formatting.GOLD));
        if (energy > quadrant * 3 && energy <= quadrant * 4) tooltip.add(new TranslatableText("ultimateupgrade.enderenergy.tooltip").append(String.valueOf(energy)).formatted(Formatting.LIGHT_PURPLE));
    }
}
