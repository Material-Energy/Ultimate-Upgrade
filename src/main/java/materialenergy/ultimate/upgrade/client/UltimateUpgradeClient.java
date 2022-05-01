package materialenergy.ultimate.upgrade.client;

import materialenergy.ultimate.upgrade.common.Registries;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.minecraft.client.particle.ExplosionLargeParticle;
import net.minecraft.screen.PlayerScreenHandler;

import static materialenergy.ultimate.upgrade.common.Registries.id;

public class UltimateUpgradeClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientSpriteRegistryCallback.event(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE).register((atlasTexture, registry) -> registry.register(id("particle/molten_explosion")));

        ParticleFactoryRegistry.getInstance().register(Registries.MOLTEN_EXPLOSION, ExplosionLargeParticle.Factory::new);
    }
}
