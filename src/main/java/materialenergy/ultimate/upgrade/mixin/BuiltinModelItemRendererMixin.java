package materialenergy.ultimate.upgrade.mixin;

import com.mojang.authlib.GameProfile;
import com.mojang.datafixers.util.Pair;
import materialenergy.ultimate.upgrade.entities.DraconicTridentEntityModel;
import materialenergy.ultimate.upgrade.registry.UUItems;
import net.minecraft.block.*;
import net.minecraft.block.entity.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BannerBlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.SkullBlockEntityModel;
import net.minecraft.client.render.block.entity.SkullBlockEntityRenderer;
import net.minecraft.client.render.entity.model.ShieldEntityModel;
import net.minecraft.client.render.entity.model.TridentEntityModel;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.*;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.util.DyeColor;
import org.apache.commons.lang3.StringUtils;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;
import java.util.Map;

@Mixin(BuiltinModelItemRenderer.class)
public class BuiltinModelItemRendererMixin {
    @Final
    @Shadow
    private static ShulkerBoxBlockEntity[] RENDER_SHULKER_BOX_DYED;
    @Final
    @Shadow
    private static ShulkerBoxBlockEntity RENDER_SHULKER_BOX;
    @Final
    @Shadow
    private ChestBlockEntity renderChestNormal;
    @Final
    @Shadow
    private ChestBlockEntity renderChestTrapped;
    @Final
    @Shadow
    private EnderChestBlockEntity renderChestEnder;
    @Final
    @Shadow
    private BannerBlockEntity renderBanner;
    @Final
    @Shadow
    private BedBlockEntity renderBed;
    @Final
    @Shadow
    private ConduitBlockEntity renderConduit;
    @Shadow
    private ShieldEntityModel modelShield;
    @Shadow
    private TridentEntityModel modelTrident;
    @Shadow
    private Map<SkullBlock.SkullType, SkullBlockEntityModel> skullModels;
    @Final
    @Shadow
    private BlockEntityRenderDispatcher blockEntityRenderDispatcher;

