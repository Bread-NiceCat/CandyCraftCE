package cn.breadnicecat.candycraftce.forge;

import cn.breadnicecat.candycraftce.CandyCraftCE;
import cn.breadnicecat.candycraftce.CandyCraftCE.ModPlatform;
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

import java.util.Objects;

import static cn.breadnicecat.candycraftce.CandyCraftCE.MOD_ID;
import static cn.breadnicecat.candycraftce.CandyCraftCE.mcSetupHooks;

@Mod(MOD_ID)
public class CandyCraftCEImpl {

	public static final Dist dist = Objects.requireNonNull(FMLEnvironment.dist);

	public CandyCraftCEImpl() {
		CandyCraftCE.runBootstrap(dist == Dist.CLIENT ? Environment.CLIENT : Environment.SERVER, ModPlatform.FORGE);
	}


	public static <I> DeferredRegister<I> registerRegister(DeferredRegister<I> register) {
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

	@SubscribeEvent
	public static void onFMLCommonSetup(FMLCommonSetupEvent setup) {
		CLogUtils.getModLogger().info("hit McSetupHooks");
		mcSetupHooks.forEach(Runnable::run);
		mcSetupHooks = null;
	}
}
