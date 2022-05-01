package materialenergy.ultimate.upgrade.mixin;

import materialenergy.ultimate.upgrade.common.api.MouseWheel;
import materialenergy.ultimate.upgrade.common.registry.UUMisc;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Mouse.class)
public class MouseMixin {

    @Final @Shadow private MinecraftClient client;
    @Shadow private double x;
    @Shadow private double y;
    @Shadow private double eventDeltaWheel;

    @Inject(
            method = "onMouseScroll",
            at = @At("HEAD"),
            cancellable = true
    )
    private void addFunction(long window, double horizontal, double vertical, CallbackInfo ci){
        if (window == MinecraftClient.getInstance().getWindow().getHandle()) {
            double d = (this.client.options.discreteMouseScroll ? Math.signum(vertical) : vertical) * this.client.options.mouseWheelSensitivity;
            if (this.client.getOverlay() == null) {
                if (this.client.currentScreen != null) {
                    double e = this.x * (double)this.client.getWindow().getScaledWidth() / (double)this.client.getWindow().getWidth();
                    double f = this.y * (double)this.client.getWindow().getScaledHeight() / (double)this.client.getWindow().getHeight();
                    this.client.currentScreen.mouseScrolled(e, f, d);
                    this.client.currentScreen.applyMousePressScrollNarratorDelay();
                } else if (this.client.player != null) {
                    if (this.eventDeltaWheel != 0.0 && Math.signum(d) != Math.signum(this.eventDeltaWheel)) {
                        this.eventDeltaWheel = 0.0;
                    }
                    this.eventDeltaWheel += d;
                    float e = (int)this.eventDeltaWheel;
                    if (e == 0.0f) {
                        return;
                    }
                    this.eventDeltaWheel -= e;
                    if (this.client.player.isSpectator()) {
                        if (this.client.inGameHud.getSpectatorHud().isOpen()) {
                            this.client.inGameHud.getSpectatorHud().cycleSlot(-e);
                        } else {
                            float g = MathHelper.clamp(this.client.player.getAbilities().getFlySpeed() + e * 0.005f, 0.0f, 0.2f);
                            this.client.player.getAbilities().setFlySpeed(g);
                        }
                    } else {
                        ItemStack offhandStack = this.client.player.getStackInHand(Hand.OFF_HAND);
                        ItemStack mainhandStack = this.client.player.getStackInHand(Hand.MAIN_HAND);
                        boolean offhand = offhandStack.isIn(UUMisc.SCROLLABLE);
                        boolean mainhand = mainhandStack.isIn(UUMisc.SCROLLABLE);
                        if (mainhand && Screen.hasAltDown()){
                            ((MouseWheel) mainhandStack.getItem()).onMouseScrolled(mainhandStack, e, this.client.player);
                        } else if (offhand && Screen.hasAltDown()){
                            ((MouseWheel) offhandStack.getItem()).onMouseScrolled(offhandStack, e, this.client.player);
                        } else {
                            this.client.player.getInventory().scrollInHotbar(e);
                        }
                    }
                }
            }
        }
        ci.cancel();
    }
}
