package cn.breadnicecat.candycraftce.block.blockentity.entities;

import cn.breadnicecat.candycraftce.block.blockentity.CBlockEntities;
import cn.breadnicecat.candycraftce.gui.block.menus.AdvancedSugarFactoryMenu;
import cn.breadnicecat.candycraftce.recipe.recipes.SugarFactoryRecipe;
import cn.breadnicecat.candycraftce.utils.TickUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

/**
 * Created in 2024/2/4
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-NiceCat">Bread_NiceCat</a>
 * <p>
 */
public class AdvancedFactoryBE extends SugarFactoryBE {
	public static final int ADVANCED_TYPE = 3;
	public static final int ADVANCED_TICKED_TOTAL = (int) (6 * TickUtils.SEC2TICK);

	protected AdvancedFactoryBE(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
		super(blockEntityType, blockPos, blockState);
	}

	public AdvancedFactoryBE(BlockPos blockPos, BlockState blockState) {
		this(CBlockEntities.ADVANCED_SUGAR_FACTORY_BE.get(), blockPos, blockState);
	}

	@Override
	public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
		return new AdvancedSugarFactoryMenu(i, inventory, this, data);
	}

	@Override
	protected int getRecipeType(@Nullable SugarFactoryRecipe recipe) {
		return recipe != null && recipe.advanced ? ADVANCED_TYPE : super.getRecipeType(recipe);
	}

	@Override
	protected int getTickTimeTotal(@Nullable SugarFactoryRecipe recipe) {
		return recipe != null && !recipe.advanced ? ADVANCED_TICKED_TOTAL : super.getTickTimeTotal(recipe);
	}
}
