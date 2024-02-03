package cn.breadnicecat.candycraftce.recipe.recipes;

import cn.breadnicecat.candycraftce.block.blockentity.entities.LicoriceFurnaceBE;
import cn.breadnicecat.candycraftce.recipe.RecipeSerializerExt;
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
import static net.minecraft.core.registries.BuiltInRegistries.ITEM;

public class SugarFurnaceRecipe implements Recipe<LicoriceFurnaceBE> {
	private final ResourceLocation id;
	private final Item result;
	private final Ingredient ingredient;
	private final int exp;

	public SugarFurnaceRecipe(ResourceLocation id, Item result, Ingredient ingredient, int exp) {
		this.id = id;
		this.result = result;
		this.ingredient = ingredient;
		this.exp = exp;
	}

	@Override
	public boolean matches(LicoriceFurnaceBE container, Level level) {
		return ingredient.test(container.getItem(LicoriceFurnaceBE.INPUT_SLOT));
	}

	public int getExp() {
		return exp;
	}

	@Override
	public @NotNull ItemStack assemble(LicoriceFurnaceBE container, RegistryAccess registryAccess) {
		return result.getDefaultInstance();
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
			object.addProperty("result", ITEM.getKey(recipe.result).toString());
			object.add("ingredient", recipe.ingredient.toJson());
			if (recipe.exp != 0) object.addProperty("exp", recipe.exp);
		}

		@Override
		public @NotNull SugarFurnaceRecipe fromJson(ResourceLocation recipeId, JsonObject serializedRecipe) {
			return new SugarFurnaceRecipe(recipeId, ITEM.get(new ResourceLocation(serializedRecipe.get("result").getAsString())),
					Ingredient.fromJson(serializedRecipe.get("ingredient")),
					serializedRecipe.has("exp") ? serializedRecipe.get("exp").getAsInt() : 0);
		}

		@Override
		public @NotNull SugarFurnaceRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
			return new SugarFurnaceRecipe(recipeId, buffer.readById(ITEM),
					Ingredient.fromNetwork(buffer),
					buffer.readVarInt());
		}

		@Override
		public void toNetwork(FriendlyByteBuf buffer, SugarFurnaceRecipe recipe) {
			buffer.writeId(ITEM, recipe.result);
			recipe.ingredient.toNetwork(buffer);
			buffer.writeVarInt(recipe.exp);
		}
	}

}
