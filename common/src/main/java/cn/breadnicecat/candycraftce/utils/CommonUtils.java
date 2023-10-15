package cn.breadnicecat.candycraftce.utils;

import org.apache.logging.log4j.util.StackLocatorUtil;

import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author <a href="https://gitee.com/Bread_NiceCat">Bread_NiceCat</a>
 * @date 2022/12/30 15:32
 * 非mc特有
 */
public class CommonUtils {

	/**
	 * 填充指定长度字符
	 */
	public static String fill(String raw, int len, char fill) {
		StringBuilder builder = new StringBuilder(raw);
		builder.append(String.valueOf(fill).repeat(Math.max(0, len)));
		return builder.toString();
	}

	/**
	 * 填充字符直到达到指定长度
	 */
	public static String fillUntil(String raw, int toLength, char fill) {
		if (raw.length() > toLength) {
			throw new IllegalArgumentException("raw.length()>toLength");
		} else if (raw.length() == toLength) {
			return raw;
		} else {
			return fill(raw, toLength - raw.length(), fill);
		}

	}


	public static final Random RANDOM = new Random();

	/**
	 * @param denominator P=1/denominator
	 */
	public static boolean probability(Random random, int denominator) {
		return random.nextInt(denominator + 1) == 0;
	}

	/**
	 * @param denominator P=1/denominator
	 */
	public static boolean probability(int denominator) {
		return probability(RANDOM, denominator);
	}

	/**
	 * 不想分行写可以用这个
	 * 可以用这个进行debug
	 * 比如{@code CommonUtils.visit(this.getClass().getDeclaredField("SHAPE"),(s)->s.setAccessible(true),(s)->{try{s.set(this,Shapes.create(0d, 0d, 0d, 1d, 0.995d, 1d));}catch(Throwable e){e.printStackTrace();}})}
	 */
	@SafeVarargs
	public static <T> T visit(T t, Consumer<T>... visitors) {
		for (Consumer<T> visitor : visitors) {
			visitor.accept(t);
		}
		return t;
	}


	public static Class<?> getCaller() {
		return getCaller(4);//因为要再调用getCaller(int)所以要+1
	}

	/**
	 * @param depth 从深度1(此方法)开始,2调用这个方法的方法,3调用调用这个方法的方法...
	 */
	public static Class<?> getCaller(int depth) {
		return StackLocatorUtil.getCallerClass(depth);
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
