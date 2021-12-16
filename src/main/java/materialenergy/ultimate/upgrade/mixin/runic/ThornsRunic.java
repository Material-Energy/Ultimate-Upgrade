package materialenergy.ultimate.upgrade.mixin.runic;

import materialenergy.ultimate.upgrade.api.Rune;
import materialenergy.ultimate.upgrade.registry.UUEnchantments;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.enchantment.ThornsEnchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.Map;
import java.util.Random;

@Mixin(ThornsEnchantment.class)
public class ThornsRunic {

    /**
     * @author MaterialEnergy
     * @reason add runic enchant
     */
    @Overwrite
    public void onUserDamaged(LivingEntity user, Entity attacker, int level) {
        if (((Rune) this).getRunic()){
            Map.Entry<EquipmentSlot, ItemStack> entry = EnchantmentHelper.chooseEquipmentWith(UUEnchantments.THORNS, user);
            if (attacker != null){
                attacker.damage(DamageSource.thorns(user), 4 * level);
                Vec3d atkVelocity = attacker.getVelocity();
                Vec3d vec3d = user.getRotationVec(1.0f);
                double f = -(attacker.getX() - (user.getX() + vec3d.x * 4.0));
                double g = attacker.getBodyY(0.5) - (0.5 + user.getBodyY(0.5));
                double h = -(attacker.getZ() - (user.getZ() + vec3d.z * 4.0));
                atkVelocity = atkVelocity.add(f, g, h);
                attacker.setVelocity(atkVelocity.add(0, level, 0));
            }
            if (entry != null) {
                entry.getValue().damage(2 * level == 2 ? 1 : 2, user, entity -> entity.sendEquipmentBreakStatus(entry.getKey()));
            }
        } else {
            Random random = user.getRandom();
            Map.Entry<EquipmentSlot, ItemStack> entry = EnchantmentHelper.chooseEquipmentWith(Enchantments.THORNS, user);
            if (ThornsEnchantment.shouldDamageAttacker(level, random)) {
                if (attacker != null) {
                    attacker.damage(DamageSource.thorns(user), ThornsEnchantment.getDamageAmount(level, random));
                }
                if (entry != null) {
                    entry.getValue().damage(2, user, entity -> entity.sendEquipmentBreakStatus(entry.getKey()));
                }
            }
        }
    }
}
