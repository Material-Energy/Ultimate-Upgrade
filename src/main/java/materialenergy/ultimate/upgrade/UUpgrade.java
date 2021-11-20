package materialenergy.ultimate.upgrade;

import materialenergy.ultimate.upgrade.entities.MoltenProjRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class UUpgrade implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(UltimateUpgrade.MOLTEN_ARROW, (dispatcher) ->
                new MoltenProjRenderer(dispatcher));

    }
}
