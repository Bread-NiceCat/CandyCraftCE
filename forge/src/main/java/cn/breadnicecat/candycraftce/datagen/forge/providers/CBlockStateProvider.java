package cn.breadnicecat.candycraftce.datagen.forge.providers;

import cn.breadnicecat.candycraftce.CandyCraftCE;
import net.minecraft.data.PackOutput;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import static cn.breadnicecat.candycraftce.registration.block.CBlocks.CARAMEL_BLOCK;
import static cn.breadnicecat.candycraftce.registration.block.CBlocks.SUGAR_BLOCK;
import static cn.breadnicecat.candycraftce.utils.CommonUtils.receive;

/**
 * Created in 2023/10/14 22:47
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 * <p>
 */
public class CBlockStateProvider extends BlockStateProvider {
	public CBlockStateProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
		super(output, CandyCraftCE.MOD_ID, existingFileHelper);
	}

	@Override
	protected void registerStatesAndModels() {
		//cubeAll+item
		{
			receive((b) -> simpleBlockWithItem(b.getBlock(), cubeAll(b.getBlock())),
					SUGAR_BLOCK,
					CARAMEL_BLOCK
			);
		}
	}
}
