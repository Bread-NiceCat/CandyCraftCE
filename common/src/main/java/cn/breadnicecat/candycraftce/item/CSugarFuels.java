package cn.breadnicecat.candycraftce.item;

import cn.breadnicecat.candycraftce.utils.TickUtils;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;

import static cn.breadnicecat.candycraftce.item.CDataComponents.SUGAR_BURN_TIME;

public class CSugarFuels {
	
	public static boolean isFuel(@NotNull ItemStack stack) {
		return stack.getItem() == Items.SUGAR || stack.has(SUGAR_BURN_TIME.get());
	}
	
	public static int getBurnDuration(@NotNull ItemStack stack) {
		if (stack.isEmpty()) return 0;
		if (stack.getItem() == Items.SUGAR) return TickUtils.TICK_PER_SEC;
		var v = stack.get(SUGAR_BURN_TIME.get());
		return v != null ? v : 0;
	}
}
