package cn.breadnicecat.candycraftce.core.items

import cn.breadnicecat.candycraftce.core.tabs.CItemTabs.CANDYCRAFT
import cn.breadnicecat.candycraftce.core.tabs.CItemTabs.add
import cn.breadnicecat.candycraftce.core.tabs.CItemTabs.tab
import cn.breadnicecat.candycraftce.core.tags.CTags.CItemTags
import cn.breadnicecat.candycraftce.data.DataUtils.modelFlat
import cn.breadnicecat.candycraftce.data.DataUtils.modelHandheld
import cn.breadnicecat.candycraftce.data.DataUtils.tag
import cn.breadnicecat.candycraftce.data.DataUtils.translate
import cn.breadnicecat.candycraftce.utils.Arguments
import cn.breadnicecat.candycraftce.utils.OperationRecordable
import cn.breadnicecat.candycraftce.utils.Utils.instance
import cn.breadnicecat.candycraftce.utils.Utils.modLoc
import cn.breadnicecat.candycraftce.utils.Utils.second
import cn.breadnicecat.candycraftce.utils.V
import cn.breadnicecat.candycraftce.utils.V.Companion.v
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.ItemTags
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.effect.MobEffects.CONFUSION
import net.minecraft.world.effect.MobEffects.NIGHT_VISION
import net.minecraft.world.food.FoodProperties
import net.minecraft.world.item.*
import net.minecraft.world.item.Item.Properties
import net.minecraft.world.level.ItemLike
import java.util.*
import java.util.function.Consumer

private typealias ItemBuilderOp<I> = CItems.ItemBuilder<I>.() -> Unit
typealias ItemFactory<I> = Arguments.(Properties) -> I

typealias PropertiesFactory = (Properties) -> Unit

object CItems {
    /*注册规范:
       .translate 翻译
       .model 模型
       .tag 标签
       .food 食用性
       .save 保存
    */
    private val simple = ItemBuilder("*simple", { Item(it) })
        .tab(CANDYCRAFT)
        .modelFlat()

    init {
        CANDYCRAFT.add(Items.SUGAR)
    }

    val licorice = simple.copy("licorice")
        .translate("Licorice", "盐甘草糖")
        .tag(CItemTags.licorice)
        .food(3, 0.3f)
        .save()
    val honeycomb = simple.copy("honeycomb")
        .translate("Honeycomb", "蜜蜡")
        .tag(CItemTags.honeycomb)
        .food(6, 0.1f)
        .save()
    val honeycomb_shard = simple.copy("honeycomb_shard")
        .translate("Honeycomb Shard", "蜜蜡碎片")
        .food(6, 0.1f)
        .save()
    val pez = simple.copy("pez")
        .translate("PEZ", "皮礼士糖")
        .tag(CItemTags.pez)
        .food(10, 0.5f, listOf(1f to NIGHT_VISION.instance(60.second)))
        .save()
    val marshmallow_stick = simple.copy("marshmallow_stick")
        .translate("Marshmallow Stick", "棉花软木木棍")
        .food(1, 1f)
        .save()
    val sugar_crystal = simple.copy("sugar_crystal")
        .translate("Sugar Crystal", "冰糖")
        .food(4, 2f)
        .save()

    val chocolate_brick = simple.copy("chocolate_brick")
        .translate("Chocolate Brick", "巧克力砖")
        .food(2, 1f)
        .save()
    val caramel_brick = chocolate_brick.copy("caramel_brick")
        .translate("Caramel Brick", "焦糖砖")
        .save()
    val white_chocolate_brick = chocolate_brick.copy("white_chocolate_brick")
        .translate("White Chocolate Brick", "白巧克力砖")
        .save()
    val cotton_candy = chocolate_brick.copy("cotton_candy")
        .translate("Cotton Candy", "棉花糖")
        .save()

    val gummy = simple.copy("gummy")
        .translate("Gummy", "软糖")
        .food(2, 1f, listOf(0.8f to CONFUSION.instance(10.second)))
        .save()
    val hot_gummy = simple.copy("hot_gummy")
        .translate("Hot Gummy", "熟软糖")
        .food(2, 1f)
        .save()
    val chocolate_coin = simple.copy("chocolate_coin")
        .translate("Chocolate Coin", "巧克力币")
        .food(2, 1f)
        .save()

