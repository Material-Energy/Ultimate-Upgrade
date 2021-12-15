package materialenergy.ultimate.upgrade.mixin;


import materialenergy.ultimate.upgrade.misc.Rune;
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Enchantment.class)
public abstract class RunicEnchant implements Rune {
    public boolean isRunic;
    public Enchantment baseEnchant;
    @Nullable @Shadow
    protected String translationKey;
    @Shadow public abstract String getTranslationKey();
    @Shadow public abstract int getMaxLevel();

    @Override
    public void set(Enchantment enchantment) {
        this.isRunic = true;
        this.baseEnchant = enchantment;
    }

    @Override
    public Enchantment getVanilla() {
        return this.baseEnchant;
    }

    @Override
    public boolean getRunic() {
        return this.isRunic;
    }

    @Inject(method = "getOrCreateTranslationKey", at = @At("RETURN"))
    public void getOrCreateTranslationKey(CallbackInfoReturnable<String> cir){
        if (this.getRunic()){
            Identifier regID = Registry.ENCHANTMENT.getId((Enchantment) (Object) this);
            this.translationKey = Util.createTranslationKey("enchantment", new Identifier(regID.getPath().replace("runic_", "")));
        }
    }

    @Inject(
            method = "getName",
            at = @At("RETURN"),
            cancellable = true
    )
    private void modifyName(int level, CallbackInfoReturnable<Text> cir) {
        Text ret = cir.getReturnValue();
        if(isRunic) {
            ret = new LiteralText(ret.asString().replace(" enchantment.level." + level,""));
            if (level > this.getMaxLevel()){
                level = this.getMaxLevel();
            }

            MutableText append;
            
            append = new LiteralText(level == 1 ? "Δ ": "Ω ").formatted(level == 1 ? Formatting.AQUA: Formatting.GOLD).append(ret);

            if(FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
                append.append(
                        Screen.hasControlDown() ?
                                new TranslatableText(this.getTranslationKey()) :
                                new TranslatableText(this.getTranslationKey()).formatted(Formatting.OBFUSCATED)
                );
            }

            cir.setReturnValue(append);
        }
    }

    @Inject(
            method = "getMaxLevel",
            at = @At("RETURN"),
            cancellable = true
    )
    private void modifyMaxLevel(CallbackInfoReturnable<Integer> cir){
        if(isRunic){
            cir.setReturnValue(2);
        }
    }

    @Inject(
            method = "isTreasure",
            at = @At("RETURN"),
            cancellable = true
    )
    private void modifyTreasure(CallbackInfoReturnable<Boolean> cir){
        if(isRunic){
            cir.setReturnValue(true);
        }
    }

    @Inject(
            method = "isAvailableForRandomSelection",
            at = @At("RETURN"),
            cancellable = true
    )
    private void modifyRandomSelection(CallbackInfoReturnable<Boolean> cir){
        if(isRunic){
            cir.setReturnValue(false);
        }
    }

    @Inject(
            method = "isAvailableForEnchantedBookOffer",
            at = @At("RETURN"),
            cancellable = true
    )
    private void modifyEnchantedBook(CallbackInfoReturnable<Boolean> cir){
        if(isRunic){
            cir.setReturnValue(false);
        }
    }

    @Inject(
            method = "getRarity",
            at = @At("RETURN"),
            cancellable = true
    )
    private void modifyRarity(CallbackInfoReturnable<Enchantment.Rarity> cir){
        if(isRunic){
            cir.setReturnValue(Enchantment.Rarity.VERY_RARE);
        }
    }



}
