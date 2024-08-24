package cn.breadnicecat.candycraftce.block.blocks;

import cn.breadnicecat.candycraftce.block.blockentity.CBlockEntities;
import cn.breadnicecat.candycraftce.block.blockentity.entities.LicoriceFurnaceBE;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Containers;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created in 2024/1/30 23:03
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 */
public class LicoriceFurnaceBlock extends AbstractFurnaceBlock {
	
	private static final MapCodec<LicoriceFurnaceBlock> CODEC = simpleCodec(LicoriceFurnaceBlock::new);
	
	public LicoriceFurnaceBlock(Properties properties) {
		super(properties);
	}
	
	@Override
	protected @NotNull MapCodec<? extends AbstractFurnaceBlock> codec() {
		return CODEC;
	}
	
	@Override
	protected void openContainer(@NotNull Level level, BlockPos pos, Player player) {
		BlockEntity blockEntity = level.getBlockEntity(pos);
		if (blockEntity instanceof LicoriceFurnaceBE fbe) {
			player.openMenu(fbe);
		}
	}
	
	@Override
	public void onRemove(@NotNull BlockState state, Level level, BlockPos pos, @NotNull BlockState newState, boolean movedByPiston) {
		if (state.is(newState.getBlock())) {
			return;
		}
		BlockEntity blockEntity = level.getBlockEntity(pos);
		if (!level.isClientSide() && blockEntity instanceof LicoriceFurnaceBE be) {
			Containers.dropContents(level, pos, be);
			be.dropExp(Vec3.atCenterOf(pos));
			level.updateNeighbourForOutputSignal(pos, this);
		}
		super.onRemove(state, level, pos, newState, movedByPiston);
	}
	
	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return CBlockEntities.LICORICE_FURNACE_BE.create(pos, state);
	}
	
	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
		return level.isClientSide() ? null : (lvl, pos, stat1, be) -> {
			if (be instanceof LicoriceFurnaceBE licoriceBE) {
				licoriceBE.serverTick();
			}
		};
	}
}

