package cn.breadnicecat.candycraftce.fabric;

import cn.breadnicecat.candycraftce.CandyCraftCE;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.impl.FabricLoaderImpl;
import net.minecraft.util.profiling.jfr.Environment;

import java.util.Objects;


public class CandyCraftCEImpl implements ModInitializer {


	private static final EnvType envType = Objects.requireNonNull(FabricLoaderImpl.InitHelper.get().getEnvironmentType());

	@SuppressWarnings("deprecation")
	public CandyCraftCEImpl() {
		CandyCraftCE.runBootstrap(envType == EnvType.CLIENT ? Environment.CLIENT : Environment.SERVER, CandyCraftCE.ModPlatform.FABRIC);
	}

	@Override
	public void onInitialize() {
		CandyCraftCE.mcSetupHooks.forEach(Runnable::run);
		CandyCraftCE.mcSetupHooks = null;
	}


}
