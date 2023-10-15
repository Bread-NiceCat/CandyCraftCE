package cn.breadnicecat.candycraftce.utils.tools;

/**
 * Created in 2023/7/2 13:57
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 */
@FunctionalInterface
public interface SuBiConsumer<A, B, C> {
	void accept(A a, B b, C c);

}
