package cn.breadnicecat.candycraftce.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.RandomSource;
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
	private final static NormalNoise GRASS_COLOR_NOISE = NormalNoise.create(RandomSource.create(8526L), -7, 1);
	
	/**
	 * @return #b0ecff 淡蓝色 #b0b0ff 淡紫色 #a376da 深紫色
	 */
	public static int getEnchantColor(double x, double z) {
		double d0 = GRASS_COLOR_NOISE.getValue(x, 0, z);
		return (d0 < -0.4) ? 0xb0ecff : ((d0 < 0.4D) ? 0xb0b0ff : 0xa376da);
	}
	
	/**
	 * @return #dd99aa 粉色
	 */
	public static int getDefaultPuddingColor() {
		return 0xdd99aa;
	}
	
	public static int getDefaultEnchantColor() {
		return 0xb0b0ff;
	}
}
