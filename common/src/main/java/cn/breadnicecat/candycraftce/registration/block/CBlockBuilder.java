package cn.breadnicecat.candycraftce.registration.block;

import cn.breadnicecat.candycraftce.registration.item.CItemBuilder;
import cn.breadnicecat.candycraftce.registration.item.ItemEntry;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Created in 2023/11/26 9:41
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 * <p>
 */
public class CBlockBuilder extends CItemBuilder<BlockItem> {
	public CBlockBuilder(String name, ItemEntry<BlockItem> copyFrom) {
		super(name, copyFrom);
	}

	public CBlockBuilder(String name, CItemBuilder<BlockItem> that) {
		super(name, that);
	}

	public CBlockBuilder(String name, Function<Item.Properties, BlockItem> factory) {
		super(name, factory);
	}

	public CBlockBuilder(String name, Supplier<BlockItem> fac) {
		super(name, fac);
	}

	public CBlockBuilder(String name, BiFunction<Item.Properties, Object[], BlockItem> factory) {
		super(name, factory);
	}
}
