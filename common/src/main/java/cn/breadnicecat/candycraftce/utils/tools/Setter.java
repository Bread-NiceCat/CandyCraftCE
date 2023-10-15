package cn.breadnicecat.candycraftce.utils.tools;

import java.util.function.Consumer;

/**
 * @author <a href="https://gitee.com/Bread_NiceCat">Bread_NiceCat</a>
 * @date 2023/1/22 13:15
 */
public interface Setter<T> extends Consumer<T> {
	
	/**
	 * @deprecated use set(t) instead
	 */
	@Deprecated
	@Override
	default void accept(T t) {
		set(t);
	}
	
	void set(T t);
}
