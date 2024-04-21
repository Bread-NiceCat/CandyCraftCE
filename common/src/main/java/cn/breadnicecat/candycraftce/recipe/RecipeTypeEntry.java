package cn.breadnicecat.candycraftce.recipe;

import cn.breadnicecat.candycraftce.utils.SimpleEntry;
import com.mojang.datafixers.util.Pair;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

import java.util.function.Supplier;

public class RecipeTypeEntry<R extends Recipe<?>> extends SimpleEntry<RecipeType<?>, RecipeType<R>> {

	private final SimpleEntry<RecipeSerializer<?>, RecipeSerializerExt<R>> serializer;

	public RecipeTypeEntry(ResourceKey<RecipeType<?>> keyRecipe, Supplier<RecipeType<R>> recipe,
	                       ResourceKey<RecipeSerializer<?>> keySerializer, Supplier<RecipeSerializerExt<R>> serializer) {
		super(keyRecipe, recipe);
		this.serializer = new SimpleEntry<>(keySerializer, serializer);
	}

	public RecipeTypeEntry(Pair<ResourceKey<RecipeType<?>>, Supplier<RecipeType<R>>> wrapper, Pair<ResourceKey<RecipeSerializer<?>>, Supplier<RecipeSerializerExt<R>>> wrapperSerializer) {
		super(wrapper);
		this.serializer = new SimpleEntry<>(wrapperSerializer);
	}

	public SimpleEntry<RecipeSerializer<?>, RecipeSerializerExt<R>> getSerializerEntry() {
		return serializer;
	}

	public RecipeSerializerExt<R> getSerializer() {
		return serializer.get();
	}
}
