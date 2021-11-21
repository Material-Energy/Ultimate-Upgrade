package materialenergy.ultimate.upgrade.registry;

import materialenergy.ultimate.upgrade.UltimateUpgrade;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.registry.Registry;

public class UUBlocks {

    public static final Block MOLTEN_BLOCK = register(
            "molten_block",
            new Block(
            FabricBlockSettings
                    .of(Material.METAL)
                    .strength(3.0f,10.0f)
                    .sounds(BlockSoundGroup.METAL)
                    .breakByTool(FabricToolTags.PICKAXES, 2)
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
