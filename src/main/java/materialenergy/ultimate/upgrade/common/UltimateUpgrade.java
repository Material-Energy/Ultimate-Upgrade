package materialenergy.ultimate.upgrade.common;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;


public class UltimateUpgrade implements ModInitializer {

	public static final String MODID = "ultimateupgrade";

	public static final ItemGroup UU_MAIN = FabricItemGroupBuilder.create(
		new Identifier(MODID, "main"))
		.icon(() -> new ItemStack(Items.LAVA_BUCKET))
		.build();

	public static final TagKey<Item> AUTOSMELT = TagKey.of(Registry.ITEM_KEY, new Identifier(MODID, "autosmelt"));

	@Override
	public void onInitialize() {
		Registries.load();
	}
}

