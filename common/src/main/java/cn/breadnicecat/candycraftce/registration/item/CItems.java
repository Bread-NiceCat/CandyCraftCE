package cn.breadnicecat.candycraftce.registration.item;

import cn.breadnicecat.candycraftce.CandyCraftCE;
import cn.breadnicecat.candycraftce.registration.item.items.ItemCaramelBow;
import cn.breadnicecat.candycraftce.registration.item.items.ItemHoneycombArrow;
import cn.breadnicecat.candycraftce.registration.item.items.ItemIIDebug;
import cn.breadnicecat.candycraftce.registration.sound.CSoundEvents;
import cn.breadnicecat.candycraftce.registration.sound.SoundEntry;
import cn.breadnicecat.candycraftce.utils.CLogUtils;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.redstone.Redstone;
import org.slf4j.Logger;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

import static cn.breadnicecat.candycraftce.registration.block.CBlocks.*;
import static cn.breadnicecat.candycraftce.registration.item.CItemBuilder.block;
import static cn.breadnicecat.candycraftce.registration.item.CItemBuilder.create;
import static cn.breadnicecat.candycraftce.utils.CommonUtils.accept;

/**
 * Created in 2023/8/9 10:22
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 * <p>
 * 传送门: Datagen: {@link cn.breadnicecat.candycraftce.datagen.forge}
 */
public class CItems {
	private static final Logger LOGGER = CLogUtils.sign();

	static {
		CandyCraftCE.hookMinecraftSetup(() -> ITEMS = Collections.unmodifiableMap(CItems.ITEMS));
	}

	public static Map<ResourceLocation, ItemEntry<? extends Item>> ITEMS = new HashMap<>();
	//TODO FOOD
	public static final ItemEntry<Item> LICORICE = create("licorice").setFood(10, 4, null).save();
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
			.setProperties(new Properties().stacksTo(1).rarity(Rarity.UNCOMMON))
			.save();
	public static final ItemEntry<Item> JELLY_BOSS_KEY = create("jelly_boss_key")
			.setProperties(new Properties().stacksTo(1).rarity(Rarity.UNCOMMON))
			.save();

	/*地牢钥匙*/
//HELPER.batch((n, a) -> new ItemDungeonKey(defaultItemProperties().stacksTo(1).rarity(Rarity.RARE), (ItemDungeonKey.DungeonTypes) a[0]),
//                GENERATED)
//        .addElement(JELLY_DUNGEON_KEY, ItemDungeonKey.DungeonTypes.JELLY_DUNGEON)
//        .addElement(BEETLE_DUNGEON_KEY, ItemDungeonKey.DungeonTypes.UNDEFINED)
//        .addElement(SKY_DUNGEON_KEY, ItemDungeonKey.DungeonTypes.UNDEFINED)
//        .addElement(SUGUARD_DUNGEON_KEY, ItemDungeonKey.DungeonTypes.UNDEFINED)
//        .register();