    val nougat_powder = simple.copy("nougat_powder")
        .translate("Nougat Powder", "牛轧糖粉")
        .food(2, 1f, fastEat = true)
        .save()
    val pez_dust = simple.copy("pez_dust")
        .translate("PEZ Dust", "皮礼士糖粉")
        .food(2, 1f, listOf(1f to NIGHT_VISION.instance((60 / 9f).second)), fastEat = true)
        .save()
    val waffle = simple.copy("waffle")
        .translate("Waffle", "华夫饼")
        .food(2, 1f)
        .save()
    val waffle_nugget = simple.copy("waffle_nugget")
        .translate("Waffle Nugget", "华夫饼碎屑")
        .food(2, 1f)
        .save()
    val candied_cherry = simple.copy("candied_cherry")
        .translate("Candied Cherry", "蜜饯樱桃")
        .food(2, 1f)
        .save()
    val candy_cane = simple.copy("candy_cane")
        .translate("Candy Cane", "拐杖糖")
        .food(2, 1f)
        .save()
    val chewing_gum = simple.copy("chewing_gum")
        .translate("Chewing Gum", "口香糖")
        .food(2, 1f)
        .save()
    val lollipop = simple.copy("lollipop")
        .translate("Lollipop", "棒棒糖")
        .food(2, 1f)
        .save()

    //叶子
    private val leaf = simple.copy("*leaf")
        .tag(CItemTags.leaf)
        .food(2, 0.5f)

    val chocolate_leaf = leaf.copy("chocolate_leaf")
        .translate("Chocolate Leaf", "巧克力叶子")
        .save()
    val white_chocolate_leaf = leaf.copy("white_chocolate_leaf")
        .translate("White Chocolate Leaf", "白巧克力叶子")
        .save()
    val caramel_leaf = leaf.copy("caramel_leaf")
        .translate("Caramel Leaf", "焦糖叶子")
        .save()
    val candied_cherry_leaf = leaf.copy("candied_cherry_leaf")
        .translate("Candied Cherry Leaf", "蜜饯樱桃叶子")
        .save()
    val magical_leaf = leaf.copy("magical_leaf")
        .translate("Magical Leaf", "魔法叶子")
        .save()

    val cranfish = simple.copy("cranfish")
        .translate("Cranfish", "蔓越莓鱼")
        .food(2, 1f)
        .save()
    val cranfish_cooked = simple.copy("cranfish_cooked")
        .translate("Cranfish Cooked", "烤蔓越莓鱼")
        .food(2, 1f)
        .save()
    val cranfish_scale = simple.copy("cranfish_scale")
        .translate("Cranfish Scale", "蔓越莓鱼鳞")
        .food(2, 1f)
        .save()

    //public static final ItemEntry<ItemNameBlockItem> DRAGIBUS = create("dragibus", (p) -> new ItemNameBlockItem(DRAGIBUS_CROPS.get(), p)).setFood(2, 2f).save();
    //public static final ItemEntry<ItemNameBlockItem> LOLLIPOP_SEEDS = create("lollipop_seeds", (p) -> new ItemNameBlockItem(LOLLIPOP_STEM.get(), p)).setFood(1, 0f).save();

    private val key = simple.copy("*key")
        .tag(CItemTags.keys)
        .modifyProperties {
            it.stacksTo(1)
                .rarity(Rarity.UNCOMMON)
        }

    val jelly_sentry_key = key.copy("jelly_sentry_key")
        .translate("Jelly Sentry Key", "果冻守卫钥匙")
        .save()
    val jelly_boss_key = key.copy("jelly_boss_key")
        .translate("Jelly Boss Key", "果冻国王钥匙")
        .save()

