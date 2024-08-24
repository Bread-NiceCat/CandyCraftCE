package cn.breadnicecat.candycraftce.recipe;

import cn.breadnicecat.candycraftce.CandyCraftCE;
import cn.breadnicecat.candycraftce.recipe.recipes.RecipeSerializerExt;
import cn.breadnicecat.candycraftce.recipe.recipes.SugarFactoryRecipe;
import cn.breadnicecat.candycraftce.recipe.recipes.SugarFurnaceRecipe;
import cn.breadnicecat.candycraftce.utils.CLogUtils;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
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
		Pair<ResourceKey<RecipeType<?>>, Supplier<RecipeType<T>>> pair = CandyCraftCE.register(BuiltInRegistries.RECIPE_TYPE, id, () -> new RecipeType<>() {
			@Override
			public String toString() {
				return id.toString();
			}
		});
		Pair<ResourceKey<RecipeSerializer<?>>, Supplier<RecipeSerializerExt<T>>> pair1 = CandyCraftCE.register(BuiltInRegistries.RECIPE_SERIALIZER, id, serializer);
		return new RecipeTypeEntry<>(pair, pair1);
	}
	
	public static void init() {
	}
}
