package cn.breadnicecat.candycraftce.block.blocks;

import net.minecraft.world.level.block.grower.TreeGrower;

import java.util.Optional;

import static cn.breadnicecat.candycraftce.level.CConfiguredFeatures.CANDIED_CHERRY_TREE;

/**
 * Created in 2024/2/10 9:59
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 * <p>
 */
public class CandiedCherrySapling extends CandySaplingBlock {
	protected CandiedCherrySapling(TreeGrower grower, Properties properties) {
		super(grower, properties);
	}
	
	public CandiedCherrySapling(Properties properties) {
		this(new TreeGrower("candied_cherry_tree", Optional.empty(), Optional.of(CANDIED_CHERRY_TREE), Optional.empty()), properties);
	}
}
