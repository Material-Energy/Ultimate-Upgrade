package materialenergy.ultimate.upgrade.common.registry;

import materialenergy.ultimate.upgrade.UltimateUpgrade;
import materialenergy.ultimate.upgrade.common.block.MoltenAnvilBlock;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.registry.Registry;

public class UUBlocks {

    public static final Block MOLTEN_BLOCK = register(
            "molten_block",
            new Block(
            FabricBlockSettings
                    .of(Material.METAL, MapColor.IRON_GRAY)
                    .strength(3.0f,3.0f)
                    .sounds(BlockSoundGroup.METAL)
                    .requiresTool()
            ),
            new FabricItemSettings()
                    .fireproof()
                    .group(UltimateUpgrade.UU_MAIN)
    );
    public static final Block MOLTEN_ANVIL = register(
            "molten_anvil",
            new MoltenAnvilBlock(
                    FabricBlockSettings
                            .of(Material.METAL, MapColor.IRON_GRAY)
                            .strength(3.0f,3.0f)
                            .sounds(BlockSoundGroup.METAL)
                            .requiresTool()
            ),
            new FabricItemSettings()
                    .fireproof()
                    .group(UltimateUpgrade.UU_MAIN)
    );


    private static Block register(String name, Block block, FabricItemSettings settings) {
        Block registered = Registry.register(Registry.BLOCK, Registries.id(name), block);
        Registry.register(Registry.ITEM,Registries.id(name), new BlockItem(block,settings));
        return registered;
    }

    public static void init(){

    }

}
