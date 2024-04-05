package cn.breadnicecat.candycraftce.item.items;

import cn.breadnicecat.candycraftce.block.CFluids;
import cn.breadnicecat.candycraftce.misc.muitlblocks.VectorPortalShape;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import org.jetbrains.annotations.Nullable;

import static cn.breadnicecat.candycraftce.block.blocks.CaramelPortalBlock.CONFIG;
import static cn.breadnicecat.candycraftce.block.blocks.CaramelPortalBlock.PLACER;

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
		VectorPortalShape.findPortal(level, pos, CONFIG).ifPresent(t -> t.build(level, PLACER));
	}
}
