package cn.breadnicecat.candycraftce.recipe.recipes;

import cn.breadnicecat.candycraftce.block.blockentity.CBlockEntities;
import cn.breadnicecat.candycraftce.block.blockentity.entities.SugarFactoryBE;
import cn.breadnicecat.candycraftce.recipe.CRecipeTypes;
import cn.breadnicecat.candycraftce.recipe.RecipeSerializerExt;
import cn.breadnicecat.candycraftce.utils.ItemUtils;
import com.google.gson.JsonObject;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

/**
 * Created in 2024/2/4
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-NiceCat">Bread_NiceCat</a>
 * <p>
 */
public class SugarFactoryRecipe implements Recipe<SugarFactoryBE> {
	public final ResourceLocation id;
	public final Item result;
	public final int count;
	public final Ingredient ingredient;
	public final boolean advanced;

	public SugarFactoryRecipe(ResourceLocation id, Ingredient ingredient, Item result, int count, boolean advanced) {
		this.id = id;
		this.ingredient = ingredient;
		this.result = result;
		this.count = count;
		this.advanced = advanced;
	}

	private static boolean isAdvanced(SugarFactoryBE be) {
		return be.getType() == CBlockEntities.ADVANCED_SUGAR_FACTORY_BE.get();
	}

	@Override
	public boolean matches(SugarFactoryBE container, Level level) {
		return (!advanced || isAdvanced(container)) && ingredient.test(container.getItem(SugarFactoryBE.INPUT_SLOT));
	}

	@Override
	public @NotNull ItemStack assemble(SugarFactoryBE container, RegistryAccess registryAccess) {
		return result.getDefaultInstance();
	}

	@Override
	public boolean canCraftInDimensions(int width, int height) {
		return true;
	}

	@Override
	public @NotNull ItemStack getResultItem(RegistryAccess registryAccess) {
		return result.getDefaultInstance();
	}

	@Override
	public @NotNull ResourceLocation getId() {
		return id;
	}

	@Override
	public @NotNull RecipeSerializer<?> getSerializer() {
		return CRecipeTypes.SUGAR_FACTORY_TYPE.getSerializer();
	}

	@Override
	public @NotNull RecipeType<?> getType() {
		return CRecipeTypes.SUGAR_FACTORY_TYPE.get();
	}

	public static class Serializer implements RecipeSerializerExt<SugarFactoryRecipe> {
		@Override
		public @NotNull SugarFactoryRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
			JsonObject result = json.get("result").getAsJsonObject();
			return new SugarFactoryRecipe(recipeId,
					Ingredient.fromJson(json),
					ItemUtils.getItem(result.get("item")),
					result.get("count").getAsInt(),
					result.get("advanced").getAsBoolean());
		}

		@Override
		public void toJson(JsonObject object, SugarFactoryRecipe recipe) {
			JsonObject result = new JsonObject();
			result.addProperty("item", ItemUtils.getKey(recipe.result).toString());
			if (recipe.count != 1) result.addProperty("count", ItemUtils.getKey(recipe.result).toString());
			object.add("result", result);
			object.add("ingredient", recipe.ingredient.toJson());
			object.addProperty("advanced", recipe.advanced);
		}

		@Override
		public @NotNull SugarFactoryRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
			return new SugarFactoryRecipe(recipeId,
					Ingredient.fromNetwork(buffer),
					buffer.readById(BuiltInRegistries.ITEM),
					buffer.readVarInt(),
					buffer.readBoolean());
		}

		@Override
		public void toNetwork(FriendlyByteBuf buffer, SugarFactoryRecipe recipe) {
			recipe.ingredient.toNetwork(buffer);
			buffer.writeId(BuiltInRegistries.ITEM, recipe.result);
			buffer.writeVarInt(recipe.count);
			buffer.writeBoolean(recipe.advanced);
		}
	}
}
