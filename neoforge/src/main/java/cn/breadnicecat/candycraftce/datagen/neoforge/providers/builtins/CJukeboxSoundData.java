package cn.breadnicecat.candycraftce.datagen.neoforge.providers.builtins;

import cn.breadnicecat.candycraftce.sound.CJukeboxSound;
import cn.breadnicecat.candycraftce.sound.CSoundEvents;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.JukeboxSong;
import net.minecraft.world.level.redstone.Redstone;

/**
 * Created in 2024/8/24 02:25
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 *
 * <p>
 **/
public class CJukeboxSoundData extends CJukeboxSound {
	public static void bootstrap(BootstrapContext<JukeboxSong> context) {
		register(context, CD_1, CSoundEvents.CD_1.getHolder(), 316, 1);
		register(context, CD_2, CSoundEvents.CD_2.getHolder(), 98, 2);
		register(context, CD_3, CSoundEvents.CD_3.getHolder(), 112, 3);
		register(context, CD_4, CSoundEvents.CD_4.getHolder(), 188, 4);
		register(context, CD_MINE, CSoundEvents.CD_o.getHolder(), 128, Redstone.SIGNAL_MAX);
		
	}
	
	private static void register(BootstrapContext<JukeboxSong> context, ResourceKey<JukeboxSong> key, Holder.Reference<SoundEvent> soundEvent, int lengthInSeconds, int comparatorOutput) {
		context.register(key, new JukeboxSong(soundEvent, Component.translatable(getTransKey(key)), (float) lengthInSeconds, comparatorOutput));
	}
	
	public static String getTransKey(ResourceKey<JukeboxSong> k) {
		return Util.makeDescriptionId("jukebox_song", k.location());
	}
}
