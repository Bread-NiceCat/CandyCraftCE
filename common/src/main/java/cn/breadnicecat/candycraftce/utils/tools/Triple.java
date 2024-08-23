package cn.breadnicecat.candycraftce.utils.tools;

/**
 * Created in 2024/8/21 下午7:17
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 *
 * <p>
 **/
public record Triple<A, B, C>(A a, B b, C c) {
	public static <A, B, C> Triple<A, B, C> of(A a, B b, C c) {
		return new Triple<>(a, b, c);
	}
}
