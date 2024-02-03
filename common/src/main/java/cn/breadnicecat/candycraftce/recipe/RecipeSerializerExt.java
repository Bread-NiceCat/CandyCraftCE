package cn.breadnicecat.candycraftce.recipe;

import com.google.gson.JsonObject;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;

public interface RecipeSerializerExt<R extends Recipe<?>> extends RecipeSerializer<R> {
	void toJson(JsonObject object, R recipe);

}
