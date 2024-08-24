package cn.breadnicecat.candycraftce.item;

import cn.breadnicecat.candycraftce.CandyCraftCE;
import cn.breadnicecat.candycraftce.block.PuddingColor;
import cn.breadnicecat.candycraftce.block.blockentity.CBlockEntities;
import cn.breadnicecat.candycraftce.entity.CEntities;
import cn.breadnicecat.candycraftce.entity.EntityEntry;
import cn.breadnicecat.candycraftce.item.items.*;
import cn.breadnicecat.candycraftce.sound.CJukeboxSound;
import cn.breadnicecat.candycraftce.utils.CLogUtils;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.*;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.material.Fluid;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.util.HashSet;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

import static cn.breadnicecat.candycraftce.CandyCraftCE.isClient;
import static cn.breadnicecat.candycraftce.block.CBlocks.*;
import static cn.breadnicecat.candycraftce.item.CItemBuilder.create;
import static cn.breadnicecat.candycraftce.item.CItemBuilder.setTab;
import static cn.breadnicecat.candycraftce.utils.CommonUtils.impossibleCode;
import static net.minecraft.world.item.ArmorItem.Type.*;
import static net.minecraft.world.level.material.Fluids.WATER;

/**
 * Created in 2023/8/9 10:22
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 * 传送门: Datagen: {@link cn.breadnicecat.candycraftce.datagen.neoforge}
 */
public class CItems {
	private static final Logger LOGGER = CLogUtils.sign();
	public static final String _SPAWN_EGG_TRANS_KEY = "item.candycraftce.spawn_egg";
	/**
	 * 包括BlockItem
	 */
	public static @NotNull HashSet<ItemEntry<?>> ITEMS = new HashSet<>();
	
	private static @Nullable List<Supplier<ItemEntry<BlockItem>>> blockItems;
	private static @Nullable List<Supplier<ItemEntry<SpawnEggItem>>> eggs;
	
	//TODO EAT
	public static final ItemEntry<Item> LICORICE = create("licorice").setFood(10, 4, null).save();
	public static final ItemEntry<Item> HONEYCOMB = create("honeycomb").save();
	public static final ItemEntry<Item> HONEYCOMB_SHARD = create("honeycomb_shard").save();
	public static final ItemEntry<Item> PEZ = create("pez").save();
	public static final ItemEntry<Item> MARSHMALLOW_STICK = create("marshmallow_stick").save();
	public static final ItemEntry<Item> SUGAR_CRYSTAL = create("sugar_crystal").save();
	public static final ItemEntry<Item> CARAMEL_BRICK = create("caramel_brick").save();
	public static final ItemEntry<Item> CHOCOLATE_BRICK = create("chocolate_brick").save();
	public static final ItemEntry<Item> WHITE_CHOCOLATE_BRICK = create("white_chocolate_brick").save();
	//	public static final ItemEntry<Item> BLACK_CHOCOLATE_BRICK = create("black_chocolate_brick").save();
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
	public static final ItemEntry<Item> CHOCOLATE_LEAF = create("chocolate_leaf").save();
	public static final ItemEntry<Item> WHITE_CHOCOLATE_LEAF = create("white_chocolate_leaf").save();
	public static final ItemEntry<Item> CARAMEL_LEAF = create("caramel_leaf").save();
	public static final ItemEntry<Item> CANDIED_CHERRY_LEAF = create("candied_cherry_leaf").save();
	public static final ItemEntry<Item> MAGICAL_LEAF = create("magical_leaf").save();
	public static final ItemEntry<Item> CRANFISH = create("cranfish").save();
	public static final ItemEntry<Item> CRANFISH_COOKED = create("cranfish_cooked").save();
	public static final ItemEntry<Item> CRANFISH_SCALE = create("cranfish_scale").save();
	public static final ItemEntry<ItemNameBlockItem> DRAGIBUS = create("dragibus", (p) -> new ItemNameBlockItem(DRAGIBUS_CROPS.get(), p)).save();
	public static final ItemEntry<ItemNameBlockItem> LOLLIPOP_SEEDS = create("lollipop_seeds", (p) -> new ItemNameBlockItem(LOLLIPOP_STEM.get(), p)).save();
	
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
	public static final ItemEntry<Item> RECORD_o = createRecord("record_o", CJukeboxSound.CD_MINE)
			.modifyProperties(p -> p.rarity(Rarity.EPIC))
			.setCtab(false)
			.save();
	public static final ItemEntry<Item> RECORD_1 = createRecord("record_1", CJukeboxSound.CD_1).save();
	public static final ItemEntry<Item> RECORD_2 = createRecord("record_2", CJukeboxSound.CD_2).save();
	public static final ItemEntry<Item> RECORD_3 = createRecord("record_3", CJukeboxSound.CD_3).save();
	public static final ItemEntry<Item> RECORD_4 = createRecord("record_4", CJukeboxSound.CD_4).save();
	public static final ItemEntry<Item> GINGERBREAD_EMBLEM = create("gingerbread_emblem").save();
	public static final ItemEntry<Item> JELLY_EMBLEM = create("jelly_emblem").save();
	public static final ItemEntry<Item> SKY_EMBLEM = create("sky_emblem").save();
	public static final ItemEntry<Item> CHEWING_GUM_EMBLEM = create("chewing_gum_emblem").save();
	public static final ItemEntry<Item> HONEYCOMB_EMBLEM = create("honeycomb_emblem").save();
	public static final ItemEntry<Item> CRANBERRY_EMBLEM = create("cranberry_emblem").save();
	public static final ItemEntry<Item> NESSIE_EMBLEM = create("nessie_emblem").save();
	public static final ItemEntry<Item> SUGUARD_EMBLEM = create("suguard_emblem").save();
	public static final ItemEntry<ForkItem> FORK = create("fork", ForkItem::new).setProperties(new Properties().stacksTo(1)).save();
	
