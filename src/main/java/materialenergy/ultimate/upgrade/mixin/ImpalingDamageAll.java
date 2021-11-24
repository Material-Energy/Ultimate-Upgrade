package materialenergy.ultimate.upgrade.mixin;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.enchantment.ImpalingEnchantment;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EquipmentSlot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ImpalingEnchantment.class)
public class ImpalingDamageAll extends Enchantment {
    protected ImpalingDamageAll(Rarity weight, EnchantmentTarget type, EquipmentSlot[] slotTypes) {
        super(weight, type, slotTypes);
    }

    @Inject(method = "getAttackDamage", at = @At("HEAD"), cancellable = true)
    public void getAttackDamage(int level, EntityGroup group, CallbackInfoReturnable<Float> cir){
        cir.setReturnValue((float)level * 2.5F);
    }
}
