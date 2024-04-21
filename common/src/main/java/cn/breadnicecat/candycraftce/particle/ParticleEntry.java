package cn.breadnicecat.candycraftce.particle;

import cn.breadnicecat.candycraftce.utils.SimpleEntry;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.resources.ResourceKey;

import java.util.function.Supplier;

/**
 * Created in 2024/4/21 上午8:52
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 * <p>
 */
public class ParticleEntry<T extends ParticleType<?>> extends SimpleEntry<ParticleType<?>, T> {
	public ParticleEntry(ResourceKey<ParticleType<?>> key, Supplier<T> getter) {
		super(key, getter);
	}

	public ParticleEntry(Pair<ResourceKey<ParticleType<?>>, Supplier<T>> wrapper) {
		super(wrapper);
	}
}
