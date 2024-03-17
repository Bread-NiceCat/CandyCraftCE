package cn.breadnicecat.candycraftce.block.blockentity.entities;

import cn.breadnicecat.candycraftce.block.blockentity.CBlockEntities;
import cn.breadnicecat.candycraftce.gui.block.menus.ChocolateFurnaceMenu;
import cn.breadnicecat.candycraftce.utils.TickUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

/**
 * Created in 2024/2/3
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-NiceCat">Bread_NiceCat</a>
 * <p>
 */
public class ChocolateFurnaceBE extends LicoriceFurnaceBE {
	protected ChocolateFurnaceBE(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState, int tickedTotal) {
		super(blockEntityType, blockPos, blockState, tickedTotal);
	}

	public ChocolateFurnaceBE(BlockPos blockPos, BlockState blockState) {
		this((BlockEntityType<?>) CBlockEntities.CHOCOLATE_FURNACE_BE.get(), blockPos, blockState, (int) (15 * TickUtils.SEC2TICK));
	}

	@Override
	public @Nullable AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
		return new ChocolateFurnaceMenu(i, inventory, this, data);
	}
}
