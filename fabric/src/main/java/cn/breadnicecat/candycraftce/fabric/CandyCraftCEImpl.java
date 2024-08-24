package cn.breadnicecat.candycraftce.fabric;

import cn.breadnicecat.candycraftce.CandyCraftCE;
import com.mojang.datafixers.util.Pair;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.impl.FabricLoaderImpl;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.profiling.jfr.Environment;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.Objects;
import java.util.function.Supplier;


public class CandyCraftCEImpl implements ModInitializer {
	
	
	private static final EnvType envType = Objects.requireNonNull(FabricLoaderImpl.InitHelper.get().getEnvironmentType());
	private static final Environment env = envType == EnvType.CLIENT ? Environment.CLIENT : Environment.SERVER;
	
	private static LinkedList<Runnable> mcSetupHooks = new LinkedList<>();
	
	@Override
	public void onInitialize() {
		CandyCraftCE.bootstrap();
		mcSetupHooks.forEach(Runnable::run);
		mcSetupHooks = null;
	}
	
	
	public static void hookMinecraftSetup(Runnable runnable) {
		mcSetupHooks.add(runnable);
	}
	
	public static Environment getEnvironment() {
		return env;
	}
	
	public static CandyCraftCE.ModPlatform getPlatform() {
		return CandyCraftCE.ModPlatform.FABRIC;
	}
	
	public static <R, S extends R> Pair<ResourceKey<R>, Supplier<S>> register(Registry<R> registry, ResourceLocation key, Supplier<S> factory) {
		ResourceKey<R> k = ResourceKey.create(registry.key(), key);
		S s = Registry.register(registry, k, factory.get());
		return Pair.of(k, () -> s);
	}
	
	public static boolean isLoaded(String modid) {
		return FabricLoaderImpl.INSTANCE.isModLoaded(modid);
	}
	
	
	public static <R, S extends R> @NotNull Holder<R> registerForHolder(Registry<R> registry, ResourceLocation key, @NotNull Supplier<S> factory) {
		return Registry.registerForHolder(registry, key, factory.get());
		
	}
	
	
}
