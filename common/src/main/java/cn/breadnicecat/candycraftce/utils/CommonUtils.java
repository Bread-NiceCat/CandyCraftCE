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
		assertTrue(bool, "assert true");
	}

	public static void assertTrue(boolean bool, String msg) {
		assertTrue(bool, () -> msg);
	}

	public static void assertTrue(boolean bool, Supplier<String> msg) {
		if (!bool) throw new IllegalStateException(msg.get());
	}

	/**
	 * 让visitor依次拜访每个house
	 */
	@SafeVarargs
	public static <I> I visit(I visitor, Consumer<I>... houses) {
		for (Consumer<I> h : houses) {
			h.accept(visitor);
		}
		return visitor;
	}

	/**
	 * 依次让所有guest拜访house
	 */
	@SafeVarargs
	public static <I> void receive(Consumer<I> house, I... guests) {
		for (I guest : guests) {
			house.accept(guest);
		}
	}

	/**
	 * 如果讨厌的object是两个candidate中的一个,那么就返回另外一个;
	 * <p>
	 * 如果不在两个candidate中,就返回preference
	 * <p>
	 * NOTE: 这里的"是"指的是 全等于(==)
	 */
	public static <E> E hate(E object, E candidate1, E candidate2, E preference) {
		if (object == candidate1) {
			return candidate2;
		} else if (object == candidate2) {
			return candidate1;
		} else {
			return preference;
		}
	}

	/**
	 * 顺反异构
	 * <pre>
	 * i1,i2==cis,trans -> r_cis
	 * ii,i2==trans,cis -> r_trans
	 * else -> r_default
	 * </pre>
	 */
	public static <I, R> R cis_trans(I i1, I i2, I cis, I trans, Supplier<R> r_cis, Supplier<R> r_trans, Supplier<R> r_default) {
		if (i1 == cis && i2 == trans) {
			return r_cis == null ? null : r_cis.get();
		} else if (i1 == trans && i2 == cis) {
			return r_trans == null ? null : r_trans.get();
		} else return r_default == null ? null : r_default.get();
	}
}
