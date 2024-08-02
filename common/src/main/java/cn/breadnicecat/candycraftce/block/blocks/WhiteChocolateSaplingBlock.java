package cn.breadnicecat.candycraftce.block.blocks;

import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.jetbrains.annotations.NotNull;

import static cn.breadnicecat.candycraftce.level.CConfiguredFeatures.WHITE_CHOCOLATE_TREE;

/**
 * Created in 2024/2/10 9:54
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 * <p>
 */
public class WhiteChocolateSaplingBlock extends CandySaplingBlock {
	protected WhiteChocolateSaplingBlock(AbstractTreeGrower abstractTreeGrower, Properties properties) {
		super(abstractTreeGrower, properties);
	}
	
	public WhiteChocolateSaplingBlock(Properties properties) {
		super(new AbstractTreeGrower() {
			@Override
			protected @NotNull ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource random, boolean hasFlowers) {
				return WHITE_CHOCOLATE_TREE;
			}
		}, properties);
	}
}
