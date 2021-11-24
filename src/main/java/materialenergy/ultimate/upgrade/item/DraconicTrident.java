package materialenergy.ultimate.upgrade.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import materialenergy.ultimate.upgrade.entities.DraconicTridentEntity;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.TridentItem;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;

import java.util.Random;

public class DraconicTrident extends TridentItem {
    private final ImmutableMultimap<EntityAttribute, EntityAttributeModifier> attributeModifiers;

    public DraconicTrident(FabricItemSettings fabricItemSettings) {
        super(fabricItemSettings);
        ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, "Tool modifier", 8.0D, EntityAttributeModifier.Operation.ADDITION));
        builder.put(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier(ATTACK_SPEED_MODIFIER_ID, "Tool modifier", -2.9000000953674316D, EntityAttributeModifier.Operation.ADDITION));
        this.attributeModifiers = builder.build();
    }

    public boolean canMine(BlockState state, World world, BlockPos pos, PlayerEntity miner) {
        return !miner.isCreative();
    }


    public UseAction getUseAction(ItemStack stack) {
        return UseAction.SPEAR;
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 36000;
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        if (user instanceof PlayerEntity playerEntity) {
            int i = this.getMaxUseTime(stack) - remainingUseTicks;
            if (i >= 10) {
                int j = EnchantmentHelper.getRiptide(stack);
                if (j >= 0) {
                    if (!world.isClient) {
                        if (j == 0) {
                            stack.damage(1, user, (e) -> e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));
                            DraconicTridentEntity tridentEntity = new DraconicTridentEntity(world, playerEntity, stack);
                            tridentEntity.setProperties(playerEntity, playerEntity.getPitch(), playerEntity.getYaw(), 0.0F, 2.5F + (float)j * 0.5F, 1.0F);
                            if (playerEntity.getAbilities().creativeMode) {
                                tridentEntity.pickupType = PersistentProjectileEntity.PickupPermission.CREATIVE_ONLY;
                            }

                            world.spawnEntity(tridentEntity);
                            world.playSoundFromEntity(null, tridentEntity, SoundEvents.ITEM_TRIDENT_THROW, SoundCategory.PLAYERS, 1.0F, 1.0F);
                            if (!playerEntity.getAbilities().creativeMode) {
                                playerEntity.getInventory().removeOne(stack);
                            }
                        }
                    }

                    playerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
                    if (j > 0) {
                        float tridentEntity = playerEntity.getYaw();
                        float f = playerEntity.getPitch();
                        float g = -MathHelper.sin(tridentEntity * 0.017453292F) * MathHelper.cos(f * 0.017453292F);
                        float h = -MathHelper.sin(f * 0.017453292F);
                        float k = MathHelper.cos(tridentEntity * 0.017453292F) * MathHelper.cos(f * 0.017453292F);
                        float l = MathHelper.sqrt(g * g + h * h + k * k);
                        float m = 3.0F * ((1.0F + (float)j) / 4.0F);
                        g *= m / l;
                        h *= m / l;
                        k *= m / l;
                        playerEntity.addVelocity(g, h, k);
                        DimensionType dimension = playerEntity.getEntityWorld().getDimension();
                        playerEntity.setRiptideTicks(dimension.hasFixedTime() && !dimension.isUltrawarm()? 60 : 20);
                        if (playerEntity.isOnGround()) {
                            float n = 1.1999999F;
                            playerEntity.move(MovementType.SELF, new Vec3d(0.0D, 1.1999999284744263D, 0.0D));
                        }

                        SoundEvent n;
                        if (j >= 3) {
                            n = SoundEvents.ITEM_TRIDENT_RIPTIDE_3;
                        } else if (j == 2) {
                            n = SoundEvents.ITEM_TRIDENT_RIPTIDE_2;
                        } else {
                            n = SoundEvents.ITEM_TRIDENT_RIPTIDE_1;
                        }

                        world.playSoundFromEntity(null, playerEntity, n, SoundCategory.PLAYERS, 1.0F, 1.0F);
                    }

                }
            }
        }
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        if (itemStack.getDamage() >= itemStack.getMaxDamage() - 1) {
            return TypedActionResult.fail(itemStack);
        } else {
            user.setCurrentHand(hand);
            return TypedActionResult.consume(itemStack);
        }
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if(new Random().nextInt(2) == 1) {
            stack.damage(1, attacker, (e) -> e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));
        }
        return true;
    }

    @Override
    public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
        if ((double)state.getHardness(world, pos) != 0.0D) {
            stack.damage(1, miner, (e) -> e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));
        }

        return true;
    }

    public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(EquipmentSlot slot) {
        return slot == EquipmentSlot.MAINHAND ? this.attributeModifiers : super.getAttributeModifiers(slot);
    }

    @Override
    public int getEnchantability() {
        return 3;
    }
}
