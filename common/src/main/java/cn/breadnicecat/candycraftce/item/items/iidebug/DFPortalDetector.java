package cn.breadnicecat.candycraftce.item.items.iidebug;

import cn.breadnicecat.candycraftce.utils.LevelUtils;
import cn.breadnicecat.candycraftce.utils.TickUtils;
import cn.breadnicecat.candycraftce.utils.multiblocks.VectorPortalShape;
import com.mojang.serialization.JsonOps;
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
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

import static cn.breadnicecat.candycraftce.level.CaramelPortal.CONFIG;
import static cn.breadnicecat.candycraftce.utils.LevelUtils.move;
import static net.minecraft.ChatFormatting.RED;
import static net.minecraft.ChatFormatting.YELLOW;
import static net.minecraft.core.particles.ParticleTypes.FLAME;

/**
 * Created in 2025/1/31 13:29
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 *
 * <p>
 **/
class DFPortalDetector implements IIDebugFunction {
	private static final Component NAME = Component.literal("传送门测试");
	private static final Component TEST = Component.literal("右键传送门框架检测").withStyle(YELLOW);
	
	@Override
	public Component getName() {
		return NAME;
	}
	
	@Override
	public void onRightClickOn(@NotNull BlockState state, @NotNull Level cl, @NotNull BlockPos pos, Direction clickedFace, @NotNull Player player, ItemStack item, CompoundTag nbt) {
		if (cl instanceof ClientLevel level) {
			long stt = System.nanoTime();
			Optional<VectorPortalShape> portal = Optional.empty();
			pos = move(pos, clickedFace, 1);
			LevelUtils.particleBlock(ParticleTypes.BUBBLE, level, pos, 1 / 4d);
			if (CONFIG.isEmpty(level.getBlockState(pos))) {
				portal = VectorPortalShape.findPortalInFrame(level, pos, CONFIG);
			}
			float ttt = (System.nanoTime() - stt) / 1E6F;
			
			if (portal.isPresent()) {
				VectorPortalShape shape = portal.get();
				level.playSound(player, pos, SoundEvents.NOTE_BLOCK_PLING.value(), SoundSource.PLAYERS);
				player.sendSystemMessage(NAME.copy().append(" 框架已找到").withStyle(ChatFormatting.GREEN));
				//viewable
				shape.getUnits().forEach(unit -> LevelUtils.particleBlock(FLAME, level, unit.bottomLeft, unit.getTopRight(), 1 / 4d));
				//colorful outputs
				player.sendSystemMessage(NbtUtils.toPrettyComponent(JsonOps.INSTANCE.convertTo(NbtOps.INSTANCE, GsonHelper.parse(shape.toJson()))));
			} else {
				level.playSound(player, pos, SoundEvents.FIRE_EXTINGUISH, SoundSource.PLAYERS);
				player.sendSystemMessage(NAME.copy().append(" 未找到正确的传送门框架").withStyle(RED));
			}
			player.sendSystemMessage(NAME.copy().append(" 共耗时: " + ttt + " ms (" + ttt / TickUtils.MS_PER_TICK + " tick)").withStyle(ChatFormatting.GOLD));
		}
	}
	
	@Override
	public void appendExtraHoverText(ItemStack stack, List<Component> tooltips, TooltipFlag isAdvanced, CompoundTag nbt) {
		tooltips.add(TEST);
	}
}
