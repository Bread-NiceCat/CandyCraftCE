package cn.breadnicecat.candycraftce.registration.block.fabric;

import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Supplier;

/**
 * Created in 2024/1/29 10:07
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 * <p>
 */
public class CBlocksImpl {

	public static StairBlock _stairBlock(Supplier<BlockState> base, BlockBehaviour.Properties p) {
		return new StairBlock(base.get(), p);
	}
}
