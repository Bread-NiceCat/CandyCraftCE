package cn.breadnicecat.candycraftce.item.forge;

import cn.breadnicecat.candycraftce.sound.SoundEntry;
import cn.breadnicecat.candycraftce.utils.TickUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.RecordItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Created in 2023/12/29 23:55
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 */
public class CItemsImpl {
	public static RecordItem _recordItem(int analog, @NotNull SoundEntry evt, Item.Properties prop, int lengthInSeconds) {
		return new RecordItem(analog, evt, prop, (int) (lengthInSeconds * TickUtils.SEC2TICK));
	}
	
	public static RecordItem _record_o(int analog, SoundEntry evt, Item.Properties prop, int lengthInSeconds) {
		MutableComponent name = Component.literal("Bread_NiceCat's Secret Record");
		MutableComponent music = Component.literal("J.D.K. - 鉄橋を越えて");
		MutableComponent appendix = Component.literal("To our lost childhood.").withStyle(ChatFormatting.AQUA);
		
		return new RecordItem(analog, evt, prop, (int) (lengthInSeconds * TickUtils.SEC2TICK)) {
			@Override
			public @NotNull MutableComponent getDisplayName() {
				return music;
			}
			
			@Override
			public @NotNull Component getName(@NotNull ItemStack stack) {
				return name;
			}
			
			@Override
			public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, @NotNull List<Component> tooltipComponents, @NotNull TooltipFlag isAdvanced) {
				super.appendHoverText(stack, level, tooltipComponents, isAdvanced);
				tooltipComponents.add(appendix);
			}
		};
	}
}
