package cn.breadnicecat.candycraftce.mixin;

import net.minecraft.world.level.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

/**
 * Created in 2024/7/11 上午11:25
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 *
 * <p>
 **/
@Mixin(Biome.class)
public interface BiomeAccessor {
	@Accessor
	Biome.ClimateSettings getClimateSettings();
}