    /*地牢钥匙*/
//HELPER.batch((n, a) -> new ItemDungeonKey(defaultItemProperties().stacksTo(1).rarity(Rarity.RARE), (ItemDungeonKey.DungeonTypes) a[0]),
//                GENERATED)
//        .addElement(JELLY_DUNGEON_KEY, ItemDungeonKey.DungeonTypes.JELLY_DUNGEON)
//        .addElement(BEETLE_DUNGEON_KEY, ItemDungeonKey.DungeonTypes.UNDEFINED)
//        .addElement(SKY_DUNGEON_KEY, ItemDungeonKey.DungeonTypes.UNDEFINED)
//        .addElement(SUGUARD_DUNGEON_KEY, ItemDungeonKey.DungeonTypes.UNDEFINED)
//        .register();
    /*唱片*/
//public static final ItemEntry<RecordItem> RECORD_o = create("record_o",
//			p -> _record_o(Redstone.SIGNAL_MAX, CSoundEvents.CD_o, p, 2 * 60 + 8))
//			.setProperties(new Properties().stacksTo(1).rarity(Rarity.EPIC))
//			.setCtab(false)
//			.save();
//	public static final ItemEntry<RecordItem> RECORD_1 = createRecord("record_1", 1, CSoundEvents.CD_1, 316).save();
//	public static final ItemEntry<RecordItem> RECORD_2 = createRecord("record_2", 2, CSoundEvents.CD_2, 98).save();
//	public static final ItemEntry<RecordItem> RECORD_3 = createRecord("record_3", 3, CSoundEvents.CD_3, 112).save();
//	public static final ItemEntry<RecordItem> RECORD_4 = createRecord("record_4", 4, CSoundEvents.CD_4, 188).save();

    //public static final ItemEntry<Item> GINGERBREAD_EMBLEM = create("gingerbread_emblem").save();
//	public static final ItemEntry<Item> JELLY_EMBLEM = create("jelly_emblem").save();
//	public static final ItemEntry<Item> SKY_EMBLEM = create("sky_emblem").save();
//	public static final ItemEntry<Item> CHEWING_GUM_EMBLEM = create("chewing_gum_emblem").save();
//	public static final ItemEntry<Item> HONEYCOMB_EMBLEM = create("honeycomb_emblem").save();
//	public static final ItemEntry<Item> CRANBERRY_EMBLEM = create("cranberry_emblem").save();
//	public static final ItemEntry<Item> NESSIE_EMBLEM = create("nessie_emblem").save();
//	public static final ItemEntry<Item> SUGUARD_EMBLEM = create("suguard_emblem").save();
//	public static final ItemEntry<ForkItem> FORK = create("fork", ForkItem::new).setProperties(new Properties().stacksTo(1)).save();
//
//	public static final ItemEntry<HoneycombArrowItem> HONEYCOMB_ARROW = create("honeycomb_arrow", HoneycombArrowItem::new).save();
//	public static final ItemEntry<CaramelBowItem> CARAMEL_BOW = create("caramel_bow", CaramelBowItem::new).setProperties(new Properties().stacksTo(1).durability(384)).save();
//	public static final ItemEntry<LicoriceSpearItem> LICORICE_SPEAR = create("licorice_spear", LicoriceSpearItem::new).setProperties(new Properties().stacksTo(1).durability(300)).save();
//	//HELPER.single(CARAMEL_CROSSBOW, ItemCaramelCrossbow::new);
//
//	public static final ItemEntry<StandingAndWallBlockItem> HONEYCOMB_TORCH_ITEM = create(HONEYCOMB_TORCH.getName(), p -> new StandingAndWallBlockItem(HONEYCOMB_TORCH.get(), WALL_HONEYCOMB_TORCH.get(), p, Direction.DOWN)).save();
//
//	/*流体*/
//	public static final ItemEntry<MobBucketItem> CRANFISH_BUCKET = create("cranfish_bucket", p -> _mob_bucket(CEntities.CRANFISH::get, () -> WATER, () -> SoundEvents.BUCKET_EMPTY_FISH, p)).setProperties(new Properties().stacksTo(1)).save();
//	//	public static final ItemEntry<CaramelBucketItem> CARAMEL_BUCKET = create("caramel_bucket", CaramelBucketItem::new).setProperties(new Properties().stacksTo(1)).save();
//	/*工具*/
    private val sword = simple.copy("*sword", {
        SwordItem(get("tier"), get("damage"), get("speed"), it)
    })
        .tag(ItemTags.SWORDS)
        .modelHandheld()
    private val shovel = simple.copy("*sword", {
        ShovelItem(get("tier"), get("damage"), get("speed"), it)
    })
        .tag(ItemTags.SHOVELS)
        .modelHandheld()
    private val pickaxe = simple.copy("*sword", {
        PickaxeItem(get("tier"), get("damage"), get("speed"), it)
    })
        .tag(ItemTags.PICKAXES)
        .modelHandheld()
    private val axe = simple.copy("*sword", {
        AxeItem(get("tier"), get("damage"), get("speed"), it)
    })
        .tag(ItemTags.AXES)
        .modelHandheld()
    private val hoe = simple.copy("*sword", {
        HoeItem(get("tier"), get("damage"), get("speed"), it)
    })
        .tag(ItemTags.HOES)
        .modelHandheld()