	/*唱片*/
	public static final ItemEntry<RecordItem> RECORD_1 = createRecord("record_1", 1, CSoundEvents.CD_1, 316).save();
	public static final ItemEntry<RecordItem> RECORD_2 = createRecord("record_2", 2, CSoundEvents.CD_2, 98).save();
	public static final ItemEntry<RecordItem> RECORD_3 = createRecord("record_3", 3, CSoundEvents.CD_3, 112).save();
	public static final ItemEntry<RecordItem> RECORD_4 = createRecord("record_4", 4, CSoundEvents.CD_4, 188).save();
	public static final ItemEntry<RecordItem> RECORD_WWWOOOWWW = create("record_wwwooowww",
			p -> _record_wwwooowww(Redstone.SIGNAL_MAX, CSoundEvents.CD_WWWOOOWWW, p, 302, "Bread_NiceCat's Secret Record", "Mono Inc. - Children of the Dark"))
			.setProperties(new Properties().stacksTo(1).rarity(Rarity.EPIC))
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
	public static final ItemEntry<SwordItem> MARSHMALLOW_SWORD = createSword("marshmallow_sword", CTiers.MARSHMALLOW, 3, -2.4F).save();
	public static final ItemEntry<ShovelItem> MARSHMALLOW_SHOVEL = createShovel("marshmallow_shovel", CTiers.MARSHMALLOW, 1.5F, -3F).save();
	public static final ItemEntry<PickaxeItem> MARSHMALLOW_PICKAXE = createPickaxe("marshmallow_pickaxe", CTiers.MARSHMALLOW, 1, -2.8F).save();
	public static final ItemEntry<AxeItem> MARSHMALLOW_AXE = createAxe("marshmallow_axe", CTiers.MARSHMALLOW, 6F, -3.2F).save();
	public static final ItemEntry<HoeItem> MARSHMALLOW_HOE = createHoe("marshmallow_hoe", CTiers.MARSHMALLOW, 0, -3.0F).save();
	//LICORICE
	public static final ItemEntry<SwordItem> LICORICE_SWORD = createSword("licorice_sword", CTiers.LICORICE, 3, -2.4F).save();
	public static final ItemEntry<ShovelItem> LICORICE_SHOVEL = createShovel("licorice_shovel", CTiers.LICORICE, 1.5F, -3F).save();
	public static final ItemEntry<PickaxeItem> LICORICE_PICKAXE = createPickaxe("licorice_pickaxe", CTiers.LICORICE, 1, -2.8F).save();
	public static final ItemEntry<AxeItem> LICORICE_AXE = createAxe("licorice_axe", CTiers.LICORICE, 7F, -3.2F).save();
	public static final ItemEntry<HoeItem> LICORICE_HOE = createHoe("licorice_hoe", CTiers.LICORICE, -1, -2.0F).save();
	//HONEYCOMB
	public static final ItemEntry<SwordItem> HONEYCOMB_SWORD = createSword("honeycomb_sword", CTiers.HONEYCOMB, 3, -2.4F).save();
	public static final ItemEntry<ShovelItem> HONEYCOMB_SHOVEL = createShovel("honeycomb_shovel", CTiers.HONEYCOMB, 1.5F, -3F).save();
	public static final ItemEntry<PickaxeItem> HONEYCOMB_PICKAXE = createPickaxe("honeycomb_pickaxe", CTiers.HONEYCOMB, 1, -2.8F).save();
	public static final ItemEntry<AxeItem> HONEYCOMB_AXE = createAxe("honeycomb_axe", CTiers.HONEYCOMB, 7F, -3.2F).save();
	public static final ItemEntry<HoeItem> HONEYCOMB_HOE = createHoe("honeycomb_hoe", CTiers.HONEYCOMB, -1, -2.0F).save();
	//PEZ
	public static final ItemEntry<SwordItem> PEZ_SWORD = createSword("pez_sword", CTiers.PEZ, 3, -2.4F).save();
	public static final ItemEntry<ShovelItem> PEZ_SHOVEL = createShovel("pez_shovel", CTiers.PEZ, 1.5F, -3.0F).save();
	public static final ItemEntry<PickaxeItem> PEZ_PICKAXE = createPickaxe("pez_pickaxe", CTiers.PEZ, 1, -2.8F).save();
	public static final ItemEntry<AxeItem> PEZ_AXE = createAxe("pez_axe", CTiers.PEZ, 5.0F, -3.0F).save();
	public static final ItemEntry<HoeItem> PEZ_HOE = createHoe("pez_hoe", CTiers.PEZ, 5, -3.0F).save();

	/*Armors*/
	//Licorice
	public static final ItemEntry<ArmorItem> LICORICE_HELMET = createHelmet("licorice_helmet", CArmorMaterials.LICORICE).save();
	public static final ItemEntry<ArmorItem> LICORICE_CHESTPLATE = createChestplate("licorice_chestplate", CArmorMaterials.LICORICE).save();
	public static final ItemEntry<ArmorItem> LICORICE_LEGGINGS = createLeggings("licorice_leggings", CArmorMaterials.LICORICE).save();
	public static final ItemEntry<ArmorItem> LICORICE_BOOTS = createBoots("licorice_boots", CArmorMaterials.LICORICE).save();
	//HoneyComb
	public static final ItemEntry<ArmorItem> HONEYCOMB_HELMET = createHelmet("honeycomb_helmet", CArmorMaterials.HONEYCOMB).save();
	public static final ItemEntry<ArmorItem> HONEYCOMB_CHESTPLATE = createChestplate("honeycomb_chestplate", CArmorMaterials.HONEYCOMB).save();
	public static final ItemEntry<ArmorItem> HONEYCOMB_LEGGINGS = createLeggings("honeycomb_leggings", CArmorMaterials.HONEYCOMB).save();
	public static final ItemEntry<ArmorItem> HONEYCOMB_BOOTS = createBoots("honeycomb_boots", CArmorMaterials.HONEYCOMB).save();
	//PEZ
	public static final ItemEntry<ArmorItem> PEZ_HELMET = createHelmet("pez_helmet", CArmorMaterials.PEZ).save();
	public static final ItemEntry<ArmorItem> PEZ_CHESTPLATE = createChestplate("pez_chestplate", CArmorMaterials.PEZ).save();
	public static final ItemEntry<ArmorItem> PEZ_LEGGINGS = createLeggings("pez_leggings", CArmorMaterials.PEZ).save();
	public static final ItemEntry<ArmorItem> PEZ_BOOTS = createBoots("pez_boots", CArmorMaterials.PEZ).save();
	//Sp(p) -> new Item
	public static final ItemEntry<ArmorItem> WATER_MASK = createHelmet("water_mask", CArmorMaterials.WATER_MASK).save();
	public static final ItemEntry<ArmorItem> JELLY_CROWN = createHelmet("jelly_crown", CArmorMaterials.JELLY_CROWN).save();
	public static final ItemEntry<ArmorItem> TRAMPOJELLY_BOOTS = createBoots("trampojelly_boots", CArmorMaterials.TRAMPOJELLY_BOOTS)
			.save();
	public static final ItemEntry<ItemIIDebug> I_I_DEBUG = create("i_i_debug", higher(ItemIIDebug::new))
			.setTab(CreativeModeTabs.OP_BLOCKS)
			.setCtab(false)
			.save();

