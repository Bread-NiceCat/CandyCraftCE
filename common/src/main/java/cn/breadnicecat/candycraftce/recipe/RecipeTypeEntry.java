package cn.breadnicecat.candycraftce.recipe;

import cn.breadnicecat.candycraftce.recipe.recipes.RecipeSerializerExt;
import cn.breadnicecat.candycraftce.utils.SimpleEntry;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

public class RecipeTypeEntry<R extends Recipe<?>> extends SimpleEntry<RecipeType<?>, RecipeType<R>> {
	
	private final SimpleEntry<RecipeSerializer<?>, RecipeSerializerExt<R>> serializer;
	
	
	public RecipeTypeEntry(SimpleEntry<RecipeType<?>, RecipeType<R>> wrapper,
	                       SimpleEntry<RecipeSerializer<?>, RecipeSerializerExt<R>> wrapperSerializer) {
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
