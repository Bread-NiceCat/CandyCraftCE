package cn.breadnicecat.candycraftce.level;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

import static cn.breadnicecat.candycraftce.utils.ResourceUtils.prefix;

/**
 * Created in 2023/12/31 10:06
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 */
public class CDims {
	public static final int LAND_HEIGHT = 384, LAND_MIN_Y = -64, LAND_MAX_Y = LAND_MIN_Y + LAND_HEIGHT;
	public static final int DUNGEONS_HEIGHT = 256, DUNGEONS_MIN_Y = 0, DUNGEONS_MAX_Y = DUNGEONS_MIN_Y + DUNGEONS_HEIGHT;
	
	public static final ResourceLocation CANDYCRAFT_LOCATION = prefix("candyland");
	public static final ResourceLocation DUNGEONS_LOCATION = prefix("dungeons");
	
	public static final ResourceKey<Level> CANDYLAND = ResourceKey.create(Registries.DIMENSION, CANDYCRAFT_LOCATION);
	public static final ResourceKey<Level> DUNGEONS = ResourceKey.create(Registries.DIMENSION, DUNGEONS_LOCATION);
	
}
