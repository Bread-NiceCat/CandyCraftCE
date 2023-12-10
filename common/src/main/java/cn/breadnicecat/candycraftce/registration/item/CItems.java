package cn.breadnicecat.candycraftce.registration.item;

import cn.breadnicecat.candycraftce.CandyCraftCE;
import cn.breadnicecat.candycraftce.registration.block.CBlocks;
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

import static cn.breadnicecat.candycraftce.CandyCraftCE.isClient;
import static cn.breadnicecat.candycraftce.registration.item.CItemBuilder.create;
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
		CandyCraftCE.hookMinecraftSetup(() -> ITEMS = Collections.unmodifiableMap(CItems.ITEMS));
	}

	public static Map<ResourceLocation, ItemEntry<? extends Item>> ITEMS = new HashMap<>();
	//TODO FOOD
	public static final ItemEntry<Item> LICORICE = create("licorice").setFood(10, 4).save();
	public static final ItemEntry<Item> HONEYCOMB = create("honeycomb").save();
	public static final ItemEntry<Item> HONEYCOMB_SHARD = create("honeycomb_shard").save();
	public static final ItemEntry<Item> PEZ = create("pez").save();
	public static final ItemEntry<Item> MARSHMALLOW_STICK = create("marshmallow_stick").save();
	public static final ItemEntry<Item> SUGAR_CRYSTAL = create("sugar_crystal").save();
	public static final ItemEntry<Item> COTTON_CANDY = create("cotton_candy").save();
	public static final ItemEntry<Item> GUMMY = create("gummy").save();
	public static final ItemEntry<Item> HOT_GUMMY = create("hot_gummy").save();
	public static final ItemEntry<Item> CHOCOLATE_COIN = create("chocolate_coin").save();
	public static final ItemEntry<Item> NOUGAT_POWDER = create("nougat_powder").save();
	public static final ItemEntry<Item> PEZ_DUST = create("pez_dust").save();
	public static final ItemEntry<Item> WAFFLE = create("waffle").save();
	public static final ItemEntry<Item> WAFFLE_NUGGET = create("waffle_nugget").save();
	public static final ItemEntry<Item> CANDIED_CHERRY = create("candied_cherry").save();
	public static final ItemEntry<Item> CANDY_CANE = create("candy_cane").save();
	public static final ItemEntry<Item> CHEWING_GUM = create("chewing_gum").save();
	public static final ItemEntry<Item> LOLLIPOP = create("lollipop").save();
	public static final ItemEntry<Item> CRANFISH = create("cranfish").save();
	public static final ItemEntry<Item> CRANFISH_COOKED = create("cranfish_cooked").save();
	public static final ItemEntry<Item> CRANFISH_SCALE = create("cranfish_scale").save();
	//HELPER.single(DRAGIBUS, () -> new ItemCustomNamedBlockItem(DRAGIBUS_CROPS.getBlock(), defaultItemProperties()),GENERATED);
//HELPER.single(LOLLIPOP_SEEDS, () -> new ItemCustomNamedBlockItem(LOLLIPOP_STEM.getBlock(), defaultItemProperties()),GENERATED);

	/* 地牢门钥匙 */
	public static final ItemEntry<Item> JELLY_SENTRY_KEY = create("jelly_sentry_key")
			.setProperties(new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON))
			.save();
	public static final ItemEntry<Item> JELLY_BOSS_KEY = create("jelly_boss_key", JELLY_SENTRY_KEY).save();

	/*地牢钥匙*/
