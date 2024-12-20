package cn.breadnicecat.candycraftce.utils;

import net.minecraft.core.NonNullList;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.ItemStack;

import java.util.Arrays;

import static cn.breadnicecat.candycraftce.utils.CommonUtils.make;
import static net.minecraft.world.item.ItemStack.EMPTY;

/**
 * Created in 2024/2/3
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-NiceCat">Bread_NiceCat</a>
 * <p>
 */
public class ItemStackList extends NonNullList<ItemStack> {
	
	public ItemStackList(int size) {
		super(make(() -> {
			ItemStack[] stacks = new ItemStack[size];
			Arrays.fill(stacks, EMPTY);
			return Arrays.asList(stacks);
		}), EMPTY);
	}
	
	@Override
	public boolean isEmpty() {
		for (ItemStack stack : this) {
			if (!stack.isEmpty()) return false;
		}
		return true;
	}
	
	public boolean isEmpty(int index) {
		return get(index).isEmpty();
	}
	
	public ItemStack extract(int index, int amount) {
		return ContainerHelper.removeItem(this, index, amount);
	}
	
	/**
	 * @return false->未插入 true->插入部分或全部
	 */
	public boolean insert(int index, ItemStack item) {
		ItemStack cur = get(index);
		if (item.isEmpty()) {//不能插空
			return false;
		}
		if (cur.isEmpty()) {//若空则直接放入
			set(index, item.copy());
			item.setCount(0);
			return true;
		}
		int left = cur.getMaxStackSize() - cur.getCount();//能插的最大数量
		if (left != 0 && ItemStack.isSameItemSameComponents(cur, item)) {
			int itemCnt = item.getCount();
			if (left >= itemCnt) {//剩余 >= 外来
				cur.grow(itemCnt);
				item.shrink(itemCnt);//0
			} else {
				cur.grow(left);//max
				item.shrink(left);
			}
			return true;
		}
		return false;
	}
	
	@Override
	public ItemStack remove(int i) {
		return set(i, EMPTY);
	}
}
