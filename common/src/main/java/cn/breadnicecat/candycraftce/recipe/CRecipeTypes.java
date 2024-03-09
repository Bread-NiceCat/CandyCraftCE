package cn.breadnicecat.candycraftce.recipe;

import cn.breadnicecat.candycraftce.EngineFeatures;
import cn.breadnicecat.candycraftce.recipe.recipes.SugarFactoryRecipe;
import cn.breadnicecat.candycraftce.recipe.recipes.SugarFurnaceRecipe;
import cn.breadnicecat.candycraftce.utils.CLogUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import org.slf4j.Logger;

import java.util.function.Supplier;

import static cn.breadnicecat.candycraftce.utils.ResourceUtils.prefix;

public class CRecipeTypes {
	private static final Logger LOGGER = CLogUtils.sign();

	public static final RecipeTypeEntry<SugarFurnaceRecipe> SUGAR_FURNACE_TYPE = register("sugar_furnace", SugarFurnaceRecipe.Serializer::new);
	public static final RecipeTypeEntry<SugarFactoryRecipe> SUGAR_FACTORY_TYPE = register("sugar_factory", SugarFactoryRecipe.Serializer::new);


	public static <T extends Recipe<?>> RecipeTypeEntry<T> register(String name, Supplier<RecipeSerializerExt<T>> serializer) {
		ResourceLocation id = prefix(name);
		return EngineFeatures.get().registerRecipe(id, () -> new RecipeType<>() {
			@Override
			public String toString() {
				return id.toString();
			}
		}, serializer);
	}

	public static void init() {
	}
}
