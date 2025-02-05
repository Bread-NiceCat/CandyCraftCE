package cn.breadnicecat.candycraftce.utils;

import net.minecraft.util.RandomSource;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Supplier;

/**
 * @author <a href="https://gitee.com/Bread_NiceCat">Bread_NiceCat</a>
 * @date 2022/12/30 15:32
 */
public class CommonUtils {
	
	
	public static Class<?> getCaller() {
		return getCaller(2);
	}
	
	/**
	 * @param depth 0为调用该方法的方法
	 */
	public static Class<?> getCaller(int depth) {
		return StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE)
				.walk(c -> c
						.skip(depth + 1)
						.findFirst())
				.orElseThrow()
				.getDeclaringClass();
	}
	
	public static void must(boolean bool) {
		must(bool, "false");
	}
	
	public static void must(boolean bool, String msg) {
		if (!bool) throw new IllegalStateException(msg);
	}
	
	public static void must(boolean bool, Supplier<String> msg) {
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
	 *
	 * @return visitor
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
	 *
	 * @return house
	 */
	@SafeVarargs
	public static <I> Consumer<I> accept(Consumer<I> house, I... guests) {
		for (I guest : guests) {
			house.accept(guest);
		}
		return house;
	}
	
	/**
	 * @return {@code factory.get()}
	 */
	public static <T> T make(@NotNull Supplier<T> factory) {
		return factory.get();
	}
	
	/**
	 * accept and return
	 *
	 * @return ori
	 */
	public static <T> T make(T ori, @NotNull Consumer<T> factory) {
		factory.accept(ori);
		return ori;
	}
	
	public static <T> T impossibleCode() {
		throw new AssertionError("Impossible code invoked. It's a bug, please report it to us");
	}
	
	public static <T> T orElse(T value, T defaultValue) {
		return value == null ? defaultValue : value;
	}
	
	public static <T> T orElse(T value, Supplier<T> defaultValue) {
		return value == null ? defaultValue.get() : value;
	}
	
	public static boolean probability(Random random, int denominator) {
		return random.nextInt(denominator) == 0;
	}
	
	public static boolean probability(RandomSource random, int denominator) {
		return random.nextInt(denominator) == 0;
	}
	
	public static void dumpAllStackTraces() {
		Thread.getAllStackTraces().forEach((t, trace) -> {
			System.out.printf("Thread: `%s` %s State: %s%n",
					t.getName(),
					t.isDaemon() ? "(Daemon)" : "",
					t.getState()
			);
			for (StackTraceElement element : t.getStackTrace()) {
				System.out.println("\tat " + element);
			}
		});
	}
	
}
