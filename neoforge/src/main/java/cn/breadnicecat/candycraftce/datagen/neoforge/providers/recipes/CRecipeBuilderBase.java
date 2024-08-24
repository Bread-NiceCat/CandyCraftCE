package cn.breadnicecat.candycraftce.datagen.neoforge.providers.recipes;

import net.minecraft.advancements.Criterion;
import net.minecraft.data.recipes.RecipeBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created in 2024/2/3
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-NiceCat">Bread_NiceCat</a>
 * <p>
 */
public abstract class CRecipeBuilderBase implements RecipeBuilder {
	@Override
	public @NotNull RecipeBuilder unlockedBy(@NotNull String name, @NotNull Criterion<?> criterion) {
		return null;
	}
	
	@Override
	@Deprecated
	public @NotNull RecipeBuilder group(@Nullable String groupName) {
		return null;
	}
	
}
