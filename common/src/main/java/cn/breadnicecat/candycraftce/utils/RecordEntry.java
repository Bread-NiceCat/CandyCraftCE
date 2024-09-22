package cn.breadnicecat.candycraftce.utils;

/**
 * Created in 2024/9/22 04:55
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 *
 * <p>
 **/
public class RecordEntry<T> extends SimpleEntry<T, T> {
	public RecordEntry(WrappedEntry<T, T> wrapper) {
		super(wrapper);
	}
}
