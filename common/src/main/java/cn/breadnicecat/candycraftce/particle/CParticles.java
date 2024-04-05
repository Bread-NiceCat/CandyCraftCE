package cn.breadnicecat.candycraftce.particle;

import cn.breadnicecat.candycraftce.EngineFeatures;
import cn.breadnicecat.candycraftce.utils.SimpleEntry;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;

import java.util.function.Supplier;

import static cn.breadnicecat.candycraftce.utils.ResourceUtils.prefix;

/**
 * Created in 2024/4/5 下午5:50
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 * <p>
 */
public class CParticles {
	public static SimpleEntry<SimpleParticleType> CARAMEL_PORTAL_PARTICLE_TYPE = register("caramel_portal_particle", false);


	private static SimpleEntry<SimpleParticleType> register(String name, boolean overrideLimiter) {
		return register(name, () -> new SimpleParticleType(overrideLimiter));
	}

	private static <T extends ParticleType<?>> SimpleEntry<T> register(String name, Supplier<T> factory) {
		return EngineFeatures.get().register(BuiltInRegistries.PARTICLE_TYPE, prefix(name), factory);
	}

	public static <T extends ParticleOptions> void registerProviders(ParticleEngine engine) {
		register(engine, CARAMEL_PORTAL_PARTICLE_TYPE, CaramelPortalParticle.CaramelProvider::new);
	}

	private static <O extends ParticleOptions> void register(ParticleEngine engine, SimpleEntry<? extends ParticleType<O>> type, ParticleEngine.SpriteParticleRegistration<O> provider) {
		engine.register(type.get(), provider);
	}

	public static void init() {
	}
}
