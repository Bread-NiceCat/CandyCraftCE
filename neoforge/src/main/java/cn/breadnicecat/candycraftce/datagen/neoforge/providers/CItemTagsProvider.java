package cn.breadnicecat.candycraftce.datagen.neoforge.providers;

import cn.breadnicecat.candycraftce.CandyCraftCE;
import cn.breadnicecat.candycraftce.block.CBlockTags;
import cn.breadnicecat.candycraftce.item.ItemEntry;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.concurrent.CompletableFuture;

import static cn.breadnicecat.candycraftce.block.CBlockTags.BT_SUGARY;
import static cn.breadnicecat.candycraftce.item.CItemTags.*;
import static cn.breadnicecat.candycraftce.item.CItems.*;
import static cn.breadnicecat.candycraftce.utils.CommonUtils.accept;
import static cn.breadnicecat.candycraftce.utils.CommonUtils.apply;
import static net.minecraft.tags.ItemTags.*;
import static net.neoforged.neoforge.common.Tags.Items.MUSIC_DISCS;

/**
 * Created in 2023/9/9 21:39
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 */
public class CItemTagsProvider extends ItemTagsProvider {
	public CItemTagsProvider(PackOutput arg, CompletableFuture<HolderLookup.Provider> completableFuture, CompletableFuture<TagLookup<Block>> completableFuture2, @Nullable ExistingFileHelper existingFileHelper) {
		super(arg, completableFuture, completableFuture2, CandyCraftCE.MOD_ID, existingFileHelper);
	}
	
	@Override
	protected void addTags(HolderLookup.@NotNull Provider arg) {
		add(BT_SUGARY.it(), apply(new HashSet<>(ITEMS), (i) -> {
			i.remove(RECORD_o);
			i.remove(IIDEBUG);
		}).toArray(ItemEntry[]::new));
		
		add(MUSIC_DISCS, RECORD_1, RECORD_2, RECORD_3, RECORD_4, RECORD_o);
		add(IT_LEAF, CARAMEL_LEAF, MAGICAL_LEAF, CHOCOLATE_LEAF, WHITE_CHOCOLATE_LEAF, CANDIED_CHERRY_LEAF);
		add(IT_EMBLEM,
				GINGERBREAD_EMBLEM, JELLY_EMBLEM, SKY_EMBLEM, CHEWING_GUM_EMBLEM, HONEYCOMB_EMBLEM,
				CRANBERRY_EMBLEM, NESSIE_EMBLEM, SUGUARD_EMBLEM
		);
		add(CANDY_ARROW, HONEYCOMB_ARROW);
		add(IT_RETURN_TICKET, JELLY_CROWN);
		add(SWORDS, MARSHMALLOW_SWORD, LICORICE_SWORD, HONEY_SWORD, PEZ_SWORD);
		add(HOES, MARSHMALLOW_HOE, LICORICE_HOE, HONEY_HOE, PEZ_HOE);
		add(AXES, MARSHMALLOW_AXE, LICORICE_AXE, HONEY_AXE, PEZ_AXE);
		add(PICKAXES, MARSHMALLOW_PICKAXE, LICORICE_PICKAXE, HONEY_PICKAXE, PEZ_PICKAXE);
		add(SHOVELS, MARSHMALLOW_SHOVEL, LICORICE_SHOVEL, HONEY_SHOVEL, PEZ_SHOVEL);
		
		CBlockTags.pairs.forEach((p) -> copy(p.bt(), p.it()));
	}
	
	private void add(TagKey<Item> tagKey, ItemEntry<?>... ie) {
		var tag = tag(tagKey);
		accept(i -> tag.add(i.get()), ie);
	}
	
}
