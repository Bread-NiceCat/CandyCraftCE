package cn.breadnicecat.candycraftce.level;

import cn.breadnicecat.candycraftce.CandyCraftCE;
import cn.breadnicecat.candycraftce.EngineFeatures;
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
	private static final EngineFeatures features = CandyCraftCE.getFeatures();
	public static final SimpleEntry<FoliagePlacerType<?>> CANDIED_CHERRY_FOLIAGE_PLACER = features.register(BuiltInRegistries.FOLIAGE_PLACER_TYPE, prefix("candied_cherry"), () -> new FoliagePlacerType<>(CandiedCherryFoliagePlacer.CODEC));

	public static void init() {
	}
}
