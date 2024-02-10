package cn.breadnicecat.candycraftce.block.blocks;

import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

import static cn.breadnicecat.candycraftce.level.CConfiguredFeatures.CANDIED_CHERRY_TREE;

/**
 * Created in 2024/2/10 9:59
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 * <p>
 */
public class CandiedCherrySapling extends CandySaplingBlock {
	protected CandiedCherrySapling(AbstractTreeGrower abstractTreeGrower, Properties properties) {
		super(abstractTreeGrower, properties);
	}

	public CandiedCherrySapling(Properties properties) {
		super(new AbstractTreeGrower() {
			@Override
			protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource random, boolean hasFlowers) {
				return CANDIED_CHERRY_TREE;
			}
		}, properties);
	}
}
