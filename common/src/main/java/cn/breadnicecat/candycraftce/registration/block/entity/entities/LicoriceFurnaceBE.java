package cn.breadnicecat.candycraftce.registration.block.entity.entities;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

/**
 * Created in 2024/1/30 23:07
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 * <p>
 */
public class LicoriceFurnaceBE extends AbstractFurnaceBlockEntity {
	protected LicoriceFurnaceBE(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState, RecipeType<? extends AbstractCookingRecipe> recipeType) {
		super(blockEntityType, blockPos, blockState, recipeType);
	}

	@Override
	protected @NotNull Component getDefaultName() {
		return null;
	}

	@Override
	protected @NotNull AbstractContainerMenu createMenu(int containerId, Inventory inventory) {
		return null;
	}
}
