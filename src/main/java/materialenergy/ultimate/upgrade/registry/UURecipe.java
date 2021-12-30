package materialenergy.ultimate.upgrade.registry;

import materialenergy.ultimate.upgrade.misc.DraconicCrossbowRecipe;
import materialenergy.ultimate.upgrade.misc.DraconicTotemRecipe;
import materialenergy.ultimate.upgrade.misc.DraconicTridentRecipe;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialRecipeSerializer;
import net.minecraft.util.registry.Registry;

public class UURecipe {
    public static final SpecialRecipeSerializer<DraconicTridentRecipe> ENDER_TRIDENT = register("crafting_special_endertrident", new SpecialRecipeSerializer<>(DraconicTridentRecipe::new));
    public static final SpecialRecipeSerializer<DraconicTotemRecipe> ENDER_TOTEM = register("crafting_special_endertotem", new SpecialRecipeSerializer<>(DraconicTotemRecipe::new));
    public static final SpecialRecipeSerializer<DraconicCrossbowRecipe> ENDER_CROSSBOW = register("crafting_special_endercrossbow", new SpecialRecipeSerializer<>(DraconicCrossbowRecipe::new));

    public static <S extends RecipeSerializer<T>, T extends Recipe<?>> S register(String id, S serializer) {
        return Registry.register(Registry.RECIPE_SERIALIZER, Registries.id(id), serializer);
    }

    public static void init(){

    }
}
