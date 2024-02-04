package cn.breadnicecat.candycraftce.recipe;

import cn.breadnicecat.candycraftce.recipe.recipes.SugarFactoryRecipe;
import cn.breadnicecat.candycraftce.recipe.recipes.SugarFurnaceRecipe;
import cn.breadnicecat.candycraftce.utils.CLogUtils;
import cn.breadnicecat.candycraftce.utils.CommonUtils;
import dev.architectury.injectables.annotations.ExpectPlatform;
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
		return _register(id, () -> new RecipeType<>() {
			@Override
			public String toString() {
				return id.toString();
			}
		}, serializer);
	}

	@ExpectPlatform
	private static <T extends Recipe<?>> RecipeTypeEntry<T> _register(ResourceLocation id, Supplier<RecipeType<T>> rt, Supplier<RecipeSerializerExt<T>> serializer) {
		return CommonUtils.impossible();
	}

	public static void init() {
		CommonUtils.logInit(LOGGER);
	}
}
