package cn.breadnicecat.candycraftce.level;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.dimension.DimensionType;

import java.util.OptionalLong;

import static cn.breadnicecat.candycraftce.level.CDims.*;
import static net.minecraft.tags.BlockTags.INFINIBURN_OVERWORLD;
import static net.minecraft.world.level.dimension.BuiltinDimensionTypes.END_EFFECTS;
import static net.minecraft.world.level.dimension.BuiltinDimensionTypes.OVERWORLD_EFFECTS;

/**
 * Created in 2024/10/13 01:17
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 *
 * <p>
 **/
public class CDimTypes {
	public static final ResourceKey<DimensionType> DUNGEONS_TYPE = ResourceKey.create(Registries.DIMENSION_TYPE, DUNGEONS_LOCATION);
	public static final ResourceKey<DimensionType> CANDYLAND_TYPE = ResourceKey.create(Registries.DIMENSION_TYPE, CANDYCRAFT_LOCATION);
	
	public static void bootstrap(BootstrapContext<DimensionType> context) {
		context.register(DUNGEONS_TYPE, new DimensionType(
				OptionalLong.of(18000),
				false,
				true,
				false,
				false,
				1d,
				true,
				true,
				DUNGEONS_MIN_Y, DUNGEONS_HEIGHT, DUNGEONS_HEIGHT,
				INFINIBURN_OVERWORLD,
				END_EFFECTS,
				0f,
				new DimensionType.MonsterSettings(false, false, ConstantInt.ZERO, 0)
		));
		context.register(CANDYLAND_TYPE, new DimensionType(
				OptionalLong.empty(),
				true,
				false,
				false,
				false,
				1d,
				true,
				true,
				LAND_MIN_Y, LAND_HEIGHT, LAND_HEIGHT,
				INFINIBURN_OVERWORLD,
				OVERWORLD_EFFECTS,
				0f,
				new DimensionType.MonsterSettings(false, false, UniformInt.of(0, 7), 0)
		));
		
	}
}
