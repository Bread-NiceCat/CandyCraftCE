package cn.breadnicecat.candycraftce.level;

import cn.breadnicecat.candycraftce.utils.CLogUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ColorResolver;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import org.slf4j.Logger;

import java.util.Optional;

import static cn.breadnicecat.candycraftce.block.CBlocks.CUSTARD_PUDDING;
import static cn.breadnicecat.candycraftce.block.CBlocks.MAGICAL_LEAVES;
import static cn.breadnicecat.candycraftce.item.CItems.MAGICAL_LEAF;
import static cn.breadnicecat.candycraftce.level.CBiomes.BIOME_PUDDING_COLORS;
import static cn.breadnicecat.candycraftce.level.CBiomes.ENCHANTED_FOREST;

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
	private static final Logger LOGGER = CLogUtils.sign();
	
	public static final ColorResolver PUDDING_COLOR_RESOLVER = PuddingColor::getColor;
	
	private static int getColor(Biome biome, double x, double z) {
		ClientLevel level = Minecraft.getInstance().level;
		Optional<ResourceKey<Biome>> op;
		if (level == null
				|| (op = level.registryAccess().registryOrThrow(Registries.BIOME).getResourceKey(biome)).isEmpty()) {
			return getDefaultPuddingColor();
		}
		ResourceKey<Biome> key = op.get();
		if (key == ENCHANTED_FOREST) {
			return getEnchantColor(x, z);
		}
		return BIOME_PUDDING_COLORS.getInt(key);
	}
	
	private final static NormalNoise GRASS_COLOR_NOISE = NormalNoise.create(RandomSource.create(8526L), -7, 1);
	
	/**
	 * @return #b0ecff 淡蓝色 #b0b0ff 淡紫色 #a376da 深紫色
	 */
	public static int getEnchantColor(double x, double z) {
		double d0 = GRASS_COLOR_NOISE.getValue(x, 0, z);
		return (d0 < -0.2) ? 0xb0ecff : d0 > 0.4 ? 0xa376da : 0xb0b0ff;
	}
	
	/**
	 * @return #dd99aa 粉色
	 */
	public static int getDefaultPuddingColor() {
		return 0xdd99aa;
	}
	
	/**
	 * @return #b0b0ff 淡紫色
	 */
	public static int getDefaultEnchantColor() {
		return 0xb0b0ff;
	}
	
	@Environment(EnvType.CLIENT)
	public static void _registerBlockColors(BlockColors colors) {
		LOGGER.info("Registering Block Colors");
		colors.register((state, getter, pos, tintindex) ->
						getter != null ? getter.getBlockTint(pos, PUDDING_COLOR_RESOLVER)
								: getDefaultEnchantColor(),
				CUSTARD_PUDDING.get(), MAGICAL_LEAVES.get());
		
	}
	
	@Environment(EnvType.CLIENT)
	public static void _registerItemColors(BlockColors blockColors, ItemColors itemColors) {
		LOGGER.info("Registering Item Colors");
		itemColors.register((item, tintindex) -> getDefaultPuddingColor(), CUSTARD_PUDDING);
		itemColors.register((item, tintindex) -> getDefaultEnchantColor(), MAGICAL_LEAF, MAGICAL_LEAVES.asItem());
	}
}
