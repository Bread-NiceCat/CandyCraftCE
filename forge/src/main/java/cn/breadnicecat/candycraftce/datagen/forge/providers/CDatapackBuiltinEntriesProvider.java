package cn.breadnicecat.candycraftce.datagen.forge.providers;

import cn.breadnicecat.candycraftce.CandyCraftCE;
import cn.breadnicecat.candycraftce.datagen.forge.providers.builtins.CConfiguredFeaturesData;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

import static net.minecraft.core.registries.Registries.CONFIGURED_FEATURE;

/**
 * Created in 2024/2/9 19:13
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 * <p>
 */
public class CDatapackBuiltinEntriesProvider extends DatapackBuiltinEntriesProvider {


	public CDatapackBuiltinEntriesProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
		super(output, registries, new RegistrySetBuilder()
						.add(CONFIGURED_FEATURE, CConfiguredFeaturesData::bootstrap)
				, Set.of(CandyCraftCE.MOD_ID));
	}
}
