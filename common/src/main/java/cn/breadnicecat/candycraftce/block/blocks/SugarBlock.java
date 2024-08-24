package cn.breadnicecat.candycraftce.block.blocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.FallingBlock;
import org.jetbrains.annotations.NotNull;

/**
 * Created in 2023/12/30 21:30
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 */
public class SugarBlock extends FallingBlock {
	public static final MapCodec<SugarBlock> CODEC = simpleCodec(SugarBlock::new);
	
	public SugarBlock(Properties properties) {
		super(properties);
	}
	
	@Override
	protected @NotNull MapCodec<? extends FallingBlock> codec() {
		return CODEC;
	}
	
}
