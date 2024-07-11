package cn.breadnicecat.candycraftce.mixin;

import net.minecraft.world.item.AxeItem;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

/**
 * Created in 2024/7/11 上午11:35
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 *
 * <p>
 **/
@Mixin(AxeItem.class)
public interface AxeItemAccessor {
	@Accessor
	static Map<Block, Block> getSTRIPPABLES() {
		throw new UnsupportedOperationException();
	}
	
	@Mutable
	@Accessor
	static void setSTRIPPABLES(Map<Block, Block> STRIPPABLES) {
		throw new UnsupportedOperationException();
	}
}
