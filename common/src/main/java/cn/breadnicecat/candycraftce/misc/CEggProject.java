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
	
	public static boolean shouldLaunch = DEV;
	
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
