package cn.breadnicecat.candycraftce.datagen.forge.providers;

import cn.breadnicecat.candycraftce.CandyCraftCE;
import net.minecraft.data.PackOutput;
import net.minecraftforge.client.model.generators.BlockModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

/**
 * Created in 2023/10/14 22:47
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 * <p>
 */
public class CBlockModelProvider extends BlockModelProvider {
	public CBlockModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
		super(output, CandyCraftCE.MOD_ID, existingFileHelper);
	}

	@Override
	protected void registerModels() {

	}
}
