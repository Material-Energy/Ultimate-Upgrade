package materialenergy.ultimate.upgrade.mixin.runic;

import materialenergy.ultimate.upgrade.registry.UUEnchantments;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.enchantment.UnbreakingEnchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(ItemStack.class)
public abstract class UnbreakingRunic {

    @Inject(
            method = "damage(ILjava/util/Random;Lnet/minecraft/server/network/ServerPlayerEntity;)Z",
            at = @At("HEAD"),
            cancellable = true
    )
    public void damage(int amount, Random random, ServerPlayerEntity player, CallbackInfoReturnable<Boolean> cir) {
        int i;
        if (!this.isDamageable()) {
            cir.setReturnValue(false);
        }
        if (amount > 0) {
            if (EnchantmentHelper.getLevel(UUEnchantments.UNBREAKING, (ItemStack)(Object)this) > 0) amount = 0;
            i = EnchantmentHelper.getLevel(Enchantments.UNBREAKING, (ItemStack)(Object)this);
            int j = 0;
            for (int k = 0; i > 0 && k < amount; ++k) {
                if (!UnbreakingEnchantment.shouldPreventDamage((ItemStack)(Object)this, i, random)) continue;
                ++j;
            }
            if ((amount -= j) <= 0) {
                cir.setReturnValue(false);
            }
        }
        if (player != null && amount != 0) {
            Criteria.ITEM_DURABILITY_CHANGED.trigger(player, (ItemStack)(Object)this, this.getDamage() + amount);
        }
        i = this.getDamage() + amount;
        this.setDamage(i);
        cir.setReturnValue(i >= this.getMaxDamage());
    }

    @Shadow public abstract boolean isDamageable();
    @Shadow public abstract int getMaxDamage();
    @Shadow public abstract void setDamage(int i);
    @Shadow public abstract int getDamage();
}
