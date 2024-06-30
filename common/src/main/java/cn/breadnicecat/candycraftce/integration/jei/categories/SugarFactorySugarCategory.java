package cn.breadnicecat.candycraftce.integration.jei.categories;

import cn.breadnicecat.candycraftce.integration.jei.CJeiRecipes;
import cn.breadnicecat.candycraftce.recipe.recipes.SugarFactoryRecipe;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.RecipeType;
import org.jetbrains.annotations.NotNull;

/**
 * Created in 2024/6/30 下午11:43
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 *
 * <p>
 **/
public class SugarFactorySugarCategory extends SugarFactoryCategory {
	public SugarFactorySugarCategory(@NotNull IGuiHelper helper) {
		super(helper);
	}
	
	@Override
	public @NotNull RecipeType<SugarFactoryRecipe> getRecipeType() {
		return CJeiRecipes.SUGAR_FACTORY_SUGAR_JEI;
	}
	
	@Override
	public @NotNull IDrawable getIcon() {
		return sugaryIcon;
	}
	
	@Override
	protected IDrawable getBg(SugarFactoryRecipe recipe) {
		return common;
	}
	
	@Override
	protected IDrawable getProcess(SugarFactoryRecipe recipe) {
		return sugaryProgress;
	}
}
