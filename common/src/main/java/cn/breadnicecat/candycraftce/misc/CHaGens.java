package cn.breadnicecat.candycraftce.misc;

import java.util.Calendar;
import java.util.Date;

/**
 * Created in 2024/8/2 上午11:48
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 * 哈
 * 原神，启动！
 * <p>
 **/
public class CHaGens {
	public static int month, date;
	
	static {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		month = calendar.get(Calendar.MONTH) + 1;
		date = calendar.get(Calendar.DATE);
	}
	
	public static boolean xiao = isToday(4, 17);
	public static boolean diluc = isToday(4, 30);
	public static boolean paimon$itto = isToday(6, 1);
	public static boolean furina = isToday(10, 13);
	public static boolean nahida = isToday(10, 27);
	public static boolean kazuha = isToday(10, 29);
	public static boolean bread = isToday(11, 18);
	public static boolean tighnari = isToday(12, 29);
	//TODO SP Items
	public static boolean shouldLaunch = xiao | diluc | paimon$itto | furina | nahida | kazuha | tighnari | bread;
	
	public static boolean isToday(int month, int date) {
		return CHaGens.month == month && CHaGens.date == date;
	}
	
	public static void init() {
	}
}
