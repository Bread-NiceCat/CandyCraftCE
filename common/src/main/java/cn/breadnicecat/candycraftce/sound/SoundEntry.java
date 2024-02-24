package cn.breadnicecat.candycraftce.sound;

import cn.breadnicecat.candycraftce.utils.RegistryEntry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

import java.util.function.Supplier;

/**
 * Created in 2023/10/14 19:00
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 */
public abstract class SoundEntry extends RegistryEntry implements Supplier<SoundEvent> {

	public SoundEntry(ResourceLocation name) {
		super(name);
	}
}
