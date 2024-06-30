package cn.breadnicecat.candycraftce.recipe.recipes;

import cn.breadnicecat.candycraftce.block.blockentity.CBlockEntities;
import cn.breadnicecat.candycraftce.block.blockentity.entities.SugarFactoryBE;
import cn.breadnicecat.candycraftce.recipe.CRecipeTypes;
import cn.breadnicecat.candycraftce.recipe.RecipeSerializerExt;
import cn.breadnicecat.candycraftce.utils.ItemUtils;
import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static cn.breadnicecat.candycraftce.utils.CommonUtils.*;

/**
 * Created in 2024/2/4
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-NiceCat">Bread_NiceCat</a>
 * <p>
 */
public class SugarFactoryRecipe implements Recipe<SugarFactoryBE> {
	public final ResourceLocation id;
	public final Item result;
	public final int count;
	public final Ingredient ingredient;
	public final boolean advanced;
	
	public SugarFactoryRecipe(ResourceLocation id, Ingredient ingredient, Item result, int count, boolean advanced) {
		this.id = id;
		this.ingredient = ingredient;
		this.result = result;
		assertTrue(count > 0, "count <= 0 in recipe" + id);
		this.count = count;
		this.advanced = advanced;
	}
	
	
	private static boolean isAdvanced(SugarFactoryBE be) {
		return be.getType() == CBlockEntities.ADVANCED_SUGAR_FACTORY_BE.get();
	}
	
	@Override
	public boolean matches(SugarFactoryBE container, Level level) {
		return (!advanced || isAdvanced(container)) && ingredient.test(container.getItem(SugarFactoryBE.INPUT_SLOT));
	}
	
	@Override
	public @NotNull ItemStack assemble(SugarFactoryBE container, RegistryAccess registryAccess) {
		ItemStack stack = result.getDefaultInstance();
		stack.setCount(count);
		return stack;
	}
	
	@Override
	public boolean canCraftInDimensions(int width, int height) {
		return true;
	}
	
	@Override
	public @NotNull NonNullList<Ingredient> getIngredients() {
		return apply(NonNullList.create(), it -> it.add(ingredient));
	}
	
	@Override
	public @NotNull ItemStack getResultItem(@Nullable RegistryAccess registryAccess) {
		ItemStack stack = result.getDefaultInstance();
		stack.setCount(count);
		return stack;
	}
	
	@Override
	public @NotNull ResourceLocation getId() {
		return id;
	}
	
	@Override
	public @NotNull RecipeSerializer<?> getSerializer() {
		return CRecipeTypes.SUGAR_FACTORY_TYPE.getSerializer();
	}
	
	@Override
	public @NotNull RecipeType<?> getType() {
		return CRecipeTypes.SUGAR_FACTORY_TYPE.get();
	}
	
	public static class Serializer implements RecipeSerializerExt<SugarFactoryRecipe> {
		@Override
		public @NotNull SugarFactoryRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
			JsonObject result = json.get("result").getAsJsonObject();
			return new SugarFactoryRecipe(recipeId,
					Ingredient.fromJson(json.get("ingredient")),
					ItemUtils.getItem(result.get("item")),
					result.has("count") ? result.get("count").getAsInt() : 1,
					json.has("advanced") && json.get("advanced").getAsBoolean());
		}
		
		@Override
		public void toJson(JsonObject object, SugarFactoryRecipe recipe) {
			object.add("result", make(() -> {
				JsonObject result = new JsonObject();
				result.addProperty("item", ItemUtils.getKey(recipe.result).toString());
				if (recipe.count != 1) result.addProperty("count", recipe.count);
				return result;
			}));
			object.add("ingredient", recipe.ingredient.toJson());
			if (recipe.advanced) object.addProperty("advanced", true);
		}
		
		@Override
		public @NotNull SugarFactoryRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
			return new SugarFactoryRecipe(recipeId,
					Ingredient.fromNetwork(buffer),
					buffer.readById(BuiltInRegistries.ITEM),
					buffer.readVarInt(),
					buffer.readBoolean());
		}
		
		@Override
		public void toNetwork(FriendlyByteBuf buffer, SugarFactoryRecipe recipe) {
			recipe.ingredient.toNetwork(buffer);
			buffer.writeId(BuiltInRegistries.ITEM, recipe.result);
			buffer.writeVarInt(recipe.count);
			buffer.writeBoolean(recipe.advanced);
		}
	}
}
