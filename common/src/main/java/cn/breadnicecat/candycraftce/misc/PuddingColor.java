package cn.breadnicecat.candycraftce.misc;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ColorResolver;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

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
	
	//	private final static NormalNoise GRASS_COLOR_NOISE = NormalNoise.create(RandomSource.create(2345L), -7, 1);
	private final static NormalNoise GRASS_COLOR_NOISE = NormalNoise.create(RandomSource.create(2345L), -7, 1);
	
	private static int getColor(Biome biome, double x, double y) {
		
		int color = biome.getSpecialEffects().getGrassColorOverride().orElseGet(PuddingColor::getDefaultColor);
		//EnchantedForest
		if (color == -1) {
			double d0 = GRASS_COLOR_NOISE.getValue(x, 0, y);
			//#b0ecff 淡蓝色 #b0b0ff 淡紫色 #a376da 深紫色
			return (d0 < -0.4) ? 0xb0ecff : ((d0 < 0.4D) ? 0xb0b0ff : 0xa376da);
		}
		return biome.getSpecialEffects().getGrassColorModifier().modifyColor(x, y, color);
	}
	
	public static int getDefaultColor() {
		//#dd99aa 粉色
		return 0xdd99aa;
	}
	
}
