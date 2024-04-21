package cn.breadnicecat.candycraftce.sound;

import cn.breadnicecat.candycraftce.utils.SimpleEntry;
import com.mojang.datafixers.util.Pair;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;

import java.util.function.Supplier;

/**
 * Created in 2023/10/14 19:00
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 */
public class SoundEntry extends SimpleEntry<SoundEvent, SoundEvent> {
	public SoundEntry(ResourceKey<SoundEvent> key, Supplier<SoundEvent> getter) {
		super(key, getter);
	}

	public SoundEntry(Pair<ResourceKey<SoundEvent>, Supplier<SoundEvent>> wrapper) {
		super(wrapper);
	}
}
