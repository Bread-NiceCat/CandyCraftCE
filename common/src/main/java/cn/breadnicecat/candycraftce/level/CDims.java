package cn.breadnicecat.candycraftce.level;

import cn.breadnicecat.candycraftce.utils.ResourceUtils;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

/**
 * Created in 2023/12/31 10:06
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 */
public class CDims {
	public static final int LAND_HEIGHT = 384, LAND_MIN_Y = -64, LAND_MAX_Y = LAND_MIN_Y + LAND_HEIGHT;
	public static final int THE_DUNGEON_HEIGHT = 256, THE_DUNGEON_MIN_Y = 0, THE_DUNGEON_MAX_Y = THE_DUNGEON_MIN_Y + THE_DUNGEON_HEIGHT;
	//	public static final ResourceKey<Level> CANDYLAND = ResourceKey.create(Registries.DIMENSION, ResourceUtils.prefix("candyland"));
	public static final ResourceKey<Level> CANDYLAND = Level.END;
	public static final ResourceKey<Level> THE_DUNGEON = ResourceKey.create(Registries.DIMENSION, ResourceUtils.prefix("the_dungeon"));

	public static void init() {
	}
}
