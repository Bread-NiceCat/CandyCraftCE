package cn.breadnicecat.candycraftce.level.foliage_placer;

import cn.breadnicecat.candycraftce.level.CDimInit;
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
 * Created in 2024/7/29 上午9:51
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 *
 * <p>
 **/
public class FancyCaramelFoliagePlacer extends FoliagePlacer {
	public static final Codec<FancyCaramelFoliagePlacer> CODEC = Codec.unit(FancyCaramelFoliagePlacer::new);
	
	public FancyCaramelFoliagePlacer() {
		super(ConstantInt.of(3), ConstantInt.of(1));
	}
	
	@Override
	protected @NotNull FoliagePlacerType<?> type() {
		return CDimInit.FANCY_CARAMEL_FOLIAGE_PLACER.get();
	}
	
	@Override
	protected void createFoliage(LevelSimulatedReader level, FoliageSetter setter, RandomSource random, TreeConfiguration config,
	                             int maxFreeTreeHeight, FoliageAttachment attachment,
	                             int foliageHeight, int foliageRadius, int offset) {
		BlockPos.MutableBlockPos pos = attachment.pos().mutable();
		setter.set(pos, config.foliageProvider.getState(random, pos));
		int last = -1;
		boolean zeroed = false;
		for (int y = 0; y < foliageHeight; y++) {
			int r;
			do {
				if (y == 0) {
					//第一行树叶最小半径2
					r = random.nextInt(3) + 2;//[2,5)
				} else if (y > foliageHeight - 3 && y > 3) {
					//最后两行树叶最大半径2
					r = random.nextInt(3);//[0,3)
				} else r = random.nextInt(5);//[0,5)
			} while (r == last || (r == 0 && zeroed));//只允许出现一次0
			last = r;
			if (r == 0) zeroed = true;
			
			
			for (int x = -r; x < r + 1; x++) {
				for (int z = -r; z < r + 1; z++) {
					if (x == 0 && z == 0) continue;
					BlockPos po = pos.offset(x, 0, z);
					setter.set(po, config.foliageProvider.getState(random, po));
				}
			}
			pos.move(Direction.DOWN);
		}
	}
	
	@Override
	public int foliageHeight(RandomSource random, int height, TreeConfiguration config) {
		return height - 1;
	}
	
	@Override
	protected boolean shouldSkipLocation(RandomSource random, int localX, int localY, int localZ, int range, boolean large) {
		return false;
	}
}
