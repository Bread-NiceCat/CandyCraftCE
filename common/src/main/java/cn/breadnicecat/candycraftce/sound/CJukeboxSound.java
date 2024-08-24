package cn.breadnicecat.candycraftce.sound;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.JukeboxSong;

import static cn.breadnicecat.candycraftce.utils.ResourceUtils.prefix;

/**
 * Created in 2024/8/24 01:19
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 *
 * <p>
 **/
public class CJukeboxSound {
	public static final ResourceKey<JukeboxSong> CD_MINE = create("cd_mine");
	public static final ResourceKey<JukeboxSong> CD_1 = create("cd_1");
	public static final ResourceKey<JukeboxSong> CD_2 = create("cd_2");
	public static final ResourceKey<JukeboxSong> CD_3 = create("cd_3");
	public static final ResourceKey<JukeboxSong> CD_4 = create("cd_4");
	
	private static ResourceKey<JukeboxSong> create(String name) {
		return ResourceKey.create(Registries.JUKEBOX_SONG, prefix(name));
	}
	
}
