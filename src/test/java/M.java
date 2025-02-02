/**
 * Created in 2025/2/2 21:58
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 *
 * <p>
 **/
public class M {
	
	
	public static Class<?> getCaller() {
		return getCaller(1);
	}
	
	/**
	 * @param depth 0为调用该方法的方法
	 */
	public static Class<?> getCaller(int depth) {
		return StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE)
				.walk(c -> c
						.peek(System.out::println)
						.skip(depth + 1)
						.findFirst())
				.orElseThrow()
				.getDeclaringClass();
	}
}
