package cn.breadnicecat.candycraftce.item.items;

import cn.breadnicecat.candycraftce.block.CFluids;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import org.jetbrains.annotations.Nullable;

/**
 * Created in 2024/4/5 下午10:41
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 * <p>
 */
public class CaramelBucketItem extends BucketItem {
	protected CaramelBucketItem(Fluid content, Properties properties) {
		super(content, properties);
	}
	
	public CaramelBucketItem(Properties properties) {
		this(CFluids.CARAMEL.get(), properties);
	}
	
	@Override
	public void checkExtraContent(@Nullable Player player, Level level, ItemStack containerStack, BlockPos pos) {
//		VectorPortalShape.findPortal(level, pos, CONFIG).ifPresent(t -> {
//			for (BlockPos frame : t.getAllFrames()) {
//				BlockState state = level.getBlockState(frame);
//				if (state.is(CBlocks.SUGAR_BLOCK.get())) {
//					level.setBlockAndUpdate(frame, CBlocks.CARAMEL_BLOCK.defaultBlockState());
//				}
//			}
//			t.build(level, PLACER);
//		});
	}
}
