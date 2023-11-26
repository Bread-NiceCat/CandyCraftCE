package cn.breadnicecat.candycraftce.fabric;

import cn.breadnicecat.candycraftce.CandyCraftCE;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.impl.FabricLoaderImpl;
import net.minecraft.util.profiling.jfr.Environment;

import java.util.function.BooleanSupplier;

public class CandyCraftCEImpl extends CandyCraftCE implements ModInitializer {


	private static final EnvType envType = FabricLoaderImpl.InitHelper.get().getEnvironmentType();

	public CandyCraftCEImpl() {
		super();
	}

	@Override
	public void onInitialize() {
		//在构造函数中
	}

	public static Environment getEnvironment() {
		return envType == EnvType.CLIENT ? Environment.CLIENT : Environment.SERVER;
	}

	@Deprecated
	public static ModPlatform getPlatform() {
		return ModPlatform.FABRIC;
	}

	public static void hookMinecraftSetup(Runnable runnable, BooleanSupplier... predicate) {
		hookPostBootstrap(runnable, predicate);
	}

}
