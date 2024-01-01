package cn.breadnicecat.candycraftce.misc.muitlblocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.NetherPortalBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Predicate;

import static cn.breadnicecat.candycraftce.utils.LevelUtils.move;

/**
 * 类似地狱门的传送门识别器
 * <p>
 * Fork from {@link  net.minecraft.world.level.portal.PortalShape}
 *
 * @author <a href="https://gitee.com/Bread_NiceCat">Bread_NiceCat</a>
 * @date 2023/1/9 22:59
 */
public class NetherLikePortalShape {

	//坑爹玩意,全被final直接插入了,改都改不了
	/**
	 * 传送门方块最小宽度
	 */
	public static final int MIN_WIDTH = 2;
	/**
	 * 传送门方块最大宽度
	 */
	public static final int MAX_WIDTH = 21;
	/**
	 * 传送门方块最小高度
	 */
	public static final int MIN_HEIGHT = 3;
	/**
	 * 传送门方块最大高度
	 */
	public static final int MAX_HEIGHT = 21;

	private final Predicate<BlockState> isFrame;
	private final Predicate<BlockState> canIgnore;
	public final Level level;
	public final Direction.Axis axis;
	public final Direction rightDir;
	public final int width;
	//	private int numPortalBlocks;
	public int height;
	@Nullable
	public BlockPos bottomLeft;

	public NetherLikePortalShape(Level pLevel, @NotNull BlockPos pBottomLeft, Direction.Axis pAxis, Predicate<BlockState> isFrame, Predicate<BlockState> canIgnore) {
		this.isFrame = isFrame;
		this.canIgnore = canIgnore;
		this.level = pLevel;
		this.axis = pAxis;
		this.rightDir = pAxis == Direction.Axis.X ? Direction.WEST : Direction.SOUTH;
		this.bottomLeft = this.calculateBottomLeft(pBottomLeft);
		if (this.bottomLeft == null) {
			this.bottomLeft = pBottomLeft;
			this.width = 1;
			this.height = 1;
		} else {
			this.width = this.calculateWidth();
			if (this.width > 0) {
				this.height = this.calculateHeight();
			}
		}

	}

	/**
	 * 使用这个方法检测传送门
	 *
	 * @param canIgnore 传送门框架内部分方块能否被忽略,例如地狱门内可以存在火方块和空气方块
	 */
	public static Optional<NetherLikePortalShape> findPortalShape(Level pLevel, BlockPos pBottomLeft, Predicate<BlockState> isFrame, Predicate<BlockState> canIgnore) {
		return findPortalShape(pLevel, pBottomLeft, NetherLikePortalShape::isValid /*&& portalShape.numPortalBlocks == 0*/, isFrame, canIgnore);
	}

	public static Optional<NetherLikePortalShape> findPortalShape(Level pLevel, BlockPos pBottomLeft, Predicate<NetherLikePortalShape> isProper, Predicate<BlockState> isFrame, Predicate<BlockState> canIgnore) {
		Optional<NetherLikePortalShape> optional = Optional.of(new NetherLikePortalShape(pLevel, pBottomLeft, Direction.Axis.X, isFrame, canIgnore)).filter(isProper);
		if (optional.isPresent()) return optional;
		return Optional.of(new NetherLikePortalShape(pLevel, pBottomLeft, Direction.Axis.Z, isFrame, canIgnore)).filter(isProper);
	}

	public boolean isValid() {
		return this.bottomLeft != null && this.width >= MIN_WIDTH && this.width <= MAX_WIDTH && this.height >= MIN_HEIGHT && this.height <= MAX_HEIGHT;
	}

	/**
	 * @param portal 一般情况下直接传入默认BlockState就行
	 */
	public void build(BlockState portal) {
		BlockState blockstate = portal.setValue(NetherPortalBlock.AXIS, this.axis);
		forEachBlockInPortal((level, pos) -> level.setBlock(pos, blockstate, 2 | 16));
	}

