package cn.breadnicecat.candycraftce.datagen.forge.providers;

import cn.breadnicecat.candycraftce.CandyCraftCE;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

/**
 * Created in 2023/9/9 21:39
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 */
public class CItemTagsProvider extends ItemTagsProvider {
	//不能用LinkedHashMap,键可以重复
	public static final LinkedHashSet<Pair<TagKey<Item>, Consumer<TagAppender<Item>>>> ENTRIES = new LinkedHashSet<>();
	public static final LinkedHashMap<TagKey<Block>, TagKey<Item>> COPIES = new LinkedHashMap<>();

	public CItemTagsProvider(PackOutput arg, CompletableFuture<HolderLookup.Provider> completableFuture, CompletableFuture<TagLookup<Block>> completableFuture2, @Nullable ExistingFileHelper existingFileHelper) {
		super(arg, completableFuture, completableFuture2, CandyCraftCE.MOD_ID, existingFileHelper);
	}

	@Override
	protected void addTags(HolderLookup.@NotNull Provider arg) {
		ENTRIES.forEach((p) -> p.getSecond().accept(tag(p.getFirst())));
		COPIES.forEach(this::copy);
	}

	/**
	 * 将条目从方块tag复制到物品tag中。
	 *
	 * @see ItemTagsProvider#copy(TagKey, TagKey)
	 */
	public static void copyTags(@NotNull TagKey<Block> from, @NotNull TagKey<Item> to) {
		COPIES.put(from, to);
	}

}