	public static final ItemEntry<HoneycombArrowItem> HONEYCOMB_ARROW = create("honeycomb_arrow", HoneycombArrowItem::new).save();
	public static final ItemEntry<CaramelBowItem> CARAMEL_BOW = create("caramel_bow", CaramelBowItem::new).setProperties(new Properties().stacksTo(1).durability(384)).save();
	public static final ItemEntry<LicoriceSpearItem> LICORICE_SPEAR = create("licorice_spear", LicoriceSpearItem::new).setProperties(new Properties().stacksTo(1).durability(300)).save();
	//HELPER.single(CARAMEL_CROSSBOW, ItemCaramelCrossbow::new);
	
	public static final ItemEntry<StandingAndWallBlockItem> HONEYCOMB_TORCH_ITEM = create(HONEYCOMB_TORCH.getName(), p -> new StandingAndWallBlockItem(HONEYCOMB_TORCH.get(), WALL_HONEYCOMB_TORCH.get(), p, Direction.DOWN)).save();
	
	/*流体*/
	public static final ItemEntry<MobBucketItem> CRANFISH_BUCKET = create("cranfish_bucket", p -> _mob_bucket(CEntities.CRANFISH::get, () -> WATER, () -> SoundEvents.BUCKET_EMPTY_FISH, p)).setProperties(new Properties().stacksTo(1)).save();
	//	public static final ItemEntry<CaramelBucketItem> CARAMEL_BUCKET = create("caramel_bucket", CaramelBucketItem::new).setProperties(new Properties().stacksTo(1)).save();
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
	public static final ItemEntry<IIDebugItem> IIDEBUG = create("iidebug", higher(IIDebugItem::new))
			.setCtab(false)
			.save();
	
	//HELPER.single(GRENADINE_BUCKET, () -> new BucketItem(CFluidEntries.GRENADINE_STATIC, defaultItemProperties().stacksTo(1)), GENERATED);
	
	
	static {
		if (isClient()) {
			CandyCraftCE.hookMinecraftSetup(CItems::declareItemProperties);
		}
		
		CCTab.add(Items.SUGAR::getDefaultInstance);
		setTab(CreativeModeTabs.OP_BLOCKS, IIDEBUG::getDefaultInstance);
		
		CBlockEntities.init();
		blockItems.forEach(Supplier::get);
		blockItems = null;
		CEntities.init();
		eggs.forEach(Supplier::get);
		eggs = null;
	}
	
	@Environment(EnvType.CLIENT)
	private static void declareItemProperties() {
		LOGGER.info("declareItemProperties");
		ItemProperties.register(CARAMEL_BOW.get(), ResourceLocation.parse("pull"), (itemStack, clientLevel, livingEntity, i) -> {
			if (livingEntity == null || livingEntity.getUseItem() != itemStack) {
				return 0.0f;
			}
			return (float) (itemStack.getUseDuration(livingEntity) - livingEntity.getUseItemRemainingTicks()) / 20.0f;
		});
		ItemProperties.register(CARAMEL_BOW.get(), ResourceLocation.parse("pulling"), (itemStack, clientLevel, livingEntity, i) -> livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack ? 1.0f : 0.0f);
		ItemProperties.register(LICORICE_SPEAR.get(), ResourceLocation.parse("throwing"), (itemStack, clientLevel, livingEntity, i) -> livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack ? 1.0f : 0.0f);
		
	}
	
