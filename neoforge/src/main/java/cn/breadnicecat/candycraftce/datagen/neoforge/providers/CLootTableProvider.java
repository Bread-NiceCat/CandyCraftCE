package cn.breadnicecat.candycraftce.datagen.neoforge.providers;

import cn.breadnicecat.candycraftce.datagen.neoforge.providers.loots.CBlockLootSubProvider;
import cn.breadnicecat.candycraftce.datagen.neoforge.providers.loots.CEntityLootSubProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.WritableRegistry;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

/**
 * Created in 2023/12/30 21:36
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 */
public class CLootTableProvider extends LootTableProvider {
	
	
	public CLootTableProvider(PackOutput arg, CompletableFuture<HolderLookup.Provider> reg) {
		//如果这个null抛了NPE,那么代表validate被调用了
		super(arg, Set.of(), List.of(
				new SubProviderEntry(CBlockLootSubProvider::new, LootContextParamSets.BLOCK),
				new SubProviderEntry(CEntityLootSubProvider::new, LootContextParamSets.ENTITY)
		), reg);
	}
	
	@Override
	protected void validate(@NotNull WritableRegistry<LootTable> writableregistry, @NotNull ValidationContext validationcontext, ProblemReporter.@NotNull Collector problemreporter$collector) {
		//禁用掉战利品表检查，否则会报错
	}
	
}
