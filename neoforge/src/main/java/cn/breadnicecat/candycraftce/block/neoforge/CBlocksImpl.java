package cn.breadnicecat.candycraftce.block.neoforge;

import cn.breadnicecat.candycraftce.block.CBlocks;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;

import java.util.function.Supplier;

/**
 * Created in 2024/1/29 10:07
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 */
@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class CBlocksImpl {
	
	public static StairBlock _stairBlock(Supplier<BlockState> base, BlockBehaviour.Properties p) {
		return new StairBlock(base.get(), p);
	}
	
	@SuppressWarnings("deprecation")
	@OnlyIn(Dist.CLIENT)
	public static void setRendererType(Block block, RenderType rt) {
		ItemBlockRenderTypes.setRenderLayer(block, rt);
	}
	
	@OnlyIn(Dist.CLIENT)
	public static void setRendererType(Fluid fluid, RenderType rt) {
		ItemBlockRenderTypes.setRenderLayer(fluid, rt);
	}
	
	@SubscribeEvent
	public static void onItemColor(RegisterColorHandlersEvent.Block event) {
		CBlocks.registerBlockColors(event.getBlockColors());
	}
	
}
