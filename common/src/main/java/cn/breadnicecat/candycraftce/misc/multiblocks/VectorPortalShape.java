package cn.breadnicecat.candycraftce.misc.multiblocks;

import cn.breadnicecat.candycraftce.utils.Axes;
import cn.breadnicecat.candycraftce.utils.LevelUtils;
import com.google.common.collect.Sets;
import it.unimi.dsi.fastutil.ints.IntIntPair;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Predicate;

import static cn.breadnicecat.candycraftce.utils.CommonUtils.newList;
import static net.minecraft.core.Direction.Axis.*;
import static net.minecraft.core.Direction.AxisDirection.NEGATIVE;
import static net.minecraft.core.Direction.AxisDirection.POSITIVE;

/**
 * Created in 2024/3/10 19:48
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 * <p>
 */
public abstract class VectorPortalShape {
	/**
	 * @return 框架内所有符合isEmpty的方块的集合
	 */
	public abstract Iterable<BlockPos> getPortals();
	
	/**
	 * @return 所有有效的框架的集合，必须框架：构建一个传送门所必须的框架。如低地狱门的四边
	 */
	public abstract Iterable<BlockPos> getRequiredFrames();
	
	public abstract Iterable<BlockPos> getExtraFrames();
	
	
	/**
	 * @return 所有框架的集合，包括必须框架和额外框架，
	 * 非必须框架：在传送门的领域内被正确识别为框架的方块，但是不是必须的方块。如地狱门的四角
	 */
	public abstract Iterable<BlockPos> getAllFrames();
	
	public boolean validate(Level level, PortalConfig config) {
		for (BlockPos frame : getAllFrames()) {
			if (!config.isFrame(level.getBlockState(frame))) return false;
		}
		for (BlockPos portal : getPortals()) {
			if (!config.isEmpty(level.getBlockState(portal))) return false;
		}
		return true;
	}
	
	public abstract List<Unit> getUnits();
	
	/**
	 * 构建传送门，这里传进来的传送门方块必须有Axis属性
	 *
	 * @param statePlacer axes, oldState : newState
	 * @return true 至少成功放置了1个方块
	 */
	public abstract boolean build(Level level, BiFunction<Axes, BlockState, BlockState> statePlacer);
	
	/**
	 * @param enableCompound   允许复合传送门，如果为true，statePlacer则为必填项
	 * @param enableHorizontal 允许传送门水平放置
	 *                         #param statePlacer enableCompound为false时，始终传入[1]，为true时，因为传送门交叉会传入更多
	 */
	public record PortalConfig(int minWidth, int maxWidth, int minHeight, int maxHeight, boolean enableHorizontal,
	                           boolean enableCompound, Predicate<BlockState> isEmpty, Predicate<BlockState> isFrame) {
		public boolean isEmpty(BlockState b) {
			return isEmpty.test(b);
		}
		
		public boolean isFrame(BlockState b) {
			return isFrame.test(b);
		}
	}
	
	private static final Axis[] XZY = new Axis[]{X, Z, Y};
	
	/**
	 * 根据框架找
	 *
	 * @param pos 任意一格有效框架
	 */
	public static Optional<VectorPortalShape> findPortalOnFrame(BlockGetter getter, BlockPos pos, PortalConfig config) {
		if (!config.isFrame(getter.getBlockState(pos))) return Optional.empty();
		List<VectorPortalShape> parts = new LinkedList<>();
		
		for (BlockPos neighbourPo : LevelUtils.getNeighbourPos(pos)) {
			BlockState state = getter.getBlockState(neighbourPo);
			if (config.isEmpty(state)) {
				findPortal(getter, neighbourPo, config).ifPresent(parts::add);
			}
		}
		return parts.isEmpty() ? Optional.empty() :
				parts.size() == 1 ? Optional.of(parts.get(0)) : Optional.of(new Compound(parts));
		
	}
	
