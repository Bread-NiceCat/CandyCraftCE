package cn.breadnicecat.candycraftce.datagen.neoforge.providers.recipes;

import cn.breadnicecat.candycraftce.recipe.recipes.SugarFurnaceRecipe;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;

/**
 * Created in 2024/2/3
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-NiceCat">Bread_NiceCat</a>
 * <p>
 */
public class SugarFurnaceRecipeBuilder extends CRecipeBuilderBase {
	private final ItemStack result;
	private Ingredient ingredient;
	private float exp;
	
	private SugarFurnaceRecipeBuilder(ItemStack result) {
		this.result = result;
	}
	
	private SugarFurnaceRecipeBuilder(ItemLike result) {
		this(result.asItem().getDefaultInstance());
	}
	
	public static SugarFurnaceRecipeBuilder furnace(ItemLike result) {
		return new SugarFurnaceRecipeBuilder(result);
	}
	
	
	public SugarFurnaceRecipeBuilder ingredient(Ingredient ingredient) {
		this.ingredient = ingredient;
		return this;
	}
	
	public SugarFurnaceRecipeBuilder ingredient(ItemLike... ingredient) {
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
		result.setCount(count);
		return this;
	}
	
	@Override
	public @NotNull Item getResult() {
		return result.getItem();
	}
	
	
	@Override
	public void save(RecipeOutput writer, @NotNull ResourceLocation id) {
		writer.accept(id, new SugarFurnaceRecipe(ingredient, result, exp), null);
	}
}
