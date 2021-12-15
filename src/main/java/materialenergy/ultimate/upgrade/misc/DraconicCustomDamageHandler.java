package materialenergy.ultimate.upgrade.misc;

import net.fabricmc.fabric.api.item.v1.CustomDamageHandler;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

import java.util.function.Consumer;

public class DraconicCustomDamageHandler implements CustomDamageHandler {
    @Override
    public int damage(ItemStack stack, int amount, LivingEntity entity, Consumer<LivingEntity> breakCallback) {
        int energy = stack.getOrCreateNbt().getByte("EnderEnergy");
        if (energy > 0 && energy >= amount){
            stack.getOrCreateNbt().putByte("EnderEnergy", (byte) (energy - amount));
            return 0;
        } else if (energy > 0){
            stack.getOrCreateNbt().putByte("EnderEnergy", (byte) energy);
            return amount - energy;
        } else {
            return amount;
        }
    }
}
