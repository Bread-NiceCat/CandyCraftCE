package cn.breadnicecat.candycraftce.recipe.recipes;

import cn.breadnicecat.candycraftce.block.blockentity.ContainerRecipeInput;
import cn.breadnicecat.candycraftce.block.blockentity.entities.LicoriceFurnaceBE;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import static cn.breadnicecat.candycraftce.recipe.CRecipeTypes.SUGAR_FURNACE_TYPE;
import static cn.breadnicecat.candycraftce.utils.CommonUtils.apply;

public class SugarFurnaceRecipe implements Recipe<ContainerRecipeInput<LicoriceFurnaceBE>> {
	public final ItemStack result;
	public final Ingredient ingredient;
	public final float exp;
	
	public SugarFurnaceRecipe(Ingredient ingredient, ItemStack result, float exp) {
		this.result = result;
		this.ingredient = ingredient;
		this.exp = exp;
	}
	
	public ItemStack getResult() {
		return result.copy();
	}
	
	public float getExp() {
		return exp;
	}
	
	public int getCount() {
		return result.getCount();
	}
	
	@Override
	public boolean matches(ContainerRecipeInput<LicoriceFurnaceBE> input, Level level) {
		return ingredient.test(input.container().getItem(LicoriceFurnaceBE.INPUT_SLOT));
	}
	
	@Override
	public @NotNull ItemStack assemble(ContainerRecipeInput<LicoriceFurnaceBE> input, HolderLookup.Provider registries) {
		return result.copy();
	}
	
	@Override
	public @NotNull NonNullList<Ingredient> getIngredients() {
		return apply(NonNullList.create(), it -> it.add(ingredient));
	}
	
	@Override
	public @NotNull ItemStack getResultItem(HolderLookup.Provider registries) {
		return result.copy();
	}
	
	@Override
	public @NotNull RecipeSerializer<?> getSerializer() {
		return SUGAR_FURNACE_TYPE.getSerializer();
	}
	
	@Override
	public @NotNull RecipeType<SugarFurnaceRecipe> getType() {
		return SUGAR_FURNACE_TYPE.get();
	}
	
	
	@Override
	public boolean canCraftInDimensions(int width, int height) {
		return true;
	}
	
	public static class Serializer extends RecipeSerializerExt<SugarFurnaceRecipe> {
		public static MapCodec<SugarFurnaceRecipe> CODEC = RecordCodecBuilder.mapCodec((i) -> i.group(
				Ingredient.CODEC.fieldOf("ingredient").forGetter(t -> t.ingredient),
				ItemStack.CODEC.fieldOf("result").forGetter(t -> t.result),
				Codec.FLOAT.fieldOf("exp").forGetter(t -> t.exp)
		).apply(i, SugarFurnaceRecipe::new));
		
		@Override
		protected @NotNull SugarFurnaceRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {
			return new SugarFurnaceRecipe(
					Ingredient.CONTENTS_STREAM_CODEC.decode(buffer),
					ItemStack.STREAM_CODEC.decode(buffer),
					buffer.readFloat());
		}
		
		@Override
		public void toNetwork(RegistryFriendlyByteBuf buffer, SugarFurnaceRecipe recipe) {
			Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.ingredient);
			ItemStack.STREAM_CODEC.encode(buffer, recipe.result);
			buffer.writeFloat(recipe.exp);
		}
		
		@Override
		public @NotNull MapCodec<SugarFurnaceRecipe> codec() {
			return CODEC;
		}
	}
	
}
