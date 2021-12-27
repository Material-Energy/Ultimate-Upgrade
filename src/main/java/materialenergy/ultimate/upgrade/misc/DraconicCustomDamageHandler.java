package materialenergy.ultimate.upgrade.misc;

import materialenergy.ultimate.upgrade.item.draconic.DraconicBaseItem;
import net.fabricmc.fabric.api.item.v1.CustomDamageHandler;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

import java.util.function.Consumer;

public class DraconicCustomDamageHandler implements CustomDamageHandler {
    @Override
    public int damage(ItemStack stack, int amount, LivingEntity entity, Consumer<LivingEntity> breakCallback) {
        int energy = DraconicBaseItem.getEnderEnergy(stack);
        if (energy > 0 && energy >= amount){
            DraconicBaseItem.writeEnderEnergy(stack, (short) (energy - amount));
            return 0;
        } else if (energy > 0){
            DraconicBaseItem.writeEnderEnergy(stack, (short) 0);
            return amount - energy;
        } else {
            return amount;
        }
    }
}
