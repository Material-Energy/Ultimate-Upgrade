package materialenergy.ultimate.upgrade.common.item.molten;

import materialenergy.ultimate.upgrade.common.entities.MoltenProjectileEntity;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.world.World;

public class MoltenBowItem extends BowItem {

    public MoltenBowItem(Settings settings) {
        super(settings);
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        if (user instanceof PlayerEntity playerEntity) {

            int useTicks = this.getMaxUseTime(stack) - remainingUseTicks;
            float pullProgress = getPullProgress(useTicks);
            if (((double) pullProgress > 0.9D)) {
                if (!world.isClient) {
                    MoltenProjectileEntity moltenProjectileEntity = createArrow(world, playerEntity);
                    moltenProjectileEntity.setProperties(playerEntity, playerEntity.getPitch(), playerEntity.getYaw(), 0.0F, pullProgress * 3.0F, 1.0F);

                    int powerLevel = EnchantmentHelper.getLevel(Enchantments.POWER, stack);
                    if (pullProgress >= 0.5F) moltenProjectileEntity.setDamage(moltenProjectileEntity.getDamage() + 1.0D);
                    if (powerLevel > 0) {
                        moltenProjectileEntity.setDamage(moltenProjectileEntity.getDamage() + (double) powerLevel + 0.5D);
                    }

                    int punchLevel = EnchantmentHelper.getLevel(Enchantments.PUNCH, stack);
                    if (punchLevel > 0) {
                        moltenProjectileEntity.setPunch(punchLevel);
                    }

                    stack.damage(1, playerEntity, (p) -> p.sendToolBreakStatus(playerEntity.getActiveHand()));

                    world.spawnEntity(moltenProjectileEntity);
                }

                world.playSound(null, playerEntity.getX(), playerEntity.getY(), playerEntity.getZ(), SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F / (world.getRandom().nextFloat() * 0.4F + 1.2F) + pullProgress * 0.5F);

                playerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
            }
        }
    }

    public MoltenProjectileEntity createArrow(World world, LivingEntity shooter) {
        return new MoltenProjectileEntity(world, shooter);
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 144000;
    }

    public static float getPullProgress(int useTicks) {
        float useSecs = (float)useTicks / 20.0f;
        if ((useSecs = (useSecs * useSecs + useSecs * 2.0f) / 8.0f) > 1.0f) {
            useSecs = 1.0f;
        }
        return useSecs;
    }
}
