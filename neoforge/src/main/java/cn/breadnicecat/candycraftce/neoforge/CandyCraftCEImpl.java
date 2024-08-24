package cn.breadnicecat.candycraftce.neoforge;

import cn.breadnicecat.candycraftce.CandyCraftCE;
import cn.breadnicecat.candycraftce.CandyCraftCE.ModPlatform;
import cn.breadnicecat.candycraftce.utils.CLogUtils;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.profiling.jfr.Environment;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

import static cn.breadnicecat.candycraftce.CandyCraftCE.MOD_ID;

@Mod(MOD_ID)
@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class CandyCraftCEImpl {
	
	public static final Dist dist = Objects.requireNonNull(FMLEnvironment.dist);
	private static final Environment env = dist == Dist.CLIENT ? Environment.CLIENT : Environment.SERVER;
	public static Map<String, Map<ResourceKey<? extends Registry<?>>, DeferredRegister<?>>> deferredRegisters = new HashMap<>();
	private static IEventBus bus;
	
	public CandyCraftCEImpl(IEventBus bus) {
		CandyCraftCEImpl.bus = bus;
		CandyCraftCE.bootstrap();
	}
	
	
	public static <I> DeferredRegister<I> registerRegister(DeferredRegister<I> register) {
		register.register(bus);
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
		return ModPlatform.NEOFORGE;
	}
	
	public static <R, S extends R> Pair<ResourceKey<R>, Supplier<S>> register(Registry<R> registry, ResourceLocation key, Supplier<S> factory) {
		DeferredHolder<R, S> object = (DeferredHolder<R, S>) registerForHolder(registry, key, factory);
		return Pair.of(object.getKey(), object);
	}
	
	public static <R, S extends R> Holder<R> registerForHolder(Registry<R> registry, ResourceLocation key, Supplier<S> factory) {
		DeferredRegister<R> register = getOrCreate(key.getNamespace(), registry.key());
		DeferredHolder<R, S> object = register.register(key.getPath(), factory);
		return object;
	}
	
	public static boolean isLoaded(String modid) {
		return ModList.get().isLoaded(modid);
	}
	
}
