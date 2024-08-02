package cn.breadnicecat.candycraftce.datagen.forge.providers;

import cn.breadnicecat.candycraftce.block.BlockEntry;
import cn.breadnicecat.candycraftce.block.CBlocks;
import cn.breadnicecat.candycraftce.item.CItems;
import cn.breadnicecat.candycraftce.item.ItemEntry;
import cn.breadnicecat.candycraftce.utils.RegistryEntry;
import com.google.common.hash.HashCode;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.TreeSet;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

import static cn.breadnicecat.candycraftce.utils.CommonUtils.apply;
import static java.util.Comparator.comparing;

/**
 * Created in 2024/7/5 下午6:58
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 *
 * <p>
 **/
public class CExportProvider implements DataProvider {
	
	private final File folder;
	private final CompletableFuture<Function<String, String>> stubZh;
	private final File csv;
	private static final char splitter = ',';
	private final String head = "物品注册名|物品名|类型|饱食度(鸡腿)|饱和度(金鸡腿)|额外效果|备注".replace('|', splitter);
	private final LinkedList<String> lines = new LinkedList<>();
	
	public CExportProvider(PackOutput pack, CompletableFuture<Function<String, String>> stubZh) {
		folder = pack.getOutputFolder(PackOutput.Target.REPORTS).toFile();
		this.stubZh = stubZh;
		folder.mkdirs();
		csv = new File(folder, "candycraftce_items.csv");
	}
	
	@Override
	public @NotNull CompletableFuture<?> run(@NotNull CachedOutput output) {
		return stubZh.thenAccept(lookup -> {
			apply(new TreeSet<ItemEntry<?>>(comparing(RegistryEntry::getId)),
					it -> it.addAll(CItems.ITEMS),
					it -> it.forEach(i -> {
						Item o = i.get();
						if (o instanceof BlockItem || o instanceof SpawnEggItem) return;
						lines.add(i.getId().toString() + splitter + lookup.apply(o.getDescriptionId()) + splitter + " - ");
					}));
			
			apply(new TreeSet<BlockEntry<?>>(comparing(RegistryEntry::getId)),
					it -> it.addAll(CBlocks.BLOCKS),
					it -> it.forEach(i -> {
						Block o = i.get();
						lines.add(i.getId().toString() + splitter + lookup.apply(o.getDescriptionId()) + splitter + " - ");
					}));
			
			
			StringWriter writer = new StringWriter();
			writer.append(head).append('\n');
			lines.forEach((l) -> writer.write(l + '\n'));
			byte[] data = writer.getBuffer().toString().getBytes(StandardCharsets.UTF_8);
			try {
				output.writeIfNeeded(csv.toPath(), data, HashCode.fromBytes(data));
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		});
	}
	
	@Override
	public @NotNull String getName() {
		return "Items Export Provider";
	}
}
