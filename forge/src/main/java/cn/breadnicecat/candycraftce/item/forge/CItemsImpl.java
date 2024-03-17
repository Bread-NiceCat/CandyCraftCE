package cn.breadnicecat.candycraftce.item.forge;

import cn.breadnicecat.candycraftce.sound.SoundEntry;
import cn.breadnicecat.candycraftce.utils.TickUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.RecordItem;
import org.jetbrains.annotations.NotNull;

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

	public static RecordItem _record_wwwooowww(int analog, SoundEntry evt, Item.Properties prop, int lengthInSeconds, String nameInGame, String musicName) {
		MutableComponent name = Component.literal(nameInGame);
		MutableComponent music = Component.literal(musicName);

		return new RecordItem(analog, evt, prop, (int) (lengthInSeconds * TickUtils.SEC2TICK)) {
			@Override
			public @NotNull MutableComponent getDisplayName() {
				return music;
			}

			@Override
			public @NotNull Component getName(@NotNull ItemStack stack) {
				return name;
			}
		};
	}
}
