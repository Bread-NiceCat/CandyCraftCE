package cn.breadnicecat.candycraftce.mixin.core;

import net.minecraft.world.item.AxeItem;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

/**
 * Created by NiceCat on 2026/1/10.
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 *
 */
@Mixin(AxeItem.class)
public interface AccessorAxeItem {
	@Accessor
	static Map<Block, Block> getSTRIPPABLES() {
		throw new AssertionError();
	}

	@Accessor
	static void setSTRIPPABLES(Map<Block, Block> map) {
		throw new AssertionError();
	}
}
