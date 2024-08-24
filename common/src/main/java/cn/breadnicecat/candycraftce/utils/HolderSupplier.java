package cn.breadnicecat.candycraftce.utils;

import net.minecraft.core.Holder;

/**
 * Created in 2024/8/23 13:44
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 *
 * <p>
 **/
public interface HolderSupplier<T> {
	Holder<T> getHolder();
}
