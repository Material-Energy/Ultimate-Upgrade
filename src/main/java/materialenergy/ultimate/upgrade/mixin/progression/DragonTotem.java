package materialenergy.ultimate.upgrade.mixin.progression;

import materialenergy.ultimate.upgrade.api.GetPos;
import materialenergy.ultimate.upgrade.item.DraconicBaseItem;
import materialenergy.ultimate.upgrade.registry.UUItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class DragonTotem extends Entity {

    @Shadow public abstract float getMaxHealth();

    public DragonTotem(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(
            method = "tryUseTotem",
            at = @At("HEAD"),
            cancellable = true
    )
    public void modifyTotemUsage(DamageSource source, CallbackInfoReturnable<Boolean> cir){
        ItemStack DRitemStack = null;
        for (Hand hand : Hand.values()) {
            ItemStack DRitemStack2 = this.getStackInHand(hand);
            if (!DRitemStack2.isOf(UUItems.DRACONIC_TOTEM)) continue;
            DRitemStack = DRitemStack2.copy();
            DRitemStack2.damage(1,
                    (LivingEntity)(Object)this,
                    (e) -> e.sendEquipmentBreakStatus(hand.equals(Hand.MAIN_HAND) ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND));
            break;
        }
        if (DRitemStack != null) {
            this.setHealth(this.getMaxHealth() / 2);
            Vec3d pos = ((GetPos) this).getPosAt(10);
            if (pos == null) pos = ((GetPos) this).getPosAt(((GetPos) this).getTotalSeconds());
            this.setPos(pos.x, pos.y, pos.z);
            this.world.sendEntityStatus(this, (byte)35);
        }
        cir.setReturnValue(DRitemStack != null);
    }
    @Shadow public abstract void setHealth(float v);
    @Shadow public abstract ItemStack getStackInHand(Hand hand);
}
