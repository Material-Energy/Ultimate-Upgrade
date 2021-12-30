package materialenergy.ultimate.upgrade.mixin.progression;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTracker;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class MoreDamage extends Entity{


    public MoreDamage(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(
            method = "applyDamage",
            at = @At("HEAD"),
            cancellable = true
    )
    public void multiply(DamageSource source, float amount, CallbackInfo ci){
        if (source.name.equals("mob") || source.name.equals("player")){
            if (this.isInvulnerableTo(source)) {
                ci.cancel();
            }
            amount *= 2.5;
            amount = this.applyArmorToDamage(source, amount);
            float f = amount = this.applyEnchantmentsToDamage(source, amount);
            amount = Math.max(amount - this.getAbsorptionAmount(), 0.0f);
            this.setAbsorptionAmount(this.getAbsorptionAmount() - (f - amount));
            float g = f - amount;
            if (g > 0.0f && g < 3.4028235E37f && source.getAttacker() instanceof ServerPlayerEntity) {
                ((ServerPlayerEntity)source.getAttacker()).increaseStat(Stats.DAMAGE_DEALT_ABSORBED, Math.round(g * 10.0f));
            }
            if (amount == 0.0f) {
                ci.cancel();
            }
            float h = this.getHealth();
            this.setHealth(h - amount);
            this.getDamageTracker().onDamage(source, h, amount);
            this.setAbsorptionAmount(this.getAbsorptionAmount() - amount);
            this.emitGameEvent(GameEvent.ENTITY_DAMAGED, source.getAttacker());
            ci.cancel();
        }
    }

    @Shadow public abstract DamageTracker getDamageTracker();
    @Shadow public abstract void setHealth(float v);
    @Shadow public abstract float getHealth();
    @Shadow public abstract void setAbsorptionAmount(float v);
    @Shadow public abstract float getAbsorptionAmount();
    @Shadow protected abstract float applyEnchantmentsToDamage(DamageSource source, float amount);
    @Shadow protected abstract float applyArmorToDamage(DamageSource source, float amount);
}
