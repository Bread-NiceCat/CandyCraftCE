package cn.breadnicecat.candycraftce.particle.neoforge;

import cn.breadnicecat.candycraftce.neoforge.CandyCraftCEImpl;
import cn.breadnicecat.candycraftce.particle.ParticleEntry;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;

import java.util.HashMap;
import java.util.function.Supplier;

import static cn.breadnicecat.candycraftce.utils.ResourceUtils.prefix;

/**
 * Created in 2024/4/5 下午5:50
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 * <p>
 */
@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class CParticlesImpl {
	static HashMap<ParticleEntry<?>, ParticleEngine.SpriteParticleRegistration<?>> providers = new HashMap<>();
	
	public static <T extends ParticleType<O>, O extends ParticleOptions> void registerSprite(ParticleEntry<T> type, ParticleEngine.SpriteParticleRegistration<O> provider) {
		providers.put(type, provider);
	}
	
	
	@SuppressWarnings({"rawtypes", "unchecked"})
	@SubscribeEvent
	public static void onRegisterParticleProviders(RegisterParticleProvidersEvent event) {
		providers.forEach((i, k) -> event.registerSpriteSet(((ParticleType) i.get()), (ParticleEngine.SpriteParticleRegistration) k));
	}
	
	public static <T extends ParticleType<?>> ParticleEntry<T> register(String name, Supplier<T> factory) {
		Pair<ResourceKey<ParticleType<?>>, Supplier<T>> pair = CandyCraftCEImpl.register(BuiltInRegistries.PARTICLE_TYPE, prefix(name), factory);
		return new ParticleEntry<>(pair);
	}
	
	
}
