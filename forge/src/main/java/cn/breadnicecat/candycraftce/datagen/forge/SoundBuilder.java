package cn.breadnicecat.candycraftce.datagen.forge;

import cn.breadnicecat.candycraftce.datagen.forge.providers.CSoundProvider;
import cn.breadnicecat.candycraftce.registration.sound.SoundEntry;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.data.SoundDefinition;
import org.jetbrains.annotations.NotNull;

/**
 * Created in 2023/10/14 21:04
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 * <p>
 */
public class SoundBuilder {
	private final SoundEntry sound;

	public SoundBuilder(SoundEntry sound) {
		this.sound = sound;
	}

	/**
	 * NOTE:只能调用一次,多次调用会覆盖
	 */
	public void set(@NotNull SoundDefinition definition) {
		CSoundProvider.ENTRIES.add(pro -> pro.add(sound.getSound(), definition));
	}

	public void setAsStepSound(ResourceLocation... soundLocs) {
		setWithSubtitle("subtitles.block.generic.footsteps", soundLocs);
	}

	public void setAsBreakSound(ResourceLocation... soundLocs) {
		setWithSubtitle("subtitles.block.generic.break", soundLocs);
	}

	public void setAsPlaceSound(ResourceLocation... soundLocs) {
		setWithSubtitle("subtitles.block.generic.place", soundLocs);
	}

	public void setWithSubtitle(String subtitle, ResourceLocation... soundLocs) {
		SoundDefinition definition = SoundDefinition.definition()
				.subtitle(subtitle);
		for (ResourceLocation loc : soundLocs) {
			definition.with(SoundDefinition.Sound.sound(loc, SoundDefinition.SoundType.EVENT));
		}
		this.set(definition);
	}

	public void setAsMusicDisc(ResourceLocation soundLoc) {
		this.set(SoundDefinition.definition()
				.with(SoundDefinition.Sound.sound(soundLoc, SoundDefinition.SoundType.SOUND)
						.stream()));
	}
}
