package cn.breadnicecat.candycraftce.forge;

import cn.breadnicecat.candycraftce.CandyCraftCE;
import cn.breadnicecat.candycraftce.CandyCraftCE.ModPlatform;
import cn.breadnicecat.candycraftce.block.forge.CBlocksImpl;
import cn.breadnicecat.candycraftce.utils.CLogUtils;
import net.minecraft.util.profiling.jfr.Environment;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.registries.DeferredRegister;

import java.util.LinkedList;
import java.util.Objects;

import static cn.breadnicecat.candycraftce.CandyCraftCE.MOD_ID;

@Mod(MOD_ID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class CandyCraftCEImpl {

	public static final Dist dist = Objects.requireNonNull(FMLEnvironment.dist);

	public CandyCraftCEImpl() {
		CandyCraftCE.bootstrap(dist == Dist.CLIENT ? Environment.CLIENT : Environment.SERVER,
				ModPlatform.FORGE,
				new ForgeFeatures()
		);
	}


	public static <I> DeferredRegister<I> registerRegister(DeferredRegister<I> register) {
		register.register(FMLJavaModLoadingContext.get().getModEventBus());
		return register;
	}

	public static LinkedList<Runnable> mcSetupHooks = new LinkedList<>();

	@SubscribeEvent
	public static void onFMLCommonSetup(FMLCommonSetupEvent setup) {
		CLogUtils.getModLogger().info("onFMLCommonSetup");
		mcSetupHooks.forEach(Runnable::run);
		mcSetupHooks = null;
		CBlocksImpl.modifyRendererType(setup);
	}

	public static void hookMinecraftSetup(Runnable runnable) {
		mcSetupHooks.add(runnable);
	}
}
