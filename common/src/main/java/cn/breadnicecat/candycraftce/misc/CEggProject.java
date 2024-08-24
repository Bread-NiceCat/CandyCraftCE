package cn.breadnicecat.candycraftce.misc;

import cn.breadnicecat.candycraftce.item.CItemBuilder;
import cn.breadnicecat.candycraftce.item.ItemEntry;
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
public class CEggProject {
	public static int month, date;
	
	static {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		month = calendar.get(Calendar.MONTH) + 1;
		date = calendar.get(Calendar.DATE);
	}
	
	public static boolean isXiao = isToday(4, 17);
	public static boolean isDiluc = isToday(4, 30);
	public static boolean isPaimon_itto = isToday(6, 1);
	public static boolean isFurina = isToday(10, 13);
	public static boolean isNahida = isToday(10, 27);
	public static boolean isKazuha = isToday(10, 29);
	public static boolean isBread = isToday(11, 18);
	public static boolean isTighnari = isToday(12, 29);
	public static boolean shouldLaunch = DEV | isXiao | isDiluc | isPaimon_itto | isFurina | isNahida | isKazuha | isTighnari | isBread;
	
	public static boolean isToday(int month, int date) {
		return CEggProject.month == month && CEggProject.date == date;
	}
	
	public static ItemEntry<GenshinLauncherItem> launcher;
	
	static {
		if (shouldLaunch) {
			launcher = CItemBuilder.create("launcher", GenshinLauncherItem::new).setCtab(false).save();
		}
	}
	
	public static void init() {
	}
}
