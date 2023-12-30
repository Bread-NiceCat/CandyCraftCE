package cn.breadnicecat.candycraftce.utils;

import cn.breadnicecat.candycraftce.CandyCraftCE;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import java.util.HashMap;

import static cn.breadnicecat.candycraftce.utils.CommonUtils.getCaller;

/**
 * Created in 2023/8/22 21:58
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 */
public class CLogUtils {
	private static final HashMap<String, Logger> CACHE = new HashMap<>();
	private static final HashMap<Class<?>, Logger> VALIDATOR = new HashMap<>();
	private static final Marker STACK_TRACE_MARKER = MarkerFactory.getMarker("StackTrace");

	public static Logger getModLogger() {
		return getModLogger(getCaller());
	}

	public static Logger getModLogger(Class<?> clazz) {
		return VALIDATOR.computeIfAbsent(clazz, (k) -> getModLogger(k.getSimpleName()));
	}


	public static Logger getModLogger(String name) {
		String id = CandyCraftCE.MOD_NAME + "/" + name;
		return CACHE.computeIfAbsent(id, LoggerFactory::getLogger);
	}


	public static Logger sign() {
		Class<?> caller = getCaller();
		boolean dup = VALIDATOR.containsKey(caller);
		Logger l = getModLogger(caller);
		l.info("{} signed", caller.getName());
		if (dup) l.warn("Duplicate sign for {}", caller.getName());
		return l;
	}

	public static void printStackTrace() {
		Logger logger = getModLogger(getCaller());
		Thread thread = Thread.currentThread();
		logger.info(STACK_TRACE_MARKER, thread.toString());
		StackTraceElement[] stackTrace = thread.getStackTrace();
		for (int i = 2; i < stackTrace.length; i++) {
			//0:at java.lang.Thread.getStackTrace(Thread.java:1610)
			//1:at cn.breadnicecat.candycraftce.utils.CLogUtils.printStackTrace(CLogUtils.java:54)
			StackTraceElement traceElement = stackTrace[i];
			logger.info(STACK_TRACE_MARKER, "\tat " + traceElement);
		}
	}

}
