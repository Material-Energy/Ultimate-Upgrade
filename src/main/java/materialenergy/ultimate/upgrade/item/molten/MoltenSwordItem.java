package materialenergy.ultimate.upgrade.item.molten;

import materialenergy.ultimate.upgrade.registry.UUEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.Random;

public class MoltenSwordItem extends SwordItem {

    public MoltenSwordItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.damage(1, attacker, (e) -> e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));
        if (new Random().nextBoolean()){
            Vec3d pos = target.getPos();
            boolean heatedUp = target.getEntityWorld().getDimension().isUltrawarm();
            ArrayList<Entity> list = (ArrayList<Entity>) target.getEntityWorld().getOtherEntities(target, new Box(
                    pos.x - 2,
                    pos.y - 2,
                    pos.z - 2,
                    pos.x + 2,
                    pos.y + 2,
                    pos.z + 2));
            for (Entity entity : list) {
                if (entity instanceof LivingEntity && entity != attacker) {
                    entity.setOnFireFor(heatedUp ? 8 : 4);
                    ((LivingEntity) entity).addStatusEffect(new StatusEffectInstance(
                            UUEffects.FIRE_WEAKNESS,
                            heatedUp ? 8 : 4,
                            heatedUp ? 0 : 1,
                            false,
                            true,
                            true
                    ));
                }
            }
            target.setOnFireFor(heatedUp ? 8 : 4);
        }
        return true;
    }
}
