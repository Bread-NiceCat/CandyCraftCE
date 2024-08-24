package cn.breadnicecat.candycraftce.block.blocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

/**
 * Created in 2024/2/5
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-NiceCat">Bread_NiceCat</a>
 * <p>
 */
public class AlchemyMixerBlock extends Block {
	public static final BooleanProperty FULL = BooleanProperty.create("full");
	
	public AlchemyMixerBlock(Properties properties) {
		super(properties);
		registerDefaultState(stateDefinition.any().setValue(FULL, false));
	}
	
	
	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(FULL);
	}
	
}
