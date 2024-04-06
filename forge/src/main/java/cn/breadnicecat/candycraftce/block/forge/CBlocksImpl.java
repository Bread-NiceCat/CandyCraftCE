package cn.breadnicecat.candycraftce.block.forge;

import cn.breadnicecat.candycraftce.CandyCraftCE;
import cn.breadnicecat.candycraftce.utils.CLogUtils;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.ForgeRegistries;
import org.slf4j.Logger;

import java.util.function.Supplier;

import static cn.breadnicecat.candycraftce.CandyCraftCE.isClient;

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
	public static void modifyRendererType(FMLCommonSetupEvent setup) {
		if (isClient()) {
			LOGGER.info(":forge:modifyRendererType");
			setup.enqueueWork(() -> ItemBlockRenderTypes.TYPE_BY_BLOCK.entrySet().parallelStream()
					.filter(t -> ForgeRegistries.BLOCKS.getKey(t.getKey()).getNamespace().equals(CandyCraftCE.MOD_ID))
					.forEach(p -> ItemBlockRenderTypes.setRenderLayer(p.getKey(), p.getValue())));
			setup.enqueueWork(() -> ItemBlockRenderTypes.TYPE_BY_FLUID.entrySet().parallelStream()
					.filter(t -> ForgeRegistries.FLUIDS.getKey(t.getKey()).getNamespace().equals(CandyCraftCE.MOD_ID))
					.forEach(p -> ItemBlockRenderTypes.setRenderLayer(p.getKey(), p.getValue())));
		}
	}
}
