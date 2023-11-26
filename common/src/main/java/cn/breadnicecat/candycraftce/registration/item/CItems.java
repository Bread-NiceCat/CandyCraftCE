package cn.breadnicecat.candycraftce.registration.item;

import cn.breadnicecat.candycraftce.CandyCraftCE;
import cn.breadnicecat.candycraftce.registration.item.items.ItemCaramelBow;
import cn.breadnicecat.candycraftce.registration.item.items.ItemHoneycombArrow;
import cn.breadnicecat.candycraftce.registration.item.items.ItemIIDebug;
import cn.breadnicecat.candycraftce.registration.item.items.ItemWaterMask;
import cn.breadnicecat.candycraftce.registration.sound.CSoundEvents;
import cn.breadnicecat.candycraftce.registration.sound.SoundEntry;
import cn.breadnicecat.candycraftce.utils.CLogUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.minecraft.world.level.redstone.Redstone;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import static cn.breadnicecat.candycraftce.registration.item.CItemTemples._recordItem;
import static cn.breadnicecat.candycraftce.registration.item.CItemTemples._record_wwwooowww;

/**
 * Created in 2023/8/9 10:22
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 * <p>
 * 传送门: Datagen: {@link cn.breadnicecat.candycraftce.datagen.forge}
 */
public class CItems {
	static {
		CLogUtils.sign();
		CandyCraftCE.hookPostBootstrap(() -> ITEMS = Collections.unmodifiableMap(CItems.ITEMS));
	}

	public static Map<ResourceLocation, ItemEntry<? extends Item>> ITEMS = new HashMap<>();
	//TODO FOOD
	public static final ItemEntry<Item> LICORICE = builder("licorice").setFood(10, 4).save();
	public static final ItemEntry<Item> HONEYCOMB = builder("honeycomb").save();
	public static final ItemEntry<Item> HONEYCOMB_SHARD = builder("honeycomb_shard").save();
	public static final ItemEntry<Item> PEZ = builder("pez").save();
	public static final ItemEntry<Item> MARSHMALLOW_STICK = builder("marshmallow_stick").save();
	public static final ItemEntry<Item> SUGAR_CRYSTAL = builder("sugar_crystal").save();
	public static final ItemEntry<Item> COTTON_CANDY = builder("cotton_candy").save();
	public static final ItemEntry<Item> GUMMY = builder("gummy").save();
	public static final ItemEntry<Item> HOT_GUMMY = builder("hot_gummy").save();
	public static final ItemEntry<Item> CHOCOLATE_COIN = builder("chocolate_coin").save();
	public static final ItemEntry<Item> NOUGAT_POWDER = builder("nougat_powder").save();
	public static final ItemEntry<Item> PEZ_DUST = builder("pez_dust").save();
	public static final ItemEntry<Item> WAFFLE = builder("waffle").save();
	public static final ItemEntry<Item> WAFFLE_NUGGET = builder("waffle_nugget").save();
	public static final ItemEntry<Item> CANDIED_CHERRY = builder("candied_cherry").save();
	public static final ItemEntry<Item> CANDY_CANE = builder("candy_cane").save();
	public static final ItemEntry<Item> CHEWING_GUM = builder("chewing_gum").save();
	public static final ItemEntry<Item> LOLLIPOP = builder("lollipop").save();
	public static final ItemEntry<Item> CRANFISH = builder("cranfish").save();
	public static final ItemEntry<Item> CRANFISH_COOKED = builder("cranfish_cooked").save();
	public static final ItemEntry<Item> CRANFISH_SCALE = builder("cranfish_scale").save();
	//HELPER.single(DRAGIBUS, () -> new ItemCustomNamedBlockItem(DRAGIBUS_CROPS.getBlock(), defaultItemProperties()),GENERATED);
//HELPER.single(LOLLIPOP_SEEDS, () -> new ItemCustomNamedBlockItem(LOLLIPOP_STEM.getBlock(), defaultItemProperties()),GENERATED);

	/* 地牢门钥匙 */
	public static final ItemEntry<Item> JELLY_SENTRY_KEY = builder("jelly_sentry_key")
			.setProperties(properties -> properties.stacksTo(1).rarity(Rarity.UNCOMMON))
			.save();
	public static final ItemEntry<Item> JELLY_BOSS_KEY = builder("jelly_boss_key", JELLY_SENTRY_KEY).save();

