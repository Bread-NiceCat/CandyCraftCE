package cn.breadnicecat.candycraftce;

import cn.breadnicecat.candycraftce.registration.item.CItems;
import cn.breadnicecat.candycraftce.registration.sound.CSoundEvents;
import cn.breadnicecat.candycraftce.utils.CLogUtils;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.util.profiling.jfr.Environment;

public class CandyCraftCE {
	public static final String MOD_ID = "candycraftce";
	public static final String MOD_NAME = "CandyCraft CE";

	static {
		CLogUtils.sign();
		CLogUtils.getModLogger().info(MOD_ID + " running in " + getEnvironment() + " with " + getPlatform());
	}

	static boolean bootstrapped = false;
//	private static LinkedList<Runnable> hooks = new LinkedList<>();


	public static void bootstrap() {
		if (isBootstrapped()) {
			throw new IllegalStateException("bootstrapped!");
		}

		CItems.init();//->CSoundEvents,CBlocks
		CSoundEvents.init();
//		CBlocks.init();
	}


	public static boolean isBootstrapped() {
		return bootstrapped;
	}

	@ExpectPlatform
	public static Environment getEnvironment() {
		throw new AssertionError();
	}

	public static boolean isClient() {
		return getEnvironment() == Environment.CLIENT;
	}

	/**
	 * @deprecated 应当淡化平台理念
	 */
	@Deprecated
	@ExpectPlatform
	public static ModPlatform getPlatform() {
		throw new AssertionError();
	}


	public enum ModPlatform {
		FORGE, FABRIC, QUILT//Quilt兼容Fabric,不单独开发
	}
}
