package cn.breadnicecat.candycraftce.sound;

import cn.breadnicecat.candycraftce.CandyCraftCE;
import cn.breadnicecat.candycraftce.utils.CLogUtils;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import org.slf4j.Logger;

import static cn.breadnicecat.candycraftce.utils.ResourceUtils.prefix;

/**
 * @author <a href="https://gitee.com/Bread_NiceCat">Bread_NiceCat</a>
 * @date 2022/12/23 10:51
 */
public class CSoundEvents {
	
	private static final Logger LOGGER = CLogUtils.sign();
	
	public static final SoundEntry O = register("o");
	public static final SoundEntry CD_1 = register("cd-1");
	public static final SoundEntry CD_2 = register("cd-2");
	public static final SoundEntry CD_3 = register("cd-3");
	public static final SoundEntry CD_4 = register("cd-4");
	
	public static final SoundEntry JELLY_DIG = register("jelly_dig");
	public static final SoundEntry JELLY_STEP = register("jelly_step");
	
	private static SoundEntry register(String name) {
		ResourceLocation id = prefix(name);
		return new SoundEntry(CandyCraftCE.register(BuiltInRegistries.SOUND_EVENT, id, () -> SoundEvent.createVariableRangeEvent(id)));
	}
	
	public static void init() {
	}
}
