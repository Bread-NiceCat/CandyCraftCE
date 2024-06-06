package cn.breadnicecat.candycraftce.datagen.forge.providers.recipes;

import cn.breadnicecat.candycraftce.recipe.CRecipeTypes;
import cn.breadnicecat.candycraftce.recipe.RecipeSerializerExt;
import cn.breadnicecat.candycraftce.recipe.recipes.SugarFactoryRecipe;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

/**
 * Created in 2024/2/5
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-NiceCat">Bread_NiceCat</a>
 * <p>
 */
public class SugarFactoryRecipeBuilder extends CRecipeBuilderBase {
	private Ingredient ingredient;
	private int count;
	private boolean advanced;
	private Item result;
	
	public SugarFactoryRecipeBuilder(Item result) {
		this.result = result;
	}
	
	public static SugarFactoryRecipeBuilder builder(Item result) {
		return new SugarFactoryRecipeBuilder(result);
	}
	
	
	@Override
	public @NotNull Item getResult() {
		return result;
	}
	
	public SugarFactoryRecipeBuilder ingredient(Ingredient ingredient) {
		this.ingredient = ingredient;
		return this;
	}
	
	public SugarFactoryRecipeBuilder ingredient(ItemLike ingredient) {
		return ingredient(Ingredient.of(ingredient));
	}
	
	public SugarFactoryRecipeBuilder ingredient(TagKey<Item> ingredient) {
		return ingredient(Ingredient.of(ingredient));
	}
	
	public SugarFactoryRecipeBuilder count(int count) {
		this.count = count;
		return this;
	}
	
	public SugarFactoryRecipeBuilder advanced(boolean advanced) {
		this.advanced = advanced;
		return this;
	}
	
	public SugarFactoryRecipeBuilder advanced() {
		return advanced(true);
	}
	
	@Override
	public void save(@NotNull Consumer<FinishedRecipe> writer, @NotNull ResourceLocation recipeId) {
		writer.accept(new Result(recipeId));
	}
	
	class Result extends CRecipeBuilderBase.CFinishedRecipeBase<SugarFactoryRecipe> {
		public Result(ResourceLocation recipeId) {
			super(new SugarFactoryRecipe(recipeId, ingredient, result, count, advanced));
		}
		
		@Override
		public @NotNull RecipeSerializerExt<SugarFactoryRecipe> getType() {
			return CRecipeTypes.SUGAR_FACTORY_TYPE.getSerializer();
		}
	}
}
