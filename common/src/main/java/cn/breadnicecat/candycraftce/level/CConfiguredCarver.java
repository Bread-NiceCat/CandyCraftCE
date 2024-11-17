package cn.breadnicecat.candycraftce.level;

import cn.breadnicecat.candycraftce.block.CBlockTags;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.valueproviders.TrapezoidFloat;
import net.minecraft.util.valueproviders.UniformFloat;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.carver.*;
import net.minecraft.world.level.levelgen.carver.CanyonCarverConfiguration.CanyonShapeConfiguration;
import net.minecraft.world.level.levelgen.heightproviders.UniformHeight;

import static cn.breadnicecat.candycraftce.utils.ResourceUtils.prefix;
import static net.minecraft.util.valueproviders.ConstantFloat.of;
import static net.minecraft.world.level.levelgen.VerticalAnchor.aboveBottom;
import static net.minecraft.world.level.levelgen.VerticalAnchor.absolute;

/**
 * Created in 2024/10/13 12:27
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 *
 * <p>
 **/
public class CConfiguredCarver {
	public static final ResourceKey<ConfiguredWorldCarver<?>> CAVE = create("cave");
	public static final ResourceKey<ConfiguredWorldCarver<?>> CAVE_EXTRA_UNDERGROUND = create("cave_extra_underground");
	public static final ResourceKey<ConfiguredWorldCarver<?>> CANYON = create("canyon");
	
	private static ResourceKey<ConfiguredWorldCarver<?>> create(String key) {
		return ResourceKey.create(Registries.CONFIGURED_CARVER, prefix(key));
	}
	
	public static void bootstrap(BootstrapContext<ConfiguredWorldCarver<?>> context) {
		HolderGetter<Block> blocks = context.lookup(Registries.BLOCK);
		context.register(CAVE, WorldCarver.CAVE.configured(new CaveCarverConfiguration(
				0.15f,
				UniformHeight.of(aboveBottom(8), absolute(180)),
				UniformFloat.of(0.1f, 0.9f),
				aboveBottom(8),
				CarverDebugSettings.DEFAULT,
				blocks.getOrThrow(CBlockTags.BT_CARVER_OVERRIDEABLE),
				UniformFloat.of(0.7f, 1.4f),
				UniformFloat.of(0.8f, 1.3f),
				UniformFloat.of(-1.0f, -0.4f))));
		context.register(CAVE_EXTRA_UNDERGROUND, WorldCarver.CAVE.configured(new CaveCarverConfiguration(
				0.07f,
				UniformHeight.of(aboveBottom(8), absolute(47)),
				UniformFloat.of(0.1f, 0.9f),
				aboveBottom(8),
				CarverDebugSettings.DEFAULT,
				blocks.getOrThrow(CBlockTags.BT_CARVER_OVERRIDEABLE),
				UniformFloat.of(0.7f, 1.4f),
				UniformFloat.of(0.8f, 1.3f),
				UniformFloat.of(-1.0f, -0.4f))));
		context.register(CANYON, WorldCarver.CANYON.configured(new CanyonCarverConfiguration(
				0.01f,
				UniformHeight.of(absolute(10), absolute(67)), of(3.0f), aboveBottom(8),
				CarverDebugSettings.DEFAULT,
				blocks.getOrThrow(CBlockTags.BT_CARVER_OVERRIDEABLE),
				UniformFloat.of(-0.125f, 0.125f),
				new CanyonShapeConfiguration(
						UniformFloat.of(0.75f, 1.0f),
						TrapezoidFloat.of(0.0f, 6.0f, 2.0f),
						3,
						UniformFloat.of(0.75f, 1.0f),
						1.0f,
						0.0f)
		)));
	}
}
