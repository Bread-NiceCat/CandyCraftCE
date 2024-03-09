package cn.breadnicecat.candycraftce.fabric;

import cn.breadnicecat.candycraftce.CandyCraftCE;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.impl.FabricLoaderImpl;
import net.minecraft.util.profiling.jfr.Environment;

import java.util.LinkedList;
import java.util.Objects;


public class CandyCraftCEImpl implements ModInitializer {


	private static final EnvType envType = Objects.requireNonNull(FabricLoaderImpl.InitHelper.get().getEnvironmentType());

	public CandyCraftCEImpl() {
		CandyCraftCE.bootstrap(envType == EnvType.CLIENT ? Environment.CLIENT : Environment.SERVER,
				CandyCraftCE.ModPlatform.FABRIC,
				new FabricFeatures()
		);
	}

	private static LinkedList<Runnable> mcSetupHooks = new LinkedList<>();

	@Override
	public void onInitialize() {
		mcSetupHooks.forEach(Runnable::run);
		mcSetupHooks = null;
	}

	public static void hookMinecraftSetup(Runnable runnable) {
		mcSetupHooks.add(runnable);
	}


}