//HELPER.batch((n, a) -> new ItemDungeonKey(defaultItemProperties().stacksTo(1).rarity(Rarity.RARE), (ItemDungeonKey.DungeonTypes) a[0]),
//                GENERATED)
//        .addElement(JELLY_DUNGEON_KEY, ItemDungeonKey.DungeonTypes.JELLY_DUNGEON)
//        .addElement(BEETLE_DUNGEON_KEY, ItemDungeonKey.DungeonTypes.UNDEFINED)
//        .addElement(SKY_DUNGEON_KEY, ItemDungeonKey.DungeonTypes.UNDEFINED)
//        .addElement(SUGUARD_DUNGEON_KEY, ItemDungeonKey.DungeonTypes.UNDEFINED)
//        .register();

	/*唱片*/
	public static final ItemEntry<RecordItem> RECORD_1 = create("record_1",
			(p, param) -> _recordItem((Integer) param[0], (SoundEntry) param[1], p, (Integer) param[2]))
			.setProperties(new Item.Properties().stacksTo(1))
			.setParameters(1, CSoundEvents.CD_1, 316)
			.save();

	public static final ItemEntry<RecordItem> RECORD_2 = create("record_2", RECORD_1)
			.setParameters(2, CSoundEvents.CD_2, 98)
			.save();
	public static final ItemEntry<RecordItem> RECORD_3 = create("record_3", RECORD_1)
			.setParameters(3, CSoundEvents.CD_3, 112)
			.save();
	public static final ItemEntry<RecordItem> RECORD_4 = create("record_4", RECORD_1)
			.setParameters(4, CSoundEvents.CD_4, 188)
			.save();
	public static final ItemEntry<RecordItem> RECORD_WWWOOOWWW = create("record_wwwooowww",
			p -> _record_wwwooowww(Redstone.SIGNAL_MAX, CSoundEvents.CD_WWWOOOWWW, p, 302, "Bread_NiceCat's Secret Record", "Mono Inc. - Children of the Dark"))
			.setProperties(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC))
			.setCtab(false)
			.save();
	public static final ItemEntry<Item> GINGERBREAD_EMBLEM = create("gingerbread_emblem").save();
	public static final ItemEntry<Item> JELLY_EMBLEM = create("jelly_emblem").save();
	public static final ItemEntry<Item> SKY_EMBLEM = create("sky_emblem").save();
	public static final ItemEntry<Item> CHEWING_GUM_EMBLEM = create("chewing_gum_emblem").save();
	public static final ItemEntry<Item> HONEYCOMB_EMBLEM = create("honeycomb_emblem").save();
	public static final ItemEntry<Item> CRANBERRY_EMBLEM = create("cranberry_emblem").save();
	public static final ItemEntry<Item> NESSIE_EMBLEM = create("nessie_emblem").save();
	public static final ItemEntry<Item> SUGUARD_EMBLEM = create("suguard_emblem").save();
//	HELPER.single(FORK, ItemFork::new, HANDHELD);

	public static final ItemEntry<ItemHoneycombArrow> HONEYCOMB_ARROW = create("honeycomb_arrow", ItemHoneycombArrow::new).save();
	public static final ItemEntry<ItemCaramelBow> CARAMEL_BOW = create("caramel_bow", ItemCaramelBow::new).save();
