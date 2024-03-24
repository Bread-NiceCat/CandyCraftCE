package cn.breadnicecat.candycraftce.level;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import org.jetbrains.annotations.NotNull;

/**
 * Created in 2024/3/24 9:36
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 * <p>
 */
public class CandiedCherryFoliagePlacer extends FoliagePlacer {
	public static final Codec<CandiedCherryFoliagePlacer> CODEC = Codec.unit(CandiedCherryFoliagePlacer::new);

	public CandiedCherryFoliagePlacer() {
		super(ConstantInt.of(2), ConstantInt.of(1));
	}

	@Override
	protected @NotNull FoliagePlacerType<?> type() {
		return CDimInit.CANDIED_CHERRY_FOLIAGE_PLACER.get();
	}

	@Override
	protected void createFoliage(LevelSimulatedReader level, FoliageSetter setter, RandomSource random, TreeConfiguration config, int maxFreeTreeHeight, FoliageAttachment attachment, int foliageHeight, int foliageRadius, int offset) {
		BlockPos.MutableBlockPos pos = attachment.pos().mutable();
		setter.set(pos, config.foliageProvider.getState(random, pos));
		for (int ny = 0; ny < foliageHeight; ny++) {
			pos.move(Direction.DOWN);
			int start, end;
			if (ny % 2 == 0) {
				start = -1;
				end = 1;
			} else {
				start = -2;
				end = 2;
			}
			for (int x = start; x < end + 1; x++) {
				for (int z = start; z < end + 1; z++) {
					if (x != 0 || z != 0) {
						BlockPos set = pos.offset(x, 0, z);
						setter.set(set, config.foliageProvider.getState(random, set));
					}
				}
			}
		}
	}

	@Override
	public int foliageHeight(RandomSource random, int height, TreeConfiguration config) {
		return height - 2;
	}

	@Override
	protected boolean shouldSkipLocation(RandomSource random, int localX, int localY, int localZ, int range, boolean large) {
		return false;
	}
}