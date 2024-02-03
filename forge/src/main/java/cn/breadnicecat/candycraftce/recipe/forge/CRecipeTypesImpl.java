package cn.breadnicecat.candycraftce.recipe.forge;

import cn.breadnicecat.candycraftce.forge.CandyCraftCEImpl;
import cn.breadnicecat.candycraftce.recipe.RecipeSerializerExt;
import cn.breadnicecat.candycraftce.recipe.RecipeTypeEntry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

import static cn.breadnicecat.candycraftce.utils.CommonUtils.assertTrue;

public class CRecipeTypesImpl {

	public static final DeferredRegister<RecipeType<?>> REGISTER = CandyCraftCEImpl.createRegister(ForgeRegistries.RECIPE_TYPES);
	private static final DeferredRegister<RecipeSerializer<?>> REGISTER_S = CandyCraftCEImpl.createRegister(ForgeRegistries.RECIPE_SERIALIZERS);

	public static <T extends Recipe<?>> RecipeTypeEntry<T> _register(ResourceLocation id, Supplier<RecipeType<T>> rt, Supplier<RecipeSerializerExt<T>> serializer) {
		var object = REGISTER.register(id.getPath(), rt);
		var object_s = REGISTER_S.register(id.getPath(), serializer);
		assertTrue(id.equals(object.getId()), "Unmatched ResourceLocation");
		return new RecipeTypeEntry<>(id) {

			@Override
			public RecipeSerializerExt<T> getSerializer() {
				return object_s.get();
			}

			@Override
			public RecipeType<T> get() {
				return object.get();
			}
		};
	}


}
