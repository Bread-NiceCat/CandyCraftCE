package cn.breadnicecat.minecraft.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
	public static final String STRING_REGEX = "((?<=\")\\w+(?=\"))";


	public static void reqNull(Object o, String name) {
		if (o != null) throw new IllegalArgumentException(name);
	}

	public static List<String> substringByRegex(String s, String regex) {
		ArrayList<String> list = new ArrayList<>();
		Matcher matcher = Pattern.compile(regex).matcher(s);
		while (matcher.find()) {
			list.add(matcher.group());
		}
		return list.isEmpty() ? null : list;
	}

	public static List<String> substringByRegex(String s, String regex, int limit) {
		ArrayList<String> list = new ArrayList<>();
		Matcher matcher = Pattern.compile(regex).matcher(s);
		while (list.size() <= limit && matcher.find()) {
			list.add(matcher.group());
		}
		return list.isEmpty() ? null : list;
	}

	public static String ifEndsWithOrAppend(String ori, String suffix) {
		return ori.endsWith(suffix) ? ori : ori + suffix;
	}

	public static void assertTrue(boolean bool) {
		assertTrue(bool, "false");
	}

	public static void assertTrue(boolean bool, String msg) {
		if (!bool) throw new IllegalStateException(msg);
	}

	public static void assertTrue(boolean bool, Supplier<String> msg) {
		if (!bool) throw new IllegalStateException(msg.get());
	}
}
