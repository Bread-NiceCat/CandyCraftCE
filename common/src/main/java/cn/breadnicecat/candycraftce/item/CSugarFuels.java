package cn.breadnicecat.candycraftce.item;

import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import static cn.breadnicecat.candycraftce.item.CDataComponents.SUGAR_BURN_TIME;

public class CSugarFuels {
	
	public static boolean isFuel(@NotNull ItemStack stack) {
		return stack.has(SUGAR_BURN_TIME.get());
	}
	
	public static int getBurnDuration(@NotNull ItemStack stack) {
		if (stack.isEmpty()) return 0;
		Integer i = stack.get(SUGAR_BURN_TIME.get());
		return i != null ? i : 0;
	}
}
