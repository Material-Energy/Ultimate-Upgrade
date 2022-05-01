package materialenergy.ultimate.upgrade.common;

import materialenergy.ultimate.upgrade.common.entity.MoltenProjectileEntity;
import materialenergy.ultimate.upgrade.common.item.MoltenBowItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.item.Item;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static materialenergy.ultimate.upgrade.common.UltimateUpgrade.MODID;
import static materialenergy.ultimate.upgrade.common.UltimateUpgrade.UU_MAIN;

public class Registries {


    public static final Item MOLTEN_BOW = Registry.register(Registry.ITEM, id("molten_bow"), new MoltenBowItem(new FabricItemSettings().maxDamage(845).group(UU_MAIN).fireproof()));

    public static final EntityType<MoltenProjectileEntity> MOLTEN_PROJECTILE_ENTITY_ENTITY_TYPE = Registry.register(
            Registry.ENTITY_TYPE,
            id("molten_projectile"),
            FabricEntityTypeBuilder.<MoltenProjectileEntity>create(
                    SpawnGroup.MISC, MoltenProjectileEntity::new)
                    .dimensions(EntityDimensions.fixed(0.5f,0.5f))
                    .build()
    );

    public static final ParticleType<DefaultParticleType> MOLTEN_EXPLOSION = Registry.register(Registry.PARTICLE_TYPE, id("molten_explosion"), FabricParticleTypes.simple());

    public static Identifier id(String id) {
        return new Identifier(MODID, id);
    }

    public static void load() {

    }
}
