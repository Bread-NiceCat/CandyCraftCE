package cn.breadnicecat.candycraftce.item;

import cn.breadnicecat.candycraftce.utils.SimpleEntry;
import cn.breadnicecat.candycraftce.utils.WrappedEntry;
import net.minecraft.core.component.DataComponentType;

/**
 * Created in 2024/9/22 04:42
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 *
 * <p>
 **/
public class DataComponentEntry<T> extends SimpleEntry<DataComponentType<?>, DataComponentType<T>> {
	public DataComponentEntry(WrappedEntry<DataComponentType<?>, DataComponentType<T>> wrapper) {
		super(wrapper);
	}
}