	public void forEachBlockInPortal(BiConsumer<Level, BlockPos> e) {
		BlockPos.betweenClosed(this.bottomLeft, this.bottomLeft.relative(Direction.UP, this.height - 1).relative(this.rightDir, this.width - 1)).forEach((pos) -> e.accept(this.level, pos));
	}

	@Nullable
	private BlockPos calculateBottomLeft(BlockPos bottomLeft) {
		//确定最低点
		int min = Math.max(this.level.getMinBuildHeight(), bottomLeft.getY() - MAX_HEIGHT);
		while (bottomLeft.getY() > min && isEmpty(this.level.getBlockState(bottomLeft.below()))) {
			bottomLeft = bottomLeft.below();
		}

		Direction direction = this.rightDir.getOpposite();
		int j = this.getDistanceUntilEdgeAboveFrame(bottomLeft, direction) - 1;
		return j < 0 ? null : bottomLeft.relative(direction, j);
	}

	private boolean isEmpty(BlockState blockState) {
		return canIgnore.test(blockState);
	}

	private int calculateWidth() {
		int i = this.getDistanceUntilEdgeAboveFrame(this.bottomLeft, this.rightDir);
		return i >= MIN_WIDTH && i <= MAX_WIDTH ? i : 0;
	}

	/**
	 * 测宽度使用,必须要贴底边和侧边
	 */
	private int getDistanceUntilEdgeAboveFrame(BlockPos pPos, Direction pDirection) {
		//  ################
		//  #              #
		//  #              #
		//  #              #
		//  #@->           #<-(End Here)
		//  ################
		BlockPos pos;
		for (int i = 0; i <= MAX_WIDTH; ++i) {
			pos = move(pPos, pDirection, i);
			BlockState blockstate = this.level.getBlockState(pos);
			if (!isEmpty(blockstate)) {
				if (isFrame.test(blockstate)) {//抵到头了
					return i;
				}
				break;
			}
			BlockState blockstate1 = this.level.getBlockState(pos.below());
			if (!isFrame.test(blockstate1)) {
				break;
			}
		}

		return 0;
	}

	private int calculateHeight() {
		BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
		int i = this.getDistanceUntilTop(blockpos$mutableblockpos);
		return i >= MIN_HEIGHT && i <= MAX_HEIGHT && this.hasTopFrame(blockpos$mutableblockpos, i) ? i : 0;
	}


	private boolean hasTopFrame(BlockPos.MutableBlockPos p_77731_, int p_77732_) {
		for (int i = 0; i < this.width; ++i) {
			BlockPos.MutableBlockPos blockpos$mutableblockpos = p_77731_.set(this.bottomLeft).move(Direction.UP, p_77732_).move(this.rightDir, i);
			if (!isFrame.test(this.level.getBlockState(blockpos$mutableblockpos))) {
				return false;
			}
		}
		return true;
	}

	private int getDistanceUntilTop(BlockPos.MutableBlockPos pPos) {
		for (int i = 0; i < MAX_HEIGHT; ++i) {
			pPos.set(this.bottomLeft).move(Direction.UP, i).move(this.rightDir, -1);
			if (!isFrame.test(this.level.getBlockState(pPos))) {
				return i;
			}

			pPos.set(this.bottomLeft).move(Direction.UP, i).move(this.rightDir, this.width);
			if (!isFrame.test(this.level.getBlockState(pPos))) {
				return i;
			}

			for (int j = 0; j < this.width; ++j) {
				pPos.set(this.bottomLeft).move(Direction.UP, i).move(this.rightDir, j);
				BlockState blockstate = this.level.getBlockState(pPos);
				if (!isEmpty(blockstate)) {
					return i;
				}
//				if (blockstate.is(Blocks.NETHER_PORTAL)) {
//					++this.numPortalBlocks;
//				}
			}
		}

		return MAX_HEIGHT;
	}
}

