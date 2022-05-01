package materialenergy.ultimate.upgrade.common.registry;

import materialenergy.ultimate.upgrade.common.entities.DraconicArrow;
import materialenergy.ultimate.upgrade.common.entities.DraconicTridentEntity;
import materialenergy.ultimate.upgrade.common.entities.MoltenProjectileEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.registry.Registry;

public class UUEntities {
    public static final EntityType<MoltenProjectileEntity> MOLTEN_ARROW = Registry.register(
            Registry.ENTITY_TYPE,
            Registries.id("molten_arrow"),
            FabricEntityTypeBuilder
                    .<MoltenProjectileEntity>create(SpawnGroup.MISC, MoltenProjectileEntity::new)
                    .dimensions(EntityDimensions.fixed(0.5f, 0.5f))
                    .build());
    public static EntityType<DraconicArrow> DRACONIC_FLARE = Registry.register(
            Registry.ENTITY_TYPE,
            Registries.id("draconic_flare"),
            FabricEntityTypeBuilder
                    .<DraconicArrow>create(SpawnGroup.MISC, DraconicArrow::new)
                    .dimensions(EntityDimensions.fixed(0.5f, 0.5f))
                    .build());
    public static EntityType<DraconicTridentEntity> DRACONIC_TRIDENT = Registry.register(
            Registry.ENTITY_TYPE,
            Registries.id("draconic_trident"),
            FabricEntityTypeBuilder
                    .<DraconicTridentEntity>create(SpawnGroup.MISC,DraconicTridentEntity::new)
                    .dimensions(EntityDimensions.fixed(0.5f, 0.5f))
                    .build());

    public static void init() {
    }
}