//HELPER.single(CARAMEL_CROSSBOW, ItemCaramelCrossbow::new);

	/*工具*/
	//MARSHMALLOW
	public static final ItemEntry<SwordItem> MARSHMALLOW_SWORD = create("marshmallow_sword",
			(p, param) -> new SwordItem((Tier) param[0],/*baseDamage*/ (Integer) param[1],/*baseSpeed*/(Float) param[2], p))
			.setParameters(CTiers.MARSHMALLOW, 3, -2.4F)
			.save();
	public static final ItemEntry<ShovelItem> MARSHMALLOW_SHOVEL = create("marshmallow_shovel",
			(p, param) -> new ShovelItem((Tier) param[0],/*baseDamage*/(Float) param[1], /*baseSpeed*/(Float) param[2], p))
			.setParameters(CTiers.MARSHMALLOW, 1.5F, -3F)
			.save();
	public static final ItemEntry<PickaxeItem> MARSHMALLOW_PICKAXE = create("marshmallow_pickaxe",
			(p, param) -> new PickaxeItem((Tier) param[0],/*baseDamage*/(Integer) param[1], /*baseSpeed*/(Float) param[2], p))
			.setParameters(CTiers.MARSHMALLOW, 1, -2.8F)
			.save();
	public static final ItemEntry<AxeItem> MARSHMALLOW_AXE = create("marshmallow_axe",
			(p, param) -> new AxeItem((Tier) param[0],/*baseDamage*/(Float) param[1], /*baseSpeed*/(Float) param[2], p))
			.setParameters(CTiers.MARSHMALLOW, 6F, -3.2F)
			.save();
	public static final ItemEntry<HoeItem> MARSHMALLOW_HOE = create("marshmallow_hoe",
			(p, param) -> new HoeItem((Tier) param[0],/*baseDamage*/(Integer) param[1], /*baseSpeed*/(Float) param[2], p))
			.setParameters(CTiers.MARSHMALLOW, 0, -3.0F)
			.save();
	//LICORICE
	public static final ItemEntry<SwordItem> LICORICE_SWORD = create("licorice_sword", MARSHMALLOW_SWORD)
			.setParameters(CTiers.LICORICE, 3, -2.4F)
			.save();
	public static final ItemEntry<ShovelItem> LICORICE_SHOVEL = create("licorice_shovel", MARSHMALLOW_SHOVEL)
			.setParameters(CTiers.LICORICE, 1.5F, -3F)
			.save();
	public static final ItemEntry<PickaxeItem> LICORICE_PICKAXE = create("licorice_pickaxe", MARSHMALLOW_PICKAXE)
			.setParameters(CTiers.LICORICE, 1, -2.8F)
			.save();
	public static final ItemEntry<AxeItem> LICORICE_AXE = create("licorice_axe", MARSHMALLOW_AXE)
			.setParameters(CTiers.LICORICE, 7F, -3.2F)
			.save();
	public static final ItemEntry<HoeItem> LICORICE_HOE = create("licorice_hoe", MARSHMALLOW_HOE)
			.setParameters(CTiers.LICORICE, -1, -2.0F)
			.save();
	//HONEYCOMB
	public static final ItemEntry<SwordItem> HONEYCOMB_SWORD = create("honeycomb_sword", MARSHMALLOW_SWORD)
			.setParameters(CTiers.HONEYCOMB, 3, -2.4F)
			.save();
	public static final ItemEntry<ShovelItem> HONEYCOMB_SHOVEL = create("honeycomb_shovel", MARSHMALLOW_SHOVEL)
			.setParameters(CTiers.HONEYCOMB, 1.5F, -3F)
			.save();
	public static final ItemEntry<PickaxeItem> HONEYCOMB_PICKAXE = create("honeycomb_pickaxe", MARSHMALLOW_PICKAXE)
			.setParameters(CTiers.HONEYCOMB, 1, -2.8F)
			.save();
	public static final ItemEntry<AxeItem> HONEYCOMB_AXE = create("honeycomb_axe", MARSHMALLOW_AXE)
			.setParameters(CTiers.HONEYCOMB, 7F, -3.2F)
			.save();
	public static final ItemEntry<HoeItem> HONEYCOMB_HOE = create("honeycomb_hoe", MARSHMALLOW_HOE)
			.setParameters(CTiers.HONEYCOMB, -1, -2.0F)
			.save();
	//PEZ
	public static final ItemEntry<SwordItem> PEZ_SWORD = create("pez_sword", MARSHMALLOW_SWORD)
			.setParameters(CTiers.PEZ, 3, -2.4F)
			.save();
	public static final ItemEntry<ShovelItem> PEZ_SHOVEL = create("pez_shovel", MARSHMALLOW_SHOVEL)
			.setParameters(CTiers.PEZ, 1.5F, -3.0F)
			.save();
	public static final ItemEntry<PickaxeItem> PEZ_PICKAXE = create("pez_pickaxe", MARSHMALLOW_PICKAXE)
			.setParameters(CTiers.PEZ, 1, -2.8F)
			.save();
	public static final ItemEntry<AxeItem> PEZ_AXE = create("pez_axe", MARSHMALLOW_AXE)
			.setParameters(CTiers.PEZ, 5.0F, -3.0F)
			.save();
	public static final ItemEntry<HoeItem> PEZ_HOE = create("pez_hoe", MARSHMALLOW_HOE)
			.setParameters(CTiers.PEZ, 5, -3.0F)
			.save();

	/*Armors*/
	//Licorice
	public static final ItemEntry<ArmorItem> LICORICE_HELMET = create("licorice_helmet",
			(prop, param) -> new ArmorItem((ArmorMaterial) param[0], ArmorItem.Type.HELMET, prop))
			.setParameters(CArmorMaterials.LICORICE)
			.save();
	public static final ItemEntry<ArmorItem> LICORICE_CHESTPLATE = create("licorice_chestplate",
			(prop, param) -> new ArmorItem((ArmorMaterial) param[0], ArmorItem.Type.CHESTPLATE, prop))
			.setParameters(CArmorMaterials.LICORICE)
			.save();
	public static final ItemEntry<ArmorItem> LICORICE_LEGGINGS = create("licorice_leggings",
			(prop, param) -> new ArmorItem((ArmorMaterial) param[0], ArmorItem.Type.LEGGINGS, prop))
			.setParameters(CArmorMaterials.LICORICE)
			.save();
	public static final ItemEntry<ArmorItem> LICORICE_BOOTS = create("licorice_boots",
			(prop, param) -> new ArmorItem((ArmorMaterial) param[0], ArmorItem.Type.BOOTS, prop))
			.setParameters(CArmorMaterials.LICORICE)
			.save();
	//Honeycomb
	public static final ItemEntry<ArmorItem> HONEYCOMB_HELMET = create("honeycomb_helmet", LICORICE_HELMET)
			.setParameters(CArmorMaterials.HONEYCOMB)
			.save();
	public static final ItemEntry<ArmorItem> HONEYCOMB_CHESTPLATE = create("honeycomb_chestplate", LICORICE_CHESTPLATE)
			.setParameters(CArmorMaterials.HONEYCOMB)
			.save();
	public static final ItemEntry<ArmorItem> HONEYCOMB_LEGGINGS = create("honeycomb_leggings", LICORICE_LEGGINGS)
			.setParameters(CArmorMaterials.HONEYCOMB)
			.save();
	public static final ItemEntry<ArmorItem> HONEYCOMB_BOOTS = create("honeycomb_boots", LICORICE_BOOTS)
			.setParameters(CArmorMaterials.HONEYCOMB)
			.save();
	//PEZ
	public static final ItemEntry<ArmorItem> PEZ_HELMET = create("pez_helmet", LICORICE_HELMET)
			.setParameters(CArmorMaterials.PEZ)
			.save();
	public static final ItemEntry<ArmorItem> PEZ_CHESTPLATE = create("pez_chestplate", LICORICE_CHESTPLATE)
			.setParameters(CArmorMaterials.PEZ)
			.save();
	public static final ItemEntry<ArmorItem> PEZ_LEGGINGS = create("pez_leggings", LICORICE_LEGGINGS)
			.setParameters(CArmorMaterials.PEZ)
			.save();
	public static final ItemEntry<ArmorItem> PEZ_BOOTS = create("pez_boots", LICORICE_BOOTS)
			.setParameters(CArmorMaterials.PEZ)
			.save();
	//Sp
	public static final ItemEntry<ItemWaterMask> WATER_MASK = create("water_mask", (p) -> new ItemWaterMask(p)).save();
	public static final ItemEntry<ArmorItem> JELLY_CROWN = create("jelly_crown", LICORICE_HELMET)
			.setParameters(CArmorMaterials.JELLY_CROWN)
			.save();
	public static final ItemEntry<ArmorItem> TRAMPOJELLY_BOOTS = create("trampojelly_boots", LICORICE_BOOTS)
			.setParameters(CArmorMaterials.TRAMPOJELLY_BOOTS)
			.save();

	//HELPER.single(IIDEBUG, ItemIiDebug::new, MODEL_SP_DEBUG, EXCLUDE_SUGARY, EXCLUDE_AUTO_GENERATE_LANG);
	public static final ItemEntry<ItemIIDebug> I_I_DEBUG = create("i_i_debug", ItemIIDebug::new)
			.setTab(CreativeModeTabs.OP_BLOCKS)
			.setCtab(false)
			.save();
	//HELPER.single(GRENADINE_BUCKET, () -> new BucketItem(CFluidEntries.GRENADINE_STATIC, defaultItemProperties().stacksTo(1)), GENERATED);

	public static final ItemEntry<BlockItem> TEST_BLOCK = CItemBuilder.block(CBlocks.TEST_BLOCK).setProperties(new Item.Properties().stacksTo(16).rarity(Rarity.EPIC)).save();


	static {
		if (isClient()) {
			CandyCraftCE.hookMinecraftSetup(() -> {
				//@see net.minecraft.client.renderer.item.ItemProperties.<cinit>
			});
		}
	}


	public static void init() {
	}
}
