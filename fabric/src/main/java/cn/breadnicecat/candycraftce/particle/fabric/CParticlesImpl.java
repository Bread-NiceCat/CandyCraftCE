package cn.breadnicecat.candycraftce.particle.fabric;

import cn.breadnicecat.candycraftce.particle.ParticleEntry;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;

import java.util.HashMap;

/**
 * Created in 2024/4/5 下午5:50
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 * <p>
 */
public class CParticlesImpl {
	
	static HashMap<ParticleEntry<?>, ParticleEngine.SpriteParticleRegistration<?>> providers = new HashMap<>();
	
	public static <T extends ParticleType<O>, O extends ParticleOptions> void registerSprite(ParticleEntry<T> type, ParticleEngine.SpriteParticleRegistration<O> provider) {
		providers.put(type, provider);
	}
	
	@SuppressWarnings({"unchecked", "rawtypes"})
	public static void _register(ParticleEngine particleEngine) {
		providers.forEach((i, k) -> particleEngine.register(((ParticleType) i.get()), (ParticleEngine.SpriteParticleRegistration) k));
	}
}
