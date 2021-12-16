package materialenergy.ultimate.upgrade.mixin.hearts;

import materialenergy.ultimate.upgrade.misc.HealthRenderer;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(value = {InGameHud.class})
public abstract class UnrenderHearts {


    /**
     * @author MaterialEnergy
     * @reason reduce lag by minimizing the number of hearts
     */
    @Overwrite
    private void renderHealthBar(MatrixStack matrices, PlayerEntity player, int x, int y, int lines, int regeneratingHeartIndex, float maxHealth, int lastHealth, int health, int absorption, boolean blinking){
        HealthRenderer healthRenderer = new HealthRenderer();
        healthRenderer.render(matrices, player, x, y, lines, regeneratingHeartIndex, maxHealth, lastHealth, health, absorption, blinking);
    }
}
