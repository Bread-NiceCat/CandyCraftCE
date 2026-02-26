package cn.breadnicecat.candycraftce.core.particle.particles

import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.multiplayer.ClientLevel
import net.minecraft.client.particle.Particle
import net.minecraft.client.particle.ParticleProvider
import net.minecraft.client.particle.PortalParticle
import net.minecraft.client.particle.SpriteSet
import net.minecraft.core.particles.SimpleParticleType

/**
 * Created in 2024/4/5 下午5:51
 * Project: candycraftce
 *
 * @author [Bread_NiceCat](https://github.com/BreadNiceCat)
 *
 *
 */
class CaramelPortalType : SimpleParticleType(false)

@Environment(EnvType.CLIENT)
class CaramelPortalParticle(
    level: ClientLevel,
    x: Double, y: Double, z: Double,
    xSpeed: Double, ySpeed: Double, zSpeed: Double,
) : PortalParticle(level, x, y, z, xSpeed, ySpeed, zSpeed) {
    init {
        val f = random.nextFloat() * 0.6f + 0.5f
        rCol = f * 0.9f + 0.5f
        gCol = f * 0.3f + 0.5f
        bCol = f
    }
}

@Environment(EnvType.CLIENT)
class CaramelProvider(private val sprite: SpriteSet) : ParticleProvider<SimpleParticleType> {
    override fun createParticle(
        type: SimpleParticleType, level: ClientLevel,
        x: Double, y: Double, z: Double,
        xSpeed: Double, ySpeed: Double, zSpeed: Double,
    ): Particle {
        val portalParticle: PortalParticle = CaramelPortalParticle(level, x, y, z, xSpeed, ySpeed, zSpeed)
        portalParticle.pickSprite(this.sprite)
        return portalParticle
    }
}