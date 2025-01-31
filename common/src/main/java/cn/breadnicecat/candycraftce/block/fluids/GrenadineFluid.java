package cn.breadnicecat.candycraftce.block.fluids;

import dev.architectury.core.fluid.ArchitecturyFlowingFluid;
import dev.architectury.core.fluid.ArchitecturyFluidAttributes;

/**
 * Created in 2025/1/30 19:50
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 *
 * <p>
 **/
public sealed interface GrenadineFluid {
	final class Flowing extends ArchitecturyFlowingFluid.Flowing implements GrenadineFluid {
		
		public Flowing(ArchitecturyFluidAttributes attributes) {
			super(attributes);
		}
	}
	
	final class Source extends ArchitecturyFlowingFluid.Source implements GrenadineFluid {
		
		public Source(ArchitecturyFluidAttributes attributes) {
			super(attributes);
		}
	}
}
