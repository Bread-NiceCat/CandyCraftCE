package cn.breadnicecat.candycraftce.block.fluids;

import cn.breadnicecat.candycraftce.block.CBlocks;
import cn.breadnicecat.candycraftce.level.CaramelPortal;
import cn.breadnicecat.candycraftce.utils.LevelUtils;
import dev.architectury.core.fluid.ArchitecturyFlowingFluid;
import dev.architectury.core.fluid.ArchitecturyFluidAttributes;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.material.FluidState;

/**
 * Created in 2025/1/30 14:13
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 *
 * <p>
 **/
public sealed interface CaramelFluid permits CaramelFluid.Flowing, CaramelFluid.Source {
	final class Source extends ArchitecturyFlowingFluid.Source implements CaramelFluid {
		
		public Source(ArchitecturyFluidAttributes attributes) {
			super(attributes);
		}
		
		@Override
		protected void randomTick(Level level, BlockPos pos, FluidState state, RandomSource random) {
			//fizz 当周围没有方块的时候
			long l = LevelUtils.getNeighbourPos(pos)
					.filter(p -> {
						FluidState f = level.getFluidState(p);
						return level.getBlockState(p).isAir() && (f.isEmpty() || f.is(FluidTags.WATER));
					})
					.count();
			if (l != 0) {
				froze(level, pos);
			}
		}
		
		protected void froze(Level level, BlockPos pos) {
			level.levelEvent(LevelEvent.LAVA_FIZZ, pos, 0);
			level.setBlockAndUpdate(pos, CBlocks.CARAMEL_BLOCK.defaultBlockState());
			CaramelPortal.tryBuildOn(level, pos);
		}
		
		@Override
		protected boolean isRandomlyTicking() {
			return true;
		}
	}
	
	final class Flowing extends ArchitecturyFlowingFluid.Flowing implements CaramelFluid {
		public Flowing(ArchitecturyFluidAttributes attributes) {
			super(attributes);
		}
		
	}
}
