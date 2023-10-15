package cn.breadnicecat.candycraftce.registration.item;

import cn.breadnicecat.candycraftce.registration.sound.CSoundEvents;
import cn.breadnicecat.candycraftce.registration.sound.SoundEntry;
import cn.breadnicecat.candycraftce.utils.CLogUtils;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.RecordItem;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.redstone.Redstone;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static cn.breadnicecat.candycraftce.utils.CommonUtils.assertTrue;
import static cn.breadnicecat.candycraftce.utils.ResourceUtils.prefix;

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
	}

	public static final HashMap<ResourceLocation, ItemEntry<? extends Item>> ITEMS = new HashMap<>();
	public static final ItemEntry<Item> LICORICE = simpleItem("licorice").save();
	public static final ItemEntry<Item> HONEYCOMB = simpleItem("honeycomb").save();
	public static final ItemEntry<Item> HONEYCOMB_SHARD = simpleItem("honeycomb_shard").save();
	public static final ItemEntry<Item> PEZ = simpleItem("pez").save();
	public static final ItemEntry<Item> MARSHMALLOW_STICK = simpleItem("marshmallow_stick").save();
	public static final ItemEntry<Item> SUGAR_CRYSTAL = simpleItem("sugar_crystal").save();
	public static final ItemEntry<Item> COTTON_CANDY = simpleItem("cotton_candy").save();
	public static final ItemEntry<Item> GUMMY = simpleItem("gummy").save();
	public static final ItemEntry<Item> HOT_GUMMY = simpleItem("hot_gummy").save();
	public static final ItemEntry<Item> CHOCOLATE_COIN = simpleItem("chocolate_coin").save();
	public static final ItemEntry<Item> NOUGAT_POWDER = simpleItem("nougat_powder").save();
	public static final ItemEntry<Item> PEZ_DUST = simpleItem("pez_dust").save();
	public static final ItemEntry<Item> WAFFLE = simpleItem("waffle").save();
	public static final ItemEntry<Item> WAFFLE_NUGGET = simpleItem("waffle_nugget").save();
	public static final ItemEntry<Item> CANDIED_CHERRY = simpleItem("candied_cherry").save();
	public static final ItemEntry<Item> CANDY_CANE = simpleItem("candy_cane").save();
	public static final ItemEntry<Item> CHEWING_GUM = simpleItem("chewing_gum").save();
	public static final ItemEntry<Item> LOLLIPOP = simpleItem("lollipop").save();
	public static final ItemEntry<Item> CRANFISH = simpleItem("cranfish").save();
	public static final ItemEntry<Item> CRANFISH_COOKED = simpleItem("cranfish_cooked").save();
	public static final ItemEntry<Item> CRANFISH_SCALE = simpleItem("cranfish_scale").save();
	//HELPER.single(DRAGIBUS, () -> new ItemCustomNamedBlockItem(DRAGIBUS_CROPS.getBlock(), defaultItemProperties()),GENERATED);
//HELPER.single(LOLLIPOP_SEEDS, () -> new ItemCustomNamedBlockItem(LOLLIPOP_STEM.getBlock(), defaultItemProperties()),GENERATED);
	//地牢门钥匙
	public static final ItemEntry<Item> JELLY_SENTRY_KEY = of("jelly_sentry_key", () -> new Item(new Properties().stacksTo(1).rarity(Rarity.UNCOMMON))).ctab().save();
	public static final ItemEntry<Item> JELLY_BOSS_KEY = copyFrom("jelly_boss_key", JELLY_SENTRY_KEY).save();
	//地牢钥匙
//HELPER.batch((n, a) -> new ItemDungeonKey(defaultItemProperties().stacksTo(1).rarity(Rarity.RARE), (ItemDungeonKey.DungeonTypes) a[0]),
//                GENERATED)
//        .addElement(JELLY_DUNGEON_KEY, ItemDungeonKey.DungeonTypes.JELLY_DUNGEON)
//        .addElement(BEETLE_DUNGEON_KEY, ItemDungeonKey.DungeonTypes.UNDEFINED)
//        .addElement(SKY_DUNGEON_KEY, ItemDungeonKey.DungeonTypes.UNDEFINED)
//        .addElement(SUGUARD_DUNGEON_KEY, ItemDungeonKey.DungeonTypes.UNDEFINED)
//        .register();

	public static final ItemEntry<RecordItem> RECORD_1 = of("record_1", () -> _recordItem(1, CSoundEvents.CD_1, new Properties().stacksTo(1), 316)).ctab().save();
	public static final ItemEntry<RecordItem> RECORD_2 = of("record_2", () -> _recordItem(2, CSoundEvents.CD_2, new Properties().stacksTo(1), 98)).ctab().save();
	public static final ItemEntry<RecordItem> RECORD_3 = of("record_3", () -> _recordItem(3, CSoundEvents.CD_3, new Properties().stacksTo(1), 112)).ctab().save();
	public static final ItemEntry<RecordItem> RECORD_4 = of("record_4", () -> _recordItem(4, CSoundEvents.CD_4, new Properties().stacksTo(1), 188)).ctab().save();
	public static final ItemEntry<RecordItem> RECORD_WWWOOOWWW = of("record_wwwooowww", () -> _record_wwwooowww(Redstone.SIGNAL_MAX, CSoundEvents.CD_WWWOOOWWW, new Properties().stacksTo(1).rarity(Rarity.EPIC), 302, "BreadNiceCat's Secret Record", "Mono Inc. - Children of the Dark")).save();


	//HOOKS

	@ExpectPlatform
	private static RecordItem _recordItem(int analog, SoundEntry evt, Properties prop, int lengthInSeconds) {
		throw new AssertionError();
	}

	@ExpectPlatform
	private static RecordItem _record_wwwooowww(int analog, SoundEntry evt, Properties prop, int lengthInSeconds, String nameInGame, String musicName) {
		throw new AssertionError();
	}

