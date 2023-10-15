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
	private static final Marker STACK_TRACE_MARKER = MarkerFactory.getMarker("StackTrace");

	public static Logger getModLogger() {
		Class<?> caller = getCaller();
		return getModLogger(caller);
	}

	public static Logger getModLogger(Class<?> clazz) {
		return getModLogger(clazz.getSimpleName());
	}


	public static Logger getModLogger(String name) {
		String id = CandyCraftCE.MOD_NAME + "/" + name;
		return CACHE.computeIfAbsent(id, LoggerFactory::getLogger);
	}


	private static Logger getCallerModLogger() {
		Class<?> caller = getCaller();
		return getModLogger(caller);
	}

	/**
	 * 一般用于{@code <clinit>}中
	 */
	public static void sign() {
		getCallerModLogger().info("{} signed", getCaller().getName());
	}

	public static void printStackTrace() {
		Logger logger = getCallerModLogger();
		Thread thread = Thread.currentThread();
		logger.info(STACK_TRACE_MARKER, thread.toString());
		for (StackTraceElement traceElement : thread.getStackTrace()) {
			logger.info(STACK_TRACE_MARKER, "\tat " + traceElement);
		}
	}

//	/**
//	 * 一般用于init方法中
//	 */
//	public static void initialing() {
//		Class<?> caller = getCaller();
//		getModLogger(caller).info("Initialing {}...", caller.getName());
//	}
}
