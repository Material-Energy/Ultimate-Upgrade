package materialenergy.ultimate.upgrade.enchantments;

import materialenergy.ultimate.upgrade.api.Rune;
import materialenergy.ultimate.upgrade.api.UUtil;
import materialenergy.ultimate.upgrade.registry.UUEnchantments;
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.enchantment.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.Vec3d;

import java.util.Map;

public class RunicEnchantment extends Enchantment implements Rune {
    private Enchantment defaultEnchantment;
    private int maxLevel = 0;

    public RunicEnchantment(Rarity weight, EnchantmentTarget type, EquipmentSlot... slotTypes) {
        super(weight, type, slotTypes);
    }

    public RunicEnchantment(Rarity rarity, EnchantmentTarget target, Enchantment defaultEnchantment, int level, EquipmentSlot... slots){
        this(rarity, target, slots);
        this.set(defaultEnchantment, level);
    }

    @Override
    public void set(Enchantment enchantment, int level) {
        this.defaultEnchantment = enchantment;
        this.maxLevel = level;
    }

    @Override
    public Enchantment getVanilla() {
        return this.defaultEnchantment;
    }

    @Override
    public int getMaxLevel() {
        return maxLevel;
    }

    @Override
    public boolean isAvailableForEnchantedBookOffer() {
        return false;
    }

    @Override
    public boolean isAvailableForRandomSelection() {
        return false;
    }

    @Override
    public Text getName(int level) {
        Text name = this.defaultEnchantment.getName(level);
        name = new LiteralText(name.asString().replace(" enchantment.level." + level,""));
        if (level > this.getMaxLevel()){
            level = this.getMaxLevel();
        }
        MutableText mutableText = (MutableText) name;
        if(FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
            mutableText = (
                    Screen.hasControlDown() ?
                            new TranslatableText(this.getTranslationKey()) :
                            new TranslatableText(this.getTranslationKey()).formatted(Formatting.OBFUSCATED)
            );
        }
        mutableText.append(UUtil.getSymbol(level));
        return mutableText;
    }

    @Override
    public String getTranslationKey() {
        return defaultEnchantment.getTranslationKey();
    }

    public boolean getProtection(DamageSource source){
        if (this.defaultEnchantment.equals(Enchantments.PROTECTION))
            return true;
        if (this.defaultEnchantment.equals(Enchantments.PROJECTILE_PROTECTION))
            return source.isProjectile();
        if (this.defaultEnchantment.equals(Enchantments.BLAST_PROTECTION))
            return source.isExplosive();
        if (this.defaultEnchantment.equals(Enchantments.FEATHER_FALLING))
            return source.isFromFalling();
        return false;
    }

    @Override
    public void onUserDamaged(LivingEntity user, Entity attacker, int level) {
        if (this.defaultEnchantment.equals(UUEnchantments.THORNS)){
            Map.Entry<EquipmentSlot, ItemStack> entry = EnchantmentHelper.chooseEquipmentWith(UUEnchantments.THORNS, user);
            if (attacker != null){
                attacker.damage(DamageSource.thorns(user), 4 * level);
                Vec3d atkVelocity = attacker.getVelocity();
                Vec3d vec3d = user.getRotationVec(-1.0f);
                double f = (attacker.getX() - (user.getX() + vec3d.x * level));
                double g = attacker.getBodyY(0.5) - (0.5 + user.getBodyY(0.5));
                double h = (attacker.getZ() - (user.getZ() + vec3d.z * level));
                atkVelocity = atkVelocity.add(f, g + level, h);
                attacker.setVelocity(atkVelocity);
            }
            if (entry != null) {
                entry.getValue().damage(maxLevel - level, user, entity -> entity.sendEquipmentBreakStatus(entry.getKey()));
            }
        }
    }

    @Override
    protected boolean canAccept(Enchantment other) {
        if (!other.equals(this.defaultEnchantment)){
            return true;
        }
        return this.defaultEnchantment instanceof DamageEnchantment;
    }
}
