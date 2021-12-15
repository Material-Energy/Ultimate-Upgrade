package materialenergy.ultimate.upgrade.misc;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;

import java.util.Random;

public class HealthRenderer extends DrawableHelper {
    private final Random random;

    public HealthRenderer() {
        this.random = new Random();
    }

    public void render(MatrixStack matrices, PlayerEntity player, int x, int y, int lines, int regeneratingHeartIndex, float maxHealth, int lastHealth, int health, int absorption, boolean blinking) {
        HeartType heartType = HeartType.fromPlayerState(player);
        int hardcore = 9 * (player.world.getLevelProperties().isHardcore() ? 5 : 0);
        int maxHp = 10;
        int HP = MathHelper.ceil(((double)health/(double)maxHealth) * maxHp * 2);
        int lastHP = MathHelper.ceil(((double)lastHealth/(double)maxHealth) * maxHp * 2);
        absorption = MathHelper.ceil((double)absorption);
        int halfHeartMax = maxHp * 2;
        for (int iterator = maxHp + absorption/2 - 1; iterator >= 0; --iterator) {
            int halfHP;
            int iterByTen = iterator / 10;
            int iterRemainTen = iterator % 10;
            int lining = x + iterRemainTen * 8;
            int spacing = y - iterByTen * lines;
            if (lastHP + absorption <= 4) {
                spacing += this.random.nextInt(2);
            }
            if (iterator < maxHp && iterator == regeneratingHeartIndex) {
                spacing -= 2;
            }
            this.drawHeart(matrices, HeartType.CONTAINER, lining, spacing, hardcore, blinking, false);
            int iterHalfHP = iterator * 2;
            boolean passedHearts = iterator >= maxHp;
            if (passedHearts && (halfHP = iterHalfHP - halfHeartMax) < absorption) {
                boolean bl22 = halfHP + 1 == absorption;
                this.drawHeart(matrices, heartType == HeartType.WITHERED ? heartType : HeartType.ABSORBING, lining, spacing, hardcore, false, bl22);
            }
            if (blinking && iterHalfHP < HP) {
                halfHP = iterHalfHP + 1 == HP ? 1 : 0;
                this.drawHeart(matrices, heartType, lining, spacing, hardcore, true, halfHP != 0);
            }
            if (iterHalfHP >= lastHP) continue;
            halfHP = iterHalfHP + 1 == lastHP ? 1 : 0;
            this.drawHeart(matrices, heartType, lining, spacing, hardcore, false, halfHP != 0);
        }
    }

    private void drawHeart(MatrixStack matrices, HeartType type, int x, int y, int v, boolean blinking, boolean halfHeart) {
        this.drawTexture(matrices, x, y, type.getU(halfHeart, blinking), v, 9, 9);
    }



    @Environment(value= EnvType.CLIENT)
    enum HeartType {
        CONTAINER(0, false),
        NORMAL(2, true),
        POISIONED(4, true),
        WITHERED(6, true),
        ABSORBING(8, false),
        FROZEN(9, false);

        private final int textureIndex;
        private final boolean hasBlinkingTexture;

        HeartType(int textureIndex, boolean hasBlinkingTexture) {
            this.textureIndex = textureIndex;
            this.hasBlinkingTexture = hasBlinkingTexture;
        }

        public int getU(boolean halfHeart, boolean blinking) {
            int i;
            if (this == CONTAINER) {
                i = blinking ? 1 : 0;
            } else {
                int j = halfHeart ? 1 : 0;
                int k = this.hasBlinkingTexture && blinking ? 2 : 0;
                i = j + k;
            }
            return 16 + (this.textureIndex * 2 + i) * 9;
        }

        static HeartType fromPlayerState(PlayerEntity player) {
            return player.hasStatusEffect(StatusEffects.POISON) ? POISIONED : (player.hasStatusEffect(StatusEffects.WITHER) ? WITHERED : (player.isFreezing() ? FROZEN : NORMAL));
        }
    }
}
