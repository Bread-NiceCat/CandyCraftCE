package cn.breadnicecat.candycraftce.sound.forge;

import cn.breadnicecat.candycraftce.forge.CandyCraftCEImpl;
import cn.breadnicecat.candycraftce.sound.SoundEntry;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static cn.breadnicecat.candycraftce.utils.ResourceUtils.prefix;

/**
 * Created in 2023/10/14 19:12
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 */
public class CSoundEventsImpl {

	private static final DeferredRegister<SoundEvent> REGISTER = CandyCraftCEImpl.createRegister(ForgeRegistries.SOUND_EVENTS);


	public static SoundEntry register(String evtName) {
		RegistryObject<SoundEvent> object = REGISTER.register(evtName, () -> SoundEvent.createVariableRangeEvent(prefix(evtName)));
		return new SoundEntry(object.getId(), object);
	}
}
