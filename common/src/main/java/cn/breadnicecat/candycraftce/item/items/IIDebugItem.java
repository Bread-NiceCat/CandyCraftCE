package cn.breadnicecat.candycraftce.item.items;

import com.google.common.collect.Lists;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
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

/**
 * Created in 2023/7/30 14:12
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 */
public class IIDebugItem extends Item {

	private final String FUN_ORD = "fun_ord";
	private final Component CUR_FUN = Component.literal("当前模式: ").withStyle(ChatFormatting.LIGHT_PURPLE);

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
		fun.onRightClickOn(level.getBlockState(pos), (ServerLevel) level, pos, player, item, nbt);
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
		CompoundTag nbt = root.getCompound(String.valueOf(ord));
		fun.appendHoverText(stack, level, tooltips, isAdvanced, nbt);
	}

	private int getFunOrd(CompoundTag tag) {
		return tag.getInt(FUN_ORD) % FUNCTIONS.size();
	}

	//============================================================//
	private static final IIIDebugFunction D_RELATIVE = new IIIDebugFunction() {

		private static final String ZERO = "zero";
		private static final Component NAME = Component.literal("[坐标测算]");

		@Override
		public Component getName() {
			return NAME;
		}

		@Override
		public void onRightClickOn(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull Player player, ItemStack item, CompoundTag nbt) {
			int[] zero = nbt.contains(ZERO) ? nbt.getIntArray(ZERO) : new int[]{0, 0, 0};
			player.sendSystemMessage(NAME.copy().append(": 坐标:  %d,%d,%d".formatted(pos.getX() - zero[0], pos.getY() - zero[1], pos.getZ() - zero[2])).withStyle(ChatFormatting.YELLOW));
		}

		@Override
		public void onLeftClickOn(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull Player player, ItemStack item, CompoundTag nbt) {
			int[] po = player.isShiftKeyDown() ? new int[]{0, 0, 0} : new int[]{pos.getX(), pos.getY(), pos.getZ()};
			nbt.putIntArray(ZERO, po);
			player.sendSystemMessage(NAME.copy().append(": 调零: [%s]".formatted(Arrays.toString(po))).withStyle(ChatFormatting.GREEN));
		}

		@Override
		public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltips, TooltipFlag isAdvanced, CompoundTag nbt) {
			int[] zero = nbt.contains(ZERO) ? nbt.getIntArray(ZERO) : new int[]{0, 0, 0};
			tooltips.add(Component.literal("当前零点: " + Arrays.toString(zero)).withStyle(ChatFormatting.GREEN));
		}
	};
	//============================================================//
	public static final List<IIIDebugFunction> FUNCTIONS = Lists.newArrayList(
			D_RELATIVE
	);

	public interface IIIDebugFunction {
		Component getName();

		default void onRightClickOn(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull Player player, ItemStack item, CompoundTag nbt) {
		}


		default void onLeftClickOn(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull Player player, ItemStack item, CompoundTag nbt) {
		}

		default void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltips, TooltipFlag isAdvanced, CompoundTag nbt) {
		}
	}
}
