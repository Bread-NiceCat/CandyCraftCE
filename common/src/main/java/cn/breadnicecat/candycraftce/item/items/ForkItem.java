package cn.breadnicecat.candycraftce.item.items;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import static cn.breadnicecat.candycraftce.block.CBlocks.*;

/**
 * Created in 2024/5/18 下午9:35
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 */
public class ForkItem extends Item {
	
	
	public ForkItem(Properties properties) {
		super(properties);
	}
	
	@Override
	public @NotNull InteractionResult useOn(UseOnContext context) {
		Level level = context.getLevel();
		BlockPos pos = context.getClickedPos();
		BlockState state = level.getBlockState(pos);
		//耕地
		if (state.is(PUDDING.get()) || state.is(CUSTARD_PUDDING.get())) {
			if (level.setBlockAndUpdate(pos, PUDDING_FARMLAND.defaultBlockState())) {
				return InteractionResult.sidedSuccess(level.isClientSide);
			}
		}
		return InteractionResult.PASS;
	}
}
