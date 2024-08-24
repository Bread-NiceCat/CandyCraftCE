package cn.breadnicecat.candycraftce.utils;

import org.apache.logging.log4j.util.StackLocatorUtil;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Supplier;

/**
 * @author <a href="https://gitee.com/Bread_NiceCat">Bread_NiceCat</a>
 * @date 2022/12/30 15:32
 * 非mc特有
 */
public class CommonUtils {
	
	
	public static Class<?> getCaller() {
		return StackLocatorUtil.getCallerClass(3);//因为要再调用getCaller(int)所以要+1
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
	
	public static <T> ArrayList<T> newList(int size, IntFunction<T> constructor) {
		ArrayList<T> list = new ArrayList<>(size);
		for (int i = 0; i < size; i++) {
			list.add(constructor.apply(i));
		}
		return list;
	}
	
	/**
	 * 让visitor依次拜访每个house
	 */
	@SafeVarargs
	public static <I> I apply(I visitor, Consumer<I>... houses) {
		for (Consumer<I> h : houses) {
			h.accept(visitor);
		}
		return visitor;
	}
	
	/**
	 * 依次让所有guest拜访house
	 */
	@SafeVarargs
	public static <I> void accept(Consumer<I> house, I... guests) {
		for (I guest : guests) {
			house.accept(guest);
		}
	}
	
	
	public static <T> T make(@NotNull Supplier<T> factory) {
		return factory.get();
	}
	
	public static <T> T make(T ori, @NotNull Consumer<T> factory) {
		factory.accept(ori);
		return ori;
	}
	
	/**
	 * 线性插值
	 *
	 * @param p  插值点坐标
	 * @param p1 顶点坐标1
	 * @param p2 顶点坐标2
	 * @param v1 顶点数值1
	 * @param v2 顶点数值2
	 * @return 插值后的数值
	 */
	public static float linearInterpolation(float p, float p1, float p2, float v1, float v2) {
		if (v1 == v2 || p1 == p2) {
			//value值相等 距离为0 不进行插值计算
			return v1;
		} else {
			return ((p2 - p) / (p2 - p1) * v1) + ((p - p1) / (p2 - p1) * v2);
		}
	}
	
	public static int[] listInt2int(List<Integer> listInt) {
		int[] res = new int[listInt.size()];
		int i = 0;
		for (int num : listInt) {
			res[i++] = num;
		}
		return res;
		
	}
	
	@Contract()//把->fail顶掉
	public static <T> T impossibleCode() {
		throw new AssertionError("Impossible code invoked. It's a bug, please report it to us");
	}
	
	public static <T> T orElse(T value, T defaultValue) {
		return value == null ? defaultValue : value;
	}
	
	public static <T> T orElse(T value, Supplier<T> defaultValue) {
		return value == null ? defaultValue.get() : value;
	}
}
