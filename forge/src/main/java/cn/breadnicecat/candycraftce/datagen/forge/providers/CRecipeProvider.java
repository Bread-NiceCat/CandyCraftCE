package cn.breadnicecat.candycraftce.datagen.forge.providers;

import cn.breadnicecat.candycraftce.datagen.forge.providers.recipes.SugarFurnaceRecipeBuilder;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.function.Consumer;

import static cn.breadnicecat.candycraftce.block.CBlocks.CARAMEL_BLOCK;
import static cn.breadnicecat.candycraftce.block.CBlocks.SUGAR_BLOCK;
import static cn.breadnicecat.candycraftce.item.CItems.CRANFISH;
import static cn.breadnicecat.candycraftce.item.CItems.CRANFISH_COOKED;

/**
 * Created in 2024/2/3
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-NiceCat">Bread_NiceCat</a>
 * <p>
 */
public class CRecipeProvider extends RecipeProvider {
	public CRecipeProvider(PackOutput arg) {
		super(arg);
	}

	private final HashSet<ResourceLocation> nameCache = new HashSet<>();

	@Override
	protected void buildRecipes(@NotNull Consumer<FinishedRecipe> writer) {
		SugarFurnaceRecipeBuilder.builder(CRANFISH_COOKED).ingredient(CRANFISH).save(writer, nameCache);
		SugarFurnaceRecipeBuilder.builder(CARAMEL_BLOCK).ingredient(SUGAR_BLOCK).save(writer, nameCache);
	}
}
