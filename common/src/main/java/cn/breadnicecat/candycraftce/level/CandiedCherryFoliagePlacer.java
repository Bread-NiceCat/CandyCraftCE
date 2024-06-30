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
		//从上往下走
		for (int y = 0; y < foliageHeight; y++) {
			pos.move(Direction.DOWN);
			//是否跳脚
			//最上面一行肯定跳
			boolean skipCorner = y % 2 == 0;
			for (int x = -1; x < 2; x++) {
				for (int z = -1; z < 2; z++) {
					//最中间忽略
					if (x == 0 && z == 0) continue;
					int st = 0;
					if (x == -1 || x == 1) st++;
					if (z == -1 || z == 1) st++;
					//不是角 或者 是角且不跳角
					if (st != 2 || !skipCorner) {
						BlockPos setPos = pos.offset(x, 0, z);
						setter.set(setPos, config.foliageProvider.getState(random, setPos));
					}
				}
			}
		}
	}
	
	@Override
	public int foliageHeight(RandomSource random, int height, TreeConfiguration config) {
		return height % 2 == 0 ? height - 2 : height - 1;//保证最下面一行一定是缺角的树叶
	}
	
	@Override
	protected boolean shouldSkipLocation(RandomSource random, int localX, int localY, int localZ, int range, boolean large) {
		return false;
	}
}