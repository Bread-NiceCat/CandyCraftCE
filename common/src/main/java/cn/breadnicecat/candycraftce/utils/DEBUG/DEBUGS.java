package cn.breadnicecat.candycraftce.utils.DEBUG;

import cn.breadnicecat.candycraftce.utils.CLogUtils;
import groovy.lang.Binding;
import groovy.util.GroovyScriptEngine;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.Map;

import static cn.breadnicecat.candycraftce.CandyCraftCE.IS_DEV;

/**
 * Created in 2023/7/29 15:23
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 * <p>
 * ANY properties in this class SHOULD NOT be used when running release!
 * It's for DEBUG
 */
@Deprecated(forRemoval = true)
public class DEBUGS {
	public static final File PROJECT_ROOT = new File("").getAbsoluteFile().getParentFile();
	public static final File TEST_CLASSES = new File(PROJECT_ROOT, "build/classes/java/test");
	public static final File TEST_SRC_CODE = new File(PROJECT_ROOT, "src/test/java");

	static {
		CLogUtils.getModLogger().warn("=".repeat(40));
		CLogUtils.getModLogger().warn("DEBUGS is using now!");
		CLogUtils.getModLogger().warn("=".repeat(40));
		if (!IS_DEV) throw new IllegalStateException("Not in DEV");
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
