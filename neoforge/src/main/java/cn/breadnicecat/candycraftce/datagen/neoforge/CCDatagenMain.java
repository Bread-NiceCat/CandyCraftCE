package cn.breadnicecat.candycraftce.datagen.neoforge;

import cn.breadnicecat.candycraftce.datagen.neoforge.providers.*;
import cn.breadnicecat.candycraftce.utils.CLogUtils;
import cn.breadnicecat.candycraftce.utils.tools.Accessor;
import cn.breadnicecat.candycraftce.utils.tools.SafeAccessor;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import org.slf4j.Logger;

import java.util.concurrent.CompletableFuture;

/**
 * Created in 2023/8/22 20:57
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 */
@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class CCDatagenMain {
	
	private static final Logger LOGGER = CLogUtils.sign();
	private static final Accessor<Boolean> STATE = new SafeAccessor<>(false);
	
	@SubscribeEvent
	public static void onInitializeDataGenerator(GatherDataEvent evt) {
		LOGGER.warn("RUNNING DATAGEN ENVIRONMENT");
		launchProcessTerminator();
		ExistingFileHelper efhelper = evt.getExistingFileHelper();
		DataGenerator generator = evt.getGenerator();
		PackOutput pack = generator.getPackOutput();
		CompletableFuture<HolderLookup.Provider> lookup = evt.getLookupProvider();
		
		CBlockTagsProvider blocktag = new CBlockTagsProvider(pack, lookup, efhelper);
		generator.addProvider(evt.includeServer(), blocktag);
		generator.addProvider(evt.includeServer(), new CItemTagsProvider(pack, lookup, blocktag.contentsGetter(), efhelper));
		generator.addProvider(evt.includeServer(), new CFluidTagsProvider(pack, lookup, efhelper));
		generator.addProvider(evt.includeServer(), new CLootTableProvider(pack, lookup));
		generator.addProvider(evt.includeServer(), new CRecipeProvider(pack, lookup));
		generator.addProvider(evt.includeServer(), new CDatapackBuiltinEntriesProvider(pack, lookup));
		
		CLanguageProvider lang = new CLanguageProvider(pack);
		generator.addProvider(evt.includeClient(), lang);
		generator.addProvider(evt.includeClient(), new CBlockStateProvider(pack, efhelper));
		generator.addProvider(evt.includeClient(), new CItemModelProvider(pack, efhelper));
		generator.addProvider(evt.includeClient(), new CSoundProvider(pack, efhelper));
		
		generator.addProvider(evt.includeServer(), new NoisingSettingsProvider(pack));
		generator.addProvider(true, new CExportProvider(pack, lang.getZhLookup()));
		
		generator.addProvider(true, new CTerminalStateProvider(STATE));
	}
	
	/**
	 * Arch 会启动几个非daemon的线程，导致运行完毕后无法正常退出
	 * 此线程通过在main线程运行终止后exit来解决此问题
	 */
	@SuppressWarnings("BusyWait")
	private static void launchProcessTerminator() {
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
			int status;
			if (STATE.get()) {
				LOGGER.info("with status = 0");
				status = 0;
			} else {
				LOGGER.warn("with status = -1");
				status = -1;
			}
			LOGGER.info("Preparing exit after 5s!");
			try {
				for (int i = 0; i < 5; i++) {
					Thread.sleep(1000);
					System.out.print("\rPreparing exit after " + (5 - i) + "s!");
				}
				System.out.println();
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
			LOGGER.warn("bye");
			System.exit(status);
		}, "Process Terminator");
		helper.setDaemon(true);
		helper.start();
	}
}
