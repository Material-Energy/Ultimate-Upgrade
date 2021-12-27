package materialenergy.ultimate.upgrade.mixin.display;

import materialenergy.ultimate.upgrade.registry.UUItems;
import net.minecraft.block.StainedGlassPaneBlock;
import net.minecraft.block.TransparentBlock;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.client.render.item.ItemModels;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemRenderer.class)
public abstract class BakedCustomModel {

    @Final @Shadow private ItemModels models;
    @Final @Shadow private BuiltinModelItemRenderer builtinModelItemRenderer;
    @Shadow protected abstract void renderBakedItemModel(BakedModel model, ItemStack stack, int light, int overlay, MatrixStack matrices, VertexConsumer vertexConsumer);

    @Inject(
            method = "getHeldItemModel",
            at = @At(value = "HEAD"),
            cancellable = true
    )
    public void addHeldModels(ItemStack stack, World world, LivingEntity entity, int seed, CallbackInfoReturnable<BakedModel> cir){
        if (stack.isOf(UUItems.DRACONIC_TRIDENT)){
            BakedModel uuBakedModel = this.models.getModelManager().getModel(new ModelIdentifier("ultimateupgrade:draconic_trident_in_hand#inventory"));
            ClientWorld clientWorld = world instanceof ClientWorld ? (ClientWorld)world : null;
            BakedModel uubakedModel2 = uuBakedModel.getOverrides().apply(uuBakedModel, stack, clientWorld, entity, seed);
            cir.setReturnValue(uubakedModel2 == null ? this.models.getModelManager().getMissingModel() : uubakedModel2);
        }
    }

    /**
     * @author MaterialEnergy
     * @reason adds trident
     */
    @Overwrite
    public void renderItem(ItemStack stack, ModelTransformation.Mode renderMode, boolean leftHanded, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, BakedModel model) {
        boolean bl;
        if (stack.isEmpty()) {
            return;
        }
        matrices.push();
        bl = renderMode == ModelTransformation.Mode.GUI || renderMode == ModelTransformation.Mode.GROUND || renderMode == ModelTransformation.Mode.FIXED;
        if (bl) {
            if (stack.isOf(Items.TRIDENT)) {
                model = this.models.getModelManager().getModel(new ModelIdentifier("minecraft:trident#inventory"));
            } else if (stack.isOf(Items.SPYGLASS)) {
                model = this.models.getModelManager().getModel(new ModelIdentifier("minecraft:spyglass#inventory"));
            } else if (stack.isOf(UUItems.DRACONIC_TRIDENT)){
                model = this.models.getModelManager().getModel(new ModelIdentifier("ultimateupgrade:draconic_trident#inventory"));
            }
        }
        model.getTransformation().getTransformation(renderMode).apply(leftHanded, matrices);
        matrices.translate(-0.5, -0.5, -0.5);
        if (model.isBuiltin() || stack.isOf(Items.TRIDENT) && !bl || stack.isOf(UUItems.DRACONIC_TRIDENT) && !bl) {
            this.builtinModelItemRenderer.render(stack, renderMode, matrices, vertexConsumers, light, overlay);
        } else {
            VertexConsumer vertexConsumer;
            Object block;
            boolean bl22 = renderMode == ModelTransformation.Mode.GUI || renderMode.isFirstPerson() || !(stack.getItem() instanceof BlockItem) || !((block = ((BlockItem) stack.getItem()).getBlock()) instanceof TransparentBlock) && !(block instanceof StainedGlassPaneBlock);
            block = RenderLayers.getItemLayer(stack, bl22);
            if (stack.isOf(Items.COMPASS) && stack.hasGlint()) {
                matrices.push();
                MatrixStack.Entry entry = matrices.peek();
                if (renderMode == ModelTransformation.Mode.GUI) {
                    entry.getModel().multiply(0.5f);
                } else if (renderMode.isFirstPerson()) {
                    entry.getModel().multiply(0.75f);
                }
                vertexConsumer = bl22 ? ItemRenderer.getDirectCompassGlintConsumer(vertexConsumers, (RenderLayer)block, entry) : ItemRenderer.getCompassGlintConsumer(vertexConsumers, (RenderLayer)block, entry);
                matrices.pop();
            } else {
                vertexConsumer = bl22 ? ItemRenderer.getDirectItemGlintConsumer(vertexConsumers, (RenderLayer)block, true, stack.hasGlint()) : ItemRenderer.getItemGlintConsumer(vertexConsumers, (RenderLayer)block, true, stack.hasGlint());
            }
            this.renderBakedItemModel(model, stack, light, overlay, matrices, vertexConsumer);
        }
        matrices.pop();
    }

}
