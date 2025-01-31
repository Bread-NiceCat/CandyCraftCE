package cn.breadnicecat.candycraftce.item.items.iidebug;

import cn.breadnicecat.candycraftce.utils.LevelUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

import static net.minecraft.ChatFormatting.YELLOW;

/**
 * Created in 2025/1/31 13:29
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 *
 * <p>
 **/
class DFRelative implements IIDebugFunction {
	
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
		if (level instanceof ClientLevel cl) {
			BlockPos zero = getZero(nbt);
			int rx = pos.getX() - zero.getX();
			int ry = pos.getY() - zero.getY();
			int rz = pos.getZ() - zero.getZ();
			if (zero.getCenter().distanceTo(zero.getCenter()) <= 128) {
				LevelUtils.particleBlock(ParticleTypes.FLAME, cl, zero, pos, 1 / 4d);
			}
			LevelUtils.particleBlock(ParticleTypes.FALLING_WATER, cl, pos, 1 / 4d);
			player.sendSystemMessage(NAME.copy().append(" 坐标:  %d,%d,%d".formatted(rx, ry, rz)).withStyle(YELLOW));
		}
	}
	
	@Override
	public void onLeftClickOn(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player, ItemStack item, CompoundTag nbt) {
		int[] po = player.isShiftKeyDown() ? new int[]{0, 0, 0} : new int[]{pos.getX(), pos.getY(), pos.getZ()};
		nbt.putIntArray(ZERO, po);
		if (level.isClientSide()) {
			player.sendSystemMessage(NAME.copy().append(" 调零: %s".formatted(Arrays.toString(po))).withStyle(ChatFormatting.GREEN));
		}
	}
	
	@Override
	public void appendExtraHoverText(ItemStack stack, List<Component> tooltips, TooltipFlag isAdvanced, CompoundTag nbt) {
		tooltips.add(SET);
		tooltips.add(RESET);
		tooltips.add(GET);
		int[] zero = nbt.contains(ZERO) ? nbt.getIntArray(ZERO) : new int[]{0, 0, 0};
		tooltips.add(Component.literal("当前零点: " + Arrays.toString(zero)).withStyle(ChatFormatting.GREEN));
	}
	
	@Override
	public void onInvTick(@NotNull Level level, @NotNull Player player, ItemStack item, CompoundTag nbt) {
		if (level instanceof ClientLevel cl) {
			BlockPos zero = getZero(nbt);
			if (player.position().distanceTo(zero.getCenter()) <= 128) {
				LevelUtils.particleBlock(ParticleTypes.FLAME, cl, zero, 1 / 4d);
			}
		}
	}
	
	private BlockPos getZero(CompoundTag nbt) {
		int[] ints = nbt.contains(ZERO) ? nbt.getIntArray(ZERO) : new int[]{0, 0, 0};
		return new BlockPos(ints[0], ints[1], ints[2]);
	}
}
