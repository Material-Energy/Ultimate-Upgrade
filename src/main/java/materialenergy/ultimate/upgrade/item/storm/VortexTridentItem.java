package materialenergy.ultimate.upgrade.item.storm;

import materialenergy.ultimate.upgrade.entities.DraconicTridentEntity;
import materialenergy.ultimate.upgrade.registry.UUItems;
import materialenergy.ultimate.upgrade.registry.UUMisc;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.TridentItem;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.world.World;

public class VortexTridentItem extends TridentItem {
    public VortexTridentItem(Settings settings) {
        super(settings);
    }

    @Override
    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
        super.usageTick(world, user, stack, remainingUseTicks);
        if (remainingUseTicks % 2 == 0 &&
                world instanceof ServerWorld &&
                user instanceof PlayerEntity
        ){
            Inventory inventory = ((PlayerEntity) user).getInventory();
            for (int k = 0; k < inventory.size(); k++){
                ItemStack currentStack = inventory.getStack(k);
                if(!currentStack.isEmpty() &&
                        EnchantmentHelper.getRiptide(currentStack) == 0 &&
                        UUMisc.TRIDENTS.contains(currentStack.getItem())
                ){
                    currentStack.damage(1, user, p -> p.sendToolBreakStatus(user.getActiveHand()));
                    stack.damage(1, user, p -> p.sendToolBreakStatus(user.getActiveHand()));
                    if (currentStack.isOf(UUItems.DRACONIC_TRIDENT)){
                        DraconicTridentEntity trident = new DraconicTridentEntity(world, user, currentStack);
                        if (((PlayerEntity) user).getAbilities().creativeMode) {
                            trident.pickupType = PersistentProjectileEntity.PickupPermission.CREATIVE_ONLY;
                        }
                        this.spawnTrident(world, user, trident);
                    } else {
                        TridentEntity trident = new TridentEntity(world, user, currentStack);
                        this.spawnTrident(world, user, trident);
                    }
                    if (!((PlayerEntity) user).getAbilities().creativeMode) {
                        ((PlayerEntity) user).getInventory().removeOne(currentStack);
                    }
                    break;
                }
            }
        }
    }

    private <T extends PersistentProjectileEntity> void spawnTrident(World world, LivingEntity user, T trident) {
        if (trident == null) return;
        trident.setProperties(user, user.getPitch(), user.getYaw(), 0.0F, 5.0f, 1.0F);
        world.spawnEntity(trident);
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        if (!(user instanceof PlayerEntity playerEntity)) {
            return;
        }
        int useTicks = this.getMaxUseTime(stack) - remainingUseTicks;
        if (useTicks < 10) {
            return;
        }
        if (!world.isClient) {
            stack.damage(1, playerEntity, p -> p.sendToolBreakStatus(user.getActiveHand()));
            TridentEntity tridentEntity = new TridentEntity(world, playerEntity, stack);
            tridentEntity.setProperties(playerEntity, playerEntity.getPitch(), playerEntity.getYaw(), 0.0f, 2.5f, 1.0f);
            if (playerEntity.getAbilities().creativeMode) {
                tridentEntity.pickupType = PersistentProjectileEntity.PickupPermission.CREATIVE_ONLY;
            }
            world.spawnEntity(tridentEntity);
            world.playSoundFromEntity(null, tridentEntity, SoundEvents.ITEM_TRIDENT_THROW, SoundCategory.PLAYERS, 1.0f, 1.0f);
            if (!playerEntity.getAbilities().creativeMode) {
                playerEntity.getInventory().removeOne(stack);
            }
        }
        playerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
    }
}
