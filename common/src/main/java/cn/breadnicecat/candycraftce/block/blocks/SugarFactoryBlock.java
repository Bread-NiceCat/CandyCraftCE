package cn.breadnicecat.candycraftce.block.blocks;

import cn.breadnicecat.candycraftce.block.blockentity.CBlockEntities;
import cn.breadnicecat.candycraftce.block.blockentity.entities.SugarFactoryBE;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created in 2024/2/3
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-NiceCat">Bread_NiceCat</a>
 * <p>
 */
public class SugarFactoryBlock extends Block implements EntityBlock {
	public SugarFactoryBlock(Properties properties) {
		super(properties);
	}
	
	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return CBlockEntities.SUGAR_FACTORY_BE.create(pos, state);
	}
	
	@Override
	protected @NotNull InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
		if (!level.isClientSide() && level.getBlockEntity(pos) instanceof SugarFactoryBE sf) {
			player.openMenu(sf);
		}
		return InteractionResult.sidedSuccess(level.isClientSide());
	}
	
	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
		return level.isClientSide() ? null : (lvl, pos, stat1, be) -> {
			if (be instanceof SugarFactoryBE sf) {
				sf.serverTick();
			}
		};
	}
	
	@Override
	public void onRemove(@NotNull BlockState state, Level level, BlockPos pos, @NotNull BlockState newState, boolean movedByPiston) {
		if (state.is(newState.getBlock())) {
			return;
		}
		if (!level.isClientSide() && level.getBlockEntity(pos) instanceof SugarFactoryBE be) {
			Containers.dropContents(level, pos, be);
		}
		super.onRemove(state, level, pos, newState, movedByPiston);
	}
	
	@Override
	public boolean hasAnalogOutputSignal(BlockState state) {
		return true;
	}
	
	@Override
	public int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos) {
		return AbstractContainerMenu.getRedstoneSignalFromBlockEntity(level.getBlockEntity(pos));
	}
}
