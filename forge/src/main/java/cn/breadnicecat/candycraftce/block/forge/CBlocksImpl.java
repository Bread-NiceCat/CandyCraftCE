package cn.breadnicecat.candycraftce.block.forge;

import cn.breadnicecat.candycraftce.utils.CLogUtils;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import org.slf4j.Logger;

import java.util.function.Supplier;

/**
 * Created in 2024/1/29 10:07
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 */
public class CBlocksImpl {
	
	private static final Logger LOGGER = CLogUtils.getModLogger();
	
	public static StairBlock _stairBlock(Supplier<BlockState> base, BlockBehaviour.Properties p) {
		return new StairBlock(base, p);
	}
	
	@SuppressWarnings("deprecation")
	public static void setRendererType(Block block, RenderType rt) {
		ItemBlockRenderTypes.setRenderLayer(block, rt);
	}
	
	public static void setRendererType(Fluid fluid, RenderType rt) {
		ItemBlockRenderTypes.setRenderLayer(fluid, rt);
	}
	
}
