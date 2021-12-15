package materialenergy.ultimate.upgrade.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

public class DraconicBaseItem extends Item {
    public DraconicBaseItem(Settings settings) {
        super(settings);
    }

    @Override
    public ItemStack getDefaultStack() {
        return resetEnderCharge(super.getDefaultStack());
    }

    protected ItemStack resetEnderCharge(ItemStack itemStack){
        NbtCompound nbt = new NbtCompound();
        nbt.putByte("EnderEnergy", (byte) 0);
        itemStack.setNbt(nbt);
        return itemStack;
    }


    @Override
    public boolean hasGlint(ItemStack stack) {
        NbtCompound nbt = stack.getNbt();
        if (nbt != null) {
            return nbt.getByte("EnderEnergy") > 0;
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
        nbtCompound.putByte("EnderEnergy", (byte) 0);
    }

    public void writeEnderEnergy(ItemStack stack, byte amount){
        NbtCompound nbtCompound = stack.getOrCreateNbt();
        nbtCompound.putByte("EnderEnergy", amount);
        stack.setNbt(nbtCompound);
    }

    public byte getEnderEnergy(ItemStack stack){
        NbtCompound nbtCompound = stack.getOrCreateNbt();
        return nbtCompound.getByte("EnderEnergy");
    }
}
