package cn.breadnicecat.minecraft.utils;

public record Quadruple<A, B, C, D>(A first, B second,
                                    C third, D fourth) {
	public static <A, B, C, D> Quadruple<A, B, C, D> of(A first, B second, C third, D fourth) {
		return new Quadruple<>(first, second, third, fourth);
	}
}
