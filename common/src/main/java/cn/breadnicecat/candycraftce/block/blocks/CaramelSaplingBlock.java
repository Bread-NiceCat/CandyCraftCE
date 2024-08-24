package cn.breadnicecat.candycraftce.block.blocks;

import net.minecraft.world.level.block.grower.TreeGrower;

import java.util.Optional;

import static cn.breadnicecat.candycraftce.level.CConfiguredFeatures.CARAMEL_TREE;

/**
 * Created in 2024/2/10 9:56
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 * <p>
 */
public class CaramelSaplingBlock extends CandySaplingBlock {
	
	protected CaramelSaplingBlock(TreeGrower abstractTreeGrower, Properties properties) {
		super(abstractTreeGrower, properties);
	}
	
	public CaramelSaplingBlock(Properties properties) {
		super(new TreeGrower("caramel_tree", Optional.empty(), Optional.of(CARAMEL_TREE), Optional.empty()), properties);
	}
}
