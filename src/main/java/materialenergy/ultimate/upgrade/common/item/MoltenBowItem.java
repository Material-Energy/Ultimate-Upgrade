package materialenergy.ultimate.upgrade.common.item;

import materialenergy.ultimate.upgrade.common.entity.MoltenProjectileEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;

public class MoltenBowItem extends BowItem {
    public MoltenBowItem(Settings settings) {
        super(settings);
    }

    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        if (user instanceof PlayerEntity playerEntity) {
            PersistentProjectileEntity projectile;
            ItemStack itemStack = playerEntity.getArrowType(stack);
            int useTicks = this.getMaxUseTime(stack) - remainingUseTicks;
            float pullProgress = getPullProgress(useTicks, itemStack);
            if (itemStack.isEmpty()){
                projectile = new MoltenProjectileEntity(playerEntity, world);
                projectile.setVelocity(playerEntity, playerEntity.getPitch(), playerEntity.getYaw(), 0.0F, pullProgress * 1.5F, 0.0F);
            } else {
                projectile = ((ArrowItem) Items.ARROW).createArrow(world, itemStack, playerEntity);
                projectile.setVelocity(playerEntity, playerEntity.getPitch(), playerEntity.getYaw(), 0.0F, pullProgress * 4.5F, 0.5F);
            }
            world.spawnEntity(projectile);
        }
    }

    public static float getPullProgress(int useTicks, ItemStack itemStack) {
        float useSeconds = (float)useTicks / 20.0F;
        useSeconds = (useSeconds * useSeconds + useSeconds * 2.0F) / (itemStack.isEmpty() ? 8.0f : 3.0f);
        if (useSeconds > 1.0F) {
            useSeconds = 1.0F;
        }
        return useSeconds;
    }

}
