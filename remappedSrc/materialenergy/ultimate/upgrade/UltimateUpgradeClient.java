package materialenergy.ultimate.upgrade;

import materialenergy.ultimate.upgrade.client.DraconicTridentEntityRenderer;
import materialenergy.ultimate.upgrade.client.MoltenProjectileEntityRenderer;
import materialenergy.ultimate.upgrade.common.registry.UUEntities;
import materialenergy.ultimate.upgrade.common.registry.UUGUI;
import materialenergy.ultimate.upgrade.common.registry.UUMisc;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.EmptyEntityRenderer;

public class UltimateUpgradeClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(UUEntities.MOLTEN_ARROW, MoltenProjectileEntityRenderer::new);
        EntityRendererRegistry.register(UUEntities.DRACONIC_TRIDENT, DraconicTridentEntityRenderer::new);
        EntityRendererRegistry.register(UUEntities.DRACONIC_FLARE, EmptyEntityRenderer::new);
        UUGUI.init();
        UUMisc.initClient();
    }
}
