package cn.breadnicecat.candycraftce.datagen.forge.providers.recipes;

import cn.breadnicecat.candycraftce.recipe.RecipeSerializerExt;
import cn.breadnicecat.candycraftce.utils.ResourceUtils;
import com.google.gson.JsonObject;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;

/**
 * Created in 2024/2/3
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-NiceCat">Bread_NiceCat</a>
 * <p>
 */
public abstract class CRecipeBuilderBase implements RecipeBuilder {

	@Override
	@Deprecated
	public @NotNull RecipeBuilder unlockedBy(@NotNull String criterionName, @NotNull CriterionTriggerInstance criterionTrigger) {
		return null;
	}

	@Override
	@Deprecated
	public @NotNull RecipeBuilder group(@Nullable String groupName) {
		return null;
	}

	public void save(Consumer<FinishedRecipe> writer, final Set<ResourceLocation> cache) {
		final ResourceLocation key = Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(getResult()));
		ResourceLocation recipeId = key;
		for (int i = 1; cache.contains(recipeId); i++) {
			recipeId = ResourceUtils.pathPostfix(key, String.valueOf(i));
		}
		cache.add(recipeId);
		save(writer, recipeId);
	}

	static abstract class CFinishedRecipeBase<R extends Recipe<?>> implements FinishedRecipe {
		private final R recipe;

		public CFinishedRecipeBase(R recipe) {
			this.recipe = recipe;
		}

		@Override
		public abstract @NotNull RecipeSerializerExt<R> getType();

		@Override
		public void serializeRecipeData(@NotNull JsonObject json) {
			getType().toJson(json, recipe);
		}

		@Override
		public @NotNull ResourceLocation getId() {
			return recipe.getId();
		}


		@Nullable
		@Override
		public JsonObject serializeAdvancement() {
			return null;
		}

		@Nullable
		@Override
		public ResourceLocation getAdvancementId() {
			return null;
		}
	}
}
