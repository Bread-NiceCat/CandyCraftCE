package cn.breadnicecat.candycraftce.item.items;

import cn.breadnicecat.candycraftce.misc.muitlblocks.VectorPortalShape;
import cn.breadnicecat.candycraftce.utils.TickUtils;
import com.google.common.collect.Lists;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static cn.breadnicecat.candycraftce.block.blocks.CaramelPortalBlock.CONFIG;

/**
 * Created in 2023/7/30 14:12
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 */
public class IIDebugItem extends Item {

	private final String FUN_ORD = "fun_ord";
	private final Component CUR_FUN = Component.literal("当前模式: ").withStyle(ChatFormatting.LIGHT_PURPLE);
	private final Component SWITCH_FUN = Component.literal("对空气 SHIFT+右键 切换模式").withStyle(ChatFormatting.YELLOW);

	public IIDebugItem() {
		super(new Properties().stacksTo(1).rarity(Rarity.EPIC));
	}

	@Override
	public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
		ItemStack item = player.getItemInHand(usedHand);
		if (level.isClientSide) return InteractionResultHolder.success(item);
		if (player.isShiftKeyDown() && usedHand == InteractionHand.MAIN_HAND) {
			//切换模式
			CompoundTag tag = item.getOrCreateTag();
			int ord = getFunOrd(tag);
			if (player.isShiftKeyDown()) {
				if (--ord < 0) {
					ord = FUNCTIONS.size() - 1;
				}
			} else if (++ord >= FUNCTIONS.size()) {
				ord = 0;
			}
			IIIDebugFunction fun = FUNCTIONS.get(ord);
			player.sendSystemMessage(CUR_FUN.copy().append(fun.getName()));
			tag.putInt(FUN_ORD, ord);
			return InteractionResultHolder.consume(item);
		}
		return InteractionResultHolder.fail(item);
	}

	@Override
	public @NotNull InteractionResult useOn(UseOnContext pContext) {
		Player player = pContext.getPlayer();
		if (player == null || !player.isCreative()) {
			return InteractionResult.FAIL;
		}
		Level level = pContext.getLevel();
		if (level.isClientSide()) {
			return InteractionResult.SUCCESS;
		}
		BlockPos pos = pContext.getClickedPos();
		ItemStack item = pContext.getItemInHand();
		CompoundTag tag = item.getOrCreateTag();
		int ord = getFunOrd(tag);
		IIIDebugFunction fun = FUNCTIONS.get(ord);
		String ord_s = String.valueOf(ord);
		CompoundTag nbt = tag.getCompound(ord_s);
		fun.onRightClickOn(level.getBlockState(pos), (ServerLevel) level, pos, pContext.getClickedFace(), player, item, nbt);
		tag.put(ord_s, nbt);
		return InteractionResult.CONSUME;
	}

	public boolean canAttackBlock(@NotNull BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos, @NotNull Player pPlayer) {
		if (!pLevel.isClientSide()) {
			ItemStack item = pPlayer.getItemInHand(InteractionHand.MAIN_HAND);
			CompoundTag tag = item.getOrCreateTag();
			int ord = getFunOrd(tag);
			IIIDebugFunction fun = FUNCTIONS.get(ord);

			String ord_s = String.valueOf(ord);
			CompoundTag nbt = tag.getCompound(ord_s);
			fun.onLeftClickOn(pState, (ServerLevel) pLevel, pPos, pPlayer, item, nbt);
			tag.put(ord_s, nbt);
		}
		return false;
	}

	public boolean isFoil(@NotNull ItemStack pStack) {
		return true;
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltips, TooltipFlag isAdvanced) {
		CompoundTag root = stack.getOrCreateTag();
		int ord = getFunOrd(root);
		IIIDebugFunction fun = FUNCTIONS.get(ord);
		tooltips.add(CUR_FUN.copy().append(fun.getName()));
		tooltips.add(SWITCH_FUN);
		CompoundTag nbt = root.getCompound(String.valueOf(ord));
		fun.appendExtraHoverText(stack, level, tooltips, isAdvanced, nbt);
	}

	@Override
	public @NotNull Component getName(ItemStack stack) {
		return super.getName(stack).copy().append("(").append(FUNCTIONS.get(getFunOrd(stack.getOrCreateTag())).getName()).append(")");
	}

	private int getFunOrd(CompoundTag tag) {
		return tag.getInt(FUN_ORD) % FUNCTIONS.size();
	}

	//============================================================//
	private static final IIIDebugFunction DF_DETECT_PORTAL = new IIIDebugFunction() {
		private static final Component NAME = Component.literal("传送门测试");
		private static final Component TEST = Component.literal("右键传送门框架检测").withStyle(ChatFormatting.YELLOW);

		@Override
		public Component getName() {
			return NAME;
		}

		@Override
		public void onRightClickOn(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, Direction clickedFace, @NotNull Player player, ItemStack item, CompoundTag nbt) {
			long stt = System.nanoTime();
			Optional<VectorPortalShape> portal = Optional.empty();
			if (CONFIG.isFrame(state)) {
				portal = VectorPortalShape.findPortalOnFrame(level, pos, CONFIG);
			}
			float ttt = (System.nanoTime() - stt) / 1E6f;
			if (portal.isPresent()) {
				VectorPortalShape shape = portal.get();
				level.playSound(null, pos, SoundEvents.NOTE_BLOCK_PLING.value(), SoundSource.BLOCKS);
				player.sendSystemMessage(NAME.copy().append(" 框架已找到").withStyle(ChatFormatting.GREEN));
				player.sendSystemMessage(Component.literal(shape.toString()));
			} else {
				level.playSound(null, pos, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS);
				player.sendSystemMessage(NAME.copy().append(" 未找到正确的传送门框架").withStyle(ChatFormatting.RED));
			}
			player.sendSystemMessage(NAME.copy().append(" 共耗时: " + ttt + " ms (" + ttt * TickUtils.MS2TICK + " tick)").withStyle(ChatFormatting.GOLD));
		}

		@Override
		public void appendExtraHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltips, TooltipFlag isAdvanced, CompoundTag nbt) {
			tooltips.add(TEST);
		}
	};
	private static final IIIDebugFunction DF_RELATIVE = new IIIDebugFunction() {

		private static final String ZERO = "zero";
		private static final Component NAME = Component.literal("坐标测算");
		private static final Component SET = Component.literal("左键 调零").withStyle(ChatFormatting.YELLOW);
		private static final Component RESET = Component.literal("SHIFT+左键 清除调零").withStyle(ChatFormatting.YELLOW);
		private static final Component GET = Component.literal("右键 获取相对坐标").withStyle(ChatFormatting.YELLOW);

		@Override
		public Component getName() {
			return NAME;
		}

		@Override
		public void onRightClickOn(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, Direction clickedFace, @NotNull Player player, ItemStack item, CompoundTag nbt) {
			int[] zero = nbt.contains(ZERO) ? nbt.getIntArray(ZERO) : new int[]{0, 0, 0};
			player.sendSystemMessage(NAME.copy().append(" 坐标:  %d,%d,%d".formatted(pos.getX() - zero[0], pos.getY() - zero[1], pos.getZ() - zero[2])).withStyle(ChatFormatting.YELLOW));
		}

		@Override
		public void onLeftClickOn(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull Player player, ItemStack item, CompoundTag nbt) {
			int[] po = player.isShiftKeyDown() ? new int[]{0, 0, 0} : new int[]{pos.getX(), pos.getY(), pos.getZ()};
			nbt.putIntArray(ZERO, po);
			player.sendSystemMessage(NAME.copy().append(" 调零: %s".formatted(Arrays.toString(po))).withStyle(ChatFormatting.GREEN));
		}

		@Override
		public void appendExtraHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltips, TooltipFlag isAdvanced, CompoundTag nbt) {
			tooltips.add(SET);
			tooltips.add(RESET);
			tooltips.add(GET);
			int[] zero = nbt.contains(ZERO) ? nbt.getIntArray(ZERO) : new int[]{0, 0, 0};
			tooltips.add(Component.literal("当前零点: " + Arrays.toString(zero)).withStyle(ChatFormatting.GREEN));
		}
	};
	//============================================================//
	public static final List<IIIDebugFunction> FUNCTIONS = Lists.newArrayList(
			DF_RELATIVE, DF_DETECT_PORTAL
	);

	public interface IIIDebugFunction {
		Component getName();

		default void onRightClickOn(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, Direction clickedFace, @NotNull Player player, ItemStack item, CompoundTag nbt) {
		}


		default void onLeftClickOn(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull Player player, ItemStack item, CompoundTag nbt) {
		}

		default void appendExtraHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltips, TooltipFlag isAdvanced, CompoundTag nbt) {
		}
	}
}
