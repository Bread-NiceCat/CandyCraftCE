package cn.breadnicecat.candycraftce.datagen.forge.providers;

import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

/**
 * Created in 2023/12/30 21:36
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 */
public class CLootTableProvider extends LootTableProvider {
	
	
	public CLootTableProvider(PackOutput arg) {
		//如果这个null抛了NPE,那么代表validate被调用了
		super(arg, null, List.of(new SubProviderEntry(CBlockLootSubProvider::new, LootContextParamSets.BLOCK)));
	}
	
	@Override
	protected void validate(@NotNull Map<ResourceLocation, LootTable> map, @NotNull ValidationContext validationcontext) {
		//禁用掉战利品表检查，否则会报错
	}
}
