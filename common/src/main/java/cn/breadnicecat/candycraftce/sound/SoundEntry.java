package cn.breadnicecat.candycraftce.sound;

import cn.breadnicecat.candycraftce.utils.HolderSupplier;
import cn.breadnicecat.candycraftce.utils.SimpleEntry;
import cn.breadnicecat.candycraftce.utils.tools.LazySupplier;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
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
public class SoundEntry extends SimpleEntry<SoundEvent, SoundEvent> implements HolderSupplier<SoundEvent> {
	private final Supplier<Holder.Reference<SoundEvent>> holder;
	
	public SoundEntry(ResourceKey<SoundEvent> key, Supplier<SoundEvent> getter) {
		super(key, getter);
		holder = LazySupplier.of(() -> BuiltInRegistries.SOUND_EVENT.getHolderOrThrow(key));
	}
	
	public SoundEntry(Pair<ResourceKey<SoundEvent>, Supplier<SoundEvent>> wrapper) {
		this(wrapper.getFirst(), wrapper.getSecond());
	}
	
	@Override
	public Holder.Reference<SoundEvent> getHolder() {
		return holder.get();
	}
}
