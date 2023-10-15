package cn.breadnicecat.candycraftce.datagen.forge;

import cn.breadnicecat.candycraftce.datagen.forge.providers.*;
import cn.breadnicecat.candycraftce.utils.CLogUtils;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.concurrent.CompletableFuture;

/**
 * Created in 2023/8/22 20:57
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class CCDatagenMain {
	static {
		CLogUtils.getModLogger().warn("RUNNING DATAGEN ENVIRONMENT");
	}

	@SubscribeEvent
	public static void onInitializeDataGenerator(GatherDataEvent evt) {
		CDatagens.init();
		ExistingFileHelper efhelper = evt.getExistingFileHelper();
		DataGenerator generator = evt.getGenerator();
		PackOutput pack = generator.getPackOutput();
		CompletableFuture<HolderLookup.Provider> lookup = evt.getLookupProvider();


		CBlockTagsProvider blocktag = new CBlockTagsProvider(pack, lookup, efhelper);
		generator.addProvider(evt.includeServer(), blocktag);
		generator.addProvider(evt.includeServer(), new CItemTagsProvider(pack, lookup, blocktag.contentsGetter(), efhelper));

		generator.addProvider(evt.includeClient(), new CLanguageProvider(pack));
		generator.addProvider(evt.includeClient(), new CBlockModelProvider(pack, efhelper));
		generator.addProvider(evt.includeClient(), new CItemModelProvider(pack, efhelper));
		generator.addProvider(evt.includeClient(), new CSoundProvider(pack, efhelper));
	}
}
