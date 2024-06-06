package cn.breadnicecat.candycraftce.datagen.forge.providers;

import cn.breadnicecat.candycraftce.datagen.forge.providers.recipes.SugarFactoryRecipeBuilder;
import cn.breadnicecat.candycraftce.datagen.forge.providers.recipes.SugarFurnaceRecipeBuilder;
import cn.breadnicecat.candycraftce.utils.ResourceUtils;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Objects;
import java.util.function.Consumer;

import static cn.breadnicecat.candycraftce.block.CBlocks.*;
import static cn.breadnicecat.candycraftce.item.CItems.CRANFISH;
import static cn.breadnicecat.candycraftce.item.CItems.CRANFISH_COOKED;
import static cn.breadnicecat.candycraftce.utils.CommonUtils.assertTrue;

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
	
	
	@Override
	protected void buildRecipes(@NotNull Consumer<FinishedRecipe> writer) {
		SugarFurnaceRecipeBuilder.builder(r(CRANFISH_COOKED)).ingredient(CRANFISH).save(writer, getId());
		SugarFurnaceRecipeBuilder.builder(r(CARAMEL_BLOCK)).ingredient(SUGAR_BLOCK).save(writer, getId());
		SugarFactoryRecipeBuilder.builder(r(Items.GOLD_NUGGET)).ingredient(GOLDEN_SUGAR_FLOWER).save(writer, getId());
	}
	
	//NameResolver
	private final HashSet<ResourceLocation> nameCache = new HashSet<>();
	private ItemLike nameNow;
	
	private <T extends ItemLike> T r(T t) {
		assertTrue(nameNow == null, "Previous Item is Unused");
		nameNow = t;
		return t;
	}
	
	private ResourceLocation getId() {
		Objects.requireNonNull(nameNow, "Undefined");
		final ResourceLocation key = Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(nameNow.asItem()));
		ResourceLocation recipeId = key;
		for (int i = 1; nameCache.contains(recipeId); i++) {
			recipeId = ResourceUtils.postfix(key, String.valueOf(i));
		}
		nameCache.add(recipeId);
		nameNow = null;
		return recipeId;
	}
}