	/**
	 * @param pos 传送门内的任意一格
	 */
	public static Optional<VectorPortalShape> findPortal(BlockGetter getter, BlockPos pos, PortalConfig config) {
		{
			if (!config.isEmpty.test(getter.getBlockState(pos))) return Optional.empty();
			List<Unit> units = new ArrayList<>(3);
			
			for (Axis axis : XZY) {
				if (axis != Y || config.enableHorizontal) {
					Unit unit = findAxis(getter, pos, axis, config);
					if (unit != null) {
						if (config.enableCompound) {
							units.add(unit);
						} else {
							return Optional.of(unit);
						}
					}
				}
			}
			return units.isEmpty() ? Optional.empty() :
					units.size() == 1 ? Optional.of(units.get(0))
							: Optional.of(new Compound(units));
		}
	}
	
	public static class Unit extends VectorPortalShape {
		
		public final BlockPos bottomLeft;
		public final Axis axis;
		public final Axes axes;
		public final int width;
		public final int height;
		public final PortalConfig config;
		private final Set<BlockPos> frames;
		private final Set<BlockPos> portals;
		private final Set<BlockPos> extraFrames;
		//对应到[width,height]
		public final Axis[] pipe2;
		
		/**
		 * 这里的width和height都是泛义的
		 * <p>
		 * bottomLeft指框架内符合isEmpty中pipe2的和最小的一个pos
		 * <p>
		 * width指bottomLeft的pipe2[0] + width +1 抵到框架
		 * <p>
		 * height指bottomLeft的pipe2[1] + height +1 抵到框架
		 */
		public Unit(BlockPos bottomLeft, Axis axis, int width, int height, PortalConfig config,
		            Set<BlockPos> portals, Set<BlockPos> frames, @Nullable Set<BlockPos> extraFrames) {
			this.bottomLeft = bottomLeft;
			this.axis = axis;
			this.axes = new Axes(axis);
			this.width = width;
			this.height = height;
			this.pipe2 = axis2pipe2(axis);
			this.config = config;
			this.frames = frames;
			this.portals = portals;
			this.extraFrames = Objects.requireNonNullElseGet(extraFrames, Set::of);
		}
		
		@Override
		public Iterable<BlockPos> getPortals() {
			return portals;
		}
		
		@Override
		public Iterable<BlockPos> getRequiredFrames() {
			return frames;
		}
		
		@Override
		public Iterable<BlockPos> getExtraFrames() {
			return extraFrames;
		}
		
		@Override
		public Iterable<BlockPos> getAllFrames() {
			return Sets.union(frames, extraFrames);
		}
		
		@Override
		public List<Unit> getUnits() {
			return List.of(this);
		}
		
		@Override
		public boolean build(Level level, BiFunction<Axes, BlockState, BlockState> statePlacer) {
			boolean flag = false;
			for (BlockPos pos : getPortals()) {
				flag |= level.setBlockAndUpdate(pos, statePlacer.apply(axes, level.getBlockState(pos)));
			}
			return flag;
		}
		
		@Override
		public String toString() {
			return """
					{"type":"unit","bottom_left":{"x":%d,"y":%d,"z":%d},"axis":"%s","width":%d,"height":%d}
					""".formatted(bottomLeft.getX(), bottomLeft.getY(), bottomLeft.getZ(), axis, width, height);
		}
		
		public BlockPos getTopRight() {
			return bottomLeft.relative(pipe2[0], width - 1).relative(pipe2[1], height - 1);
		}
	}
	
	
	public static class Compound extends VectorPortalShape {
		
		private final Map<BlockPos, Axes> portals;
		private final Set<BlockPos> frames;
		private final Set<BlockPos> extraFrames;
		private final List<? extends VectorPortalShape> parts;
		
