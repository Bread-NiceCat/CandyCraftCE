package cn.breadnicecat.candycraftce.utils.tools;

/**
 * @author <a href="https://gitee.com/Bread_NiceCat">Bread_NiceCat</a>
 * @date 2023/1/27 13:15
 */
public record Triple<F, S, T>(F first, S second, T third) {

	public static <F, S, T> Triple<F, S, T> of(F first, S second, T third) {
		return new Triple<>(first, second, third);
	}

	@Override
	public String toString() {
		return "Triple[" +
				"first=" + first + ", " +
				"second=" + second + ", " +
				"third=" + third + ']';
	}

}