package materialenergy.ultimate.upgrade.mixin.display;

import materialenergy.ultimate.upgrade.item.draconic.DraconicCrossbowItem;
import materialenergy.ultimate.upgrade.registry.UUItems;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(HeldItemRenderer.class)
public abstract class HeldCrossbowRenderer {

    @Final @Shadow private MinecraftClient client;
    @Shadow private ItemStack offHand;

    @Inject(
            method = "getHandRenderType",
            at = @At("HEAD"),
            cancellable = true
    )
    private static void setCrossbow(ClientPlayerEntity player, CallbackInfoReturnable<HeldItemRenderer.HandRenderType> cir){
        ItemStack itemStack = player.getMainHandStack();
        ItemStack itemStack2 = player.getOffHandStack();
        boolean b = itemStack.isOf(Items.BOW) ;
        boolean ob = itemStack2.isOf(Items.BOW);
        boolean b2 = itemStack.isOf(Items.CROSSBOW) ;
        boolean ob2 = itemStack2.isOf(Items.CROSSBOW);
        boolean b3 = itemStack.isOf(UUItems.DRACONIC_CROSSBOW) ;
        boolean ob3 = itemStack2.isOf(UUItems.DRACONIC_CROSSBOW);
        if (b2 || b3){
            cir.setReturnValue(HeldItemRenderer.HandRenderType.RENDER_MAIN_HAND_ONLY);
        } else if (ob2 || ob3){
            cir.setReturnValue(HeldItemRenderer.HandRenderType.RENDER_OFF_HAND_ONLY);
        } else if (b && player.isUsingItem()){
            cir.setReturnValue(HeldItemRenderer.HandRenderType.RENDER_MAIN_HAND_ONLY);
        } else if (ob && player.isUsingItem()){
            cir.setReturnValue(HeldItemRenderer.HandRenderType.RENDER_OFF_HAND_ONLY);
        } else {
            cir.setReturnValue(HeldItemRenderer.HandRenderType.RENDER_BOTH_HANDS);
        }
    }

