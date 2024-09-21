package cn.breadnicecat.candycraftce.utils;

import cn.breadnicecat.candycraftce.CandyCraftCE;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

import static cn.breadnicecat.candycraftce.utils.CommonUtils.getCaller;

/**
 * Created in 2023/8/22 21:58
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 */
public class CLogUtils {
	private static final HashMap<String, Logger> CACHE = new HashMap<>();
	private static final HashMap<Class<?>, Logger> CACHE_BY_CLASS = new HashMap<>();
	private static final HashMap<Class<?>, Logger> SIGN_VALIDATOR = new HashMap<>();
	
	public static Logger getModLogger() {
		return getModLogger(getCaller());
	}
	
	public static Logger getModLogger(Class<?> clazz) {
		return CACHE_BY_CLASS.computeIfAbsent(clazz, (k) -> getModLogger(k.getSimpleName()));
	}
	
	
	public static Logger getModLogger(String name) {
		String id = CandyCraftCE.MOD_NAME + "/" + name;
		return CACHE.computeIfAbsent(id, LoggerFactory::getLogger);
	}
	
	/**
	 * Should invoke in all {@code init()}
	 */
	@NotNull
	public static Logger sign() {
		Class<?> caller = getCaller();
		boolean dup = SIGN_VALIDATOR.containsKey(caller);
		Logger l = getModLogger(caller);
		l.info("{} signed", caller.getName());
		if (dup) {
			l.warn("Duplicate sign for {}", caller.getName());
		} else {
			SIGN_VALIDATOR.put(caller, l);
		}
		return l;
	}
	
}
