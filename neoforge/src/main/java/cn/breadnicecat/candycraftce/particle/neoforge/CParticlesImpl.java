package cn.breadnicecat.candycraftce.particle.neoforge;

import cn.breadnicecat.candycraftce.particle.ParticleEntry;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;

import java.util.HashMap;

/**
 * Created in 2024/4/5 下午5:50
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 * <p>
 */
@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
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
	
}
