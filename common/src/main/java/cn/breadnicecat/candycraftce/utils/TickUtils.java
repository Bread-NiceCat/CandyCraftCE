package cn.breadnicecat.candycraftce.utils;

import net.minecraft.server.MinecraftServer;

/**
 * Created in 2023/4/15 22:04
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 */

public class TickUtils {
	public static final int MS_PER_TICK = MinecraftServer.MS_PER_TICK;
	public static final int TICK_PER_SEC = 1000 / MS_PER_TICK;
	public static final int SEC2TICK = TICK_PER_SEC;
	public static final float TICK2SEC = 1f / TICK_PER_SEC;
}
