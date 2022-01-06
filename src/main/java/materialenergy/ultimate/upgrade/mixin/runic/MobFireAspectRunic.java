package materialenergy.ultimate.upgrade.mixin.runic;

import materialenergy.ultimate.upgrade.registry.UUEnchantments;
import materialenergy.ultimate.upgrade.registry.UUMisc;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(MobEntity.class)
public abstract class MobFireAspectRunic extends LivingEntity {

    protected MobFireAspectRunic(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(
            method = "tryAttack",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/EnchantmentHelper;getFireAspect(Lnet/minecraft/entity/LivingEntity;)I")
    )
    public void modifyAttack(Entity target, CallbackInfoReturnable<Boolean> cir){
        int level = EnchantmentHelper.getEquipmentLevel(UUEnchantments.FIRE_ASPECT, this);
        if (level > 0) {
            Box box = new Box(this.getPos().add(4 * level, 4 * level, 4 * level), this.getPos().add(-4 * level, -4 * level, -4 * level));
            List<Entity> entityList = target.getEntityWorld().getOtherEntities(this, box);
            for (Entity entity : entityList){
                if (entity instanceof LivingEntity){
                    entity.setOnFireFor((int) (4 * Math.pow(level, 2)));
                    entity.damage(UUMisc.INCINERATE, (float) (6.0f * Math.pow(level, 2)));
                }
            }
        }
    }

}
