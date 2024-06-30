package cn.breadnicecat.candycraftce.integration.jei;

import cn.breadnicecat.candycraftce.recipe.recipes.SugarFactoryRecipe;
import cn.breadnicecat.candycraftce.recipe.recipes.SugarFurnaceRecipe;
import mezz.jei.api.recipe.RecipeType;

import static cn.breadnicecat.candycraftce.utils.ResourceUtils.prefix;

/**
 * Created in 2024/2/25 8:44
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 * <p>
 */
public class CJeiRecipes {
	public static final RecipeType<SugarFactoryRecipe> SUGAR_FACTORY_JEI = new RecipeType<>(prefix("sugar_factory"), SugarFactoryRecipe.class);
	public static final RecipeType<SugarFactoryRecipe> SUGAR_FACTORY_SUGAR_JEI = new RecipeType<>(prefix("sugar_factory_sugar"), SugarFactoryRecipe.class);
	public static final RecipeType<SugarFurnaceRecipe> SUGAR_FURNACE_JEI = new RecipeType<>(prefix("sugar_furnace"), SugarFurnaceRecipe.class);
}
