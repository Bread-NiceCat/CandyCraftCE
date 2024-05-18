package cn.breadnicecat.candycraftce.datagen.forge.providers;

import cn.breadnicecat.candycraftce.CandyCraftCE;
import cn.breadnicecat.candycraftce.sound.CSoundEvents;
import cn.breadnicecat.candycraftce.sound.SoundEntry;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.SoundDefinition;
import net.minecraftforge.common.data.SoundDefinitionsProvider;
import org.jetbrains.annotations.NotNull;

import static cn.breadnicecat.candycraftce.sound.CSoundEvents.*;
import static cn.breadnicecat.candycraftce.utils.ResourceUtils.prefix;

/**
 * Created in 2023/10/14 21:07
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 */
public class CSoundProvider extends SoundDefinitionsProvider {
	
	public CSoundProvider(PackOutput output, ExistingFileHelper helper) {
		super(output, CandyCraftCE.MOD_ID, helper);
	}
	
	@Override
	public void registerSounds() {
		asMusicDisc(CD_1, CD_1.getId());
		asMusicDisc(CD_2, CD_2.getId());
		asMusicDisc(CD_3, CD_3.getId());
		asMusicDisc(CD_4, CD_4.getId());
		asMusicDisc(CD_o, CD_o.getId());
		simple(CSoundEvents.JELLY_STEP, prefix("jelly1"), prefix("jelly2"), prefix("jelly3"), prefix("jelly4"));
		simple(CSoundEvents.JELLY_DIG, prefix("jelly1"), prefix("jelly2"));
	}
	
	public void asStepSound(SoundEntry sound, ResourceLocation... soundLocs) {
		withSubtitle(sound, "subtitles.block.generic.footsteps", soundLocs);
	}
	
	public void asBreakSound(SoundEntry sound, ResourceLocation... soundLocs) {
		withSubtitle(sound, "subtitles.block.generic.break", soundLocs);
	}
	
	public void asPlaceSound(SoundEntry sound, ResourceLocation... soundLocs) {
		withSubtitle(sound, "subtitles.block.generic.place", soundLocs);
	}
	
	public void asMusicDisc(SoundEntry sound, ResourceLocation soundLoc) {
		add(sound, SoundDefinition.definition()
				.with(SoundDefinition.Sound.sound(soundLoc, SoundDefinition.SoundType.SOUND).stream())
		);
	}
	
	public void withSubtitle(SoundEntry sound, String subtitle, ResourceLocation... soundLocs) {
		SoundDefinition definition = base(soundLocs).subtitle(subtitle);
		add(sound, definition);
	}
	
	public void simple(SoundEntry sound, ResourceLocation... soundLocs) {
		add(sound, base(soundLocs));
	}
	
	public SoundDefinition base(ResourceLocation... soundLocs) {
		SoundDefinition definition = SoundDefinition.definition();
		for (ResourceLocation loc : soundLocs) {
			definition.with(SoundDefinition.Sound.sound(loc, SoundDefinition.SoundType.SOUND));
		}
		return definition;
	}
	
	public void add(SoundEntry sound, @NotNull SoundDefinition definition) {
		add(sound.get(), definition);
	}
	
	
}
