package cn.breadnicecat.candycraftce.registration.item.fabric;

import cn.breadnicecat.candycraftce.registration.sound.SoundEntry;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.RecordItem;
import org.jetbrains.annotations.NotNull;

/**
 * Created in 2023/10/14 18:50
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 * <p>
 */
public class CItemsImpl {


	public static RecordItem _recordItem(int analog, SoundEntry evt, Item.Properties prop, int lengthInSeconds) {
		return new RecordItem(analog, evt.getSound(), prop, lengthInSeconds);
	}


	public static RecordItem _record_wwwooowww(int analog, SoundEntry evt, Item.Properties prop, int lengthInSeconds, String nameInGame, String musicName) {
		MutableComponent name = Component.literal(nameInGame);
		MutableComponent music = Component.literal(musicName);

		return new RecordItem(analog, evt.getSound(), prop, lengthInSeconds) {
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
