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
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class CCDatagenMain {

	private static final Logger LOGGER = CLogUtils.sign();

	@SubscribeEvent
	public static void onInitializeDataGenerator(GatherDataEvent evt) {
		launchTerminalHelper();
		LOGGER.warn("RUNNING DATAGEN ENVIRONMENT");
		ExistingFileHelper efhelper = evt.getExistingFileHelper();
		DataGenerator generator = evt.getGenerator();
		PackOutput pack = generator.getPackOutput();
		CompletableFuture<HolderLookup.Provider> lookup = evt.getLookupProvider();

		CBlockTagsProvider blocktag = new CBlockTagsProvider(pack, lookup, efhelper);
		generator.addProvider(evt.includeServer(), blocktag);
		generator.addProvider(evt.includeServer(), new CItemTagsProvider(pack, lookup, blocktag.contentsGetter(), efhelper));
		generator.addProvider(evt.includeServer(), new CLootTableProvider(pack));
		generator.addProvider(evt.includeServer(), new CRecipeProvider(pack));
		generator.addProvider(evt.includeServer(), new CDatapackBuiltinEntriesProvider(pack, lookup));

		generator.addProvider(evt.includeClient(), new CLanguageProvider(pack));
		generator.addProvider(evt.includeClient(), new CBlockStateProvider(pack, efhelper));
		generator.addProvider(evt.includeClient(), new CItemModelProvider(pack, efhelper));
		generator.addProvider(evt.includeClient(), new CSoundProvider(pack, efhelper));
	}

	/**
	 * Arch 会启动几个非daemon的线程，导致运行完毕后无法正常退出
	 * 此线程通过在main线程运行终止后执行exit(0)来解决此问题
	 */
	private static void launchTerminalHelper() {
		Thread main = Thread.currentThread();
		Thread helper = new Thread(() -> {
			LOGGER.info("Thread {} started!", Thread.currentThread().getName());
			while (main.isAlive()) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			}
			LOGGER.info("main Thread ended, preparing exit(0) after 5s!");
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
			System.exit(0);
		}, "Process Terminator");
		helper.setDaemon(true);
		helper.start();
	}
}
