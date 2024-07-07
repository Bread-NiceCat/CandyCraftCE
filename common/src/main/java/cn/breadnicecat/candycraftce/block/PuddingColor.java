package cn.breadnicecat.candycraftce.block;

import cn.breadnicecat.candycraftce.utils.CLogUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.resources.LegacyStuffWrapper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.ReloadableResourceManager;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.Mth;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.ColorResolver;
import net.minecraft.world.level.biome.Biome;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.io.IOException;

import static cn.breadnicecat.candycraftce.utils.ResourceUtils.prefix;

/**
 * Created in 2024/7/6 下午9:54
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 *
 * <p>
 **/
@Environment(EnvType.CLIENT)
public class PuddingColor {
	
	public static final ColorResolver PUDDING_COLOR_RESOLVER = PuddingColor::getColor;
	private static final Logger LOGGER = CLogUtils.sign();
	private static final ResourceLocation LOCATION = prefix("textures/colormap/pudding.png");
	private static int[] pixels = new int[0];
	public static final PreparableReloadListener RELOAD_LISTENER = new SimplePreparableReloadListener<int[]>() {
		@SuppressWarnings("deprecation")
		@Override
		protected int @NotNull [] prepare(ResourceManager resourceManager, ProfilerFiller profiler) {
			LOGGER.info("loading PuddingColor Color-map");
			try {
				return LegacyStuffWrapper.getPixels(resourceManager, LOCATION);
			} catch (IOException iOException) {
				throw new IllegalStateException("Failed to load color texture", iOException);
			}
		}
		
		@Override
		protected void apply(int[] object, ResourceManager resourceManager, ProfilerFiller profiler) {
			pixels = object;
		}
	};
	
	private PuddingColor() {
	}
	
	public static void init(ReloadableResourceManager m) {
		LOGGER.info("adding PuddingColor Reload Listener");
	}
	
	private static int getColor(Biome biome, double x, double y) {
		Biome.ClimateSettings settings = biome.climateSettings;
		int color = biome.getSpecialEffects().getGrassColorOverride().orElseGet(() -> {
			double temp = Mth.clamp(settings.temperature(), 0.0f, 1.0f);
			double df = Mth.clamp(settings.downfall(), 0.0f, 1.0f);
			return get(temp, df);
		});
		return biome.getSpecialEffects().getGrassColorModifier().modifyColor(x, y, color);
	}
	
	private static int get(double temperature, double humidity) {
		int j = (int) ((1.0 - (humidity * temperature)) * 255.0);
		int i = (int) ((1.0 - temperature) * 255.0);
		int k = j << 8 | i;
		//-65281 : 紫色
		//14522794 : 粉色
		return k >= pixels.length ? 14522794 : pixels[k];
	}
	
	public static int getDefaultColor() {
		return get(0.5, 1.0);
	}
	
}
