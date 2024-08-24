package cn.breadnicecat.candycraftce.recipe.recipes;

import com.google.gson.JsonElement;
import com.mojang.serialization.JsonOps;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.NotNull;

/**
 * Created in 2024/8/23 15:40
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 *
 * <p>
 **/
public abstract class RecipeSerializerExt<T extends Recipe<?>> implements RecipeSerializer<T> {
	
	protected JsonElement toJson(T t) {
		return codec().codec().encodeStart(JsonOps.INSTANCE, t).getOrThrow();
	}
	
	protected abstract @NotNull T fromNetwork(RegistryFriendlyByteBuf buffer);
	
	protected abstract void toNetwork(RegistryFriendlyByteBuf buffer, T recipe);
	
	protected StreamCodec<RegistryFriendlyByteBuf, T> streamCodec = StreamCodec.of(this::toNetwork, this::fromNetwork);
	
	@Override
	public @NotNull StreamCodec<RegistryFriendlyByteBuf, T> streamCodec() {
		return streamCodec;
	}
}
