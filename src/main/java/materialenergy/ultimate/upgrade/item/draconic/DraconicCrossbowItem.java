package materialenergy.ultimate.upgrade.item.draconic;

import materialenergy.ultimate.upgrade.entities.DraconicArrow;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.RangedWeaponItem;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.ItemTags;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

import java.util.function.Predicate;

public class DraconicCrossbowItem extends DraconicBaseItem {

    public DraconicCrossbowItem(Settings settings) {
        super(settings);
    }

    @Override
    public int getTotalEnergy(){
        return 256;
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.CROSSBOW;
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return DraconicCrossbowItem.getPullTime(stack) + 3;
    }

    public static int getPullTime(ItemStack stack) {
        return 15 * (stack.isEmpty() ? 0 : 1);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        if (CrossbowItem.isCharged(itemStack)) {
            this.shoot(world, user, hand, itemStack, DraconicCrossbowItem.getArrow(itemStack));
            CrossbowItem.setCharged(itemStack, false);
            return TypedActionResult.consume(itemStack);
        } else {
            if (!this.getArrowType(user, itemStack).isEmpty()) {
                user.setCurrentHand(hand);
                return TypedActionResult.consume(itemStack);
            }
        }
        return TypedActionResult.fail(itemStack);
    }

    private ItemStack getArrowType(PlayerEntity user, ItemStack cb){
        PlayerInventory inventory = user.getInventory();
        Predicate<ItemStack> predicate;
        if (DraconicBaseItem.getEnderEnergy(cb) > 0){
            predicate = stack -> stack.isOf(Items.SPECTRAL_ARROW);
        } else {
            predicate = stack -> stack.isIn(ItemTags.ARROWS);
        }
        ItemStack itemStack = RangedWeaponItem.getHeldProjectile(user, predicate);
        if (!itemStack.isEmpty()) {
            return itemStack;
        }
        for (int i = 0; i < inventory.size(); ++i) {
            ItemStack itemStack2 = inventory.getStack(i);
            if (!predicate.test(itemStack2)) continue;
            return itemStack2;
        }
        return user.getAbilities().creativeMode ? new ItemStack(Items.ARROW) : ItemStack.EMPTY;
    }

    private static boolean getArrow(ItemStack itemStack) {
        return itemStack.getOrCreateNbt().getBoolean("isNonEnergyArrow");
    }

    public void shoot(World world, LivingEntity shooter, Hand hand, ItemStack cb, boolean arrow) {
        if (world.isClient){
            return;
        }
        cb.damage(1, shooter, e -> e.sendToolBreakStatus(hand));
        if (!arrow) {
            DraconicArrow proj = new DraconicArrow(shooter, world, DraconicBaseItem.getEnderEnergy(cb));
            proj.setProperties(shooter, shooter.getPitch(), shooter.getYaw(), 0f, 6f, 0.01f * (this.getTotalEnergy() - DraconicBaseItem.getEnderEnergy(cb)));
            world.spawnEntity(proj);
        } else {
            ArrowEntity proj = new ArrowEntity(world, shooter);
            proj.setProperties(shooter, shooter.getPitch(), shooter.getYaw(), 0f, 4.15f, 0f);
            proj.setDamage(proj.getDamage() + (double)5 * 0.5 + 0.5);
            proj.setCritical(true);
            world.spawnEntity(proj);
        }
    }

    private static float getPullProgress(int useTicks, ItemStack stack) {
        float f = (float)useTicks / (float)DraconicCrossbowItem.getPullTime(stack);
        if (f > 1.0f) {
            f = 1.0f;
        }
        return f;
    }

    @Override
    public boolean isUsedOnRelease(ItemStack stack) {
        return stack.isOf(this);
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        int i = this.getMaxUseTime(stack) - remainingUseTicks;
        float f = DraconicCrossbowItem.getPullProgress(i, stack);
        if (f >= 1.0f && !CrossbowItem.isCharged(stack)) {
            CrossbowItem.setCharged(stack, true);
            DraconicCrossbowItem.setArrow(stack, !this.getArrowType((PlayerEntity) user, stack).isEmpty() && DraconicBaseItem.getEnderEnergy(stack) == 0);
            SoundCategory soundCategory = SoundCategory.PLAYERS;
            world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ITEM_CROSSBOW_LOADING_END, soundCategory, 1.0f, 1.0f / (world.getRandom().nextFloat() * 0.5f + 1.0f) + 0.2f);
        }
    }

    private static void setArrow(ItemStack stack, boolean b) {
        stack.getOrCreateNbt().putBoolean("isNonEnergyArrow",b);
    }
}
