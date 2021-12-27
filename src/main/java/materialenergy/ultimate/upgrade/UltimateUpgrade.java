package materialenergy.ultimate.upgrade;

import materialenergy.ultimate.upgrade.registry.*;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.loot.v1.event.LootTableLoadingCallback;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.util.Identifier;


public class UltimateUpgrade implements ModInitializer {

	public static final ItemGroup UU_MAIN = FabricItemGroupBuilder.create(
		new Identifier("ultimateupgrade", "main"))
		.icon(() -> new ItemStack(UUBlocks.MOLTEN_BLOCK))
		.build();

	@Override
	public void onInitialize() {
		UUItems.init();
		UUBlocks.init();
		UUEffects.init();
		UUEntities.init();
		UUEnchantments.init();
		UUCommands.init();
		UUGUI.init();
		UURecipe.init();
		UUMisc.init();

	}
}

