package cn.breadnicecat.candycraftce.datagen.forge.providers.recipes;

import cn.breadnicecat.candycraftce.recipe.CRecipeTypes;
import cn.breadnicecat.candycraftce.recipe.RecipeSerializerExt;
import cn.breadnicecat.candycraftce.recipe.recipes.SugarFurnaceRecipe;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
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
	private final Item result;
	private Ingredient ingredient;
	private float exp;
	private int count = 1;
	
	private SugarFurnaceRecipeBuilder(ItemLike result) {
		this.result = result.asItem();
	}
	
	public static SugarFurnaceRecipeBuilder furnace(ItemLike result) {
		return new SugarFurnaceRecipeBuilder(result);
	}
	
	
	public SugarFurnaceRecipeBuilder ingredient(Ingredient ingredient) {
		this.ingredient = ingredient;
		return this;
	}
	
	public SugarFurnaceRecipeBuilder ingredient(ItemLike ingredient) {
		return ingredient(Ingredient.of(ingredient));
	}
	
	public SugarFurnaceRecipeBuilder ingredient(TagKey<Item> ingredient) {
		return ingredient(Ingredient.of(ingredient));
	}
	
	public SugarFurnaceRecipeBuilder exp(float exp) {
		this.exp = exp;
		return this;
	}
	
	public SugarFurnaceRecipeBuilder count(int count) {
		this.count = count;
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
	
	private class Result extends CFinishedRecipeBase<SugarFurnaceRecipe> {
		
		
		public Result(ResourceLocation id) {
			super(new SugarFurnaceRecipe(id, ingredient, result, count, exp));
		}
		
		@Override
		public @NotNull RecipeSerializerExt<SugarFurnaceRecipe> getType() {
			return CRecipeTypes.SUGAR_FURNACE_TYPE.getSerializer();
		}
	}
}
