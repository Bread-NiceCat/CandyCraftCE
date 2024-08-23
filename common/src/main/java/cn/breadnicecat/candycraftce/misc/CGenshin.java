package cn.breadnicecat.candycraftce.misc;

import cn.breadnicecat.candycraftce.item.CItemBuilder;
import cn.breadnicecat.candycraftce.item.items.GenshinLauncherItem;

import java.util.Calendar;
import java.util.Date;

import static cn.breadnicecat.candycraftce.CandyCraftCE.DEV;

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
public class CGenshin {
	public static int month, date;
	
	static {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		month = calendar.get(Calendar.MONTH) + 1;
		date = calendar.get(Calendar.DATE);
	}
	
	public static boolean xiao = isToday(4, 17);
	public static boolean diluc = isToday(4, 30);
	public static boolean paimon_itto = isToday(6, 1);
	public static boolean furina = isToday(10, 13);
	public static boolean nahida = isToday(10, 27);
	public static boolean kazuha = isToday(10, 29);
	public static boolean bread = isToday(11, 18);
	public static boolean tighnari = isToday(12, 29);
	public static boolean shouldLaunch = DEV | xiao | diluc | paimon_itto | furina | nahida | kazuha | tighnari | bread;
	
	public static boolean isToday(int month, int date) {
		return CGenshin.month == month && CGenshin.date == date;
	}
	
	static {
		if (shouldLaunch) {
			CItemBuilder.create("genshin_launcher", GenshinLauncherItem::new).setCtab(false).save();
		}
	}
	
	public static void init() {
	}
}
