package cn.breadnicecat.candycraftce.registration.block.blocks;

import cn.breadnicecat.candycraftce.level.CDims;
import cn.breadnicecat.candycraftce.misc.muitlblocks.NetherLikePortalShape;
import cn.breadnicecat.candycraftce.registration.block.CBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Optional;
import java.util.function.Predicate;

import static cn.breadnicecat.candycraftce.registration.block.CBlocks.*;
import static cn.breadnicecat.candycraftce.utils.LevelUtils.getNeighbourPos;

/**
 * Created in 2023/12/30 21:30
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 * <p>
 */
public class SugarBlock extends FallingBlock {

	private static final Predicate<BlockState> IS_FRAME = (b) -> b.is(CBlockTags.CARAMEL_PORTAL_FRAME) || b.is(TEST_BLOCK.getBlock());
	private static final Predicate<BlockState> IS_EMPTY = blockState -> blockState.isAir() || blockState.is(Blocks.LAVA) || blockState.is(CARAMEL_PORTAL.getBlock());

	public SugarBlock(Properties properties) {
		super(properties);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
		if (level.dimension() == Level.OVERWORLD) {
			for (BlockPos moved : getNeighbourPos(pos)) {
				if (level.getBlockState(moved).is(Blocks.LAVA)) {
					//变成焦糖块
					if (tryHeatSugar(state, level, pos)) {
						if (tryBuild(level, pos)) {
							level.playSound(null, pos, SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.BLOCKS);
						}
					}
					break;
				}
			}
		}
	}

	/**
	 * 掉落检测
	 */
	@Override
	public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
		//检测附近是否有非 可掉落方块
		for (BlockPos po : getNeighbourPos(pos)) {
			BlockState state1 = level.getBlockState(po);
			if (!(state1.getBlock() instanceof FallingBlock) && !isFree(state1)) return;
		}
		//掉落
		super.tick(state, level, pos, random);
	}

	/**
	 * @param pos frame-pos
	 */
	private boolean tryBuild(Level level, BlockPos pos) {
		if (level.dimension() == Level.OVERWORLD || level.dimension() == CDims.CANDYLAND) {
			//PortalShape.findEmptyPortalShape必须传入火的pos,而参数pos是当前方块的pos
			for (BlockPos po : getNeighbourPos(pos)) {
				BlockState ps = level.getBlockState(po);
				if (IS_EMPTY.test(ps)) {
					//寻找框架
					Optional<NetherLikePortalShape> opt = NetherLikePortalShape.findPortalShape(level, po, IS_FRAME, IS_EMPTY);
					if (opt.isPresent()) {
						NetherLikePortalShape shape = opt.get();
						shape.build(CARAMEL_PORTAL.getBlock().defaultBlockState());
						shape.forEachBlockOfFrame((l, p) -> tryHeatSugar(l.getBlockState(p), level, p));
						return true;
					}
				}
			}
		}
		return false;
	}

	private boolean tryHeatSugar(BlockState state, Level level, BlockPos pos) {
		if (state.getBlock() == this) {
			level.setBlock(pos, CARAMEL_BLOCK.defaultBlockState(), 2 | 16);
			return true;
		}
		return false;
	}

//	@Override
//	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
//		if ((level.dimension().equals(Level.OVERWORLD) || level.dimension().equals(CDims.CANDYLAND))) {
//			ItemStack itemInHand = player.getItemInHand(hand);
//			if (itemInHand.getItem() instanceof BucketItem bucketItem && bucketItem.content == Fluids.LAVA) {
//				player.setItemInHand(hand, BucketItem.getEmptySuccessItem(itemInHand, player));
//
//				//PortalShape.findEmptyPortalShape必须传入火的pos,而参数pos是当前方块的pos
//				BlockPos portalPos = LevelUtils.move(pos, hit.getDirection(), 1);
//				level.setBlock(pos, CARAMEL_BLOCK.defaultBlockState(), 2 | 16);
//				Optional<NetherLikePortalShape> shape = tryFindPortal(level, portalPos);
//				if (shape.isPresent()) {
//					shape.get().build(CARAMEL_PORTAL.getBlock().defaultBlockState());
//					return InteractionResult.sidedSuccess(level.isClientSide());
//				}
//			}
//
//		}
//		return InteractionResult.PASS;
//	}
}