    val marshmallow_sword = sword.copy("marshmallow_sword")
        .translate("Marshmallow Sword", "棉花软糖木剑")
        .argument("tier", CTiers.MARSHMALLOW)
        .argument("damage", 3)
        .argument("speed", -2.4f)
        .save()
    val marshmallow_shovel = shovel.copy("marshmallow_shovel")
        .translate("Marshmallow Shovel", "棉花软糖木铲")
        .argument("tier", CTiers.MARSHMALLOW)
        .argument("damage", 1.5f)
        .argument("speed", -3f)
        .save()
    val marshmallow_pickaxe = pickaxe.copy("marshmallow_pickaxe")
        .translate("Marshmallow Pickaxe", "棉花软糖木镐")
        .argument("tier", CTiers.MARSHMALLOW)
        .argument("damage", 1)
        .argument("speed", -2.8f)
        .save()
    val marshmallow_axe = axe.copy("marshmallow_axe")
        .translate("Marshmallow Axe", "棉花软糖木斧")
        .argument("tier", CTiers.MARSHMALLOW)
        .argument("damage", 6f)
        .argument("speed", -3.2f)
        .save()
    val marshmallow_hoe = hoe.copy("marshmallow_hoe")
        .translate("Marshmallow Hoe", "棉花软糖木锄")
        .argument("tier", CTiers.MARSHMALLOW)
        .argument("damage", 0)
        .argument("speed", -3.0f)
        .save()

    val licorice_sword = sword.copy("licorice_sword")
        .translate("Licorice Sword", "盐甘草糖剑")
        .argument("tier", CTiers.LICORICE)
        .argument("damage", 3)
        .argument("speed", -2.4f)
        .save()
    val licorice_shovel = shovel.copy("licorice_shovel")
        .translate("Licorice Shovel", "盐甘草糖铲")
        .argument("tier", CTiers.LICORICE)
        .argument("damage", 1.5f)
        .argument("speed", -3f)
        .save()
    val licorice_pickaxe = pickaxe.copy("licorice_pickaxe")
        .translate("Licorice Pickaxe", "盐甘草糖镐")
        .argument("tier", CTiers.LICORICE)
        .argument("damage", 1)
        .argument("speed", -2.8f)
        .save()
    val licorice_axe = axe.copy("licorice_axe")
        .translate("Licorice Axe", "盐甘草糖斧")
        .argument("tier", CTiers.LICORICE)
        .argument("damage", 7f)
        .argument("speed", -3.2f)
        .save()
    val licorice_hoe = hoe.copy("licorice_hoe")
        .translate("Licorice Hoe", "盐甘草糖锄")
        .argument("tier", CTiers.LICORICE)
        .argument("damage", -1)
        .argument("speed", -2f)
        .save()

