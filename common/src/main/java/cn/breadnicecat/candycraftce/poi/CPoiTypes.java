package cn.breadnicecat.candycraftce.poi;

import cn.breadnicecat.candycraftce.CandyCraftCE;
import cn.breadnicecat.candycraftce.utils.CLogUtils;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import org.slf4j.Logger;

import java.util.function.Function;

import static cn.breadnicecat.candycraftce.utils.ResourceUtils.prefix;

public class CPoiTypes {
	
	private static final Logger LOGGER = CLogUtils.sign();
	
	public static void init() {
	}
	
	private static <F extends PoiType> PoiTypeEntry<F> register(String name, Function<ResourceLocation, F> factory) {
		ResourceLocation id = prefix(name);
		return new PoiTypeEntry<>(CandyCraftCE.register(BuiltInRegistries.POINT_OF_INTEREST_TYPE, id, () -> factory.apply(id)));
	}
}
