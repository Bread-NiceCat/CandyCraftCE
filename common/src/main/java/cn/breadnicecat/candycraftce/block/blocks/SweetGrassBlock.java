package cn.breadnicecat.candycraftce.block.blocks;

import net.minecraft.Util;
import org.jetbrains.annotations.NotNull;

import static cn.breadnicecat.candycraftce.utils.ResourceUtils.prefix;

/**
 * Created in 2024/2/14 21:38
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 * <p>
 */
public class SweetGrassBlock extends CandyPlantBlock {
	public static final String DESCRIPTION_ID = Util.makeDescriptionId("block", prefix("sweet_grass"));

	public SweetGrassBlock(Properties properties) {
		super(properties);
	}

	@Override
	public @NotNull String getDescriptionId() {
		return DESCRIPTION_ID;
	}
}
