package cn.breadnicecat.candycraftce.block.blocks;

import cn.breadnicecat.candycraftce.utils.CommonUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.jetbrains.annotations.NotNull;

import static cn.breadnicecat.candycraftce.level.CConfiguredFeatures.CHOCOLATE_FANCY_TREE;
import static cn.breadnicecat.candycraftce.level.CConfiguredFeatures.CHOCOLATE_TREE;

/**
 * Created in 2024/2/7 12:18
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 * <p>
 */
public class ChocolateSaplingBlock extends CandySaplingBlock {
	protected ChocolateSaplingBlock(AbstractTreeGrower abstractTreeGrower, Properties properties) {
		super(abstractTreeGrower, properties);
	}

	public ChocolateSaplingBlock(Properties properties) {
		super(new AbstractTreeGrower() {
			@Override
			protected @NotNull ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource random, boolean hasFlowers) {
				return CommonUtils.probability(random, 20) ? CHOCOLATE_FANCY_TREE : CHOCOLATE_TREE;
			}
		}, properties);
	}
}
