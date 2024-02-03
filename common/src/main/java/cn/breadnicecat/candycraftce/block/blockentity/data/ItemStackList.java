package cn.breadnicecat.candycraftce.block.blockentity.data;

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

	@Override
	public ItemStack remove(int i) {
		return set(i, EMPTY);
	}
}
