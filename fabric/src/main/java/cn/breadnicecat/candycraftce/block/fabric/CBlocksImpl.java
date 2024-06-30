package cn.breadnicecat.candycraftce.block.fabric;

import net.fabricmc.fabric.impl.blockrenderlayer.BlockRenderLayerMapImpl;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;

import java.util.function.Supplier;

/**
 * Created in 2024/1/29 10:07
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 */
public class CBlocksImpl {
	
	public static StairBlock _stairBlock(Supplier<BlockState> base, BlockBehaviour.Properties p) {
		return new StairBlock(base.get(), p);
	}
	
	@SuppressWarnings("UnstableApiUsage")
	public static void setRendererType(Block block, RenderType rt) {
		BlockRenderLayerMapImpl.INSTANCE.putBlock(block, rt);
	}
	
	@SuppressWarnings("UnstableApiUsage")
	public static void setRendererType(Fluid block, RenderType rt) {
		BlockRenderLayerMapImpl.INSTANCE.putFluid(block, rt);
	}
}
