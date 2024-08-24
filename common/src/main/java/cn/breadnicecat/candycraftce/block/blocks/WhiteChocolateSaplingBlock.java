package cn.breadnicecat.candycraftce.block.blocks;

import net.minecraft.world.level.block.grower.TreeGrower;

import java.util.Optional;

import static cn.breadnicecat.candycraftce.level.CConfiguredFeatures.WHITE_CHOCOLATE_TREE;

/**
 * Created in 2024/2/10 9:54
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 * <p>
 */
public class WhiteChocolateSaplingBlock extends CandySaplingBlock {
	protected WhiteChocolateSaplingBlock(TreeGrower abstractTreeGrower, Properties properties) {
		super(abstractTreeGrower, properties);
	}
	
	public WhiteChocolateSaplingBlock(Properties properties) {
		super(new TreeGrower("white_chocolate_tree", Optional.empty(), Optional.of(WHITE_CHOCOLATE_TREE), Optional.empty()), properties);
	}
}
