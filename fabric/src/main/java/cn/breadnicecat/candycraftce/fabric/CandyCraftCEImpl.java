package cn.breadnicecat.candycraftce.fabric;

import cn.breadnicecat.candycraftce.CandyCraftCE;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.impl.FabricLoaderImpl;
import net.minecraft.util.profiling.jfr.Environment;

public class CandyCraftCEImpl extends CandyCraftCE implements ModInitializer {


	private static final EnvType envType = FabricLoaderImpl.InitHelper.get().getEnvironmentType();

	public CandyCraftCEImpl() {
		super();
	}

	@Override
	public void onInitialize() {
		mcSetupHooks.forEach(Runnable::run);
		mcSetupHooks = null;
	}

	public static Environment getEnvironment() {
		return envType == EnvType.CLIENT ? Environment.CLIENT : Environment.SERVER;
	}

	@Deprecated
	public static ModPlatform getPlatform() {
		return ModPlatform.FABRIC;
	}


}
