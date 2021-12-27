package materialenergy.ultimate.upgrade.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.ingame.AnvilScreen;
import net.minecraft.client.gui.screen.ingame.ForgingScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.AnvilScreenHandler;
import net.minecraft.screen.SmithingScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;

@Environment(value=EnvType.CLIENT)
public class MoltenAnvilScreen
        extends ForgingScreen<MoltenAnvilScreenHandler> {
    private static final Identifier TEXTURE = new Identifier("ultimateupgrade","textures/gui/container/molten_anvil.png");
    private final PlayerEntity player;

    public MoltenAnvilScreen(MoltenAnvilScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title, TEXTURE);
        this.player = inventory.player;
        this.titleX = 60;
    }

    @Override
    protected void drawForeground(MatrixStack matrices, int mouseX, int mouseY) {
        RenderSystem.disableBlend();
        super.drawForeground(matrices, mouseX, mouseY);
        int i = this.handler.getLevelCost();
        if (i > 0) {
            Text text;
            int j = 8453920;
            if (!(this.handler).getSlot(2).hasStack()) {
                text = null;
            } else {
                text = new TranslatableText("container.repair.cost").append(String.valueOf(i));
                if (!(this.handler).getSlot(2).canTakeItems(this.player)) {
                    j = 0xFF6060;
                }
            }
            if (text != null) {
                int k = this.backgroundWidth - 8 - this.textRenderer.getWidth(text) - 2;
                MoltenAnvilScreen.fill(matrices, k - 2, 67, this.backgroundWidth - 8, 79, 0x4F000000);
                this.textRenderer.drawWithShadow(matrices, text, (float)k, 69.0f, j);
            }
        }
    }
}

