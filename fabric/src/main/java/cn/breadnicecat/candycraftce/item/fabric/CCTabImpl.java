package cn.breadnicecat.candycraftce.item.fabric;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.world.item.CreativeModeTab;

/**
 * Created in 2024/4/21 上午1:44
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 * <p>
 */
public class CCTabImpl {
	public static CreativeModeTab.Builder builder() {
		return FabricItemGroup.builder();
	}
}
