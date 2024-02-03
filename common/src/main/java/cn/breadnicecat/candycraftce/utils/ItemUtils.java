package cn.breadnicecat.candycraftce.utils;

import com.google.gson.JsonElement;
import com.mojang.serialization.JsonOps;
import net.minecraft.world.item.ItemStack;

/**
 * @author <a href="https://gitee.com/Bread_NiceCat">Bread_NiceCat</a>
 * @date 2023/1/22 20:02
 */
public class ItemUtils {
	public static JsonElement toJson(ItemStack stack) {
		return ItemStack.CODEC.encodeStart(JsonOps.INSTANCE, stack).get().orThrow();
	}

	public static ItemStack fromJson(JsonElement element) {
		return ItemStack.CODEC.decode(JsonOps.INSTANCE, element).get().orThrow().getFirst();
	}

}