	/*地牢钥匙*/
//HELPER.batch((n, a) -> new ItemDungeonKey(defaultItemProperties().stacksTo(1).rarity(Rarity.RARE), (ItemDungeonKey.DungeonTypes) a[0]),
//                GENERATED)
//        .addElement(JELLY_DUNGEON_KEY, ItemDungeonKey.DungeonTypes.JELLY_DUNGEON)
//        .addElement(BEETLE_DUNGEON_KEY, ItemDungeonKey.DungeonTypes.UNDEFINED)
//        .addElement(SKY_DUNGEON_KEY, ItemDungeonKey.DungeonTypes.UNDEFINED)
//        .addElement(SUGUARD_DUNGEON_KEY, ItemDungeonKey.DungeonTypes.UNDEFINED)
//        .register();

	/*唱片*/
	public static final ItemEntry<RecordItem> RECORD_1 = builder("record_1",
			(p, param) -> _recordItem((Integer) param[0], (SoundEntry) param[1], p, (Integer) param[2]))
			.setProperties(p -> p.stacksTo(1))
			.setParameters(1, CSoundEvents.CD_1, 316)

			.save();

	public static final ItemEntry<RecordItem> RECORD_2 = builder("record_2", RECORD_1)
			.setParameters(2, CSoundEvents.CD_2, 98)
			.save();
	public static final ItemEntry<RecordItem> RECORD_3 = builder("record_3", RECORD_1)
			.setParameters(3, CSoundEvents.CD_3, 112)
			.save();
	public static final ItemEntry<RecordItem> RECORD_4 = builder("record_4", RECORD_1)
			.setParameters(4, CSoundEvents.CD_4, 188)
			.save();
	public static final ItemEntry<RecordItem> RECORD_WWWOOOWWW = builder("record_wwwooowww",
			p -> _record_wwwooowww(Redstone.SIGNAL_MAX, CSoundEvents.CD_WWWOOOWWW, p, 302, "Bread_NiceCat's Secret Record", "Mono Inc. - Children of the Dark"))
			.setProperties(p -> p.stacksTo(1).rarity(Rarity.EPIC))
			.setCtab(false)
			.save();
	public static final ItemEntry<Item> GINGERBREAD_EMBLEM = builder("gingerbread_emblem").save();
	public static final ItemEntry<Item> JELLY_EMBLEM = builder("jelly_emblem").save();
	public static final ItemEntry<Item> SKY_EMBLEM = builder("sky_emblem").save();
	public static final ItemEntry<Item> CHEWING_GUM_EMBLEM = builder("chewing_gum_emblem").save();
	public static final ItemEntry<Item> HONEYCOMB_EMBLEM = builder("honeycomb_emblem").save();
	public static final ItemEntry<Item> CRANBERRY_EMBLEM = builder("cranberry_emblem").save();
	public static final ItemEntry<Item> NESSIE_EMBLEM = builder("nessie_emblem").save();
	public static final ItemEntry<Item> SUGUARD_EMBLEM = builder("suguard_emblem").save();
//	HELPER.single(FORK, ItemFork::new, HANDHELD);

	public static final ItemEntry<ItemHoneycombArrow> HONEYCOMB_ARROW = builder("honeycomb_arrow", ItemHoneycombArrow::new).save();
	public static final ItemEntry<ItemCaramelBow> CARAMEL_BOW = builder("caramel_bow", ItemCaramelBow::new).save();
//HELPER.single(CARAMEL_CROSSBOW, ItemCaramelCrossbow::new);

