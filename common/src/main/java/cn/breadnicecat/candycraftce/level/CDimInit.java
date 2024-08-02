package cn.breadnicecat.candycraftce.level;

import cn.breadnicecat.candycraftce.CandyCraftCE;
import cn.breadnicecat.candycraftce.level.foliage_placer.CandiedCherryFoliagePlacer;
import cn.breadnicecat.candycraftce.level.foliage_placer.FancyCaramelFoliagePlacer;
import cn.breadnicecat.candycraftce.utils.CLogUtils;
import cn.breadnicecat.candycraftce.utils.SimpleEntry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import org.slf4j.Logger;

import static cn.breadnicecat.candycraftce.utils.ResourceUtils.prefix;

/**
 * Created in 2024/3/24 9:40
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 * <p>
 */
public class CDimInit {
	private static final Logger LOGGER = CLogUtils.sign();
	public static final SimpleEntry<FoliagePlacerType<?>, FoliagePlacerType<CandiedCherryFoliagePlacer>> CANDIED_CHERRY_FOLIAGE_PLACER = new SimpleEntry<>(CandyCraftCE.register(BuiltInRegistries.FOLIAGE_PLACER_TYPE, prefix("candied_cherry"), () -> new FoliagePlacerType<>(CandiedCherryFoliagePlacer.CODEC)));
	public static final SimpleEntry<FoliagePlacerType<?>, FoliagePlacerType<FancyCaramelFoliagePlacer>> FANCY_CARAMEL_FOLIAGE_PLACER = new SimpleEntry<>(CandyCraftCE.register(BuiltInRegistries.FOLIAGE_PLACER_TYPE, prefix("fancy_caramel"), () -> new FoliagePlacerType<>(FancyCaramelFoliagePlacer.CODEC)));
	
	public static void init() {
	}
}
