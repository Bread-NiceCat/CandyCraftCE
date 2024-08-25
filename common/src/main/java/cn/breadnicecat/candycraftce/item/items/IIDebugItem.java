package cn.breadnicecat.candycraftce.item.items;

import cn.breadnicecat.candycraftce.item.CDataComponents;
import cn.breadnicecat.candycraftce.item.CItems;
import cn.breadnicecat.candycraftce.misc.multiblocks.VectorPortalShape;
import cn.breadnicecat.candycraftce.utils.LevelUtils;
import cn.breadnicecat.candycraftce.utils.TickUtils;
import com.google.common.collect.Lists;
import com.mojang.serialization.JsonOps;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.ChatFormatting;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static cn.breadnicecat.candycraftce.block.blocks.CaramelPortalBlock.CONFIG;
import static cn.breadnicecat.candycraftce.utils.LevelUtils.move;
import static net.minecraft.ChatFormatting.*;
import static net.minecraft.core.particles.ParticleTypes.FLAME;

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
	private final String USED_TIMES_KEY = "used_times";
	private final Component CUR_FUN = Component.literal("当前模式: ").withStyle(ChatFormatting.LIGHT_PURPLE);
	private final Component SWITCH_FUN = Component.literal("对空气 SHIFT+右键 切换模式").withStyle(YELLOW);
	
	public IIDebugItem() {
		super(new Properties().stacksTo(1).rarity(Rarity.EPIC));
	}
	
	@Override
	public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
		ItemStack item = player.getItemInHand(usedHand);
		
		if (player.isShiftKeyDown() && usedHand == InteractionHand.MAIN_HAND) {
			//切换模式
			CompoundTag tag = item.getOrDefault(CDataComponents.NBT.get(), new CompoundTag());
//			CompoundTag tag = item.getOrCreateTag();
			int ord = getFunOrd(tag);
			if (player.isShiftKeyDown()) {
				if (--ord < 0) {
					ord = FUNCTIONS.size() - 1;
				}
			} else if (++ord >= FUNCTIONS.size()) {
				ord = 0;
			}
			IIDebugFunction fun = FUNCTIONS.get(ord);
			if (!level.isClientSide()) player.sendSystemMessage(CUR_FUN.copy().append(fun.getName()));
			tag.putInt(FUN_ORD_KEY, ord);
			item.set(CDataComponents.NBT.get(), tag);
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

//		if (level.isClientSide()) return InteractionResult.SUCCESS;
		
		BlockPos pos = pContext.getClickedPos();
		ItemStack item = pContext.getItemInHand();
		
		CompoundTag tag = item.getOrDefault(CDataComponents.NBT.get(), new CompoundTag());
//		CompoundTag tag= item.getOrCreateTag();
		int ord = getFunOrd(tag);
		IIDebugFunction fun = FUNCTIONS.get(ord);
		
		String ord_s = String.valueOf(ord);
		CompoundTag nbt = tag.getCompound(ord_s);
		
		fun.onRightClickOn(level.getBlockState(pos), level, pos, pContext.getClickedFace(), player, item, nbt);
		if (level.isClientSide()) {
			fun.onRightClickOn_Client(level.getBlockState(pos), (ClientLevel) level, pos, pContext.getClickedFace(), player, item, nbt);
		}
		int used = (tag.contains(USED_TIMES_KEY) ? tag.getInt(USED_TIMES_KEY) : 0) + 1;
		tag.putInt(USED_TIMES_KEY, used);
		if (!level.isClientSide()) {
			if (used > 0 && used % 666 == 0) {
				LevelUtils.spawnItemEntity(level, pos, CItems.RECORD_o.getDefaultInstance());
				LevelUtils.spawnItemEntity(level, pos, Items.JUKEBOX.getDefaultInstance());
				level.playSound(player, pos, SoundEvents.FIREWORK_ROCKET_LARGE_BLAST_FAR, SoundSource.BLOCKS);
				player.sendSystemMessage(Component.literal("这是你第" + used + "次使用").withStyle(YELLOW).append(getName(item)).withStyle(BLUE));
			}
		}
		
		tag.put(ord_s, nbt);
		item.set(CDataComponents.NBT.get(), tag);
		return InteractionResult.sidedSuccess(level.isClientSide);
	}
	
	public boolean canAttackBlock(@NotNull BlockState pState, @NotNull Level level, @NotNull BlockPos pPos, @NotNull Player pPlayer) {
		ItemStack item = pPlayer.getItemInHand(InteractionHand.MAIN_HAND);

//		CompoundTag tag = item.getOrCreateTag();
		CompoundTag tag = item.getComponents().getOrDefault(CDataComponents.NBT.get(), new CompoundTag());
		int ord = getFunOrd(tag);
		IIDebugFunction fun = FUNCTIONS.get(ord);
		
		String ord_s = String.valueOf(ord);
		CompoundTag nbt = tag.getCompound(ord_s);
		fun.onLeftClickOn(pState, level, pPos, pPlayer, item, nbt);
		if (level.isClientSide()) {
			fun.onLeftClickOn_Client(pState, (ClientLevel) level, pPos, pPlayer, item, nbt);
		}
		tag.put(ord_s, nbt);
		return false;
	}
	
	public boolean isFoil(@NotNull ItemStack pStack) {
		return true;
	}
	
	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltips, TooltipFlag isAdvanced) {
//		CompoundTag root = stack.getOrCreateTag();
		CompoundTag root = stack.getComponents().getOrDefault(CDataComponents.NBT.get(), new CompoundTag());
		int ord = getFunOrd(root);
		IIDebugFunction fun = FUNCTIONS.get(ord);
		tooltips.add(CUR_FUN.copy().append(fun.getName()));
		tooltips.add(SWITCH_FUN);
		CompoundTag nbt = root.getCompound(String.valueOf(ord));
		fun.appendExtraHoverText(stack, tooltips, isAdvanced, nbt);
	}
	
	@Override
	public @NotNull Component getName(ItemStack stack) {
		CompoundTag tag = stack.getComponents().getOrDefault(CDataComponents.NBT.get(), new CompoundTag());
		return super.getName(stack).copy().append("(").append(FUNCTIONS.get(getFunOrd(tag)).getName().copy().withStyle(AQUA)).append(")");
	}
	
	private int getFunOrd(CompoundTag tag) {
		return tag.getInt(FUN_ORD_KEY) % FUNCTIONS.size();
	}
	
	//============================================================//
	private static final IIDebugFunction DF_RELATIVE = new IIDebugFunction() {
		
		private static final String ZERO = "zero";
		private static final Component NAME = Component.literal("坐标测算");
		private static final Component SET = Component.literal("左键 调零").withStyle(YELLOW);
		private static final Component RESET = Component.literal("SHIFT+左键 清除调零").withStyle(YELLOW);
		private static final Component GET = Component.literal("右键 获取相对坐标").withStyle(YELLOW);
		
		
		@Override
		public Component getName() {
			return NAME;
		}
		
		@Override
		public void onRightClickOn(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, Direction clickedFace, @NotNull Player player, ItemStack item, CompoundTag nbt) {
			if (level.isClientSide()) return;
			int[] zero = nbt.contains(ZERO) ? nbt.getIntArray(ZERO) : new int[]{0, 0, 0};
			int rx = pos.getX() - zero[0];
			int ry = pos.getY() - zero[1];
			int rz = pos.getZ() - zero[2];
			
			player.sendSystemMessage(NAME.copy().append(" 坐标:  %d,%d,%d".formatted(rx, ry, rz)).withStyle(YELLOW));
		}
		
		@Override
		public void onLeftClickOn(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player, ItemStack item, CompoundTag nbt) {
			if (level.isClientSide()) return;
			int[] po = player.isShiftKeyDown() ? new int[]{0, 0, 0} : new int[]{pos.getX(), pos.getY(), pos.getZ()};
			nbt.putIntArray(ZERO, po);
			player.sendSystemMessage(NAME.copy().append(" 调零: %s".formatted(Arrays.toString(po))).withStyle(ChatFormatting.GREEN));
		}
		
		@Override
		public void appendExtraHoverText(ItemStack stack, List<Component> tooltips, TooltipFlag isAdvanced, CompoundTag nbt) {
			tooltips.add(SET);
			tooltips.add(RESET);
			tooltips.add(GET);
			int[] zero = nbt.contains(ZERO) ? nbt.getIntArray(ZERO) : new int[]{0, 0, 0};
			tooltips.add(Component.literal("当前零点: " + Arrays.toString(zero)).withStyle(ChatFormatting.GREEN));
		}
	};
	private static final IIDebugFunction DF_DETECT_PORTAL = new IIDebugFunction() {
		private static final Component NAME = Component.literal("传送门测试");
		private static final Component TEST = Component.literal("右键传送门框架检测").withStyle(YELLOW);
		
		@Override
		public Component getName() {
			return NAME;
		}
		
		@Override
		public void onRightClickOn_Client(@NotNull BlockState state, @NotNull ClientLevel level, @NotNull BlockPos pos, Direction clickedFace, @NotNull Player player, ItemStack item, CompoundTag nbt) {
			long stt = System.nanoTime();
			Optional<VectorPortalShape> portal = Optional.empty();
			pos = move(pos, clickedFace, 1);
			LevelUtils.particleBlock(ParticleTypes.BUBBLE, level, pos, 1 / 4d);
			if (CONFIG.isEmpty(level.getBlockState(pos))) {
				portal = VectorPortalShape.findPortal(level, pos, CONFIG);
			}
			float ttt = (System.nanoTime() - stt) / 1E6F;
			
			if (portal.isPresent()) {
				VectorPortalShape shape = portal.get();
				level.playSound(null, pos, SoundEvents.NOTE_BLOCK_PLING.value(), SoundSource.BLOCKS);
				player.sendSystemMessage(NAME.copy().append(" 框架已找到").withStyle(ChatFormatting.GREEN));
				//viewable
				shape.getUnits().forEach(unit -> LevelUtils.particleBlock(FLAME, level, unit.bottomLeft, unit.getTopRight(), 1 / 4d));
				//colorful outputs
				player.sendSystemMessage(NbtUtils.toPrettyComponent(JsonOps.INSTANCE.convertTo(NbtOps.INSTANCE, GsonHelper.parse(shape.toString()))));
//				player.sendSystemMessage(Component.literal(shape.toString()));
			} else {
				level.playSound(null, pos, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS);
				player.sendSystemMessage(NAME.copy().append(" 未找到正确的传送门框架").withStyle(RED));
			}
			player.sendSystemMessage(NAME.copy().append(" 共耗时: " + ttt + " ms (" + ttt / TickUtils.MS_PER_TICK + " tick)").withStyle(ChatFormatting.GOLD));
		}
		
		@Override
		public void appendExtraHoverText(ItemStack stack, List<Component> tooltips, TooltipFlag isAdvanced, CompoundTag nbt) {
			tooltips.add(TEST);
		}
	};
	//============================================================//
	public static final List<IIDebugFunction> FUNCTIONS = Lists.newArrayList(
			DF_RELATIVE, DF_DETECT_PORTAL
	);
	
	public interface IIDebugFunction {
		Component getName();
		
		//Common
		default void onRightClickOn(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, Direction clickedFace, @NotNull Player player, ItemStack item, CompoundTag nbt) {
		}
		
		@Environment(EnvType.CLIENT)
		default void onRightClickOn_Client(@NotNull BlockState state, @NotNull ClientLevel level, @NotNull BlockPos pos, Direction clickedFace, @NotNull Player player, ItemStack item, CompoundTag nbt) {
		}
		
		//Common
		default void onLeftClickOn(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player, ItemStack item, CompoundTag nbt) {
		}
		
		@Environment(EnvType.CLIENT)
		default void onLeftClickOn_Client(@NotNull BlockState state, @NotNull ClientLevel level, @NotNull BlockPos pos, @NotNull Player player, ItemStack item, CompoundTag nbt) {
		}
		
		default void appendExtraHoverText(ItemStack stack, List<Component> tooltips, TooltipFlag isAdvanced, CompoundTag nbt) {
		}
		
	}
}