	/*工具*/
	//MARSHMALLOW
	public static final ItemEntry<SwordItem> MARSHMALLOW_SWORD = builder("marshmallow_sword",
			(p, param) -> new SwordItem((Tier) param[0],/*baseDamage*/ (Integer) param[1],/*baseSpeed*/(Float) param[2], p))
			.setParameters(CTiers.MARSHMALLOW, 3, -2.4F)
			.save();
	public static final ItemEntry<ShovelItem> MARSHMALLOW_SHOVEL = builder("marshmallow_shovel",
			(p, param) -> new ShovelItem((Tier) param[0],/*baseDamage*/(Float) param[1], /*baseSpeed*/(Float) param[2], p))
			.setParameters(CTiers.MARSHMALLOW, 1.5F, -3F)
			.save();
	public static final ItemEntry<PickaxeItem> MARSHMALLOW_PICKAXE = builder("marshmallow_pickaxe",
			(p, param) -> new PickaxeItem((Tier) param[0],/*baseDamage*/(Integer) param[1], /*baseSpeed*/(Float) param[2], p))
			.setParameters(CTiers.MARSHMALLOW, 1, -2.8F)
			.save();
	public static final ItemEntry<AxeItem> MARSHMALLOW_AXE = builder("marshmallow_axe",
			(p, param) -> new AxeItem((Tier) param[0],/*baseDamage*/(Float) param[1], /*baseSpeed*/(Float) param[2], p))
			.setParameters(CTiers.MARSHMALLOW, 6F, -3.2F)
			.save();
	public static final ItemEntry<HoeItem> MARSHMALLOW_HOE = builder("marshmallow_hoe",
			(p, param) -> new HoeItem((Tier) param[0],/*baseDamage*/(Integer) param[1], /*baseSpeed*/(Float) param[2], p))
			.setParameters(CTiers.MARSHMALLOW, 0, -3.0F)
			.save();
	//LICORICE
	public static final ItemEntry<SwordItem> LICORICE_SWORD = builder("licorice_sword", MARSHMALLOW_SWORD)
			.setParameters(CTiers.LICORICE, 3, -2.4F)
			.save();
	public static final ItemEntry<ShovelItem> LICORICE_SHOVEL = builder("licorice_shovel", MARSHMALLOW_SHOVEL)
			.setParameters(CTiers.LICORICE, 1.5F, -3F)
			.save();
	public static final ItemEntry<PickaxeItem> LICORICE_PICKAXE = builder("licorice_pickaxe", MARSHMALLOW_PICKAXE)
			.setParameters(CTiers.LICORICE, 1, -2.8F)
			.save();
	public static final ItemEntry<AxeItem> LICORICE_AXE = builder("licorice_axe", MARSHMALLOW_AXE)
			.setParameters(CTiers.LICORICE, 7F, -3.2F)
			.save();
	public static final ItemEntry<HoeItem> LICORICE_HOE = builder("licorice_hoe", MARSHMALLOW_HOE)
			.setParameters(CTiers.LICORICE, -1, -2.0F)
			.save();
	//HONEYCOMB
	public static final ItemEntry<SwordItem> HONEYCOMB_SWORD = builder("honeycomb_sword", MARSHMALLOW_SWORD)
			.setParameters(CTiers.HONEYCOMB, 3, -2.4F)
			.save();
	public static final ItemEntry<ShovelItem> HONEYCOMB_SHOVEL = builder("honeycomb_shovel", MARSHMALLOW_SHOVEL)
			.setParameters(CTiers.HONEYCOMB, 1.5F, -3F)
			.save();
	public static final ItemEntry<PickaxeItem> HONEYCOMB_PICKAXE = builder("honeycomb_pickaxe", MARSHMALLOW_PICKAXE)
			.setParameters(CTiers.HONEYCOMB, 1, -2.8F)
			.save();
	public static final ItemEntry<AxeItem> HONEYCOMB_AXE = builder("honeycomb_axe", MARSHMALLOW_AXE)
			.setParameters(CTiers.HONEYCOMB, 7F, -3.2F)
			.save();
	public static final ItemEntry<HoeItem> HONEYCOMB_HOE = builder("honeycomb_hoe", MARSHMALLOW_HOE)
			.setParameters(CTiers.HONEYCOMB, -1, -2.0F)
			.save();
	//PEZ
	public static final ItemEntry<SwordItem> PEZ_SWORD = builder("pez_sword", MARSHMALLOW_SWORD)
			.setParameters(CTiers.PEZ, 3, -2.4F)
			.save();
	public static final ItemEntry<ShovelItem> PEZ_SHOVEL = builder("pez_shovel", MARSHMALLOW_SHOVEL)
			.setParameters(CTiers.PEZ, 1.5F, -3.0F)
			.save();
	public static final ItemEntry<PickaxeItem> PEZ_PICKAXE = builder("pez_pickaxe", MARSHMALLOW_PICKAXE)
			.setParameters(CTiers.PEZ, 1, -2.8F)
			.save();
	public static final ItemEntry<AxeItem> PEZ_AXE = builder("pez_axe", MARSHMALLOW_AXE)
			.setParameters(CTiers.PEZ, 5.0F, -3.0F)
			.save();
	public static final ItemEntry<HoeItem> PEZ_HOE = builder("pez_hoe", MARSHMALLOW_HOE)
			.setParameters(CTiers.PEZ, 5, -3.0F)
			.save();

