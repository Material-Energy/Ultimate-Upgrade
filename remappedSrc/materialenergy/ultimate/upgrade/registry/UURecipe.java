package materialenergy.ultimate.upgrade.common.registry;

import materialenergy.ultimate.upgrade.common.misc.DraconicCrossbowRecipe;
import materialenergy.ultimate.upgrade.common.misc.DraconicTotemRecipe;
import materialenergy.ultimate.upgrade.common.misc.DraconicTridentRecipe;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialRecipeSerializer;
import net.minecraft.util.registry.Registry;

public class UURecipe {
    public static final SpecialRecipeSerializer<DraconicTridentRecipe> ENDER_TRIDENT = register("crafting_ender_trident", new SpecialRecipeSerializer<>(DraconicTridentRecipe::new));
    public static final SpecialRecipeSerializer<DraconicTotemRecipe> ENDER_TOTEM = register("crafting_ender_totem", new SpecialRecipeSerializer<>(DraconicTotemRecipe::new));
    public static final SpecialRecipeSerializer<DraconicCrossbowRecipe> ENDER_CROSSBOW = register("crafting_ender_crossbow", new SpecialRecipeSerializer<>(DraconicCrossbowRecipe::new));

    public static <S extends RecipeSerializer<T>, T extends Recipe<?>> S register(String id, S serializer) {
        return Registry.register(Registry.RECIPE_SERIALIZER, Registries.id(id), serializer);
    }

    public static void init(){

    }
}
