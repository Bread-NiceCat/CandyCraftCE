package cn.breadnicecat.candycraftce.item.items.iidebug;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Created in 2025/1/31 13:29
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 *
 * <p>
 **/
public interface IIDebugFunction {
	Component getName();
	
	default void onRightClickOn(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, Direction clickedFace, @NotNull Player player, ItemStack item, CompoundTag nbt) {
	}
	
	default void onLeftClickOn(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player, ItemStack item, CompoundTag nbt) {
	}
	
	default void onInvTick(@NotNull Level level, @NotNull Player player, ItemStack item, CompoundTag nbt) {
	}
	
	default void appendExtraHoverText(ItemStack stack, List<Component> tooltips, TooltipFlag isAdvanced, CompoundTag nbt) {
	}
	
}
