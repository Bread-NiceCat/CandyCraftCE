package cn.breadnicecat.candycraftce.block;

import cn.breadnicecat.candycraftce.CandyCraftCE;
import cn.breadnicecat.candycraftce.block.fluids.CaramelFluid;
import cn.breadnicecat.candycraftce.utils.CLogUtils;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.Fluid;
import org.slf4j.Logger;

import java.util.function.Function;

import static cn.breadnicecat.candycraftce.utils.CommonUtils.impossibleCode;
import static cn.breadnicecat.candycraftce.utils.ResourceUtils.extend;
import static cn.breadnicecat.candycraftce.utils.ResourceUtils.prefix;

/**
 * Created in 2024/4/4 0:38
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 * <p>
 */
public class CFluids {

    private static final Logger LOGGER = CLogUtils.sign();

    public static final FluidEntry<CaramelFluid> CARAMEL = register("caramel", id -> _caramel_fluid(asTex(id, "_static")));

    private static <F extends Fluid> FluidEntry<F> register(String name, Function<ResourceLocation, F> factory) {
        ResourceLocation id = prefix(name);
        return new FluidEntry<>(CandyCraftCE.register(BuiltInRegistries.FLUID, id, () -> factory.apply(id)));
    }

    public static void init() {
    }

    @ExpectPlatform
    private static CaramelFluid _caramel_fluid(ResourceLocation tex) {
        return impossibleCode();
    }

    private static ResourceLocation asTex(ResourceLocation loc, String postfix) {
        return extend(loc, "block/", postfix);
    }
}
