package cn.breadnicecat.candycraftce.datagen.forge.providers;

import cn.breadnicecat.candycraftce.CandyCraftCE;
import cn.breadnicecat.candycraftce.item.ItemEntry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import static cn.breadnicecat.candycraftce.item.CItems.*;
import static cn.breadnicecat.candycraftce.utils.CommonUtils.accept;
import static cn.breadnicecat.candycraftce.utils.ResourceUtils.prefix;

/**
 * Created in 2023/10/14 22:51
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 */
public class CItemModelProvider extends ItemModelProvider {
	private static final ModelFile.UncheckedModelFile HANDHELD = new ModelFile.UncheckedModelFile("item/handheld");
	private static final ModelFile.UncheckedModelFile GENERATED = new ModelFile.UncheckedModelFile("item/generated");
	
	public CItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
		super(output, CandyCraftCE.MOD_ID, existingFileHelper);
	}
	
	@Override
	protected void registerModels() {
		//generated
		{
//			CCEntityManager.SPAWN_EGG_HELPER.getEntries().forEach((k, v) -> withExistingParent(k.getEntryName(), "item/template_spawn_egg"));
			BuiltInRegistries.ITEM.entrySet().stream()
					.filter(e -> e.getKey().location().getNamespace().equals(CandyCraftCE.MOD_ID))
					.filter(e -> e.getValue() instanceof SpawnEggItem)
					.forEach(e -> withExistingParent(e.getKey().location().getPath(), "item/template_spawn_egg"));
			accept(((i) -> basicItem(i.get())),
					LICORICE, HONEYCOMB, HONEYCOMB_SHARD, PEZ, MARSHMALLOW_STICK,
					SUGAR_CRYSTAL, COTTON_CANDY, GUMMY, HOT_GUMMY, CHOCOLATE_COIN,
					NOUGAT_POWDER, PEZ_DUST, WAFFLE, WAFFLE_NUGGET,
					DRAGIBUS, LOLLIPOP_SEEDS, CANDIED_CHERRY, CANDY_CANE, CHEWING_GUM,
					LOLLIPOP, CRANFISH_SCALE, CRANFISH, CRANFISH_COOKED, WHITE_CHOCOLATE_BRICK,
//					BLACK_CHOCOLATE_BRICK,
					WHITE_CHOCOLATE_LEAF, JELLY_SENTRY_KEY, JELLY_BOSS_KEY,
					CANDIED_CHERRY_LEAF, CARAMEL_LEAF, CHOCOLATE_LEAF, MAGICAL_LEAF,
					RECORD_1, RECORD_2, RECORD_3, RECORD_4,
					GINGERBREAD_EMBLEM, JELLY_EMBLEM, SKY_EMBLEM, CHEWING_GUM_EMBLEM, HONEYCOMB_EMBLEM, CRANBERRY_EMBLEM, NESSIE_EMBLEM, SUGUARD_EMBLEM,
					HONEYCOMB_ARROW, IIDEBUG, CARAMEL_BRICK, CHOCOLATE_BRICK,
					LICORICE_HELMET, LICORICE_CHESTPLATE, LICORICE_LEGGINGS, LICORICE_BOOTS,
					HONEYCOMB_HELMET, HONEYCOMB_CHESTPLATE, HONEYCOMB_LEGGINGS, HONEYCOMB_BOOTS,
					PEZ_HELMET, PEZ_CHESTPLATE, PEZ_LEGGINGS, PEZ_BOOTS, WATER_MASK, JELLY_CROWN, TRAMPOJELLY_BOOTS
			);
		}
		generated(RECORD_o, prefix("item/record_unknown"));
		//handheld
		{
			accept(this::handheldItem,
					MARSHMALLOW_SWORD, MARSHMALLOW_SHOVEL, MARSHMALLOW_PICKAXE, MARSHMALLOW_AXE, MARSHMALLOW_HOE,
					LICORICE_SWORD, LICORICE_SHOVEL, LICORICE_PICKAXE, LICORICE_AXE, LICORICE_HOE,
					HONEYCOMB_SWORD, HONEYCOMB_SHOVEL, HONEYCOMB_PICKAXE, HONEYCOMB_AXE, HONEYCOMB_HOE,
					PEZ_SWORD, PEZ_SHOVEL, PEZ_PICKAXE, PEZ_AXE, PEZ_HOE, FORK);
		}
	}
	
	
	public ItemModelBuilder handheldItem(ItemEntry<?> item) {
		return handheldItem(item, item.getId());
	}
	
	public ItemModelBuilder handheldItem(ItemEntry<?> item, @NotNull ResourceLocation tex) {
		return getBuilder(item.getName())
				.parent(HANDHELD)
				.texture("layer0", new ResourceLocation(tex.getNamespace(), "item/" + tex.getPath()));
	}
	
	public ItemModelBuilder generated(ItemEntry<?> item, ResourceLocation tex) {
		return getBuilder(item.getName())
				.parent(GENERATED)
				.texture("layer0", tex);
	}
	
	
}
