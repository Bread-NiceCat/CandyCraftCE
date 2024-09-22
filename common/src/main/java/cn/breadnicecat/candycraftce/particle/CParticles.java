package cn.breadnicecat.candycraftce.particle;

import cn.breadnicecat.candycraftce.CandyCraftCE;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;

import java.util.function.Supplier;

import static cn.breadnicecat.candycraftce.utils.CommonUtils.impossibleCode;
import static cn.breadnicecat.candycraftce.utils.ResourceUtils.prefix;

/**
 * Created in 2024/4/5 下午5:50
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 * <p>
 */
public class CParticles {
	public static ParticleEntry<SimpleParticleType> CARAMEL_PORTAL_PARTICLE_TYPE = create("caramel_portal_particle", false, CaramelPortalParticle.CaramelProvider::new);
	
	private static ParticleEntry<SimpleParticleType> create(String name, boolean overrideLimiter, ParticleEngine.SpriteParticleRegistration<SimpleParticleType> provider) {
		ParticleEntry<SimpleParticleType> entry = register(name, () -> new SimpleParticleType(overrideLimiter));
		registerSprite(entry, provider);
		return entry;
	}
	
	@ExpectPlatform
	private static <T extends ParticleType<O>, O extends ParticleOptions> void registerSprite(ParticleEntry<T> type, ParticleEngine.SpriteParticleRegistration<O> provider) {
		impossibleCode();
	}
	
	private static <T extends ParticleType<?>> ParticleEntry<T> register(String name, Supplier<T> factory) {
		return CandyCraftCE.register(BuiltInRegistries.PARTICLE_TYPE, prefix(name), factory)
				.as(ParticleEntry::new);
	}
	
	public static void init() {
	}
}
