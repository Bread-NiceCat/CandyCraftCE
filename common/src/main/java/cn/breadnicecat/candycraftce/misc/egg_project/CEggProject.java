package cn.breadnicecat.candycraftce.misc.egg_project;

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
	
	
	static {
	}
	
	public static void init() {
	}
}
