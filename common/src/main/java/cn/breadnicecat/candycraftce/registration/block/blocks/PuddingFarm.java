package cn.breadnicecat.candycraftce.registration.block.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.Nullable;

/**
 * Created in 2024/1/28 16:01
 * Project: candycraftce
 * <p>
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 * @see FarmBlock [Vanilla Copy]
 * @see cn.breadnicecat.candycraftce.mixin.MixinFarmBlock [Mixin]
 */
public class PuddingFarm extends FarmBlock {

	public static final Block DIRT_LIKE = CustardPudding.DIRT_LIKE;

	public PuddingFarm(Properties properties) {
		super(properties);
	}

	public static void turnToDirt(@Nullable Entity entity, BlockState state, Level level, BlockPos pos) {
		BlockState blockState = FarmBlock.pushEntitiesUp(state, DIRT_LIKE.defaultBlockState(), level, pos);
		level.setBlockAndUpdate(pos, blockState);
		level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(entity, blockState));
	}

	public static boolean shouldMaintainFarmland(BlockGetter level, BlockPos pos) {
//		TODO #MAINTAINS_FARMLAND，或许直接检测是否有植物就行了
		return level.getBlockState(pos.above()).is(BlockTags.MAINTAINS_FARMLAND);
	}
}
