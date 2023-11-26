package cn.breadnicecat.candycraftce.datagen.forge;

import cn.breadnicecat.candycraftce.datagen.forge.providers.CItemModelProvider;
import cn.breadnicecat.candycraftce.datagen.forge.providers.CItemTagsProvider;
import cn.breadnicecat.candycraftce.datagen.forge.providers.CLanguageProvider;
import cn.breadnicecat.candycraftce.registration.item.ItemEntry;
import com.mojang.datafixers.util.Pair;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * Created in 2023/9/10 1:51
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 */
class ItemBuilder {
	private final ItemEntry<?> entry;

	ItemBuilder(ItemEntry<?> entry) {
		this.entry = entry;
	}

	/*=====ItemTags=====*/
	@SuppressWarnings("deprecation")
	ItemBuilder tag(TagKey<Item> tag, BiConsumer<ResourceKey<Item>, TagsProvider.TagAppender<Item>> con) {
//		ResourceKey<Item> rk = ResourceKey.create(Registries.ITEM, entry.getName());
		ResourceKey<Item> rk = entry.getItem().builtInRegistryHolder().key();
		CItemTagsProvider.ENTRIES.add(Pair.of(tag, (k) -> con.accept(rk, k)));
		return this;
	}

	@SafeVarargs
	final ItemBuilder tags(TagKey<Item> @NotNull ... tags) {
		for (TagKey<Item> tag : tags) {
			tag(tag, (i, t) -> t.add(i));
		}
		return this;
	}


	/*=====I18n=====*/
	ItemBuilder english(String name) {
		CLanguageProvider.ENTRIES.add(pro -> pro.add(entry.getItem(), name));
		return this;
	}

	/**
	 * @see CLanguageProvider#byId
	 */
	ItemBuilder englishAuto() {
		englishAuto(null);
		return this;
	}

	ItemBuilder englishAuto(@Nullable Function<String, String> modifier) {
		String prim = CLanguageProvider.byId(entry.getID().getPath());
		if (modifier != null) prim = modifier.apply(prim);
		english(prim);
		return this;
	}
	/*=====Models=====*/

	ItemBuilder model(BiConsumer<ItemEntry<?>, CItemModelProvider> con) {
		CItemModelProvider.ENTRIES.add(provider -> con.accept(entry, provider));
		return this;
	}

	ItemBuilder modelGenerated() {
		model((ie, con) -> con.basicItem(ie.getItem()));
		return this;
	}


	ItemBuilder modelHandheld() {
		model((ie, con) -> con.handheldItem(ie.getItem()));
		return this;
	}
}
