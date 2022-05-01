package materialenergy.ultimate.upgrade.common.registry;

import materialenergy.ultimate.upgrade.common.entities.DraconicTridentEntity;
import materialenergy.ultimate.upgrade.common.item.draconic.DraconicTridentItem;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class Registries {
    public static Identifier id(String name) {
        return new Identifier("ultimateupgrade",name);
    }

    public static PersistentProjectileEntity spawnTrident(World world, LivingEntity user, ItemStack currentStack) {
        if(currentStack.isOf(UUItems.DRACONIC_TRIDENT)){
            DraconicTridentEntity tridentEntity = new DraconicTridentEntity(world, user, currentStack);
            if (((PlayerEntity) user).getAbilities().creativeMode) {
                tridentEntity.pickupType = PersistentProjectileEntity.PickupPermission.CREATIVE_ONLY;
            }
            return tridentEntity;
        } else {
            return null;
        }
    }
}
