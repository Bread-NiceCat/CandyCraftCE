package cn.breadnicecat.candycraftce.item.fabric;

import cn.breadnicecat.candycraftce.sound.SoundEntry;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.RecordItem;
import org.jetbrains.annotations.NotNull;

/**
 * Created in 2023/12/29 23:54
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 */
public class CItemsImpl {
	public static RecordItem _recordItem(int analog, SoundEntry evt, Item.Properties prop, int lengthInSeconds) {
		return new RecordItem(analog, evt.get(), prop, lengthInSeconds);
	}


	public static RecordItem _record_wwwooowww(int analog, SoundEntry evt, Item.Properties prop, int lengthInSeconds, String nameInGame, String musicName) {
		MutableComponent name = Component.literal(nameInGame);
		MutableComponent music = Component.literal(musicName);
		return new RecordItem(analog, evt.get(), prop, lengthInSeconds) {
			@Override
			public @NotNull MutableComponent getDisplayName() {
				return music;
			}

			@Override
			public @NotNull Component getName(ItemStack stack) {
				return name;
			}
		};
	}
}
