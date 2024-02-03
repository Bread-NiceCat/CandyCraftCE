package cn.breadnicecat.candycraftce.recipe.fabric;

import cn.breadnicecat.candycraftce.recipe.RecipeSerializerExt;
import cn.breadnicecat.candycraftce.recipe.RecipeTypeEntry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class CRecipeTypesImpl {

	public static <T extends Recipe<?>> RecipeTypeEntry<T> _register(ResourceLocation id, @NotNull Supplier<RecipeType<T>> rt, @NotNull Supplier<RecipeSerializerExt<T>> serializer) {
		RecipeType<T> type = Registry.register(BuiltInRegistries.RECIPE_TYPE, id, rt.get());
		RecipeSerializerExt<T> serial = Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, id, serializer.get());
		return new RecipeTypeEntry<>(id) {
			@Override
			public RecipeSerializerExt<T> getSerializer() {
				return serial;
			}

			@Override
			public RecipeType<T> get() {
				return type;
			}
		};
	}
}
