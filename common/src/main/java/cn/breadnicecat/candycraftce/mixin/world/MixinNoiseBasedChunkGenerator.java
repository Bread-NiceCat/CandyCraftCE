package cn.breadnicecat.candycraftce.mixin.world;

import cn.breadnicecat.candycraftce.block.CBlocks;
import net.minecraft.world.level.levelgen.Aquifer;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Created in 2024/7/7 下午11:55
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 *
 * <p>
 **/
@Mixin(NoiseBasedChunkGenerator.class)
public abstract class MixinNoiseBasedChunkGenerator {
	
	@Inject(method = "createFluidPicker", at = @At("HEAD"), cancellable = true)
	private static void createFluidPicker(NoiseGeneratorSettings settings, CallbackInfoReturnable<Aquifer.FluidPicker> cir) {
		if (settings.defaultBlock().is(CBlocks.CHOCOLATE_STONE.get())) {
			//no lava places in world
			Aquifer.FluidStatus fluidStatus2 = new Aquifer.FluidStatus(settings.seaLevel(), settings.defaultFluid());
			cir.setReturnValue((j, k, l) -> fluidStatus2);
		}
	}
}
