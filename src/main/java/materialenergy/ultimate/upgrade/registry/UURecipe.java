package materialenergy.ultimate.upgrade.registry;

import materialenergy.ultimate.upgrade.misc.EnderEnergyChargeRecipe;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialRecipeSerializer;
import net.minecraft.util.registry.Registry;

public class UURecipe {
    public static final SpecialRecipeSerializer<EnderEnergyChargeRecipe> EE_CHARGE = register("crafting_special_enderenergycharge", new SpecialRecipeSerializer<>(EnderEnergyChargeRecipe::new));

    public static <S extends RecipeSerializer<T>, T extends Recipe<?>> S register(String id, S serializer) {
        return Registry.register(Registry.RECIPE_SERIALIZER, Registries.id(id), serializer);
    }

    public static void init(){

    }
}
