package cn.breadnicecat.candycraftce.fabric;

import cn.breadnicecat.candycraftce.CandyCraftCE;
import cn.breadnicecat.candycraftce.utils.CLogUtils;
import cn.breadnicecat.candycraftce.utils.SimpleEntry;
import net.fabricmc.api.ModInitializer;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;

import java.util.LinkedList;
import java.util.function.Supplier;


public class CandyCraftCEImpl implements ModInitializer {
	
	private static final Logger log = CLogUtils.sign();
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
	
	public static <R, S extends R> SimpleEntry<R, S> register(Registry<R> registry, ResourceLocation key, Supplier<S> factory) {
		ResourceKey<R> k = ResourceKey.create(registry.key(), key);
		S v = factory.get();
		Holder.Reference<R> holder = Registry.registerForHolder(registry, k, v);
		return new SimpleEntry<>(k, () -> v, holder);
	}
	
	
}
