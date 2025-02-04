package cn.breadnicecat.candycraftce.level;

import cn.breadnicecat.candycraftce.block.CBlockTags;
import cn.breadnicecat.candycraftce.block.CBlocks;
import cn.breadnicecat.candycraftce.utils.Axes;
import cn.breadnicecat.candycraftce.utils.multiblocks.VectorPortalShape;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.BiFunction;

import static cn.breadnicecat.candycraftce.block.CBlocks.CARAMEL_PORTAL;
import static cn.breadnicecat.candycraftce.block.blocks.CaramelPortalBlock.*;
import static net.minecraft.world.level.block.Blocks.LAVA;

/**
 * Created in 2025/2/4 15:22
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 *
 * <p>
 **/
public class CaramelPortal {
	public static final VectorPortalShape.PortalConfig CONFIG = new VectorPortalShape.PortalConfig(
			2, 21, 3, 21,
			true, true,
			b -> b.isAir() || /*b.is(CARAMEL_LIQUID.get()) ||*/ b.is(LAVA) || b.is(CARAMEL_PORTAL.get()),
			b -> b.is(CBlockTags.BT_CARAMEL_PORTAL_FRAME));
	
	public static final BiFunction<Axes, BlockState, BlockState> PLACER = (axes, old) -> {
		if (old.is(CARAMEL_PORTAL.get())) {
			return old.setValue(X, axes.hasX() | old.getValue(X))
					.setValue(Y, axes.hasY() | old.getValue(Y))
					.setValue(Z, axes.hasZ() | old.getValue(Z));
		} else return CARAMEL_PORTAL.defaultBlockState()
				.setValue(X, axes.hasX())
				.setValue(Y, axes.hasY())
				.setValue(Z, axes.hasZ());
	};
	
	public static void tryBuildOn(Level level, BlockPos pos) {
		VectorPortalShape.findPortalOnFrame(level, pos, CONFIG).ifPresent(t -> build(level, t));
	}
	
	/**
	 * 当前pos在框架内
	 */
	public static void tryBuildIn(Level level, BlockPos pos) {
		VectorPortalShape.findPortalInFrame(level, pos, CONFIG).ifPresent(t -> build(level, t));
	}
	
	public static void build(Level level, VectorPortalShape shape) {
		for (BlockPos frame : shape.getAllFrames()) {
			BlockState state = level.getBlockState(frame);
			if (state.is(CBlocks.SUGAR_BLOCK.get())) {
				level.setBlockAndUpdate(frame, CBlocks.CARAMEL_BLOCK.defaultBlockState());
			}
		}
		shape.build(level, PLACER);
	}
}
