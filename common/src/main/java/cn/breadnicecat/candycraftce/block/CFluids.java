package cn.breadnicecat.candycraftce.block;

import cn.breadnicecat.candycraftce.EngineFeatures;
import cn.breadnicecat.candycraftce.block.fluids.CaramelFluid;
import cn.breadnicecat.candycraftce.utils.CommonUtils;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.Fluid;

import java.util.function.Function;

import static cn.breadnicecat.candycraftce.utils.ResourceUtils.pathExtend;
import static cn.breadnicecat.candycraftce.utils.ResourceUtils.prefix;

/**
 * Created in 2024/4/4 0:38
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 * <p>
 */
public class CFluids {
	public static final FluidEntry<CaramelFluid> CARAMEL = register("caramel", id -> _caramel_fluid(asTex(id, "_static")));

	private static <F extends Fluid> FluidEntry<F> register(String name, Function<ResourceLocation, F> factory) {
		ResourceLocation id = prefix(name);
		return EngineFeatures.get().registerFluid(id, () -> factory.apply(id));
	}

	public static void init() {
	}

	@ExpectPlatform
	private static CaramelFluid _caramel_fluid(ResourceLocation tex) {
		return CommonUtils.impossibleCode();
	}

	private static ResourceLocation asTex(ResourceLocation loc, String postfix) {
		return pathExtend(loc, "block/", postfix);
	}
}
