package cn.breadnicecat.candycraftce.integration.jei;

import cn.breadnicecat.candycraftce.recipe.recipes.SugarFactoryRecipe;
import cn.breadnicecat.candycraftce.recipe.recipes.SugarFurnaceRecipe;
import mezz.jei.api.recipe.RecipeType;

import static cn.breadnicecat.candycraftce.integration.jei.CJeiPlugin.UID;

/**
 * Created in 2024/2/25 8:44
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 * <p>
 */
public class CJeiRecipes {
	public static final RecipeType<SugarFactoryRecipe> SUGAR_FACTORY = new RecipeType<>(UID, SugarFactoryRecipe.class);
	public static final RecipeType<SugarFurnaceRecipe> SUGAR_FURNACE = new RecipeType<>(UID, SugarFurnaceRecipe.class);
}
