package cn.breadnicecat.candycraftce.datagen.neoforge.providers;

import cn.breadnicecat.candycraftce.CandyCraftCE;
import cn.breadnicecat.candycraftce.sound.CSoundEvents;
import cn.breadnicecat.candycraftce.sound.SoundEntry;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.SoundDefinition;
import net.neoforged.neoforge.common.data.SoundDefinitionsProvider;
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
		base(CD_1, CD_1.getId());
		base(CD_2, CD_2.getId());
		base(CD_3, CD_3.getId());
		base(CD_4, CD_4.getId());
		base(O, O.getId());
		base(CSoundEvents.JELLY_STEP, prefix("jelly1"), prefix("jelly2"), prefix("jelly3"), prefix("jelly4"));
		base(CSoundEvents.JELLY_DIG, prefix("jelly1"), prefix("jelly2"));
	}
	
	public SoundDefinition base(SoundEntry sound, ResourceLocation... soundLocs) {
		SoundDefinition definition = SoundDefinition.definition();
		for (ResourceLocation loc : soundLocs) {
			definition.with(SoundDefinition.Sound.sound(loc, SoundDefinition.SoundType.SOUND));
		}
		add(sound, definition);
		return definition;
	}
	
	public void add(SoundEntry sound, @NotNull SoundDefinition definition) {
		add(sound.get(), definition);
	}
	
	
}
