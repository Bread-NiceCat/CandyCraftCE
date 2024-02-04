package cn.breadnicecat.candycraftce.block.blocks;

import cn.breadnicecat.candycraftce.block.blockentity.CBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

/**
 * Created in 2024/2/3
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-NiceCat">Bread_NiceCat</a>
 * <p>
 */
public class ChocolateFurnace extends LicoriceFurnace {
	public ChocolateFurnace(Properties properties) {
		super(properties);
	}

	@Override
	public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return CBlockEntities.CHOCOLATE_FURNACE_BE.create(pos, state);
	}
}
