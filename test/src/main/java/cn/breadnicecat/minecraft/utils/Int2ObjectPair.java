package cn.breadnicecat.minecraft.utils;

/**
 * Created in 2023/8/23 23:32
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 */
public record Int2ObjectPair<T>(int first, T second) {
	public static <T> Int2ObjectPair<T> of(int first, T second) {
		return new Int2ObjectPair<>(first, second);
	}
}
