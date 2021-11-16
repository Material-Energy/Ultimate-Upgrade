package materialenergy.ultimate.upgrade;

import materialenergy.ultimate.upgrade.tools.MoltenPickaxe;
import materialenergy.ultimate.upgrade.tools.MoltenToolMaterial;
import materialenergy.ultimate.upgrade.tools.Ultimatum;
import materialenergy.ultimate.upgrade.tools.UltimatumMat;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings ;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.*;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;

public class UltimateUpgrade implements ModInitializer {

	public static Item ULTIMATUM = new Ultimatum(UltimatumMat.INSTANCE,0, 0.0F, new Item.Settings().fireproof().rarity(Rarity.EPIC));

	public static Item MOLTEN_SHOVEL = new ShovelItem(MoltenToolMaterial.INSTANCE, 1.5F, -3.0F, new Item.Settings().fireproof());

	public static Item MOLTEN_SWORD = new SwordItem(MoltenToolMaterial.INSTANCE, 3, -2.4F, new Item.Settings().fireproof());

	public static Item MOLTEN_PICKAXE = new MoltenPickaxe(MoltenToolMaterial.INSTANCE, 1, -2.8F, new Item.Settings().fireproof());

	public static final Item MOLTEN_INGOT = new Item(new FabricItemSettings().fireproof());

	public static final Item MOLTEN_ALLOY = new Item(new FabricItemSettings().fireproof());

	public static BlockEntityType<UUBlockEntity> UU_BLOCK_ENTITY;

	public static final Block MOLTEN_BLOCK = new Block(FabricBlockSettings.of(Material.METAL).strength(3.0f,10.0f).sounds(BlockSoundGroup.METAL).breakByTool(FabricToolTags.PICKAXES, 2).requiresTool());

	public static final ItemGroup UU_MAIN = FabricItemGroupBuilder.create(
		new Identifier("ultimateupgrade", "main"))
		.icon(() -> new ItemStack(MOLTEN_BLOCK))
		.appendItems(stacks -> {
			stacks.add(new ItemStack(MOLTEN_ALLOY));
			stacks.add(new ItemStack(MOLTEN_INGOT));
			stacks.add(new ItemStack(MOLTEN_BLOCK));
			stacks.add(new ItemStack(MOLTEN_SWORD));
			stacks.add(new ItemStack(MOLTEN_SHOVEL));
			stacks.add(new ItemStack(MOLTEN_PICKAXE));
			stacks.add(new ItemStack(ULTIMATUM));
		})
		.build();

	@Override
	public void onInitialize() {
		Registry.register(Registry.ITEM, new Identifier("ultimateupgrade", "molten_ingot"), MOLTEN_INGOT);
		Registry.register(Registry.ITEM, new Identifier("ultimateupgrade", "molten_alloy"), MOLTEN_ALLOY);
		Registry.register(Registry.BLOCK, new Identifier("ultimateupgrade", "molten_block"),MOLTEN_BLOCK);
        Registry.register(Registry.ITEM, new Identifier("ultimateupgrade", "molten_block"), new BlockItem(MOLTEN_BLOCK, new FabricItemSettings().group(UU_MAIN).fireproof()));
		Registry.register(Registry.ITEM, new Identifier("ultimateupgrade","molten_sword"),MOLTEN_SWORD);
		Registry.register(Registry.ITEM, new Identifier("ultimateupgrade","molten_pickaxe"),MOLTEN_PICKAXE);
		Registry.register(Registry.ITEM, new Identifier("ultimateupgrade","molten_shovel"),MOLTEN_SHOVEL);
		Registry.register(Registry.ITEM, new Identifier("ultimateupgrade","ultimatum"),ULTIMATUM);
	}
}

