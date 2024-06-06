package cn.breadnicecat.candycraftce.integration.jei.categories;

import cn.breadnicecat.candycraftce.block.CBlocks;
import cn.breadnicecat.candycraftce.gui.block.screens.LicoriceFurnaceScreen;
import cn.breadnicecat.candycraftce.integration.jei.CJeiRecipes;
import cn.breadnicecat.candycraftce.recipe.recipes.SugarFurnaceRecipe;
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
import net.minecraft.network.chat.MutableComponent;
import org.jetbrains.annotations.NotNull;

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
public class SugarFurnaceCategory implements IRecipeCategory<SugarFurnaceRecipe> {
	
	public final static String TITLE_KEY = "jei.sugar_furnace.title";
	private final MutableComponent TITLE = Component.keybind(TITLE_KEY);
	
	private final IDrawableStatic bg;
	private final IDrawable icon;
	private final IDrawableAnimated fireAnim;
	private final IDrawableAnimated progressAnim;
	
	public SugarFurnaceCategory(@NotNull IGuiHelper helper) {
		icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, CBlocks.LICORICE_FURNACE.getDefaultInstance());
		bg = helper.createDrawable(JEI_TEX, 0, 60, 176, 80);
		IDrawableStatic fire = helper.createDrawable(LicoriceFurnaceScreen.LICORICE, 176, 0, 14, 14);
		int tick = (int) (4 * SEC2TICK);
		fireAnim = helper.createAnimatedDrawable(fire, tick, IDrawableAnimated.StartDirection.TOP, true);
		IDrawableStatic progress = helper.createDrawable(LicoriceFurnaceScreen.LICORICE, 176, 14, 22, 16);
		progressAnim = helper.createAnimatedDrawable(progress, tick, IDrawableAnimated.StartDirection.LEFT, false);
	}
	
	@Override
	public @NotNull RecipeType<SugarFurnaceRecipe> getRecipeType() {
		return CJeiRecipes.SUGAR_FURNACE_JEI;
	}
	
	@Override
	public @NotNull Component getTitle() {
		return TITLE;
	}
	
	@Override
	public @NotNull IDrawable getBackground() {
		return bg;
	}
	
	@Override
	public @NotNull IDrawable getIcon() {
		return icon;
	}
	
	@Override
	public void setRecipe(IRecipeLayoutBuilder builder, SugarFurnaceRecipe recipe, IFocusGroup focuses) {
		builder.addSlot(RecipeIngredientRole.INPUT, 55 + 1, 17).addIngredients(recipe.getIngredients().get(0));
		builder.addSlot(RecipeIngredientRole.OUTPUT, 115 + 1, 35).addItemStack(recipe.getResultItem(null));
	}
	
	@Override
	public void draw(SugarFurnaceRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
		fireAnim.draw(guiGraphics, 56, 37);
		progressAnim.draw(guiGraphics, 79, 35);
	}
}