    val honeycomb_sword = sword.copy("honeycomb_sword")
        .translate("Honeycomb Sword", "蜜蜡剑")
        .argument("tier", CTiers.HONEYCOMB)
        .argument("damage", 3)
        .argument("speed", -2.4f)
        .save()
    val honeycomb_shovel = shovel.copy("honeycomb_shovel")
        .translate("Honeycomb Shovel", "蜜蜡铲")
        .argument("tier", CTiers.HONEYCOMB)
        .argument("damage", 1.5f)
        .argument("speed", -3f)
        .save()
    val honeycomb_pickaxe = pickaxe.copy("honeycomb_pickaxe")
        .translate("Honeycomb Pickaxe", "蜜蜡镐")
        .argument("tier", CTiers.HONEYCOMB)
        .argument("damage", 1)
        .argument("speed", -2.8f)
        .save()
    val honeycomb_axe = axe.copy("honeycomb_axe")
        .translate("Honeycomb Axe", "蜜蜡斧")
        .argument("tier", CTiers.HONEYCOMB)
        .argument("damage", 7f)
        .argument("speed", -3.2f)
        .save()
    val honeycomb_hoe = hoe.copy("honeycomb_hoe")
        .translate("Honeycomb Hoe", "蜜蜡锄")
        .argument("tier", CTiers.HONEYCOMB)
        .argument("damage", -1)
        .argument("speed", -2f)
        .save()

    val pez_sword = sword.copy("pez_sword")
        .translate("Pez Sword", "皮礼士糖剑")
        .argument("tier", CTiers.PEZ)
        .argument("damage", 3)
        .argument("speed", -2.4f)
        .save()
    val pez_shovel = shovel.copy("pez_shovel")
        .translate("Pez Shovel", "皮礼士糖铲")
        .argument("tier", CTiers.PEZ)
        .argument("damage", 1.5f)
        .argument("speed", -3f)
        .save()
    val pez_pickaxe = pickaxe.copy("pez_pickaxe")
        .translate("Pez Pickaxe", "皮礼士糖镐")
        .argument("tier", CTiers.PEZ)
        .argument("damage", 1)
        .argument("speed", -2.8f)
        .save()
    val pez_axe = axe.copy("pez_axe")
        .translate("Pez Axe", "皮礼士糖斧")
        .argument("tier", CTiers.PEZ)
        .argument("damage", 5f)
        .argument("speed", -3f)
        .save()
    val pez_hoe = hoe.copy("pez_hoe")
        .translate("Pez Hoe", "皮礼士糖锄")
        .argument("tier", CTiers.PEZ)
        .argument("damage", 5)
        .argument("speed", -3f)
        .save()

    private val armor = simple.copy("*armor", {
        ArmorItem(get("material"), get("slot"), it)
    })
    private val helmet = simple.copy("*helmet")
        .argument("slot", ArmorItem.Type.HELMET)
    private val chestplate = simple.copy("*chestplate")
        .argument("slot", ArmorItem.Type.CHESTPLATE)
    private val leggings = simple.copy("*leggings")
        .argument("slot", ArmorItem.Type.LEGGINGS)
    private val boots = simple.copy("*boots")
        .argument("slot", ArmorItem.Type.BOOTS)

