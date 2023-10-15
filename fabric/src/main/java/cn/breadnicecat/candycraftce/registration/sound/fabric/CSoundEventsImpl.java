package cn.breadnicecat.candycraftce.registration.sound.fabric;

import cn.breadnicecat.candycraftce.registration.sound.SoundEntry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

import static cn.breadnicecat.candycraftce.utils.ResourceUtils.prefix;

/**
 * Created in 2023/10/14 19:03
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 * <p>
 */
public class CSoundEventsImpl {
	public static SoundEntry register(String evtName) {
		ResourceLocation prefix = prefix(evtName);
		SoundEvent event = Registry.register(BuiltInRegistries.SOUND_EVENT, prefix, SoundEvent.createVariableRangeEvent(prefix));
		return new SoundEntry(prefix, () -> event);
	}
}
