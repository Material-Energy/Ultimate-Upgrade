package materialenergy.ultimate.upgrade.mixin.runic;

import materialenergy.ultimate.upgrade.enchantments.RunicEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class ProtectionRunic extends Entity {


    public int resPts = 0;

    public ProtectionRunic(EntityType<?> type, World world) {
        super(type, world);
    }

    @Shadow public abstract Iterable<ItemStack> getArmorItems();

    @Inject(
            method = "writeCustomDataToNbt",
            at = @At("HEAD")
    )
    protected void returnCustomData(NbtCompound nbt, CallbackInfo ci){
        nbt.putInt("Resistance", this.resPts);
    }

    @Inject(
            method = "readCustomDataFromNbt",
            at = @At("HEAD")
    )
    protected void addCustomData(NbtCompound nbt, CallbackInfo ci){
        if (nbt.contains("Resistance", 99)) {
            this.resPts = nbt.getInt("Resistance");
        }
    }

    @Inject(
            method = "applyEnchantmentsToDamage",
            at = @At("HEAD"),
            cancellable = true
    )
    protected void applyDamage(DamageSource source, float amount, CallbackInfoReturnable<Float> cir){
        int addition = 0;
        Iterable<ItemStack> itemStacks =  this.getArmorItems();
        for (ItemStack itemStack: itemStacks){
            NbtList enchantments = itemStack.getEnchantments();
            for (int i = 0; i < enchantments.size(); ++i) {
                Enchantment enchantment = null;
                NbtCompound nbtCompound = enchantments.getCompound(i);
                if (Registry.ENCHANTMENT.getOrEmpty(EnchantmentHelper.getIdFromNbt(nbtCompound)).isPresent()) {
                    enchantment = Registry.ENCHANTMENT.get(EnchantmentHelper.getIdFromNbt(nbtCompound));
                }
                if (enchantment != null){
                    if (enchantment instanceof RunicEnchantment runicEnchantment &&
                            runicEnchantment.getProtection(source)
                    ){
                        addition += EnchantmentHelper.getLevelFromNbt(nbtCompound);
                    }
                }
            }
        }
        this.resPts += addition;
    }

    @Shadow public abstract StatusEffectInstance getStatusEffect(StatusEffect effect);
    @Shadow public abstract boolean hasStatusEffect(StatusEffect effect);
}