		@SuppressWarnings({"rawtypes", "unchecked"})
		Compound(List<? extends VectorPortalShape> parts) {
			this.parts = parts;
			Map<BlockPos, Axes.Mutable> portalMap = new HashMap<>();
			this.portals = (Map) portalMap;
			frames = new HashSet<>();
			extraFrames = new HashSet<>();
			
			for (VectorPortalShape part : parts) {
				
				if (part instanceof Unit unit) {
					for (BlockPos pos : unit.getPortals()) {
						Axes.Mutable mutable = portalMap.get(pos);
						if (mutable == null) {
							portalMap.put(pos, unit.axes.mutable());//如果表里面没有就加进去
						} else {
							mutable.with(unit.axis);//地址值已经存在表里面，直接改就行
						}
					}
				} else if (part instanceof Compound compound) {
					for (Map.Entry<BlockPos, Axes> entry : compound.getAxesPortals()) {
						BlockPos pos = entry.getKey();
						Axes axes = entry.getValue();
						Axes.Mutable mutable = portalMap.get(pos);
						if (mutable == null) {
							portalMap.put(pos, axes.mutable());
						} else {
							mutable.withOr(axes);
						}
					}
				}
				
				part.getRequiredFrames().forEach(frames::add);
				part.getExtraFrames().forEach(extraFrames::add);
			}
		}
		
		
		@Override
		public Iterable<BlockPos> getPortals() {
			return portals.keySet();
		}
		
		public Iterable<Map.Entry<BlockPos, Axes>> getAxesPortals() {
			return portals.entrySet();
		}
		
		@Override
		public Iterable<BlockPos> getRequiredFrames() {
			return frames;
		}
		
		@Override
		public Iterable<BlockPos> getExtraFrames() {
			return extraFrames;
		}
		
		@Override
		public Iterable<BlockPos> getAllFrames() {
			return Sets.union(frames, extraFrames);
		}
		
		@Override
		public List<Unit> getUnits() {
			LinkedList<Unit> list = new LinkedList<>();
			parts.forEach(p -> list.addAll(p.getUnits()));
			return Collections.unmodifiableList(list);
		}
		
		@Override
		public boolean build(Level level, BiFunction<Axes, BlockState, BlockState> statePlacer) {
			boolean flag = false;
			for (Map.Entry<BlockPos, Axes> entry : getAxesPortals()) {
				flag |= level.setBlockAndUpdate(entry.getKey(), statePlacer.apply(entry.getValue(), level.getBlockState(entry.getKey())));
			}
			return flag;
		}
		
		@Override
		public String toString() {
			return """
					{"type": "compound", "parts": %s}
					""".formatted(parts);
		}
	}
	
	private static @Nullable Unit findAxis(BlockGetter level, BlockPos pos, Axis axis, @NotNull PortalConfig config) {
		Axis[] pipe2 = axis2pipe2(axis);
		boolean exchangeable = axis == Y;//只有传送门水平的时候才启用
		/*
		 * exchangeable的思路是：
		 * 先按对拿出最大值较大和较小的两组，
		 * 因为findBound要的是limit，
		 * 而为了把两个bound都拿出来，
		 * 肯定要先用宽松的limit，
		 * 所以先比较较大值，
		 * M>m
		 * 随后第一个bound拿出来了，
		 * 如果此bound同时满足两个限制，
		 * 则优先给这个这个bound赋予限制强的limit。
		 *
		 * */
		IntIntPair limitWM = IntIntPair.of(config.minWidth, config.maxWidth);
		IntIntPair limitHm = IntIntPair.of(config.minHeight, config.maxHeight);
		
		if (exchangeable && limitWM.rightInt() < limitHm.rightInt()) {
			var i = limitHm;
			limitHm = limitWM;
			limitWM = i;
		}
		//bound[正边界宽度,总宽度]
		//默认情况下(!exchangeable)
		//pipe2[1] = Y
		int[] boundW = findBound(level, pos, pipe2[0], config.isEmpty, config.isFrame, limitWM.rightInt());
		int[] boundH = findBound(level, pos, pipe2[1], config.isEmpty, config.isFrame, exchangeable ? limitWM.rightInt() : limitHm.rightInt());
		
		if (exchangeable) {
			boolean flag = false;
			for (int i = 0; i < 2; i++) {
				//定双方都满足的limit
				if (boundW[1] >= limitWM.leftInt() && boundH[1] >= limitHm.leftInt()) flag = true;
				//交换,继续
				var cg = limitHm;
				limitHm = limitWM;
				limitWM = cg;
			}
			if (!flag) return null;
		} else if (boundW[1] < limitWM.leftInt() || boundH[1] < limitHm.leftInt()) return null;
		
		int delta1 = boundW[1] - boundW[0] - 1;
		int delta2 = boundH[1] - boundH[0] - 1;
		BlockPos bottomLeft = pos.relative(pipe2[0], -delta1)
				.relative(pipe2[1], -delta2);
		List<Set<BlockPos>> sets = collectBlocks(level, bottomLeft, pipe2, boundW[1], boundH[1], config.isEmpty, config.isFrame);
		return sets == null ? null :
				new Unit(bottomLeft, axis, boundW[1], boundH[1], config, sets.get(0), sets.get(1), sets.get(2));
	}
	
