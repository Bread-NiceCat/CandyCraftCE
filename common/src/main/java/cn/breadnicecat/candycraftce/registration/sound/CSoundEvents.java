package cn.breadnicecat.candycraftce.registration.sound;

import cn.breadnicecat.candycraftce.utils.CLogUtils;
import dev.architectury.injectables.annotations.ExpectPlatform;

/**
 * @author <a href="https://gitee.com/Bread_NiceCat">Bread_NiceCat</a>
 * @date 2022/12/23 10:51
 */
public final class CSoundEvents {
	static {
		CLogUtils.sign();
	}

	public static final SoundEntry CD_1 = register("cd-1");
	public static final SoundEntry CD_2 = register("cd-2");
	public static final SoundEntry CD_3 = register("cd-3");
	public static final SoundEntry CD_4 = register("cd-4");
	public static final SoundEntry CD_WWWOOOWWW = register("wwwooowww");

	public static final SoundEntry JELLY_DIG = register("jelly_dig");
	public static final SoundEntry JELLY_STEP = register("jelly_step");

	@ExpectPlatform
	private static SoundEntry register(String evtName) {
		throw new AssertionError();
	}

	public static void init() {
	}
}
