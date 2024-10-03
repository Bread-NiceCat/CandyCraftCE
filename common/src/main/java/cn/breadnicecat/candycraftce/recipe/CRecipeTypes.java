package cn.breadnicecat.candycraftce.recipe;

import cn.breadnicecat.candycraftce.CandyCraftCE;
import cn.breadnicecat.candycraftce.recipe.recipes.RecipeSerializerExt;
import cn.breadnicecat.candycraftce.recipe.recipes.SugarFactoryRecipe;
import cn.breadnicecat.candycraftce.recipe.recipes.SugarFurnaceRecipe;
import cn.breadnicecat.candycraftce.utils.CLogUtils;
import cn.breadnicecat.candycraftce.utils.SimpleEntry;
import net.minecraft.core.registries.BuiltInRegistries;
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
		SimpleEntry<RecipeType<?>, RecipeType<T>> wrap1 = CandyCraftCE.register(BuiltInRegistries.RECIPE_TYPE, id, () -> new RecipeType<>() {
			@Override
			public String toString() {
				return id.toString();
			}
		});
		var wrap2 = CandyCraftCE.register(BuiltInRegistries.RECIPE_SERIALIZER, id, serializer);
		return new RecipeTypeEntry<>(wrap1, wrap2);
	}
	
	public static void init() {
	}
}
