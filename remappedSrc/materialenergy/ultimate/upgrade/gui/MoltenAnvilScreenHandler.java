package materialenergy.ultimate.upgrade.common.gui;

import materialenergy.ultimate.upgrade.common.registry.UUBlocks;
import materialenergy.ultimate.upgrade.common.registry.UUEnchantments;
import materialenergy.ultimate.upgrade.common.registry.UUGUI;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.ForgingScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.Property;

import java.util.Map;

public class MoltenAnvilScreenHandler extends ForgingScreenHandler {
    private Property levelCost = Property.create();

    public MoltenAnvilScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, ScreenHandlerContext.EMPTY);
    }


    public MoltenAnvilScreenHandler(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
        super(UUGUI.MOLTEN_ANVIL, syncId, playerInventory, context);
        this.addProperty(this.levelCost);
    }

    @Override
    protected boolean canUse(BlockState state) {
        return state.isOf(UUBlocks.MOLTEN_ANVIL);
    }

    @Override
    protected boolean canTakeOutput(PlayerEntity player, boolean present) {
        return !this.output.isEmpty();
    }

    @Override
    protected void onTakeOutput(PlayerEntity player, ItemStack stack) {
        this.decrementStack(0);
        this.decrementStack(1);
        if (!player.isCreative()) player.addExperienceLevels(-this.levelCost.get());
        this.levelCost.set(0);
    }

    private void decrementStack(int slot) {
        ItemStack itemStack = this.input.getStack(slot);
        itemStack.decrement(1);
        this.input.setStack(slot, itemStack);
    }

    private void runicMerge(){
        int xp = 0;
        Map<Enchantment, Integer> Enchantments1 = EnchantmentHelper.get(this.input.getStack(0));
        Map<Enchantment, Integer> Enchantments2 = EnchantmentHelper.get(this.input.getStack(1));
        Map<Enchantment, Integer> Enchants1 = EnchantmentHelper.get(this.input.getStack(0));
        Map<Enchantment, Integer> Enchants2 = EnchantmentHelper.get(this.input.getStack(1));
        for(Enchantment enchantment: Enchantments1.keySet()){
            int level1 = Enchantments1.get(enchantment);
            if(level1 == enchantment.getMaxLevel()){
                for (Enchantment enchant: Enchantments2.keySet()){
                    int level2 = Enchantments1.get(enchantment);
                    if (enchant.equals(enchantment) && level2 == enchant.getMaxLevel()){
                        Enchantment runicEnchantment = UUEnchantments.getFromId(enchant);
                        if (runicEnchantment != null){
                            Enchants1.remove(enchantment);
                            Enchants2.remove(enchant);
                            Enchants1.put(runicEnchantment,1);
                            xp += 60;
                        }
                    }
                }
            }
        }
        this.levelCost.set(xp);
        ItemStack output = new ItemStack(Items.ENCHANTED_BOOK);
        Enchants1.putAll(Enchants2);
        EnchantmentHelper.set(Enchants1,output);
        if(this.levelCost.get() <= player.experienceLevel || player.isCreative()) {
            this.output.setStack(0, output);
        }
    }

    @Override
    public void updateResult() {
        if (this.input.getStack(0).isOf(Items.ENCHANTED_BOOK) &&
                this.input.getStack(1).isOf(Items.ENCHANTED_BOOK)){
            this.runicMerge();
        } else {
            this.output.setStack(0, new ItemStack(Items.AIR));
        }
    }

    @Override
    public boolean canInsertIntoSlot(ItemStack stack, Slot slot) {
        return slot.inventory != this.output && super.canInsertIntoSlot(stack, slot) && stack.isOf(Items.ENCHANTED_BOOK);
    }

    public int getLevelCost() {
        return this.levelCost.get();
    }
}
