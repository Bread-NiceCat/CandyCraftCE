package cn.breadnicecat.candycraftce.block.blocks;

import net.minecraft.world.level.block.grower.TreeGrower;

import java.util.Optional;

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
	protected ChocolateSaplingBlock(TreeGrower abstractTreeGrower, Properties properties) {
		super(abstractTreeGrower, properties);
	}
	
	public ChocolateSaplingBlock(Properties properties) {
		super(new TreeGrower("chocolate_tree", 0.1f, Optional.empty(), Optional.empty(), Optional.of(CHOCOLATE_TREE), Optional.of(CHOCOLATE_FANCY_TREE), Optional.empty(), Optional.empty()), properties);
	}
}
