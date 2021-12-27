package materialenergy.ultimate.upgrade.item.molten;

import materialenergy.ultimate.upgrade.entities.MoltenProj;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
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
            boolean isInfinite = playerEntity.getAbilities().creativeMode || EnchantmentHelper.getLevel(Enchantments.INFINITY, stack) > 0;
            ItemStack arrowStack = playerEntity.getArrowType(stack);
            if (!arrowStack.isEmpty() || isInfinite) {
                if (arrowStack.isEmpty()) {
                    arrowStack = new ItemStack(Items.ARROW);
                }

                int useTicks = this.getMaxUseTime(stack) - remainingUseTicks;
                float pullProgress = getPullProgress(useTicks);
                if (!((double) pullProgress < 0.1D)) {
                    boolean canShootInfinity = isInfinite && arrowStack.isOf(Items.ARROW);
                    if (!world.isClient) {
                        MoltenProj moltenProj = createArrow(world, playerEntity);
                        moltenProj.setProperties(playerEntity, playerEntity.getPitch(), playerEntity.getYaw(), 0.0F, pullProgress * 3.0F, 1.0F);
                        if(pullProgress == 1.0F) {
                            moltenProj.setSupercharged(true);
                        }

                        int powerLevel = EnchantmentHelper.getLevel(Enchantments.POWER, stack);
                        if (pullProgress >= 0.5F) moltenProj.setDamage(moltenProj.getDamage() + 1.0D);
                        if (powerLevel > 0) {
                            moltenProj.setDamage(moltenProj.getDamage() + (double) powerLevel + 0.5D);
                        }

                        int punchLevel = EnchantmentHelper.getLevel(Enchantments.PUNCH, stack);
                        if (punchLevel > 0) {
                            moltenProj.setPunch(punchLevel);
                        }

                        stack.damage(1, playerEntity, (p) -> p.sendToolBreakStatus(playerEntity.getActiveHand()));
                        if (canShootInfinity || playerEntity.getAbilities().creativeMode && (arrowStack.isOf(Items.SPECTRAL_ARROW) || arrowStack.isOf(Items.TIPPED_ARROW))) {
                            moltenProj.pickupType = PersistentProjectileEntity.PickupPermission.CREATIVE_ONLY;
                        }

                        world.spawnEntity(moltenProj);
                    }

                    world.playSound(null, playerEntity.getX(), playerEntity.getY(), playerEntity.getZ(), SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F / (world.getRandom().nextFloat() * 0.4F + 1.2F) + pullProgress * 0.5F);
                    if (!canShootInfinity && !playerEntity.getAbilities().creativeMode) {
                        arrowStack.decrement(1);
                        if (arrowStack.isEmpty()) {
                            playerEntity.getInventory().removeOne(arrowStack);
                        }
                    }

                    playerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
                }
            }
        }
    }

    public MoltenProj createArrow(World world, LivingEntity shooter) {
        return new MoltenProj(world, shooter);
    }

    public int getMaxUseTime(ItemStack stack) {
        return 144000;
    }
}
