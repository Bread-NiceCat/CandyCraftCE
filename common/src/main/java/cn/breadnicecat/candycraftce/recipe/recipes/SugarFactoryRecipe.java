package cn.breadnicecat.candycraftce.recipe.recipes;

import cn.breadnicecat.candycraftce.block.blockentity.CBlockEntities;
import cn.breadnicecat.candycraftce.block.blockentity.ContainerRecipeInput;
import cn.breadnicecat.candycraftce.block.blockentity.entities.SugarFactoryBE;
import cn.breadnicecat.candycraftce.recipe.CRecipeTypes;
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

import static cn.breadnicecat.candycraftce.utils.CommonUtils.apply;

/**
 * Created in 2024/2/4
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-NiceCat">Bread_NiceCat</a>
 * <p>
 */
public class SugarFactoryRecipe implements Recipe<ContainerRecipeInput<SugarFactoryBE>> {
	private final ItemStack result;
	public final Ingredient ingredient;
	public final boolean advanced;
	
	public SugarFactoryRecipe(Ingredient ingredient, ItemStack result, boolean advanced) {
		this.ingredient = ingredient;
		this.result = result;
		this.advanced = advanced;
	}
	
	public ItemStack getResult() {
		return result.copy();
	}
	
	public int getCount() {
		return result.getCount();
	}
	
	private static boolean isAdvanced(SugarFactoryBE be) {
		return be.getType() == CBlockEntities.ADVANCED_SUGAR_FACTORY_BE.get();
	}
	
	
	@Override
	public boolean matches(ContainerRecipeInput<SugarFactoryBE> input, Level level) {
		SugarFactoryBE container = input.container();
		return (!advanced || isAdvanced(container)) && ingredient.test(container.getItem(SugarFactoryBE.INPUT_SLOT));
	}
	
	@Override
	public ItemStack assemble(ContainerRecipeInput<SugarFactoryBE> input, HolderLookup.Provider registries) {
		return result.copy();
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
	public @NotNull ItemStack getResultItem(HolderLookup.Provider registries) {
		return result.copy();
	}
	
	@Override
	public @NotNull RecipeSerializer<?> getSerializer() {
		return CRecipeTypes.SUGAR_FACTORY_TYPE.getSerializer();
	}
	
	@Override
	public @NotNull RecipeType<?> getType() {
		return CRecipeTypes.SUGAR_FACTORY_TYPE.get();
	}
	
	public static class Serializer extends RecipeSerializerExt<SugarFactoryRecipe> {
		
		
		@Override
		public @NotNull SugarFactoryRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {
			return new SugarFactoryRecipe(
					Ingredient.CONTENTS_STREAM_CODEC.decode(buffer),
					ItemStack.STREAM_CODEC.decode(buffer),
					buffer.readBoolean());
		}
		
		public void toNetwork(RegistryFriendlyByteBuf buffer, SugarFactoryRecipe recipe) {
			Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.ingredient);
			ItemStack.STREAM_CODEC.encode(buffer, recipe.result);
			buffer.writeBoolean(recipe.advanced);
		}
		
		public static final MapCodec<SugarFactoryRecipe> CODEC = RecordCodecBuilder.mapCodec(
				i -> i.group(
						Ingredient.CODEC.fieldOf("ingredient").forGetter(t -> t.ingredient),
						ItemStack.CODEC.fieldOf("result").forGetter(t -> t.result),
						Codec.BOOL.fieldOf("advanced").forGetter(t -> t.advanced)
				).apply(i, SugarFactoryRecipe::new));
		
		@Override
		public @NotNull MapCodec<SugarFactoryRecipe> codec() {
			return CODEC;
		}
		
	}
}
