package cn.breadnicecat.candycraftce.utils;

import net.minecraft.core.NonNullList;
import net.minecraft.tags.TagKey;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.Arrays;

import static cn.breadnicecat.candycraftce.utils.CommonUtils.make;
import static java.lang.Math.min;
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
	
	public ItemStack extract(int index, int expectCount) {
		return ContainerHelper.removeItem(this, index, expectCount);
	}
	
	/**
	 * 注意：会额外对参数{@code item}直接修改
	 *
	 * @return 未被插入的部分
	 */
	public ItemStack insert(int index, ItemStack item) {
		ItemStack cur = get(index);
		if (item.isEmpty()) {//不能插空
			return item;
		}
		if (cur.isEmpty()) {//若空则直接放入
			set(index, item.copy());
			item.setCount(0);
			return item;
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
		}
		return item;
	}
	
	/**
	 * 插入，但是不对列表中的数据进行修改
	 *
	 * @return 未被插入的部分
	 */
	public ItemStack simulateInsert(int index, ItemStack item) {
		ItemStack cur = get(index);
		if (item.isEmpty()) {//不能插空
			return item;
		}
		if (cur.isEmpty()) {//若空则直接放入
			item.setCount(0);
			return item;
		}
		int left = cur.getMaxStackSize() - cur.getCount();//能插的最大数量
		if (left != 0 && ItemStack.isSameItemSameComponents(cur, item)) {
			int itemCnt = item.getCount();
			//剩余 >= 外来
			item.shrink(min(left, itemCnt));//0
		}
		return item;
	}
	
	@Override
	public ItemStack remove(int index) {
		return set(index, EMPTY);
	}
	
	/**
	 * 尝试把from中{@code min(count,expectedCount)}转移到to中
	 * @return 转移的数量
	 */
	public int transfer(int from,int to,int expectedCount) {
		ItemStack ori = get(from);
		ItemStack dest = get(to);
		if(dest.isEmpty()){
			ItemStack split = ori.split(expectedCount);
			set(to, split);
			return split.getCount();
		}else if(dest.is(ori.getItem())){
			int cnt=min(expectedCount,min(ori.getCount(),dest.getMaxStackSize()-dest.getCount()));
			ori.shrink(cnt);
			dest.grow(cnt);
			return cnt;
		}
		return 0;
	}
	
	/**
	 * 交换
	 */
	public void shift(int from,int to) {
		ItemStack fr = get(from);
		ItemStack dest = get(to);
		set(from,dest);
		set(to,fr);
	}
	
	public boolean is(int index, Item item) {
		return get(index).is(item);
	}
	public boolean is(int index, TagKey<Item> tag) {
		return get(index).is(tag);
	}
}