    /**
     * @author MaterialEnergy
     * @reason adding new renders
     */
    @Overwrite
    public void render(ItemStack stack, ModelTransformation.Mode mode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        Item item = stack.getItem();
        if (item instanceof BlockItem) {
            Block block = ((BlockItem)item).getBlock();
            if (block instanceof AbstractSkullBlock) {
                GameProfile gameProfile = null;
                if (stack.hasNbt()) {
                    NbtCompound nbtCompound = stack.getOrCreateNbt();
                    if (nbtCompound.contains("SkullOwner", 10)) {
                        gameProfile = NbtHelper.toGameProfile(nbtCompound.getCompound("SkullOwner"));
                    } else if (nbtCompound.contains("SkullOwner", 8) && !StringUtils.isBlank(nbtCompound.getString("SkullOwner"))) {
                        gameProfile = new GameProfile(null, nbtCompound.getString("SkullOwner"));
                        nbtCompound.remove("SkullOwner");
                        SkullBlockEntity.loadProperties(gameProfile, (gameProfilex) -> nbtCompound.put("SkullOwner", NbtHelper.writeGameProfile(new NbtCompound(), gameProfilex)));
                    }
                }

                SkullBlock.SkullType nbtCompound = ((AbstractSkullBlock)block).getSkullType();
                SkullBlockEntityModel skullBlockEntityModel = this.skullModels.get(nbtCompound);
                RenderLayer renderLayer = SkullBlockEntityRenderer.getRenderLayer(nbtCompound, gameProfile);
                SkullBlockEntityRenderer.renderSkull(null, 180.0F, 0.0F, matrices, vertexConsumers, light, skullBlockEntityModel, renderLayer);
            } else {
                BlockState nbtCompound = block.getDefaultState();
                BlockEntity gameProfile;
                if (block instanceof AbstractBannerBlock) {
                    this.renderBanner.readFrom(stack, ((AbstractBannerBlock)block).getColor());
                    gameProfile = this.renderBanner;
                } else if (block instanceof BedBlock) {
                    this.renderBed.setColor(((BedBlock)block).getColor());
                    gameProfile = this.renderBed;
                } else if (nbtCompound.isOf(Blocks.CONDUIT)) {
                    gameProfile = this.renderConduit;
                } else if (nbtCompound.isOf(Blocks.CHEST)) {
                    gameProfile = this.renderChestNormal;
                } else if (nbtCompound.isOf(Blocks.ENDER_CHEST)) {
                    gameProfile = this.renderChestEnder;
                } else if (nbtCompound.isOf(Blocks.TRAPPED_CHEST)) {
                    gameProfile = this.renderChestTrapped;
                } else {
                    if (!(block instanceof ShulkerBoxBlock)) {
                        return;
                    }

                    DyeColor skullBlockEntityModel = ShulkerBoxBlock.getColor(item);
                    if (skullBlockEntityModel == null) {
                        gameProfile = RENDER_SHULKER_BOX;
                    } else {
                        gameProfile = RENDER_SHULKER_BOX_DYED[skullBlockEntityModel.getId()];
                    }
                }

                this.blockEntityRenderDispatcher.renderEntity(gameProfile, matrices, vertexConsumers, light, overlay);
            }
        } else {
            if (stack.isOf(Items.SHIELD)) {
                boolean block = stack.getSubNbt("BlockEntityTag") != null;
                matrices.push();
                matrices.scale(1.0F, -1.0F, -1.0F);
                SpriteIdentifier gameProfile = block ? ModelLoader.SHIELD_BASE : ModelLoader.SHIELD_BASE_NO_PATTERN;
                VertexConsumer nbtCompound = gameProfile.getSprite().getTextureSpecificVertexConsumer(ItemRenderer.getDirectItemGlintConsumer(vertexConsumers, this.modelShield.getLayer(gameProfile.getAtlasId()), true, stack.hasGlint()));
                this.modelShield.getHandle().render(matrices, nbtCompound, light, overlay, 1.0F, 1.0F, 1.0F, 1.0F);
                if (block) {
                    List<Pair<BannerPattern, DyeColor>> skullBlockEntityModel = BannerBlockEntity.getPatternsFromNbt(ShieldItem.getColor(stack), BannerBlockEntity.getPatternListTag(stack));
                    BannerBlockEntityRenderer.renderCanvas(matrices, vertexConsumers, light, overlay, this.modelShield.getPlate(), gameProfile, false, skullBlockEntityModel, stack.hasGlint());
                } else {
                    this.modelShield.getPlate().render(matrices, nbtCompound, light, overlay, 1.0F, 1.0F, 1.0F, 1.0F);
                }

                matrices.pop();
            } else if (stack.isOf(Items.TRIDENT)) {
                matrices.push();
                matrices.scale(1.0F, -1.0F, -1.0F);
                VertexConsumer block = ItemRenderer.getDirectItemGlintConsumer(vertexConsumers, this.modelTrident.getLayer(TridentEntityModel.TEXTURE), false, stack.hasGlint());
                this.modelTrident.render(matrices, block, light, overlay, 1.0F, 1.0F, 1.0F, 1.0F);
                matrices.pop();
            } else if (stack.isOf(UUItems.DRACONIC_TRIDENT)){
                matrices.push();
                matrices.scale(1.0F, -1.0F, -1.0F);
                VertexConsumer block = ItemRenderer.getDirectItemGlintConsumer(vertexConsumers, this.modelTrident.getLayer(DraconicTridentEntityModel.TEXTURE), false, stack.hasGlint());
                this.modelTrident.render(matrices, block, light, overlay, 1.0F, 1.0F, 1.0F, 1.0F);
                matrices.pop();
            }

        }
    }
}
