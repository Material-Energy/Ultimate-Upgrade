package materialenergy.ultimate.upgrade.mixin;

import materialenergy.ultimate.upgrade.registry.UUItems;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class DragonTotem extends Entity {

    public DragonTotem(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(
            method = "tryUseTotem",
            at = @At("HEAD"),
            cancellable = true
    )
    public void modifyTotemUsage(DamageSource source, CallbackInfoReturnable<Boolean> cir){
        ItemStack itemStack = null;
        ItemStack itemStack2 = null;
        for (Hand hand : Hand.values()) {
            itemStack2 = this.getStackInHand(hand);
            if (!itemStack2.isOf(UUItems.DRACONIC_TOTEM)) continue;
            itemStack = itemStack2.copy();
            itemStack2.damage(1,
                    (LivingEntity)(Object)this,
                    (e) -> e.sendEquipmentBreakStatus(hand.equals(Hand.MAIN_HAND) ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND));
            break;
        }
        if (itemStack != null) {
            int energy = itemStack2.getOrCreateNbt().getByte("EnderEnergy");
            this.setHealth(energy != 0 ? energy : 1.0f);
            this.clearStatusEffects();
            this.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 900, MathHelper.ceil(energy/10.0f)));
            this.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 100, 1));
            this.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 800, 0));
            if (energy > 20){
                this.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 400, MathHelper.ceil((energy-20.0f)/10)));
            }
            if (source.isOutOfWorld()){
                this.addStatusEffect(new StatusEffectInstance(StatusEffects.LEVITATION, 50, 4));
            }
            this.world.sendEntityStatus(this, (byte)35);
        }
        cir.setReturnValue(itemStack != null);
    }
    @Shadow public abstract boolean addStatusEffect(StatusEffectInstance statusEffectInstance);
    @Shadow public abstract boolean clearStatusEffects();
    @Shadow public abstract void setHealth(float v);
    @Shadow public abstract ItemStack getStackInHand(Hand hand);
}
