package cn.breadnicecat.candycraftce.item.items.iidebug;

import com.google.common.collect.Lists;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static cn.breadnicecat.candycraftce.item.CDataComponents.NBT;
import static net.minecraft.ChatFormatting.AQUA;
import static net.minecraft.ChatFormatting.YELLOW;

/**
 * Created in 2023/7/30 14:12
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 * <hr>
 **/
public class IIDebugItem extends Item {
	
	private final String FUN_ORD_KEY = "fun_ord";
	private final Component CUR_PREFIX_TEXT = Component.literal("当前模式: ").withStyle(ChatFormatting.LIGHT_PURPLE);
	private final Component SWITCH_TEXT = Component.literal("对空气 SHIFT+右键 切换模式").withStyle(YELLOW);
	
	public IIDebugItem() {
		super(new Properties().stacksTo(1).rarity(Rarity.EPIC));
	}
	
	@Override
	public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
		ItemStack stack = player.getItemInHand(usedHand);
		if (player.isShiftKeyDown() && usedHand == InteractionHand.MAIN_HAND) {
			//切换模式
			CompoundTag tag = getOrCreateNBT(stack);
			int ord = getFunOrd(tag);
			if (player.isShiftKeyDown()) {
				if (--ord < 0) {
					ord = FUNCTIONS.size() - 1;
				}
			} else if (++ord >= FUNCTIONS.size()) {
				ord = 0;
			}
			IIDebugFunction fun = FUNCTIONS.get(ord);
			if (!level.isClientSide()) player.sendSystemMessage(CUR_PREFIX_TEXT.copy().append(fun.getName()));
			tag.putInt(FUN_ORD_KEY, ord);
			setNbt(stack, tag);
			return InteractionResultHolder.consume(stack);
		}
		return InteractionResultHolder.fail(stack);
	}
	
	@Override
	public @NotNull InteractionResult useOn(UseOnContext pContext) {
		Player player = pContext.getPlayer();
		if (player == null || !player.isCreative()) {
			return InteractionResult.FAIL;
		}
		Level level = pContext.getLevel();
		BlockPos pos = pContext.getClickedPos();
		ItemStack stack = pContext.getItemInHand();
		
		CompoundTag tag = getOrCreateNBT(stack);
		int ord = getFunOrd(tag);
		IIDebugFunction fun = FUNCTIONS.get(ord);
		String ord_s = String.valueOf(ord);
		CompoundTag nbt = tag.getCompound(ord_s);
		
		fun.onRightClickOn(level.getBlockState(pos), level, pos, pContext.getClickedFace(), player, stack, nbt);
		
		tag.put(ord_s, nbt);
		setNbt(stack, tag);
		
		return InteractionResult.sidedSuccess(level.isClientSide);
	}
	
	public boolean canAttackBlock(@NotNull BlockState pState, @NotNull Level level, @NotNull BlockPos pPos, @NotNull Player pPlayer) {
		ItemStack stack = pPlayer.getItemInHand(InteractionHand.MAIN_HAND);
		
		CompoundTag tag = getOrCreateNBT(stack);
		int ord = getFunOrd(tag);
		IIDebugFunction fun = FUNCTIONS.get(ord);
		
		String ord_s = String.valueOf(ord);
		CompoundTag nbt = tag.getCompound(ord_s);
		
		fun.onLeftClickOn(pState, level, pPos, pPlayer, stack, nbt);
		
		tag.put(ord_s, nbt);
		setNbt(stack, tag);
		
		return false;
	}
	
	public boolean isFoil(@NotNull ItemStack pStack) {
		return true;
	}
	
	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltips, TooltipFlag isAdvanced) {
		CompoundTag root = getOrCreateNBT(stack);
		int ord = getFunOrd(root);
		IIDebugFunction fun = FUNCTIONS.get(ord);
		tooltips.add(CUR_PREFIX_TEXT.copy().append(fun.getName()));
		tooltips.add(SWITCH_TEXT);
		CompoundTag nbt = root.getCompound(String.valueOf(ord));
		fun.appendExtraHoverText(stack, tooltips, isAdvanced, nbt);
	}
	
	@Override
	public @NotNull Component getName(ItemStack stack) {
		CompoundTag tag = getOrCreateNBT(stack);
		return super.getName(stack).copy().append("(").append(FUNCTIONS.get(getFunOrd(tag)).getName().copy().withStyle(AQUA)).append(")");
	}
	
	private int getFunOrd(CompoundTag tag) {
		return tag.getInt(FUN_ORD_KEY) % FUNCTIONS.size();
	}
	
	private CompoundTag getOrCreateNBT(ItemStack stack) {
		return stack.getComponents().getOrDefault(NBT.get(), new CompoundTag());
	}
	
	private void setNbt(ItemStack stack, CompoundTag tag) {
		stack.set(NBT.get(), tag);
	}
	
	public static final List<IIDebugFunction> FUNCTIONS = Lists.newArrayList(new DFRelative(), new DFPortalDetector());
	
	@Override
	public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
		super.inventoryTick(stack, level, entity, slotId, isSelected);
		if (entity instanceof Player player) {
			CompoundTag tag = getOrCreateNBT(stack);
			int ord = getFunOrd(tag);
			IIDebugFunction fun = FUNCTIONS.get(ord);
			String ord_s = String.valueOf(ord);
			CompoundTag nbt = tag.getCompound(ord_s);
			
			fun.onInvTick(level, player, stack, nbt);
			
			tag.put(ord_s, nbt);
			setNbt(stack, tag);
		}
	}
}
