package cn.breadnicecat.candycraftce.utils;

import net.minecraft.world.entity.player.Inventory;

public class InventoryUtils {
	public static boolean isArmorSlot(int slot) {
		return slot >= Inventory.INVENTORY_SIZE && slot < Inventory.INVENTORY_SIZE + 4;
	}
}