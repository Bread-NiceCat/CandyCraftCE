package cn.breadnicecat.candycraftce.utils;

/**
 * Created in 2023/4/15 22:04
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 */

public class TickUtils {
	public static final int TICK_PER_SEC = 20;
	public static final int MS_PER_TICK = 1000 / TICK_PER_SEC;
	
	public static final float SEC2TICK = TICK_PER_SEC;
	public static final float TICK2SEC = 1f / SEC2TICK;
	
	public static final float MS2TICK = 1f / MS_PER_TICK;
	public static final float TICK2MS = MS_PER_TICK;
}
