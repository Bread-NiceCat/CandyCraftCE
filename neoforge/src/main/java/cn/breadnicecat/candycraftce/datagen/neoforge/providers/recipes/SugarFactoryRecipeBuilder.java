package cn.breadnicecat.candycraftce.datagen.neoforge.providers.recipes;

import cn.breadnicecat.candycraftce.recipe.recipes.SugarFactoryRecipe;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;

/**
 * Created in 2024/2/5
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-NiceCat">Bread_NiceCat</a>
 * <p>
 */
public class SugarFactoryRecipeBuilder extends CRecipeBuilderBase {
	private Ingredient ingredient;
	private boolean advanced;
	private final ItemStack result;
	
	public SugarFactoryRecipeBuilder(ItemLike result) {
		this(result.asItem().getDefaultInstance());
	}
	
	public SugarFactoryRecipeBuilder(ItemStack result) {
		this.result = result;
	}
	
	public static SugarFactoryRecipeBuilder factory(ItemLike result) {
		return new SugarFactoryRecipeBuilder(result);
	}
	
	public static SugarFactoryRecipeBuilder factory(ItemLike result, int cnt) {
		return factory(result).count(cnt);
	}
	
	
	@Override
	public @NotNull Item getResult() {
		return result.getItem();
	}
	
	public SugarFactoryRecipeBuilder ingredient(Ingredient ingredient) {
		this.ingredient = ingredient;
		return this;
	}
	
	public SugarFactoryRecipeBuilder ingredient(ItemLike... ingredient) {
		return ingredient(Ingredient.of(ingredient));
	}
	
	public SugarFactoryRecipeBuilder ingredient(TagKey<Item> ingredient) {
		return ingredient(Ingredient.of(ingredient));
	}
	
	public SugarFactoryRecipeBuilder count(int count) {
		result.setCount(count);
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
	public void save(RecipeOutput writer, @NotNull ResourceLocation id) {
		writer.accept(id, new SugarFactoryRecipe(ingredient, result, advanced), null);
	}
	
}