    val licorice_helmet = helmet.copy("licorice_helmet")
        .translate("Licorice Helmet", "盐甘草糖头盔")
        .argument("material", CMaterials.LICORICE)
        .save()
    val licorice_chestplate = chestplate.copy("licorice_chestplate")
        .translate("Licorice Chestplate", "盐甘草糖胸甲")
        .argument("material", CMaterials.LICORICE)
        .save()
    val licorice_leggings = leggings.copy("licorice_leggings")
        .translate("Licorice Leggings", "盐甘草糖护腿")
        .argument("material", CMaterials.LICORICE)
        .save()
    val licorice_boots = boots.copy("licorice_boots")
        .translate("Licorice Boots", "盐甘草糖靴")
        .argument("material", CMaterials.LICORICE)
        .save()
    val honeycomb_helmet = helmet.copy("honeycomb_helmet")
        .translate("Honeycomb Helmet", "蜜蜡头盔")
        .argument("material", CMaterials.HONEYCOMB)
        .save()
    val honeycomb_chestplate = chestplate.copy("honeycomb_chestplate")
        .translate("Honeycomb Chestplate", "蜜蜡胸甲")
        .argument("material", CMaterials.HONEYCOMB)
        .save()
    val honeycomb_leggings = leggings.copy("honeycomb_leggings")
        .translate("Honeycomb Leggings", "蜜蜡护腿")
        .argument("material", CMaterials.HONEYCOMB)
        .save()
    val honeycomb_boots = boots.copy("honeycomb_boots")
        .translate("Honeycomb Boots", "蜜蜡靴")
        .argument("material", CMaterials.HONEYCOMB)
        .save()
    val pez_helmet = helmet.copy("pez_helmet")
        .translate("Pez Helmet", "皮礼士糖头盔")
        .argument("material", CMaterials.PEZ)
        .save()
    val pez_chestplate = chestplate.copy("pez_chestplate")
        .translate("Pez Chestplate", "皮礼士糖胸甲")
        .argument("material", CMaterials.PEZ)
        .save()
    val pez_leggings = leggings.copy("pez_leggings")
        .translate("Pez Leggings", "皮礼士糖护腿")
        .argument("material", CMaterials.PEZ)
        .save()
    val pez_boots = boots.copy("pez_boots")
        .translate("Pez Boots", "皮礼士糖靴")
        .argument("material", CMaterials.PEZ)
        .save()
    val trampojelly_boots = boots.copy("trampojelly_boots")
        .translate("Trampojelly Boots", "减震果冻靴")
        .argument("material", CMaterials.TRAMPOJELLY_BOOTS)
        .save()
    val water_mask = helmet.copy("water_mask")
        .translate("Water Mask", "水下面罩")
        .argument("material", CMaterials.WATER_MASK)
        .save()
    val jelly_crown = helmet.copy("jelly_crown")
        .translate("Jelly Crown", "果冻皇冠")
        .argument("material", CMaterials.JELLY_CROWN)
        .save()