    @Inject(
            method = "renderFirstPersonItem",
            at = @At("HEAD"),
            cancellable = true
    )
    public void addDraconicCrossbow(AbstractClientPlayerEntity player, float tickDelta, float pitch, Hand hand, float swingProgress, ItemStack item, float equipProgress, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CallbackInfo ci){
        if (player.isUsingSpyglass()) {
            return;
        }
        boolean bl = hand == Hand.MAIN_HAND;
        Arm arm = bl ? player.getMainArm() : player.getMainArm().getOpposite();
        matrices.push();
        if (item.isEmpty()) {
            if (bl && !player.isInvisible()) {
                this.renderArmHoldingItem(matrices, vertexConsumers, light, equipProgress, swingProgress, arm);
            }
        } else if (item.isOf(Items.FILLED_MAP)) {
            if (bl && this.offHand.isEmpty()) {
                this.renderMapInBothHands(matrices, vertexConsumers, light, pitch, equipProgress, swingProgress);
            } else {
                this.renderMapInOneHand(matrices, vertexConsumers, light, equipProgress, arm, swingProgress, item);
            }
        } else if (item.isOf(Items.CROSSBOW) || item.isOf(UUItems.DRACONIC_CROSSBOW)) {
            int i;
            boolean bl2 = CrossbowItem.isCharged(item);
            boolean bl3 = arm == Arm.RIGHT;
            i = bl3 ? 1 : -1;
            if (player.isUsingItem() && player.getItemUseTimeLeft() > 0 && player.getActiveHand() == hand) {
                this.applyEquipOffset(matrices, arm, equipProgress);
                matrices.translate((float)i * -0.4785682f, -0.094387f, 0.05731530860066414);
                matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(-11.935f));
                matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion((float)i * 65.3f));
                matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion((float)i * -9.785f));
                float f = (float)item.getMaxUseTime() - ((float)this.client.player.getItemUseTimeLeft() - tickDelta + 1.0f);
                float g = f / (float)CrossbowItem.getPullTime(item);
                if (g > 1.0f) {
                    g = 1.0f;
                }
                if (g > 0.1f) {
                    float h = MathHelper.sin((f - 0.1f) * 1.3f);
                    float j = g - 0.1f;
                    float k = h * j;
                    matrices.translate(k * 0.0f, k * 0.004f, k * 0.0f);
                }
                matrices.translate(g * 0.0f, g * 0.0f, g * 0.04f);
                matrices.scale(1.0f, 1.0f, 1.0f + g * 0.2f);
                matrices.multiply(Vec3f.NEGATIVE_Y.getDegreesQuaternion((float)i * 45.0f));
            } else {
                float f = -0.4f * MathHelper.sin(MathHelper.sqrt(swingProgress) * (float)Math.PI);
                float g = 0.2f * MathHelper.sin(MathHelper.sqrt(swingProgress) * ((float)Math.PI * 2));
                float h = -0.2f * MathHelper.sin(swingProgress * (float)Math.PI);
                matrices.translate((float)i * f, g, h);
                this.applyEquipOffset(matrices, arm, equipProgress);
                this.applySwingOffset(matrices, arm, swingProgress);
                if (bl2 && swingProgress < 0.001f && bl) {
                    matrices.translate((float)i * -0.641864f, 0.0, 0.0);
                    matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion((float)i * 10.0f));
                }
            }
            this.renderItem(player, item, bl3 ? ModelTransformation.Mode.FIRST_PERSON_RIGHT_HAND : ModelTransformation.Mode.FIRST_PERSON_LEFT_HAND, !bl3, matrices, vertexConsumers, light);
        } else {
            boolean bl2;
            bl2 = arm == Arm.RIGHT;
            if (player.isUsingItem() && player.getItemUseTimeLeft() > 0 && player.getActiveHand() == hand) {
                int bl32 = bl2 ? 1 : -1;
                switch (item.getUseAction()) {
                    case NONE, BLOCK -> this.applyEquipOffset(matrices, arm, equipProgress);
                    case EAT, DRINK -> {
                        this.applyEatOrDrinkTransformation(matrices, tickDelta, arm, item);
                        this.applyEquipOffset(matrices, arm, equipProgress);
                    }
                    case BOW -> {
                        this.applyEquipOffset(matrices, arm, equipProgress);
                        matrices.translate((float) bl32 * -0.2785682f, 0.18344387412071228, 0.15731531381607056);
                        matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(-13.935f));
                        matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion((float) bl32 * 35.3f));
                        matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion((float) bl32 * -9.785f));
                        float i = (float) item.getMaxUseTime() - ((float) this.client.player.getItemUseTimeLeft() - tickDelta + 1.0f);
                        float f = i / 20.0f;
                        f = (f * f + f * 2.0f) / 3.0f;
                        if (f > 1.0f) {
                            f = 1.0f;
                        }
                        if (f > 0.1f) {
                            float g = MathHelper.sin((i - 0.1f) * 1.3f);
                            float h = f - 0.1f;
                            float j = g * h;
                            matrices.translate(j * 0.0f, j * 0.004f, j * 0.0f);
                        }
                        matrices.translate(f * 0.0f, f * 0.0f, f * 0.04f);
                        matrices.scale(1.0f, 1.0f, 1.0f + f * 0.2f);
                        matrices.multiply(Vec3f.NEGATIVE_Y.getDegreesQuaternion((float) bl32 * 45.0f));
                    }
                    case SPEAR -> {
                        this.applyEquipOffset(matrices, arm, equipProgress);
                        matrices.translate((float) bl32 * -0.5f, 0.7f, 0.1f);
                        matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(-55.0f));
                        matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion((float) bl32 * 35.3f));
                        matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion((float) bl32 * -9.785f));
                        float i = (float) item.getMaxUseTime() - ((float) this.client.player.getItemUseTimeLeft() - tickDelta + 1.0f);
                        float f = i / 10.0f;
                        if (f > 1.0f) {
                            f = 1.0f;
                        }
                        if (f > 0.1f) {
                            float g = MathHelper.sin((i - 0.1f) * 1.3f);
                            float h = f - 0.1f;
                            float j = g * h;
                            matrices.translate(j * 0.0f, j * 0.004f, j * 0.0f);
                        }
                        matrices.translate(0.0, 0.0, f * 0.2f);
                        matrices.scale(1.0f, 1.0f, 1.0f + f * 0.2f);
                        matrices.multiply(Vec3f.NEGATIVE_Y.getDegreesQuaternion((float) bl32 * 45.0f));
                    }
                }
            } else if (player.isUsingRiptide()) {
                this.applyEquipOffset(matrices, arm, equipProgress);
                int bl33 = bl2 ? 1 : -1;
                matrices.translate((float)bl33 * -0.4f, 0.8f, 0.3f);
                matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion((float)bl33 * 65.0f));
                matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion((float)bl33 * -85.0f));
            } else {
                float bl34 = -0.4f * MathHelper.sin(MathHelper.sqrt(swingProgress) * (float)Math.PI);
                float i = 0.2f * MathHelper.sin(MathHelper.sqrt(swingProgress) * ((float)Math.PI * 2));
                float f = -0.2f * MathHelper.sin(swingProgress * (float)Math.PI);
                int g = bl2 ? 1 : -1;
                matrices.translate((float)g * bl34, i, f);
                this.applyEquipOffset(matrices, arm, equipProgress);
                this.applySwingOffset(matrices, arm, swingProgress);
            }
            this.renderItem(player, item, bl2 ? ModelTransformation.Mode.FIRST_PERSON_RIGHT_HAND : ModelTransformation.Mode.FIRST_PERSON_LEFT_HAND, !bl2, matrices, vertexConsumers, light);
        }
        matrices.pop();
        ci.cancel();
    }

    private static HeldItemRenderer.HandRenderType getUsingItemHandRenderType(ClientPlayerEntity player) {
        ItemStack itemStack = player.getActiveItem();
        Hand hand = player.getActiveHand();
        if (itemStack.isOf(Items.BOW) || itemStack.isOf(Items.CROSSBOW) || itemStack.isOf(UUItems.DRACONIC_CROSSBOW)) {
            return HeldItemRenderer.HandRenderType.shouldOnlyRender(hand);
        }
        return hand == Hand.MAIN_HAND && HeldCrossbowRenderer.isChargedCrossbow(player.getOffHandStack()) ? HeldItemRenderer.HandRenderType.RENDER_MAIN_HAND_ONLY : HeldItemRenderer.HandRenderType.RENDER_BOTH_HANDS;
    }

    private static boolean isChargedCrossbow(ItemStack stack) {
        return (stack.isOf(Items.CROSSBOW) || stack.isOf(UUItems.DRACONIC_CROSSBOW)) && CrossbowItem.isCharged(stack);
    }

    @Shadow protected abstract void renderMapInOneHand(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, float equipProgress, Arm arm, float swingProgress, ItemStack item);
    @Shadow protected abstract void applyEatOrDrinkTransformation(MatrixStack matrices, float tickDelta, Arm arm, ItemStack item);
    @Shadow protected abstract void renderMapInBothHands(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, float pitch, float equipProgress, float swingProgress);
    @Shadow public abstract void renderItem(LivingEntity entity, ItemStack stack, ModelTransformation.Mode renderMode, boolean leftHanded, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light);
    @Shadow protected abstract void applySwingOffset(MatrixStack matrices, Arm arm, float swingProgress);
    @Shadow protected abstract void applyEquipOffset(MatrixStack matrices, Arm arm, float equipProgress);
    @Shadow protected abstract void renderArmHoldingItem(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, float equipProgress, float swingProgress, Arm arm);
}
