package cn.breadnicecat.candycraftce.misc;

import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;

import static cn.breadnicecat.candycraftce.item.CItems.PEZ;
import static cn.breadnicecat.candycraftce.utils.CommonUtils.apply;

public class CSugarFuels {
	public static final Object2IntOpenHashMap<Item> FUELS = apply(new Object2IntOpenHashMap<>(), (m) -> {
		m.put(PEZ.get(), 1000);
	});

	public static boolean isFuel(Item item) {
		return item != Items.AIR && FUELS.containsKey(item);
	}

	public static boolean isFuel(@NotNull ItemStack stack) {
		return !stack.isEmpty() && FUELS.containsKey(stack.getItem());
	}

	public static int getBurnDuration(Item item) {
		return item == Items.AIR ? 0 : FUELS.getInt(item);
	}

	public static int getBurnDuration(@NotNull ItemStack stack) {
		return stack.isEmpty() ? 0 : FUELS.getInt(stack.getItem());
	}
}
