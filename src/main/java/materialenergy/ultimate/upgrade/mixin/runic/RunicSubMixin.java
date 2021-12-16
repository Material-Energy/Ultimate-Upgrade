package materialenergy.ultimate.upgrade.mixin.runic;


import materialenergy.ultimate.upgrade.api.Rune;
import materialenergy.ultimate.upgrade.mixin.runic.RunicEnchant;
import net.minecraft.enchantment.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(value = {
        ProtectionEnchantment.class,
        MendingEnchantment.class,
        PiercingEnchantment.class,
        QuickChargeEnchantment.class,
        MultishotEnchantment.class,
        ChannelingEnchantment.class,
        RiptideEnchantment.class,
        ImpalingEnchantment.class,
        LoyaltyEnchantment.class,
        LureEnchantment.class,
        LuckEnchantment.class,
        InfinityEnchantment.class,
        FlameEnchantment.class,
        PunchEnchantment.class,
        PowerEnchantment.class,
        UnbreakingEnchantment.class,
        SilkTouchEnchantment.class,
        EfficiencyEnchantment.class,
        SweepingEnchantment.class,
        FireAspectEnchantment.class,
        KnockbackEnchantment.class,
        DamageEnchantment.class,
        SoulSpeedEnchantment.class,
        BindingCurseEnchantment.class,
        FrostWalkerEnchantment.class,
        DepthStriderEnchantment.class,
        ThornsEnchantment.class,
        AquaAffinityEnchantment.class,
        RespirationEnchantment.class,
        ProtectionEnchantment.class,
        VanishingCurseEnchantment.class
})
public abstract class RunicSubMixin extends RunicEnchant implements Rune {

    @ModifyConstant(
            method = "getMaxLevel",
            constant = {@Constant(intValue = 1),@Constant(intValue = 2),@Constant(intValue = 3),@Constant(intValue = 4),@Constant(intValue = 5)}
    )
    private int modifyMaxLevel(int level){
        return isRunic ? 2: level;
    }

}
