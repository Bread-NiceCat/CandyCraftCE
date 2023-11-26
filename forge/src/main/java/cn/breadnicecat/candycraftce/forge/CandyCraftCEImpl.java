package cn.breadnicecat.candycraftce.forge;

import cn.breadnicecat.candycraftce.CandyCraftCE;
import cn.breadnicecat.candycraftce.utils.CLogUtils;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.profiling.jfr.Environment;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.LinkedList;
import java.util.function.BooleanSupplier;

import static cn.breadnicecat.candycraftce.CandyCraftCE.MOD_ID;
import static cn.breadnicecat.candycraftce.utils.CommonUtils.conditionalRunnable;

@Mod(MOD_ID)
public class CandyCraftCEImpl extends CandyCraftCE {

	public static final Dist dist = FMLEnvironment.dist;

	public CandyCraftCEImpl() {
	}


	private static <I> DeferredRegister<I> registerRegister(DeferredRegister<I> register) {
		register.register(FMLJavaModLoadingContext.get().getModEventBus());
		return register;
	}

	public static <I> DeferredRegister<I> createRegister(IForgeRegistry<I> type) {
		return registerRegister(DeferredRegister.create(type, MOD_ID));
	}

	public static <I> DeferredRegister<I> createRegister(ResourceKey<? extends Registry<I>> key) {
		return registerRegister(DeferredRegister.create(key, MOD_ID));
	}

	public static Environment getEnvironment() {
		return dist == Dist.CLIENT ? Environment.CLIENT : Environment.SERVER;
	}

	@Deprecated
	public static ModPlatform getPlatform() {
		return ModPlatform.FORGE;
	}

	public static LinkedList<Runnable> mcSetupHooks = new LinkedList<>();

	@SubscribeEvent
	public static void onFMLCommonSetup(FMLCommonSetupEvent setup) {
		CLogUtils.getModLogger().info("hit McSetupHooks");
		mcSetupHooks.forEach(Runnable::run);
		mcSetupHooks = null;
	}

	public static void hookMinecraftSetup(Runnable runnable, BooleanSupplier... predicate) {
		mcSetupHooks.add(conditionalRunnable(runnable, predicate));
	}
}
