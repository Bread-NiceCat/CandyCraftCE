package cn.breadnicecat.candycraftce.block.fluids;

import dev.architectury.core.fluid.ArchitecturyFlowingFluid;
import dev.architectury.core.fluid.ArchitecturyFluidAttributes;

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
	final class Flowing extends ArchitecturyFlowingFluid.Flowing implements CaramelFluid {
		
		public Flowing(ArchitecturyFluidAttributes attributes) {
			super(attributes);
		}
	}
	
	final class Source extends ArchitecturyFlowingFluid.Source implements CaramelFluid {
		
		public Source(ArchitecturyFluidAttributes attributes) {
			super(attributes);
		}
	}
}
