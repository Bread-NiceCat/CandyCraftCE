package cn.breadnicecat.candycraftce.integration.jei;

import cn.breadnicecat.candycraftce.integration.jei.categories.SugarFactoryCategory;
import cn.breadnicecat.candycraftce.integration.jei.categories.SugarFactorySugarCategory;
import cn.breadnicecat.candycraftce.integration.jei.categories.SugarFurnaceCategory;
import cn.breadnicecat.candycraftce.recipe.recipes.SugarFactoryRecipe;
import cn.breadnicecat.candycraftce.utils.ItemUtils;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeManager;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.stream.Collectors;

import static cn.breadnicecat.candycraftce.block.CBlocks.*;
import static cn.breadnicecat.candycraftce.block.blockentity.entities.SugarFactoryBE.SUGARY;
import static cn.breadnicecat.candycraftce.integration.jei.CJeiRecipes.*;
import static cn.breadnicecat.candycraftce.recipe.CRecipeTypes.SUGAR_FACTORY_TYPE;
import static cn.breadnicecat.candycraftce.recipe.CRecipeTypes.SUGAR_FURNACE_TYPE;
import static cn.breadnicecat.candycraftce.utils.CommonUtils.apply;
import static cn.breadnicecat.candycraftce.utils.ResourceUtils.guiTex;
import static cn.breadnicecat.candycraftce.utils.ResourceUtils.prefix;

/**
 * Created in 2023/9/30 15:44
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 */
@JeiPlugin
public class CJeiPlugin implements IModPlugin {
	public static final ResourceLocation UID = prefix("jei-plugin");
	public static final ResourceLocation JEI_TEX = guiTex("jei");
	
	public CJeiPlugin() {
	}
	
	@Override
	public @NotNull ResourceLocation getPluginUid() {
		return UID;
	}
	
	@Override
	public void registerRecipes(IRecipeRegistration registration) {
		RecipeManager manager = Minecraft.getInstance().level.getRecipeManager();
		registration.addRecipes(SUGAR_FURNACE_JEI, manager.getAllRecipesFor(SUGAR_FURNACE_TYPE.get()));
		registration.addRecipes(SUGAR_FACTORY_JEI, manager.getAllRecipesFor(SUGAR_FACTORY_TYPE.get()));
		registration.addRecipes(SUGAR_FACTORY_SUGAR_JEI, apply(Arrays.stream(SUGARY.ingredient.getItems())
				.map(i -> new SugarFactoryRecipe(ItemUtils.getKey(i.getItem()).withSuffix("__sugary"),
						Ingredient.of(i), Items.SUGAR, 1, false))
				.collect(Collectors.toList()), (i) -> i.add(0, SUGARY)));
	}
	
	@Override
	public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
		registration.addRecipeCatalyst(SUGAR_FACTORY.getDefaultInstance(), SUGAR_FACTORY_JEI, SUGAR_FACTORY_SUGAR_JEI);
		registration.addRecipeCatalyst(ADVANCED_SUGAR_FACTORY.getDefaultInstance(), SUGAR_FACTORY_JEI, SUGAR_FACTORY_SUGAR_JEI);
		
		registration.addRecipeCatalyst(CHOCOLATE_FURNACE.getDefaultInstance(), SUGAR_FURNACE_JEI);
		registration.addRecipeCatalyst(WHITE_CHOCOLATE_FURNACE.getDefaultInstance(), SUGAR_FURNACE_JEI);
		registration.addRecipeCatalyst(LICORICE_FURNACE.getDefaultInstance(), SUGAR_FURNACE_JEI);
	}
	
	@Override
	public void registerCategories(IRecipeCategoryRegistration registration) {
		IGuiHelper helper = registration.getJeiHelpers().getGuiHelper();
		registration.addRecipeCategories(
				new SugarFurnaceCategory(helper),
				new SugarFactoryCategory(helper),
				new SugarFactorySugarCategory(helper)
		);
	}
}