	/**
	 * <pre>
	 * | = = = = = = = | : 总宽度 = 7
	 * |       = = = = | : 正边界宽度 = 4
	 * 8 7 6 0 1 2 3 4 5 : i(for),7号位是bottomLeft
	 * | = = = = = = = |
	 *       ↑pos
	 * </pre>
	 *
	 * @param limit 限制宽度
	 * @return {正边界宽度，总宽度} 如果未找到则返回 {-1,-1}
	 */
	private static int @NotNull [] findBound(BlockGetter level, BlockPos pos, Axis pipe, Predicate<BlockState> isEmpty,
	                                         Predicate<BlockState> isFrame, int limit) {
		int pn = -1;
		Direction direction = axis2direction(pipe, true);
		BlockPos.MutableBlockPos mutable = pos.mutable();
		for (int i = 0; i < limit + 1; i++) {
			BlockState state = level.getBlockState(mutable);
			if (isEmpty.test(state)) {
			} else if (isFrame.test(state)) {
				if (pn == -1) {
					pn = i - 1;
					direction = direction.getOpposite();
					mutable.set(pos);
				} else {
					return new int[]{pn, i - 1};
				}
			} else break;
			mutable.move(direction);
		}
		return new int[]{-1, -1};
	}
	
	private static @Nullable List<Set<BlockPos>> collectBlocks(BlockGetter level, @NotNull BlockPos bottomLeft, Axis @NotNull [] pipe2, int length1, int length2,
	                                                           Predicate<BlockState> isEmpty, Predicate<BlockState> isFrame) {
		// | bw * * * * | length=5
		List<Set<BlockPos>> sets = newList(3, i -> new HashSet<>());
		
		int minW = bottomLeft.get(pipe2[0]) - 1;
		int minH = bottomLeft.get(pipe2[1]) - 1;
		int maxW = minW + length1 + 1;
		int maxH = minH + length2 + 1;
		BlockPos.MutableBlockPos mutable = bottomLeft.mutable();
		for (int w = minW; w < maxW + 1; w++) {
			at(mutable, pipe2[0], w);
			for (int h = minH; h < maxH + 1; h++) {
				at(mutable, pipe2[1], h);
				int flag = 0;
				if (w == minW || w == maxW) flag++;
				if (h == minH || h == maxH) flag++;
				//flag:   0      1      2
				//func: portal frame  extra
				BlockState state = level.getBlockState(mutable);
				if ((flag == 0 ? isEmpty : isFrame).test(state)) {
					sets.get(flag).add(mutable.immutable());
				} else if (flag == 2) {
					//extra
				} else return null;
			}
		}
		return sets;
	}
	
	public static Axis[] axis2pipe2(Axis axis) {
		return switch (axis) {
			case X -> new Axis[]{Z, Y};
			case Z -> new Axis[]{X, Y};
			
			case Y -> new Axis[]{X, Z};
		};
	}
	
	public static Direction axis2direction(Axis axis, boolean positive) {
		return Direction.get(positive ? POSITIVE : NEGATIVE, axis);
	}
	
	
	private static BlockPos.MutableBlockPos at(BlockPos.MutableBlockPos pos, Axis axis, int value) {
		return switch (axis) {
			case X -> pos.setX(value);
			case Y -> pos.setY(value);
			case Z -> pos.setZ(value);
		};
	}
}