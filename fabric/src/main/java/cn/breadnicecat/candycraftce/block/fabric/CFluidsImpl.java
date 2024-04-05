package cn.breadnicecat.candycraftce.block.fabric;

import cn.breadnicecat.candycraftce.block.fluids.CaramelFluid;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler;
import net.minecraft.resources.ResourceLocation;

import static cn.breadnicecat.candycraftce.utils.ResourceUtils.pathPrefix;

/**
 * Created in 2024/4/5 下午11:05
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 * <p>
 */
public class CFluidsImpl {
	public static CaramelFluid _caramel_fluid(ResourceLocation tex) {
		CaramelFluid fluid = new CaramelFluid() {
		};
		FluidRenderHandlerRegistry.INSTANCE.register(fluid, new SimpleFluidRenderHandler(tex, tex));
		return fluid;
	}

	private static ResourceLocation asTex(ResourceLocation loc) {
		return pathPrefix(loc, "block/");
	}
}
