package cn.breadnicecat.candycraftce.recipe.recipes;

import cn.breadnicecat.candycraftce.block.blockentity.entities.LicoriceFurnaceBE;
import cn.breadnicecat.candycraftce.recipe.RecipeSerializerExt;
import cn.breadnicecat.candycraftce.utils.ItemUtils;
import com.google.gson.JsonObject;
import net.minecraft.core.RegistryAccess;
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

import static cn.breadnicecat.candycraftce.recipe.CRecipeTypes.SUGAR_FURNACE_TYPE;
import static cn.breadnicecat.candycraftce.utils.CommonUtils.assertTrue;
import static net.minecraft.core.registries.BuiltInRegistries.ITEM;

public class SugarFurnaceRecipe implements Recipe<LicoriceFurnaceBE> {
	private final ResourceLocation id;
	private final Item result;
	private final Ingredient ingredient;
	private final int count;
	private final float exp;

	public SugarFurnaceRecipe(ResourceLocation id, Ingredient ingredient, Item result, int count, float exp) {
		this.id = id;
		this.result = result;
		this.ingredient = ingredient;
		assertTrue(count > 0, "Count > 0");
		this.count = count;
		this.exp = exp;
	}

	@Override
	public boolean matches(LicoriceFurnaceBE container, Level level) {
		return ingredient.test(container.getItem(LicoriceFurnaceBE.INPUT_SLOT));
	}

	public float getExp() {
		return exp;
	}

	public int getCount() {
		return count;
	}

	@Override
	public @NotNull ItemStack assemble(LicoriceFurnaceBE container, RegistryAccess registryAccess) {
		ItemStack stack = result.getDefaultInstance();
		stack.setCount(count);
		return stack;
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
		return SUGAR_FURNACE_TYPE.getSerializer();
	}

	@Override
	public @NotNull RecipeType<SugarFurnaceRecipe> getType() {
		return SUGAR_FURNACE_TYPE.get();
	}

	@Override
	public boolean canCraftInDimensions(int width, int height) {
		return true;
	}

	public static class Serializer implements RecipeSerializerExt<SugarFurnaceRecipe> {

		@Override
		public void toJson(JsonObject object, SugarFurnaceRecipe recipe) {
			JsonObject result = new JsonObject();
			result.addProperty("item", ItemUtils.getKey(recipe.result).toString());
			object.add("ingredient", recipe.ingredient.toJson());
			if (recipe.exp != 0f) object.addProperty("exp", recipe.exp);
			if (recipe.count != 1) result.addProperty("count", recipe.count);
			object.add("result", result);
		}

		@Override
		public @NotNull SugarFurnaceRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
			JsonObject result = json.get("result").getAsJsonObject();
			return new SugarFurnaceRecipe(recipeId,
					Ingredient.fromJson(json.get("ingredient")),
					ItemUtils.getItem(result.get("item")),
					result.has("count") ? result.get("count").getAsInt() : 1,
					json.has("exp") ? json.get("exp").getAsFloat() : 0f);
		}

		@Override
		public @NotNull SugarFurnaceRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
			return new SugarFurnaceRecipe(recipeId,
					Ingredient.fromNetwork(buffer),
					buffer.readById(ITEM),
					buffer.readVarInt(),
					buffer.readFloat());
		}

		@Override
		public void toNetwork(FriendlyByteBuf buffer, SugarFurnaceRecipe recipe) {
			buffer.writeId(ITEM, recipe.result);
			recipe.ingredient.toNetwork(buffer);
			buffer.writeVarInt(recipe.count);
			buffer.writeFloat(recipe.exp);
		}
	}

}