//	public static final ItemEntry<BlockItem> TEST_BLOCK = simpleBlockItem(CBlocks.TEST_BLOCK).save();

	/**
	 * 自动与CFoods挂钩
	 */
	private static ItemBuilder<Item> simpleItem(String name) {
		final var ref = new Object() {
			Supplier<FoodProperties> prop;
		};
		ItemBuilder<Item> builder = of(name, () ->
				new Item(new Properties().food(ref.prop.get()))).ctab();
		ref.prop = () -> CFoods.getFoodProperties(builder.name);
		return builder;
	}

	public static <I extends Item> ItemBuilder<I> of(String name, Supplier<I> item) {
		return new ItemBuilder<>(prefix(name), item);
	}

	public static <I extends Item> ItemBuilder<I> copyFrom(String name, ItemEntry<I> origin) {
		return new ItemBuilder<>(prefix(name), origin);
	}

	/**
	 * 自动与CFoods挂钩
	 */
	/*=====BLOCK=====*/
//	private static ItemBuilder<BlockItem> simpleBlockItem(BlockEntry<?> block) {
//		final var ref = new Object() {
//			FoodProperties prop;
//		};
//		ItemBuilder<BlockItem> builder = ofBlock(block, (t) ->
//				new BlockItem(t.getBlock(), new Item.Properties().food(ref.prop))).ctab();
//		ref.prop = CFoods.getFoodProperties(builder.name);
//		return builder;
//	}

//	private static <B extends Block, BI extends BlockItem> ItemBuilder<BI> ofBlock(BlockEntry<B> block, Function<BlockEntry<B>, BI> item) {
//		return new ItemBuilder<>(block.getID(), () -> item.apply(block));
//	}
	public static void init() {
	}

	/**
	 * Created in 2023/8/11 9:03
	 * Project: candycraftce
	 *
	 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
	 */
	public static class ItemBuilder<I extends Item> {

		private static final HashMap<ItemEntry<?>, ItemBuilder<?>> FOR_COPY = new HashMap<>();

		private final Supplier<I> sup;
		private final ResourceLocation name;
		private final LinkedList<Consumer<ItemEntry<I>>> consumers;

		private boolean isSaved = false;


		private ItemBuilder(ResourceLocation name, Supplier<I> sup) {
			this.name = Objects.requireNonNull(name);
			this.sup = Objects.requireNonNull(sup);
			consumers = new LinkedList<>();
		}

		@SuppressWarnings("unchecked")
		private ItemBuilder(ResourceLocation name, ItemEntry<I> copyFrom) {
			this.name = Objects.requireNonNull(name);
			ItemBuilder<I> co = (ItemBuilder<I>) FOR_COPY.get(copyFrom);
			this.sup = Objects.requireNonNull(co.sup);
			consumers = new LinkedList<>(co.consumers);
		}

		public ItemBuilder<I> accept(Consumer<ItemEntry<I>> consumer) {
			checkState();
			consumers.add(consumer);
			return this;
		}

		public ItemBuilder<I> ctab() {
			return accept(CCTab::add);
		}

		public <B extends Block & ItemLike> ItemEntry<I> save() {
			checkState();
			isSaved = true;

			ItemEntry<I> entry = register(name, sup);
			assertTrue(ITEMS.put(name, entry) == null, "Duplicate name: " + name);
			FOR_COPY.put(entry, this);
			consumers.forEach(k -> k.accept(entry));
			return entry;
		}

		private void checkState() {
			assertTrue(!isSaved, "saved");
		}

		@ExpectPlatform
		private static <I extends Item> ItemEntry<I> register(ResourceLocation name, Supplier<I> sup) {
			throw new AssertionError();
		}


	}
}
