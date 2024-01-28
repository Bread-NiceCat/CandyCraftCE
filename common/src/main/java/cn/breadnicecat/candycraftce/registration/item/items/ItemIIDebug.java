package cn.breadnicecat.candycraftce.registration.item.items;

import com.google.common.collect.Lists;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
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
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 */
public class ItemIIDebug extends Item {

	private final String FUN_ORD = "fun_ord";
	private final Component CUR_FUN = Component.literal("当前模式: ").withStyle(ChatFormatting.LIGHT_PURPLE);

	public ItemIIDebug() {
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
			if (++ord >= FUNCTIONS.size()) {
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
		Level level = pContext.getLevel();
		if (level.isClientSide()) {
			return InteractionResult.SUCCESS;
		}
		BlockPos pos = pContext.getClickedPos();
		Player player = pContext.getPlayer();
		ItemStack item = pContext.getItemInHand();
		if (player != null) {
			CompoundTag tag = item.getOrCreateTag();
			int ord = getFunOrd(tag);
			IIIDebugFunction fun = FUNCTIONS.get(ord);
			String ord_s = String.valueOf(ord);
			tag.getCompound(ord_s);
			fun.onRightClickOn(level.getBlockState(pos), level, pos, player, item, tag);
			return InteractionResult.CONSUME;
		}
		return InteractionResult.FAIL;
	}

	public boolean canAttackBlock(@NotNull BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos, @NotNull Player pPlayer) {
		if (!pLevel.isClientSide()) {
			ItemStack item = pPlayer.getItemInHand(InteractionHand.MAIN_HAND);
			CompoundTag tag = item.getOrCreateTag();
			int ord = getFunOrd(tag);
			IIIDebugFunction fun = FUNCTIONS.get(ord);

			String key = String.valueOf(ord);
			CompoundTag spec_tag = tag.getCompound(key);
			fun.onLeftClickOn(pState, pLevel, pPos, pPlayer, item, spec_tag);
			tag.put(key, spec_tag);
		}
		return false;
	}

	public boolean isFoil(@NotNull ItemStack pStack) {
		return true;
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltips, TooltipFlag isAdvanced) {
		int ord = getFunOrd(stack.getOrCreateTag());
		IIIDebugFunction fun = FUNCTIONS.get(ord);
		tooltips.add(CUR_FUN.copy().append(fun.getName()));
		fun.appendHoverText(stack, level, tooltips, isAdvanced);
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
		public void onRightClickOn(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player, ItemStack item, CompoundTag nbt) {
			int[] zero = nbt.getIntArray(ZERO);
			if (zero.length != 0) {
				zero = new int[]{0, 0, 0};
			}
			player.sendSystemMessage(NAME.copy().append(": 坐标:  %d,%d,%d".formatted(pos.getX() - zero[0], pos.getY() - zero[1], pos.getZ() - zero[2])).withStyle(ChatFormatting.YELLOW));
		}

		@Override
		public void onLeftClickOn(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player, ItemStack item, CompoundTag nbt) {
			int[] po;
			if (player.isShiftKeyDown()) {
				po = new int[]{0, 0, 0};
				player.sendSystemMessage(NAME.copy().append(": 零点已清零").withStyle(ChatFormatting.DARK_GREEN));
			} else {
				po = new int[]{pos.getX(), pos.getY(), pos.getZ()};
				player.sendSystemMessage(NAME.copy().append(": 调零: [%s]".formatted(pos.toShortString())).withStyle(ChatFormatting.GREEN));
			}
			nbt.putIntArray(ZERO, po);
		}
	};
	//============================================================//
	public static final List<IIIDebugFunction> FUNCTIONS = Lists.newArrayList(
			D_RELATIVE
	);

	public interface IIIDebugFunction {
		Component getName();

		default void onRightClickOn(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player, ItemStack item, CompoundTag nbt) {
		}


		default void onLeftClickOn(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player, ItemStack item, CompoundTag nbt) {
		}

		default void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltips, TooltipFlag isAdvanced) {
		}

		static List<Component> tips(String... line) {
			return Arrays.stream(line)
					.map(Component::literal)
					.map(i -> (Component) i.withStyle(ChatFormatting.YELLOW))
					.toList();
		}
	}
}
