package materialenergy.ultimate.upgrade.registry;

import materialenergy.ultimate.upgrade.entities.MoltenProj;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.registry.Registry;

public class UUEntities {
    public static final EntityType<MoltenProj> MOLTEN_ARROW = Registry.register(
            Registry.ENTITY_TYPE,
            Registries.id("molten_arrow"),
            FabricEntityTypeBuilder
                    .<MoltenProj>create(SpawnGroup.MISC, MoltenProj::new)
                    .dimensions(EntityDimensions.fixed(0.5f, 0.5f))
                    .build());

    public static void init() {
    }
}
