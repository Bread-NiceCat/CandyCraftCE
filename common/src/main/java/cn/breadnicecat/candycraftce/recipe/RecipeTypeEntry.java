package cn.breadnicecat.candycraftce.recipe;

import cn.breadnicecat.candycraftce.utils.RegistryEntry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;

import java.util.function.Supplier;

public abstract class RecipeTypeEntry<R extends Recipe<?>> extends RegistryEntry implements Supplier<RecipeType<R>> {
	public RecipeTypeEntry(ResourceLocation id) {
		super(id);
	}

	public abstract RecipeSerializerExt<R> getSerializer();
}
