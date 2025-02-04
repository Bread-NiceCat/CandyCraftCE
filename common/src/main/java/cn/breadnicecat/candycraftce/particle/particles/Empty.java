package cn.breadnicecat.candycraftce.particle.particles;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.BreakingItemParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.item.Items;

/**
 * Created in 2025/2/2 12:42
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 *
 * <p>
 **/
public class Empty extends BreakingItemParticle {
	protected Empty(ClientLevel level, double x, double y, double z) {
		super(level, x, y, z, Items.AIR.getDefaultInstance());
	}
	
	@Environment(EnvType.CLIENT)
	public static class Provider implements ParticleProvider<SimpleParticleType> {
		public Provider(SpriteSet set) {
		}
		
		public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
			return new Empty(level, x, y, z);
		}
	}
}
