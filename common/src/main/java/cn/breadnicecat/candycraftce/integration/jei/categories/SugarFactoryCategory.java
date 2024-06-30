package cn.breadnicecat.candycraftce.integration.jei.categories;

import cn.breadnicecat.candycraftce.integration.jei.CJeiRecipes;
import cn.breadnicecat.candycraftce.integration.jei.utils.ScaledDrawable;
import cn.breadnicecat.candycraftce.recipe.recipes.SugarFactoryRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

import static cn.breadnicecat.candycraftce.block.CBlocks.ADVANCED_SUGAR_FACTORY;
import static cn.breadnicecat.candycraftce.block.CBlocks.SUGAR_FACTORY;
import static cn.breadnicecat.candycraftce.gui.block.screens.AdvancedFactoryScreen.ADVANCED_STYLE;
import static cn.breadnicecat.candycraftce.gui.block.screens.SugarFactoryScreen.COMMON_STYLE;
import static cn.breadnicecat.candycraftce.integration.jei.CJeiPlugin.JEI_TEX;
import static cn.breadnicecat.candycraftce.utils.TickUtils.SEC2TICK;

/**
 * Created in 2024/6/6 下午1:05
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 *
 * <p>
 **/
public class SugarFactoryCategory implements IRecipeCategory<SugarFactoryRecipe> {
	private final Component title = SUGAR_FACTORY.get().getName();
	protected final IDrawableStatic common;
	protected final IDrawableStatic advanced;
	protected final IDrawableAnimated commonProgress;
	protected final IDrawableAnimated advancedProgress;
	protected final IDrawableAnimated sugaryProgress;
	protected final IDrawableStatic bg;
	protected final IDrawable commonIcon;
	protected final IDrawable advancedIcon;
	protected final IDrawable sugaryIcon;
	
	public SugarFactoryCategory(IGuiHelper guiHelper) {
		int ticksPerCycle = (int) (4 * SEC2TICK);
		bg = guiHelper.createBlankDrawable(180, 50);
		commonIcon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, SUGAR_FACTORY.getDefaultInstance());
		advancedIcon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, ADVANCED_SUGAR_FACTORY.getDefaultInstance());
		sugaryIcon = new ScaledDrawable(guiHelper.createDrawable(JEI_TEX, 0, 141, 32, 32), 2);
		common = guiHelper.createDrawable(JEI_TEX, 0, 0, 174, 30);
		advanced = guiHelper.createDrawable(JEI_TEX, 0, 30, 174, 30);
		
		IDrawableStatic commonProgress1 = guiHelper.createDrawable(COMMON_STYLE, 0, 114, 120, 12);
		commonProgress = guiHelper.createAnimatedDrawable(commonProgress1, ticksPerCycle, IDrawableAnimated.StartDirection.LEFT, false);
		IDrawableStatic advancedProgress1 = guiHelper.createDrawable(ADVANCED_STYLE, 0, 114, 120, 12);
		advancedProgress = guiHelper.createAnimatedDrawable(advancedProgress1, ticksPerCycle, IDrawableAnimated.StartDirection.LEFT, false);
		IDrawableStatic sugaryProgress1 = guiHelper.createDrawable(COMMON_STYLE, 0, 126, 120, 12);
		sugaryProgress = guiHelper.createAnimatedDrawable(sugaryProgress1, ticksPerCycle, IDrawableAnimated.StartDirection.LEFT, false);
	}
	
	@Override
	public @NotNull RecipeType<SugarFactoryRecipe> getRecipeType() {
		return CJeiRecipes.SUGAR_FACTORY_JEI;
	}
	
	@Override
	public @NotNull IDrawable getBackground() {
		return bg;
	}
	
	@Override
	public @NotNull Component getTitle() {
		return title;
	}
	
	@Override
	public @NotNull IDrawable getIcon() {
		return commonIcon;
	}
	
	@Override
	public void setRecipe(IRecipeLayoutBuilder builder, SugarFactoryRecipe recipe, IFocusGroup focuses) {
		builder.addSlot(RecipeIngredientRole.INPUT, 7 + 3, 7 + 10).addIngredients(recipe.getIngredients().get(0));
		builder.addSlot(RecipeIngredientRole.OUTPUT, 151 + 3, 7 + 10).addItemStack(recipe.getResultItem(null));
	}
	
	@Override
	public void draw(SugarFactoryRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
		IDrawable bg = getBg(recipe);
		IDrawable process = getProcess(recipe);
		bg.draw(guiGraphics, 3, 10);
		process.draw(guiGraphics, 27 + 3, 9 + 10);
	}
	
	protected IDrawable getBg(SugarFactoryRecipe recipe) {
		return recipe.advanced ? advanced : common;
	}
	
	protected IDrawable getProcess(SugarFactoryRecipe recipe) {
		return recipe.advanced ? advancedProgress : commonProgress;
	}
}
