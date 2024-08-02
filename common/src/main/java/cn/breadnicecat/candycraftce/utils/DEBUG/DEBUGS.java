package cn.breadnicecat.candycraftce.utils.DEBUG;

import cn.breadnicecat.candycraftce.utils.CLogUtils;
import groovy.lang.Binding;
import groovy.util.GroovyScriptEngine;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static cn.breadnicecat.candycraftce.CandyCraftCE.DEV;

/**
 * Created in 2023/7/29 15:23
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 * ANY memeber in this class SHOULD NOT be used when running release!
 * It's for DEBUG only
 * <p>
 * <p>
 * Warning: an exception will be thrown when invoke any memeber of this class
 * if you're not in a development environment
 */
public class DEBUGS {
	public static final File PROJECT_ROOT = new File("").getAbsoluteFile().getParentFile();
	public static final File TEST_CLASSES = new File(PROJECT_ROOT, "build/classes/java/test");
	public static final File TEST_SRC_CODE = new File(PROJECT_ROOT, "src/test/java");
	/**
	 * 给idea的调试器eval存储数据的地方
	 */
	public static final HashMap<?, ?> EVAL = new HashMap<>();
	private static final Logger LOGGER = CLogUtils.sign();
	
	static {
		LOGGER.warn("=".repeat(40));
		LOGGER.warn("DEBUGS ON!");
		LOGGER.warn("=".repeat(40));
		if (!DEV) throw new IllegalStateException("Not in DEV");
	}

//	public static Class<?> loadClass(File directory, String className) {
//		try (URLClassLoader loader = new URLClassLoader(new URL[]{directory.toURI().toURL()})) {
//			return loader.loadClass(className);
//		} catch (IOException | ClassNotFoundException e) {
//			throw new RuntimeException(e);
//		}
//	}
	
	public static @Nullable Object runGroovyScript(File path, Map<String, Object> args) throws Exception {
		GroovyScriptEngine engine = new GroovyScriptEngine(path.getAbsoluteFile().getParent(), Thread.currentThread().getContextClassLoader());
		return engine.run(path.getName(), new Binding(args));
	}
}
