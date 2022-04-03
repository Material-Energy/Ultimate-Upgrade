package materialenergy.ultimate.upgrade.mixin;

import materialenergy.ultimate.upgrade.api.HealthRenderer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Random;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin extends DrawableHelper {
    @Shadow private long heartJumpEndTick;
    @Shadow private int lastHealthValue;
    @Shadow private long lastHealthCheckTime;
    @Shadow private int renderHealthValue;
    @Final @Shadow private Random random;
    @Shadow private int scaledWidth;
    @Shadow private int ticks;
    @Shadow private int scaledHeight;
    @Final @Shadow private MinecraftClient client;
    @Shadow protected abstract int getHeartCount(LivingEntity w);
    @Shadow protected abstract int getHeartRows(int x);
    @Shadow protected abstract LivingEntity getRiddenEntity();
    @Shadow protected abstract PlayerEntity getCameraPlayer();



    /**
     * @author MaterialEnergy
     * @reason fix bar levels
     */
    @Overwrite
    private void renderStatusBars(MatrixStack matrices) {
        int ac;
        int ab;
        int aa;
        int z;
        int y;
        int x;
        PlayerEntity playerEntity = this.getCameraPlayer();
        if (playerEntity == null) {
            return;
        }
        int health = MathHelper.ceil(playerEntity.getHealth());
        boolean isHeartJumpEnd = this.heartJumpEndTick > (long)this.ticks && (this.heartJumpEndTick - (long)this.ticks) / 3L % 2L == 1L;
        long timeMs = Util.getMeasuringTimeMs();
        if (health < this.lastHealthValue && playerEntity.timeUntilRegen > 0) {
            this.lastHealthCheckTime = timeMs;
            this.heartJumpEndTick = this.ticks + 20;
        } else if (health > this.lastHealthValue && playerEntity.timeUntilRegen > 0) {
            this.lastHealthCheckTime = timeMs;
            this.heartJumpEndTick = this.ticks + 10;
        }
        if (timeMs - this.lastHealthCheckTime > 1000L) {
            this.lastHealthValue = health;
            this.renderHealthValue = health;
            this.lastHealthCheckTime = timeMs;
        }
        this.lastHealthValue = health;
        int renderHealthValue = this.renderHealthValue;
        this.random.setSeed(this.ticks * 312871L);
        HungerManager hungerManager = playerEntity.getHungerManager();
        int foodLevel = hungerManager.getFoodLevel();
        int scaledHeightArmor = this.scaledWidth / 2 - 91;
        int scaledHeightFood = this.scaledWidth / 2 + 91;
        int scaledHeightHearts = this.scaledHeight - 39;
        float entityMaxHealth = Math.max((float)playerEntity.getAttributeValue(EntityAttributes.GENERIC_MAX_HEALTH), (float)Math.max(renderHealthValue, health));
        int absorption = MathHelper.ceil(playerEntity.getAbsorptionAmount());
        int absorptionRows = MathHelper.ceil((20 + (float) absorption) / 2.0f / 10.0f);
        int spaceBetweenRows = Math.max(10 - (absorptionRows - 2), 3);
        int s = scaledHeightHearts - (absorptionRows - 1) * spaceBetweenRows - 10;
        int t = scaledHeightHearts - 10;
        int armor = playerEntity.getArmor();
        int regenTicks = -1;
        if (playerEntity.hasStatusEffect(StatusEffects.REGENERATION)) {
            regenTicks = this.ticks % MathHelper.ceil(entityMaxHealth + 5.0f);
        }
        this.client.getProfiler().push("armor");
        for (int w = 0; w < 10; ++w) {
            x = scaledHeightArmor + w * 8;
            if (w * 2 + 1 < armor) this.drawTexture(matrices, x, s, 34, 9, 9, 9);
            if (w * 2 + 1 == armor) this.drawTexture(matrices, x, s, 25, 9, 9, 9);
            if (w * 2 + 1 > armor) this.drawTexture(matrices, x, s, 16, 9, 9, 9);
            this.drawTexture(matrices, x, s, 43, 9, 9, 9);
        }
        this.client.getProfiler().swap("health");
        this.renderHealthBar(matrices, playerEntity, scaledHeightArmor, scaledHeightHearts, spaceBetweenRows, regenTicks, entityMaxHealth, health, renderHealthValue, absorption, isHeartJumpEnd);
        LivingEntity w = this.getRiddenEntity();
        x = this.getHeartCount(w);
        if (x == 0) {
            this.client.getProfiler().swap("food");
            for (y = 0; y < 10; ++y) {
                z = scaledHeightHearts;
                aa = 16;
                ab = 0;
                if (playerEntity.hasStatusEffect(StatusEffects.HUNGER)) {
                    aa += 36;
                    ab = 13;
                }
                if (playerEntity.getHungerManager().getSaturationLevel() <= 0.0f && this.ticks % (foodLevel * 3 + 1) == 0) {
                    z += this.random.nextInt(3) - 1;
                }
                ac = scaledHeightFood - y * 8 - 9;
                this.drawTexture(matrices, ac, z, 16 + ab * 9, 27, 9, 9);
                if (y * 2 + 1 < foodLevel) {
                    this.drawTexture(matrices, ac, z, aa + 36, 27, 9, 9);
                }
                if (y * 2 + 1 != foodLevel) continue;
                this.drawTexture(matrices, ac, z, aa + 45, 27, 9, 9);
            }
            t -= 10;
        }
        this.client.getProfiler().swap("air");
        y = playerEntity.getMaxAir();
        z = Math.min(playerEntity.getAir(), y);
        if (playerEntity.isSubmergedIn(FluidTags.WATER) || z < y) {
            aa = this.getHeartRows(x) - 1;
            t -= aa * 10;
            ab = MathHelper.ceil((double)(z - 2) * 10.0 / (double)y);
            ac = MathHelper.ceil((double)z * 10.0 / (double)y) - ab;
            for (int ad = 0; ad < ab + ac; ++ad) {
                if (ad < ab) {
                    this.drawTexture(matrices, scaledHeightFood - ad * 8 - 9, t, 16, 18, 9, 9);
                    continue;
                }
                this.drawTexture(matrices, scaledHeightFood - ad * 8 - 9, t, 25, 18, 9, 9);
            }
        }
        this.client.getProfiler().pop();
    }

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
