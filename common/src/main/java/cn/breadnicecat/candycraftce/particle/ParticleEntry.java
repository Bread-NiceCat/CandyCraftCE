package cn.breadnicecat.candycraftce.particle;

import cn.breadnicecat.candycraftce.utils.SimpleEntry;
import cn.breadnicecat.candycraftce.utils.WrappedEntry;
import net.minecraft.core.particles.ParticleType;

/**
 * Created in 2024/4/21 上午8:52
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 * <p>
 */
public class ParticleEntry<T extends ParticleType<?>> extends SimpleEntry<ParticleType<?>, T> {
	
	public ParticleEntry(WrappedEntry<ParticleType<?>, T> wrapper) {
		super(wrapper);
	}
}
