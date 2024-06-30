package cn.breadnicecat.candycraftce.forge;

import cn.breadnicecat.candycraftce.CandyCraftCE;
import cn.breadnicecat.candycraftce.CandyCraftCE.ModPlatform;
import cn.breadnicecat.candycraftce.utils.CLogUtils;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.profiling.jfr.Environment;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

import static cn.breadnicecat.candycraftce.CandyCraftCE.MOD_ID;

@Mod(MOD_ID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class CandyCraftCEImpl {
	
	public static final Dist dist = Objects.requireNonNull(FMLEnvironment.dist);
	private static final Environment env = dist == Dist.CLIENT ? Environment.CLIENT : Environment.SERVER;
	public static Map<String, Map<ResourceKey<? extends Registry<?>>, DeferredRegister<?>>> deferredRegisters = new HashMap<>();
	
	public CandyCraftCEImpl() {
		CandyCraftCE.bootstrap();
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
		deferredRegisters = null;
	}
	
	
	@SuppressWarnings("unchecked")
	public static <I> DeferredRegister<I> getOrCreate(String modid, ResourceKey<? extends Registry<I>> key) {
		return (DeferredRegister<I>) deferredRegisters.computeIfAbsent(modid, (k) -> new HashMap<>())
				.computeIfAbsent(key, (k) -> registerRegister(DeferredRegister.create(key, MOD_ID)));
	}
	
	public static void hookMinecraftSetup(Runnable runnable) {
		mcSetupHooks.add(runnable);
	}
	
	public static Environment getEnvironment() {
		return env;
	}
	
	public static ModPlatform getPlatform() {
		return ModPlatform.FORGE;
	}
	
	public static <R, S extends R> Pair<ResourceKey<S>, Supplier<S>> register(Registry<R> registry, ResourceLocation key, Supplier<S> factory) {
		DeferredRegister<R> register = getOrCreate(key.getNamespace(), registry.key());
		RegistryObject<S> object = register.register(key.getPath(), factory);
		return Pair.of(object.getKey(), object);
	}
	
	public static boolean isLoaded(String modid) {
		return ModList.get().isLoaded(modid);
	}
	
}
