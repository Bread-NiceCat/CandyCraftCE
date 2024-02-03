package cn.breadnicecat.candycraftce.datagen.forge.providers.recipe_builders;

import cn.breadnicecat.candycraftce.recipe.CRecipeTypes;
import cn.breadnicecat.candycraftce.recipe.RecipeSerializerExt;
import cn.breadnicecat.candycraftce.recipe.recipes.SugarFurnaceRecipe;
import com.google.gson.JsonObject;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

/**
 * Created in 2024/2/3
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-NiceCat">Bread_NiceCat</a>
 * <p>
 */
public class SugarFurnaceRecipeBuilder extends CRecipeBuilderBase {
	private Item result;
	private Ingredient ingredient;
	private int xp;

	private SugarFurnaceRecipeBuilder() {
	}

	public static SugarFurnaceRecipeBuilder builder() {
		return new SugarFurnaceRecipeBuilder();
	}

	public SugarFurnaceRecipeBuilder result(ItemLike result) {
		this.result = result.asItem();
		return this;
	}

	public SugarFurnaceRecipeBuilder ingredient(Ingredient ingredient) {
		this.ingredient = ingredient;
		return this;
	}

	public SugarFurnaceRecipeBuilder xp(int exp) {
		this.xp = exp;
		return this;
	}

	@Override
	public @NotNull Item getResult() {
		return result;
	}

	@Override
	public void save(@NotNull Consumer<FinishedRecipe> consumer, @NotNull ResourceLocation recipeId) {
		consumer.accept(new Result(recipeId));
	}

	private class Result extends CFinishedRecipeBase {


		public Result(ResourceLocation id) {
			super(id);
		}

		@Override
		public void serializeRecipeData(@NotNull JsonObject json) {
			getType().toJson(json, new SugarFurnaceRecipe(id, result, ingredient, xp));
		}

		@Override
		public @NotNull RecipeSerializerExt<SugarFurnaceRecipe> getType() {
			return CRecipeTypes.SUGAR_FURNACE_TYPE.getSerializer();
		}
	}
}
