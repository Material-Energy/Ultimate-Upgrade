package materialenergy.ultimate.upgrade;

import materialenergy.ultimate.upgrade.entities.DraconicShardRenderer;
import materialenergy.ultimate.upgrade.entities.DraconicTridentRenderer;
import materialenergy.ultimate.upgrade.entities.MoltenProjRenderer;
import materialenergy.ultimate.upgrade.registry.UUEntities;
import materialenergy.ultimate.upgrade.registry.UUGUI;
import materialenergy.ultimate.upgrade.registry.UUMisc;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.EmptyEntityRenderer;

public class UltimateUpgradeClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(UUEntities.MOLTEN_ARROW, MoltenProjRenderer::new);
        EntityRendererRegistry.register(UUEntities.DRACONIC_TRIDENT, DraconicTridentRenderer::new);
        EntityRendererRegistry.register(UUEntities.DRACONIC_SHARD, DraconicShardRenderer::new);
        EntityRendererRegistry.register(UUEntities.DRACONIC_FLARE, EmptyEntityRenderer::new);
        UUGUI.init();
        UUMisc.initClient();
    }
}
