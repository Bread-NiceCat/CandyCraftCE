package cn.breadnicecat.candycraftce.datagen.forge.providers;

import cn.breadnicecat.candycraftce.CandyCraftCE;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.SoundDefinition;
import net.minecraftforge.common.data.SoundDefinitionsProvider;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashSet;
import java.util.function.Consumer;

/**
 * Created in 2023/10/14 21:07
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 * <p>
 */
public class CSoundProvider extends SoundDefinitionsProvider {
	public static final LinkedHashSet<Consumer<CSoundProvider>> ENTRIES = new LinkedHashSet<>();

	public CSoundProvider(PackOutput output, ExistingFileHelper helper) {
		super(output, CandyCraftCE.MOD_ID, helper);
	}

	@Override
	public void registerSounds() {
		ENTRIES.forEach((k) -> k.accept(this));
	}

	@Override
	public void add(@NotNull SoundEvent soundEvent, @NotNull SoundDefinition definition) {
		super.add(soundEvent, definition);
	}

	@Override
	public void add(@NotNull ResourceLocation soundEvent, @NotNull SoundDefinition definition) {
		super.add(soundEvent, definition);
	}

}
