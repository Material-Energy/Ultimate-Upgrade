package materialenergy.ultimate.upgrade.registry;

import materialenergy.ultimate.upgrade.item.draconic.DraconicBaseItem;
import net.fabricmc.fabric.api.loot.v1.event.LootTableLoadingCallback;
import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.util.Identifier;

public class UUMisc {


    public static void init() {
        LootTableLoadingCallback.EVENT.register(
                (resourceManager, manager, id, supplier, setter) -> {
                    if(id.toString().equals("minecraft:entities/ender_dragon")) {
                    LootPool crystals = LootPool.builder()
                            .with(ItemEntry.builder(UUItems.RAW_DRACONIC_CRYSTAL).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(10F, 20F))))
                            .rolls(ConstantLootNumberProvider.create(1))
                            .build();

                    supplier.withPool(crystals);
                    }

                }
        );

    }

    public static void initClient(){
        FabricModelPredicateProviderRegistry.register(UUItems.MOLTEN_BOW, new Identifier("pull"), (itemStack, clientWorld, livingEntity, seed) -> {
            if (livingEntity == null) {
                return 0.0F;
            }
            return livingEntity.getActiveItem() != itemStack ? 0.0F : (itemStack.getMaxUseTime() - livingEntity.getItemUseTimeLeft()) / 20.0F;
        });
        FabricModelPredicateProviderRegistry.register(UUItems.MOLTEN_BOW, new Identifier("pulling"), (itemStack, clientWorld, livingEntity, seed) -> {
            if (livingEntity == null) {
                return 0.0F;
            }
            return livingEntity.isUsingItem() && livingEntity.getActiveItem() == itemStack ? 1.0F : 0.0F;
        });


        FabricModelPredicateProviderRegistry.register(UUItems.DRACONIC_CROSSBOW, new Identifier("charged"), (itemStack, clientWorld, livingEntity, seed) -> {
            if (livingEntity == null) {
                return 0.0F;
            }
            return CrossbowItem.isCharged(itemStack) ? 1.0f : 0.0f;
        });
        FabricModelPredicateProviderRegistry.register(UUItems.DRACONIC_CROSSBOW, new Identifier("pulling"), (itemStack, clientWorld, livingEntity, seed) -> {
            if (livingEntity == null) {
                return 0.0F;
            }
            return livingEntity.isUsingItem() && livingEntity.getActiveItem() == itemStack && !CrossbowItem.isCharged(itemStack) ? 1.0f : 0.0f;
        });
        FabricModelPredicateProviderRegistry.register(UUItems.DRACONIC_CROSSBOW, new Identifier("pull"), (stack, world, entity, seed) -> {
            if (entity == null) {
                return 0.0f;
            }
            if (CrossbowItem.isCharged(stack)) {
                return 0.0f;
            }
            return (float)(stack.getMaxUseTime() - entity.getItemUseTimeLeft()) / (float)CrossbowItem.getPullTime(stack);
        });
        FabricModelPredicateProviderRegistry.register(UUItems.DRACONIC_CROSSBOW, new Identifier("energy"), (stack, world, entity, seed) -> {
            if (entity == null) {
                return 0.0f;
            }
            return DraconicBaseItem.getEnderEnergy(stack) > 0? 1.0f : 0.0f;
        });


        FabricModelPredicateProviderRegistry.register(UUItems.DRACONIC_TOTEM, new Identifier("energy"), (stack, world, entity, seed) -> (float)DraconicBaseItem.getEnderEnergy(stack) / ((DraconicBaseItem)stack.getItem()).getTotalEnergy());


        FabricModelPredicateProviderRegistry.register(UUItems.DRACONIC_TRIDENT, new Identifier("throwing"), (itemStack, clientWorld, livingEntity, seed) -> {
            if (livingEntity == null) {
                return 0.0F;
            }
            return livingEntity.isUsingItem() && livingEntity.getActiveItem() == itemStack ? 1.0F : 0.0F;
        });
    }
}
