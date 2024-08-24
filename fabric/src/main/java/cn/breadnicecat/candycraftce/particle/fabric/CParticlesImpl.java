package cn.breadnicecat.candycraftce.particle.fabric;

import cn.breadnicecat.candycraftce.particle.ParticleEntry;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;

import java.util.HashMap;
import java.util.function.Supplier;

import static cn.breadnicecat.candycraftce.utils.ResourceUtils.prefix;
import static net.minecraft.core.registries.BuiltInRegistries.PARTICLE_TYPE;

/**
 * Created in 2024/4/5 下午5:50
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 * <p>
 */
public class CParticlesImpl {
	public static <T extends ParticleType<?>> ParticleEntry<T> register(String name, Supplier<T> factory) {
		ResourceKey<ParticleType<?>> key = ResourceKey.create(Registries.PARTICLE_TYPE, prefix(name));
		T register = Registry.register(PARTICLE_TYPE, key, factory.get());
		return new ParticleEntry<>(key, () -> register);
	}
	
	static HashMap<ParticleEntry<?>, ParticleEngine.SpriteParticleRegistration<?>> providers = new HashMap<>();
	
	public static <T extends ParticleType<O>, O extends ParticleOptions> void registerSprite(ParticleEntry<T> type, ParticleEngine.SpriteParticleRegistration<O> provider) {
		providers.put(type, provider);
	}
	
	@SuppressWarnings({"unchecked", "rawtypes"})
	public static void _register(ParticleEngine particleEngine) {
		providers.forEach((i, k) -> particleEngine.register(((ParticleType) i.get()), (ParticleEngine.SpriteParticleRegistration) k));
	}
}
