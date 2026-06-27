package cn.breadnicecat.candycraftce.core.particle

import cn.breadnicecat.candycraftce.core.particle.particles.CaramelPortalType
import cn.breadnicecat.candycraftce.core.particle.particles.CaramelProvider
import cn.breadnicecat.candycraftce.utils.CLogUtils
import cn.breadnicecat.candycraftce.utils.CUtils.modLoc
import cn.breadnicecat.candycraftce.utils.CUtils.register
import cn.breadnicecat.candycraftce.utils.ifClient
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry
import net.minecraft.core.particles.ParticleType
import net.minecraft.core.registries.BuiltInRegistries

/**
 * Created in 2024/4/5 下午5:50
 * Project: candycraftce
 *
 * @author [Bread_NiceCat](https://github.com/BreadNiceCat)
 *
 *
 */
object CParticles {
    init {
        CLogUtils.sign()
    }

    var caramel_portal_particle_type = register("caramel_portal_particle", CaramelPortalType())

    private fun <P : ParticleType<*>> register(id: String, type: P): P {
        return BuiltInRegistries.PARTICLE_TYPE.register(id.modLoc(), type)
    }

    init {
        ifClient {
            val instance = ParticleFactoryRegistry.getInstance()
            instance.register(caramel_portal_particle_type, ::CaramelProvider)
        }
    }

}
