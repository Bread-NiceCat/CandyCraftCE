package cn.breadnicecat.candycraftce.particle.particles;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.PortalParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;

/**
 * Created in 2024/4/5 下午5:51
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 * <p>
 */
@Environment(EnvType.CLIENT)
public class CaramelPortalParticle extends PortalParticle {
	protected CaramelPortalParticle(ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
		super(level, x, y, z, xSpeed, ySpeed, zSpeed);
		float f = random.nextFloat() * 0.6f + 0.5f;
		rCol = f * 0.9f + 0.5f;
		gCol = f * 0.3f + 0.5f;
		bCol = f;
	}
	
	public static class CaramelProvider implements ParticleProvider<SimpleParticleType> {
		private final SpriteSet sprite;
		
		public CaramelProvider(SpriteSet sprite) {
			this.sprite = sprite;
		}
		
		public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
			PortalParticle portalParticle = new CaramelPortalParticle(level, x, y, z, xSpeed, ySpeed, zSpeed);
			portalParticle.pickSprite(this.sprite);
			return portalParticle;
		}
	}
}