	public static void init() {
		//初始化
		CArmorMaterials.init();
		CDataComponents.init();
	}
	
	@Environment(EnvType.CLIENT)
	public static void registerItemColors(BlockColors blockColors, ItemColors itemColors) {
		itemColors.register((item, tintindex) -> PuddingColor.getDefaultPuddingColor()
				, CUSTARD_PUDDING);
		itemColors.register((item, tintindex) -> PuddingColor.getDefaultEnchantColor(),
				MAGICAL_LEAVES, MAGICAL_LEAF);
		
	}
	
	/**
	 * 仅仅是为了创造模式物品栏排序
	 */
	public static void hookBlockItems(List<Supplier<ItemEntry<BlockItem>>> items) {
		blockItems = items;
	}
	
	/**
	 * 仅仅是为了创造模式物品栏排序
	 */
	public static void hookEntityEggs(List<Supplier<ItemEntry<SpawnEggItem>>> items) {
		eggs = items;
	}
	
	//===========模板===========
	private static <I> Function<Properties, I> higher(Supplier<I> sup) {
		return (p) -> sup.get();
	}
	
	private static CItemBuilder<Item> createRecord(String name, ResourceKey<JukeboxSong> song) {
		return create(name, (p) -> _recordItem(song, p))
				.setProperties(new Properties().stacksTo(1));
	}
	//工具
	
	private static CItemBuilder<SwordItem> createSword(String name, Tier tier, int baseDamage, float baseSpeed) {
		return create(name, (p) -> new SwordItem(tier, p.attributes(SwordItem.createAttributes(tier, baseDamage, baseSpeed))));
	}
	
	private static CItemBuilder<ShovelItem> createShovel(String name, Tier tier, float baseDamage, float baseSpeed) {
		return create(name, (p) -> new ShovelItem(tier, p.attributes(ShovelItem.createAttributes(tier, baseDamage, baseSpeed))));
	}
	
	private static CItemBuilder<PickaxeItem> createPickaxe(String name, Tier tier, int baseDamage, float baseSpeed) {
		return create(name, (p) -> new PickaxeItem(tier, p.attributes(PickaxeItem.createAttributes(tier, baseDamage, baseSpeed))));
	}
	
	private static CItemBuilder<AxeItem> createAxe(String name, Tier tier, float baseDamage, float baseSpeed) {
		return create(name, (p) -> new AxeItem(tier, p.attributes(AxeItem.createAttributes(tier, baseDamage, baseSpeed))));
	}
	
	private static CItemBuilder<HoeItem> createHoe(String name, Tier tier, int baseDamage, float baseSpeed) {
		return create(name, (p) -> new HoeItem(tier, p.attributes(HoeItem.createAttributes(tier, baseDamage, baseSpeed))));
	}
	
	//盔甲
	private static CItemBuilder<ArmorItem> createHelmet(String name, CArmorMaterials material) {
		return create(name, (p) -> new ArmorItem(material.holder, HELMET, p.durability(HELMET.getDurability(material.durabilityMultiplier))));
	}
	
	private static CItemBuilder<ArmorItem> createChestplate(String name, CArmorMaterials material) {
		return create(name, (p) -> new ArmorItem(material.holder, CHESTPLATE, p.durability(CHESTPLATE.getDurability(material.durabilityMultiplier))));
	}
	
	private static CItemBuilder<ArmorItem> createLeggings(String name, CArmorMaterials material) {
		return create(name, (p) -> new ArmorItem(material.holder, LEGGINGS, p.durability(LEGGINGS.getDurability(material.durabilityMultiplier))));
	}
	
	private static CItemBuilder<ArmorItem> createBoots(String name, CArmorMaterials material) {
		return create(name, (p) -> new ArmorItem(material.holder, BOOTS, p.durability(BOOTS.getDurability(material.durabilityMultiplier))));
	}
	
	//平台差异
	@ExpectPlatform
	public static Item _recordItem(ResourceKey<JukeboxSong> jukeSong, Item.Properties prop) {
		return impossibleCode();
	}
	
	@ExpectPlatform
	private static MobBucketItem _mob_bucket(Supplier<EntityType<? extends Mob>> entityType, Supplier<Fluid> fluid, Supplier<SoundEvent> empty, Properties p) {
		return impossibleCode();
	}
	
	/**
	 * Supplier延迟new
	 */
	@ExpectPlatform
	public static Supplier<ItemEntry<SpawnEggItem>> _spawn_egg(EntityEntry<? extends Mob> entity, int backgroundColor, int highlightColor, @Nullable Item.Properties properties) {
		return impossibleCode();
	}
}