	//HELPER.single(GRENADINE_BUCKET, () -> new BucketItem(CFluidEntries.GRENADINE_STATIC, defaultItemProperties().stacksTo(1)), GENERATED);

	static {
		//BlockItems
		accept(b -> block(b).save(),
				SUGAR_BLOCK, CARAMEL_BLOCK, CHOCOLATE_STONE, CHOCOLATE_COBBLESTONE, PUDDING, CUSTARD_PUDDING
		);
	}


	static {
		if (CandyCraftCE.isClient()) {
			CandyCraftCE.hookMinecraftSetup(() -> {
				LOGGER.info("Loading CC Item Properties for CLIENT");
				//@see net.minecraft.client.renderer.item.ItemProperties.<cinit>
			});
		}
	}


	public static void init() {
		LOGGER.info("init");
	}

	private static <I> Function<Properties, I> higher(Supplier<I> sup) {
		return (p) -> sup.get();
	}

	//===========模板===========
	private static CItemBuilder<RecordItem> createRecord(String name, int analog, SoundEntry evt, int lengthInSeconds) {
		return create(name, (p) -> _recordItem(analog, evt, p, lengthInSeconds))
				.setProperties(new Properties().stacksTo(1));
	}

	//工具
	private static CItemBuilder<SwordItem> createSword(String name, Tier tier, int baseDamage, float baseSpeed) {
		return create(name, (p) -> new SwordItem(tier, baseDamage, baseSpeed, p));
	}

	private static CItemBuilder<ShovelItem> createShovel(String name, Tier tier, float baseDamage, float baseSpeed) {
		return create(name, (p) -> new ShovelItem(tier, baseDamage, baseSpeed, p));
	}

	private static CItemBuilder<PickaxeItem> createPickaxe(String name, Tier tier, int baseDamage, float baseSpeed) {
		return create(name, (p) -> new PickaxeItem(tier, baseDamage, baseSpeed, p));
	}

	private static CItemBuilder<AxeItem> createAxe(String name, Tier tier, float baseDamage, float baseSpeed) {
		return create(name, (p) -> new AxeItem(tier, baseDamage, baseSpeed, p));
	}

	private static CItemBuilder<HoeItem> createHoe(String name, Tier tier, int baseDamage, float baseSpeed) {
		return create(name, (p) -> new HoeItem(tier, baseDamage, baseSpeed, p));
	}

	//盔甲
	private static CItemBuilder<ArmorItem> createHelmet(String name, ArmorMaterial material) {
		return create(name, (p) -> new ArmorItem(material, ArmorItem.Type.HELMET, p));
	}

	private static CItemBuilder<ArmorItem> createChestplate(String name, ArmorMaterial material) {
		return create(name, (p) -> new ArmorItem(material, ArmorItem.Type.CHESTPLATE, p));
	}

	private static CItemBuilder<ArmorItem> createLeggings(String name, ArmorMaterial material) {
		return create(name, (p) -> new ArmorItem(material, ArmorItem.Type.LEGGINGS, p));
	}

	private static CItemBuilder<ArmorItem> createBoots(String name, ArmorMaterial material) {
		return create(name, (p) -> new ArmorItem(material, ArmorItem.Type.BOOTS, p));
	}


	//平台差异
	@ExpectPlatform
	private static RecordItem _recordItem(int analog, SoundEntry evt, Properties prop, int lengthInSeconds) {
		throw new AssertionError();
	}

	@ExpectPlatform
	private static RecordItem _record_wwwooowww(int analog, SoundEntry evt, Properties prop, int lengthInSeconds, String nameInGame, String musicName) {
		throw new AssertionError();
	}
}
