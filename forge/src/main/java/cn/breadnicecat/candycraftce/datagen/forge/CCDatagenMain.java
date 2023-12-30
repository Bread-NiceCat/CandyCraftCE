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
import org.slf4j.Logger;

import java.util.concurrent.CompletableFuture;

/**
 * Created in 2023/8/22 20:57
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class CCDatagenMain {

	private static final Logger LOGGER = CLogUtils.sign();

	@SubscribeEvent
	public static void onInitializeDataGenerator(GatherDataEvent evt) {
		terminalHelper();
		LOGGER.warn("RUNNING DATAGEN ENVIRONMENT");
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

	private static void terminalHelper() {
		Thread main = Thread.currentThread();
		Thread helper = new Thread(() -> {
			LOGGER.info("Thread Terminal Helper started!");
			while (main.isAlive()) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			}
			LOGGER.info("main Thread terminated, prepare exit(0)!");
			System.exit(0);
		});
		helper.setName("Terminal Helper");
		helper.setDaemon(true);
		helper.start();
	}
}