	/*Armors*/
	//Licorice
	public static final ItemEntry<ArmorItem> LICORICE_HELMET = builder("licorice_helmet",
			(prop, param) -> new ArmorItem((ArmorMaterial) param[0], ArmorItem.Type.HELMET, prop))
			.setParameters(CArmorMaterials.LICORICE)
			.save();
	public static final ItemEntry<ArmorItem> LICORICE_CHESTPLATE = builder("licorice_chestplate",
			(prop, param) -> new ArmorItem((ArmorMaterial) param[0], ArmorItem.Type.CHESTPLATE, prop))
			.setParameters(CArmorMaterials.LICORICE)
			.save();
	public static final ItemEntry<ArmorItem> LICORICE_LEGGINGS = builder("licorice_leggings",
			(prop, param) -> new ArmorItem((ArmorMaterial) param[0], ArmorItem.Type.LEGGINGS, prop))
			.setParameters(CArmorMaterials.LICORICE)
			.save();
	public static final ItemEntry<ArmorItem> LICORICE_BOOTS = builder("licorice_boots",
			(prop, param) -> new ArmorItem((ArmorMaterial) param[0], ArmorItem.Type.BOOTS, prop))
			.setParameters(CArmorMaterials.LICORICE)
			.save();
	//Honeycomb
	public static final ItemEntry<ArmorItem> HONEYCOMB_HELMET = builder("honeycomb_helmet", LICORICE_HELMET)
			.setParameters(CArmorMaterials.HONEYCOMB)
			.save();
	public static final ItemEntry<ArmorItem> HONEYCOMB_CHESTPLATE = builder("honeycomb_chestplate", LICORICE_CHESTPLATE)
			.setParameters(CArmorMaterials.HONEYCOMB)
			.save();
	public static final ItemEntry<ArmorItem> HONEYCOMB_LEGGINGS = builder("honeycomb_leggings", LICORICE_LEGGINGS)
			.setParameters(CArmorMaterials.HONEYCOMB)
			.save();
	public static final ItemEntry<ArmorItem> HONEYCOMB_BOOTS = builder("honeycomb_boots", LICORICE_BOOTS)
			.setParameters(CArmorMaterials.HONEYCOMB)
			.save();
	//PEZ
	public static final ItemEntry<ArmorItem> PEZ_HELMET = builder("pez_helmet", LICORICE_HELMET)
			.setParameters(CArmorMaterials.PEZ)
			.save();
	public static final ItemEntry<ArmorItem> PEZ_CHESTPLATE = builder("pez_chestplate", LICORICE_CHESTPLATE)
			.setParameters(CArmorMaterials.PEZ)
			.save();
	public static final ItemEntry<ArmorItem> PEZ_LEGGINGS = builder("pez_leggings", LICORICE_LEGGINGS)
			.setParameters(CArmorMaterials.PEZ)
			.save();
	public static final ItemEntry<ArmorItem> PEZ_BOOTS = builder("pez_boots", LICORICE_BOOTS)
			.setParameters(CArmorMaterials.PEZ)
			.save();
	//Sp
	public static final ItemEntry<ItemWaterMask> WATER_MASK = builder("water_mask", (p) -> new ItemWaterMask(p)).save();
	public static final ItemEntry<ArmorItem> JELLY_CROWN = builder("jelly_crown", LICORICE_HELMET)
			.setParameters(CArmorMaterials.JELLY_CROWN)
			.save();
	public static final ItemEntry<ArmorItem> TRAMPOJELLY_BOOTS = builder("trampojelly_boots", LICORICE_BOOTS)
			.setParameters(CArmorMaterials.TRAMPOJELLY_BOOTS)
			.save();

	//HELPER.single(IIDEBUG, ItemIiDebug::new, MODEL_SP_DEBUG, EXCLUDE_SUGARY, EXCLUDE_AUTO_GENERATE_LANG);
	public static final ItemEntry<ItemIIDebug> I_I_DEBUG = builder("i_i_debug", ItemIIDebug::new)
			.setTab(CreativeModeTabs.OP_BLOCKS)
			.setCtab(false)
			.save();
//HELPER.single(GRENADINE_BUCKET, () -> new BucketItem(CFluidEntries.GRENADINE_STATIC, defaultItemProperties().stacksTo(1)), GENERATED);

	//	public static final ItemEntry<BlockItem> TEST_BLOCK = simpleBlockItem(CBlocks.TEST_BLOCK).save();


	static {
		CandyCraftCE.hookMinecraftSetup(() -> {
			//@see net.minecraft.client.renderer.item.ItemProperties.<cinit>
		}, CandyCraftCE::isClient);
	}

	public static CItemBuilder<Item> builder(String name) {
		return new CItemBuilder<>(name, Item::new);
	}

	public static <I extends Item> CItemBuilder<I> builder(String name, Function<Item.Properties, I> fac) {
		return new CItemBuilder<>(name, fac);
	}

	public static <I extends Item> CItemBuilder<I> builder(String name, BiFunction<Item.Properties, Object[], I> fac) {
		return new CItemBuilder<>(name, fac);
	}

	public static <I extends Item> CItemBuilder<I> builder(String name, Supplier<I> fac) {
		return new CItemBuilder<>(name, fac);
	}

	public static <I extends Item> CItemBuilder<I> builder(String name, ItemEntry<I> from) {
		return new CItemBuilder<>(name, from);
	}

	public static void init() {
	}
}