    //	/*Armors*/
//	//Licorice
//	public static final ItemEntry<ArmorItem> LICORICE_HELMET = createHelmet("licorice_helmet", CArmorMaterials.LICORICE).save();
//	public static final ItemEntry<ArmorItem> LICORICE_CHESTPLATE = createChestplate("licorice_chestplate", CArmorMaterials.LICORICE).save();
//	public static final ItemEntry<ArmorItem> LICORICE_LEGGINGS = createLeggings("licorice_leggings", CArmorMaterials.LICORICE).save();
//	public static final ItemEntry<ArmorItem> LICORICE_BOOTS = createBoots("licorice_boots", CArmorMaterials.LICORICE).save();
//	//HoneyComb
//	public static final ItemEntry<ArmorItem> HONEYCOMB_HELMET = createHelmet("honeycomb_helmet", CArmorMaterials.HONEYCOMB).save();
//	public static final ItemEntry<ArmorItem> HONEYCOMB_CHESTPLATE = createChestplate("honeycomb_chestplate", CArmorMaterials.HONEYCOMB).save();
//	public static final ItemEntry<ArmorItem> HONEYCOMB_LEGGINGS = createLeggings("honeycomb_leggings", CArmorMaterials.HONEYCOMB).save();
//	public static final ItemEntry<ArmorItem> HONEYCOMB_BOOTS = createBoots("honeycomb_boots", CArmorMaterials.HONEYCOMB).save();
//	//PEZ
//	public static final ItemEntry<ArmorItem> PEZ_HELMET = createHelmet("pez_helmet", CArmorMaterials.PEZ).save();
//	public static final ItemEntry<ArmorItem> PEZ_CHESTPLATE = createChestplate("pez_chestplate", CArmorMaterials.PEZ).save();
//	public static final ItemEntry<ArmorItem> PEZ_LEGGINGS = createLeggings("pez_leggings", CArmorMaterials.PEZ).save();
//	public static final ItemEntry<ArmorItem> PEZ_BOOTS = createBoots("pez_boots", CArmorMaterials.PEZ).save();
//	//Sp(p) -> new Item
//	public static final ItemEntry<ArmorItem> WATER_MASK = createHelmet("water_mask", CArmorMaterials.WATER_MASK).save();
//	public static final ItemEntry<ArmorItem> JELLY_CROWN = createHelmet("jelly_crown", CArmorMaterials.JELLY_CROWN).save();
//	public static final ItemEntry<ArmorItem> TRAMPOJELLY_BOOTS = createBoots("trampojelly_boots", CArmorMaterials.TRAMPOJELLY_BOOTS)
//			.save();
//	public static final ItemEntry<IIDebugItem> IIDEBUG = create("iidebug", higher(IIDebugItem::new))
//			.setCtab(false)
//			.save();
//
    class ItemBuilder<I : Item>(
        val id: String,
        val factory: ItemFactory<I>,
        internal var propBuilder: PropertiesFactory = {},
    ) : OperationRecordable<ItemBuilder<I>>() {

        private val lateUsage = LinkedList<Consumer<Entry<I>>>()
        private val arguments = Arguments.Builder()
        fun food(
            nut: Int,
            satMod: Float,
            mobEffect: List<Pair<Float, MobEffectInstance>> = listOf(),
            fastEat: Boolean = false,
            alwaysEat: Boolean = false,
        ): ItemBuilder<I> {
            record("food") {
                modifyProperties {
                    it.food(
                        FoodProperties.Builder()
                            .nutrition(nut)
                            .saturationMod(satMod)
                            .apply {
                                if (fastEat) fast()
                                if (alwaysEat) alwaysEat()
                                mobEffect.forEach { (probability, effect) ->
                                    effect(effect, probability)
                                }
                            }
                            .build()
                    )
                }
            }
            return this
        }

        /*==============================
                   Core Zone
        ==============================*/
        fun modifyProperties(action: PropertiesFactory): ItemBuilder<I> {
            record("modifyProperties", overridable = false) {
                val prop = propBuilder
                propBuilder = { prop(it); action(it) }
            }
            return this
        }

        fun argument(key: String, value: Any?): ItemBuilder<I> {
            record("argument", overridable = false) {
                if (value == null) arguments.remove(key)
                else arguments[key] = value
            }
            return this
        }

        fun arguments(vararg args: Pair<String, Any?>): ItemBuilder<I> {
            args.forEach { (key, value) ->
                argument(key, value)
            }
            return this
        }

        /**
         * 在注册后调用
         * */
        fun lateUsage(action: (Entry<I>) -> Unit) {
            check(!frozen)
            lateUsage.add(action)
        }

        fun save(): Entry<I> {
            executeRecords()
            val item = register(id.modLoc()) {
                factory(arguments.build(), Properties().apply(propBuilder))
            }
            val entry = Entry(id.modLoc(), item, this.v())
            lateUsage.forEach { it.accept(entry) }
            return entry
        }

        fun copy(
            id: String,
            factory: ItemFactory<I> = this.factory,
            properties: PropertiesFactory = this.propBuilder,
            withRecord: Boolean = true,
        ): ItemBuilder<I> {
            val new = ItemBuilder(id, factory, properties)
            new.arguments.putAll(arguments)
            if (withRecord) {
                new.copyRecord(this)
            }
            return new
        }

        override val receiver: ItemBuilder<I>
            get() = this

        companion object {
            @Suppress("UNCHECKED_CAST")
            private fun <I : Item> register(
                id: ResourceLocation,
                factory: () -> I,
            ): I {
                return Items.registerItem(id, factory()) as I
            }

        }
    }

    class Entry<I : Item>(
        val id: ResourceLocation,
        val item: I,
        private val builder: V<ItemBuilder<I>>,
    ) : ItemLike {
        operator fun component1() = id
        operator fun component2() = item

        override fun asItem(): Item = item
        fun copy(
            id: String,
            factory: ItemFactory<I> = builder.get().factory,
            properties: PropertiesFactory = builder.get().propBuilder,
            withRecord: Boolean = true,
        ) = builder.get().copy(id, factory, properties, withRecord)

        //        val defaultInstance: ItemStack get() = item.defaultInstance
        fun getDefaultInstance(): ItemStack = item.defaultInstance
    }

}