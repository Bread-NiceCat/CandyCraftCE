package cn.breadnicecat.candycraftce.datagen.forge.providers;

import cn.breadnicecat.candycraftce.block.BlockEntry;
import cn.breadnicecat.candycraftce.datagen.forge.providers.langs.EnUsCLanguageProvider;
import cn.breadnicecat.candycraftce.datagen.forge.providers.langs.ZhCnCLanguageProvider;
import cn.breadnicecat.candycraftce.item.CCTab;
import cn.breadnicecat.candycraftce.item.ItemEntry;
import cn.breadnicecat.candycraftce.misc.CGameRules;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.data.LanguageProvider;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import static cn.breadnicecat.candycraftce.block.CBlocks.*;
import static cn.breadnicecat.candycraftce.item.CItems.*;
import static cn.breadnicecat.candycraftce.utils.CommonUtils.assertTrue;

/**
 * Created in 2023/8/22 21:05
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 * 只生成en_us,剩下的手动补全
 */
public class CLanguageProvider implements DataProvider {
	private final EnUsCLanguageProvider enProv;
	private final ZhCnCLanguageProvider zhProv;
	private final Set<LanguageProvider> subs;
	private final Set<String> existKeys = new HashSet<>();

	public CLanguageProvider(PackOutput output) {
		subs = Set.of(
				enProv = new EnUsCLanguageProvider(output),
				zhProv = new ZhCnCLanguageProvider(output)
		);
	}

	protected void addTranslations() {
		add(CCTab.TITLE_KEY, "CandyCraft CE", "糖果世界非官方版");
		add(CGameRules.CARAMEL_PORTAL_WORKS.getDescriptionId(), "Enable Caramel Portal Teleport Player.", "允许焦糖传送门传送玩家");
		addDamage("step_on_spikes", "%s's feet was pierced.", "%s的脚被刺穿了");
		addDamageBy("step_on_spikes", "%s's feet was accidentally pierced while he escapes from %s", "%s在逃离%s的时候不慎被尖刺刺穿了脚");
		addItemById(LICORICE, "盐甘草糖");
		addItemById(HONEYCOMB, "蜜蜡");
		addItemById(HONEYCOMB_SHARD, "蜜蜡碎片");
		addItemById(PEZ, "皮礼士糖");
		addItemById(MARSHMALLOW_STICK, "棉花软糖木棍");
		addItemById(SUGAR_CRYSTAL, "冰糖");
		addItemById(COTTON_CANDY, "棉花糖");
		addItemById(GUMMY, "软糖");
		addItemById(HOT_GUMMY, "熟软糖");
		addItemById(CHOCOLATE_COIN, "巧克力币");
		addItemById(NOUGAT_POWDER, "牛轧糖");
		addItemById(PEZ_DUST, "皮礼士糖粉");
		addItemById(WAFFLE, "华夫饼");
		addItemById(WAFFLE_NUGGET, "华夫饼碎屑");
		addItemById(CANDIED_CHERRY, "蜜饯樱桃");
		addItemById(CANDY_CANE, "拐杖糖");
		addItemById(CHEWING_GUM, "口香糖");
		addItemById(LOLLIPOP, "棒棒糖");
		addItemById(CRANFISH_SCALE, "蔓越莓鱼鱼鳞");
		addItemById(CRANFISH, "蔓越莓鱼");
		addItem(CRANFISH_COOKED, "Cooked Cranfish", "烤蔓越莓鱼");
		addItemById(JELLY_SENTRY_KEY, "果冻守卫的钥匙");
		addItemById(JELLY_BOSS_KEY, "果冻国王的钥匙");
		addItem(RECORD_1, "Jelly Queen's Secret Record", "果冻皇后的私藏唱片");
		add(RECORD_1.get().getDescriptionId() + ".desc", "Caution & Crisis C418 - Sweden (Caution & Crisis Remix)", null);
		addItem(RECORD_2, "Suguard's Secret Record", "姜饼人守卫的私藏唱片");
		add(RECORD_2.get().getDescriptionId() + ".desc", "Jakim - Every", null);
		addItem(RECORD_3, "Rainbow Record", "彩虹唱片");
		add(RECORD_3.get().getDescriptionId() + ".desc", "Jean Jacques Perrey - Brazilian Flower", null);
		addItem(RECORD_4, "Licorice beetle's Secret Record", "盐甘草糖甲虫的私藏唱片");
		add(RECORD_4.get().getDescriptionId() + ".desc", "Little End - Rain travel in the MineCraft", null);
		addItemById(GINGERBREAD_EMBLEM, "姜饼人徽章");
		addItemById(JELLY_EMBLEM, "果冻徽章");
		addItemById(SKY_EMBLEM, "天空徽章");
		addItemById(CHEWING_GUM_EMBLEM, "口香糖徽章");
		addItemById(HONEYCOMB_EMBLEM, "蜜蜡徽章");
		addItemById(CRANBERRY_EMBLEM, "蔓越莓徽章");
		addItemById(NESSIE_EMBLEM, "尼斯徽章");
		addItemById(SUGUARD_EMBLEM, "姜饼人守卫徽章");
		addItemById(HONEYCOMB_ARROW, "蜜蜡箭");
		addItemById(CARAMEL_BOW, "焦糖弓");
		addItemById(MARSHMALLOW_SWORD, "棉花软糖木剑");
		addItemById(MARSHMALLOW_SHOVEL, "棉花软糖木锹");
		addItemById(MARSHMALLOW_PICKAXE, "棉花软糖木镐");
		addItemById(MARSHMALLOW_AXE, "棉花软糖木斧");
		addItemById(MARSHMALLOW_HOE, "棉花软糖木锄");
		addItemById(LICORICE_SWORD, "盐甘草糖剑");
		addItemById(LICORICE_SHOVEL, "盐甘草糖锹");
		addItemById(LICORICE_PICKAXE, "盐甘草糖镐");
		addItemById(LICORICE_AXE, "盐甘草糖斧");
		addItemById(LICORICE_HOE, "盐甘草糖锄");
		addItemById(HONEYCOMB_SWORD, "蜜蜡剑");
		addItemById(HONEYCOMB_SHOVEL, "蜜蜡锹");
		addItemById(HONEYCOMB_PICKAXE, "蜜蜡镐");
		addItemById(HONEYCOMB_AXE, "蜜蜡斧");
		addItemById(HONEYCOMB_HOE, "蜜蜡锄");
		addItemById(PEZ_SWORD, "皮礼士糖剑");
		addItemById(PEZ_SHOVEL, "皮礼士糖锹");
		addItemById(PEZ_PICKAXE, "皮礼士糖镐");
		addItemById(PEZ_AXE, "皮礼士糖斧");
		addItemById(PEZ_HOE, "皮礼士糖锄");
		addItemById(LICORICE_HELMET, "盐甘草糖头盔");
		addItemById(LICORICE_CHESTPLATE, "盐甘草糖胸甲");
		addItemById(LICORICE_LEGGINGS, "盐甘草糖护腿");
		addItemById(LICORICE_BOOTS, "盐甘草糖靴子");
		addItemById(HONEYCOMB_HELMET, "蜜蜡头盔");
		addItemById(HONEYCOMB_CHESTPLATE, "蜜蜡胸甲");
		addItemById(HONEYCOMB_LEGGINGS, "蜜蜡护腿");
		addItemById(HONEYCOMB_BOOTS, "蜜蜡靴子");
		addItemById(PEZ_HELMET, "皮礼士糖头盔");
		addItemById(PEZ_CHESTPLATE, "皮礼士糖胸甲");
		addItemById(PEZ_LEGGINGS, "皮礼士糖护腿");
		addItemById(PEZ_BOOTS, "皮礼士糖靴子");
		addItemById(WATER_MASK, "水下面罩");
		addItemById(JELLY_CROWN, "果冻国王的皇冠");
		addItemById(TRAMPOJELLY_BOOTS, "果冻靴子");
		addItemById(DRAGIBUS, "彩虹糖");
		addItemById(LOLLIPOP_SEEDS, "棒棒糖种子");
		addItemById(CARAMEL_BUCKET, "熔融焦糖桶");
		addItem(I_I_DEBUG, "I 爱 DEBUG !!!", null);
		/*=======================Blocks=======================*/
		addBlockById(SUGAR_BLOCK, "糖块");
		addBlockById(CARAMEL_PORTAL, "焦糖传送门");
		addBlockById(CARAMEL_BLOCK, "焦糖块");
		addBlockById(CHOCOLATE_STONE, "巧克力石头");
		addBlockById(CHOCOLATE_COBBLESTONE, "巧克力圆石");
		addBlockById(BLACK_CHOCOLATE_STONE, "黑色巧克力石头");
		addBlockById(BLACK_CHOCOLATE_COBBLESTONE, "黑色巧克力圆石");
		addBlockById(WHITE_CHOCOLATE_STONE, "白色巧克力石头");
		addBlockById(WHITE_CHOCOLATE_COBBLESTONE, "白色巧克力圆石");
		addBlockById(PUDDING, "布丁");
		addBlockById(CUSTARD_PUDDING, "奶皮布丁");
		addBlockById(PUDDING_FARMLAND, "布丁耕地");
		addBlockById(CANDY_CANE_BLOCK, "拐杖糖块");
		addBlockById(CANDY_CANE_WALL, "拐杖糖墙");
		addBlockById(CANDY_CANE_FENCE, "拐杖糖栅栏");
		addBlockById(CANDY_CANE_SLAB, "拐杖糖台阶");
		addBlockById(CANDY_CANE_STAIRS, "拐杖糖楼梯");
		addBlockById(MARSHMALLOW_CRAFTING_TABLE, "棉花软糖工作台");
		addBlockById(LICORICE_FURNACE, "盐甘草糖熔炉");
		addBlockById(CHOCOLATE_FURNACE, "巧克力熔炉");
		addBlockById(SUGAR_FACTORY, "制糖机");
		addBlockById(ADVANCED_SUGAR_FACTORY, "高级制糖机");
		addBlockById(ALCHEMY_MIXER, "炼金搅拌器");
		addBlockById(MARSHMALLOW_LOG, "棉花软糖原木");
		addBlockById(LIGHT_MARSHMALLOW_LOG, "浅色棉花软糖原木");
		addBlockById(DARK_MARSHMALLOW_LOG, "深色棉花软糖原木");
		addBlockById(MARSHMALLOW_PLANKS, "棉花软糖木板");
		addBlockById(LIGHT_MARSHMALLOW_PLANKS, "浅色棉花软糖木板");
		addBlockById(CHOCOLATE_LEAVES, "巧克力树叶");
		addBlockById(WHITE_CHOCOLATE_LEAVES, "白巧克力树叶");
		addBlockById(CARAMEL_LEAVES, "焦糖树叶");
		addBlockById(CANDIED_CHERRY_LEAVES, "蜜饯樱桃树叶");
		addBlockById(MAGIC_LEAVES, "魔法树叶");
		addBlockById(HONEYCOMB_TORCH, "蜜蜡火把");
		addBlockById(JELLY_ORE, "果冻矿石");
		addBlockById(NOUGAT_ORE, "牛轧糖矿石");
		addBlockById(LICORICE_ORE, "盐甘草糖矿石");
		addBlockById(HONEYCOMB_ORE, "蜜蜡矿石");
		addBlockById(PEZ_ORE, "皮礼士糖矿石");
		addBlockById(CHOCOLATE_SAPLING, "巧克力树苗");
		addBlockById(WHITE_CHOCOLATE_SAPLING, "白巧克力树苗");
		addBlockById(CARAMEL_SAPLING, "焦糖树苗");
		addBlockById(CANDIED_CHERRY_SAPLING, "蜜饯樱桃树苗");
		addBlockById(LICORICE_BLOCK, "盐甘草糖块");
		addBlockById(LICORICE_WALL, "盐甘草糖墙");
		addBlockById(LICORICE_SLAB, "盐甘草糖台阶");
		addBlockById(LICORICE_STAIRS, "盐甘草糖楼梯");
		addBlockById(LICORICE_BRICK, "盐甘草糖砖");
		addBlockById(LICORICE_BRICK_WALL, "盐甘草糖砖墙");
		addBlockById(LICORICE_BRICK_SLAB, "盐甘草糖砖台阶");
		addBlockById(LICORICE_BRICK_STAIRS, "盐甘草糖砖楼梯");
		addBlockById(MARSHMALLOW_FENCE, "棉花软糖栅栏");
		addBlockById(LIGHT_MARSHMALLOW_FENCE, "浅色棉花软糖栅栏");
		addBlockById(DARK_MARSHMALLOW_FENCE, "深色棉花软糖栅栏");
		addBlockById(MARSHMALLOW_SLAB, "棉花软糖台阶");
		addBlockById(LIGHT_MARSHMALLOW_SLAB, "浅色棉花软糖台阶");
		addBlockById(DARK_MARSHMALLOW_SLAB, "深色棉花软糖台阶");
		addBlockById(MARSHMALLOW_STAIRS, "棉花软糖楼梯");
		addBlockById(LIGHT_MARSHMALLOW_STAIRS, "浅色棉花软糖楼梯");
		addBlockById(DARK_MARSHMALLOW_STAIRS, "深色棉花软糖楼梯");
		addBlockById(NOUGAT_BLOCK, "牛轧糖块");
		addBlockById(NOUGAT_HEAD, "錾制牛轧糖块");
		addBlockById(HONEYCOMB_BLOCK, "蜜蜡块");
		addBlockById(HONEYCOMB_LAMP, "蜜蜡灯");
		addBlockById(PEZ_BLOCK, "皮礼士糖块");
		addBlockById(COTTON_CANDY_WEB, "棉花糖网");
		addBlockById(CHEWING_GUM_PUDDLE, "口香糖片");
		addBlockById(MARSHMALLOW_LADDER, "棉花软糖梯子");
		addBlockById(MARSHMALLOW_DOOR, "棉花软糖门");
		addBlockById(LIGHT_MARSHMALLOW_DOOR, "浅色棉花软糖门");
		addBlockById(DARK_MARSHMALLOW_DOOR, "深色棉花软糖门");
		addBlockById(MARSHMALLOW_FENCE_GATE, "棉花软糖栅栏门");
		addBlockById(LIGHT_MARSHMALLOW_FENCE_GATE, "浅色棉花软糖栅栏门");
		addBlockById(DARK_MARSHMALLOW_FENCE_GATE, "深色棉花软糖栅栏门");
		addBlockById(TRAMPOJELLY, "弹力果冻");
		addBlockById(RED_TRAMPOJELLY, "红色弹力果冻");
		addBlockById(SOFT_TRAMPOJELLY, "软弹力果冻");
		addBlockById(JELLY_SHOCK_ABSORBER, "减震果冻");
		addBlockById(SENSITIVE_JELLY, "敏感果冻");
		addBlockById(SUGAR_SPIKES, "糖刺");
		addBlockById(CRANBERRY_SPIKES, "蔓越莓刺");
		addBlockById(CARAMEL_GLASS, "焦糖玻璃");
		addBlockById(ROUND_CARAMEL_GLASS, "圆形焦糖玻璃");
		addBlockById(DIAMOND_CARAMEL_GLASS, "钻石形焦糖玻璃");
		addBlockById(CARAMEL_GLASS_PANE, "焦糖玻璃板");
		addBlockById(ROUND_CARAMEL_GLASS_PANE, "圆形焦糖玻璃");
		addBlockById(DIAMOND_CARAMEL_GLASS_PANE, "钻石形焦糖玻璃");
		addBlockById(MINT_BLOCK, "水生薄荷块");
		addBlockById(RASPBERRY_BLOCK, "水生树莓块");
		addBlockById(CHEWING_GUM_BLOCK, "口香糖块");
		addBlockById(BANANA_SEAWEEDS_BLOCK, "香蕉海草块");
		addBlockById(COTTON_CANDY_BLOCK, "棉花糖块");
		addBlockById(CANDIED_CHERRY_SACK, "蜜饯樱桃块");
		addBlockById(COTTON_CANDY_SLAB, "棉花糖台阶");
		addBlockById(COTTON_CANDY_STAIRS, "棉花糖楼梯");
		addBlockById(SWEET_GRASS_0, "甜草");
		addBlockById(MINT_SLAB, "水生薄荷台阶");
		addBlockById(RASPBERRY_SLAB, "水生树莓台阶");
		addBlockById(BANANA_SEAWEEDS_SLAB, "香蕉海草台阶");
		addBlockById(CANDIED_CHERRY_SLAB, "蜜饯樱桃台阶");
		addBlockById(CHEWING_GUM_SLAB, "口香糖台阶");
		addBlockById(MINT_STAIRS, "水生薄荷楼梯");
		addBlockById(RASPBERRY_STAIRS, "水生树莓楼梯");
		addBlockById(BANANA_SEAWEEDS_STAIRS, "香蕉海草楼梯");
		addBlockById(CANDIED_CHERRY_STAIRS, "蜜饯樱桃楼梯");
		addBlockById(CHEWING_GUM_STAIRS, "口香糖楼梯");
		addBlockById(MINT, "水生薄荷");
		addBlockById(ROPE_RASPBERRY, "绳状树莓");
		addBlockById(BANANA_SEAWEED, "香蕉海草");
		addBlockById(FRAISE_TAGADA_FLOWER, "果蜜花");
		addBlockById(GOLDEN_SUGAR_FLOWER, "金糖果花");
		addBlockById(ACID_MINT_FLOWER, "酸薄荷花");
		addBlockById(DRAGIBUS_CROPS, "彩虹糖作物");
		addBlockById(LOLLIPOP_STEM, "棒棒糖茎");
		addBlockById(LOLLIPOP_FRUIT, "棒棒糖果实");
		addBlockById(ICE_CREAM, "冰淇淋");
		addBlockById(MINT_ICE_CREAM, "薄荷冰淇淋");
		addBlockById(STRAWBERRY_ICE_CREAM, "草莓冰淇淋");
		addBlockById(BLUEBERRY_ICE_CREAM, "蓝莓冰淇淋");
		addBlockById(ICE_CREAM_SLAB, "冰淇淋台阶");
		addBlockById(MINT_ICE_CREAM_SLAB, "薄荷冰淇淋台阶");
		addBlockById(STRAWBERRY_ICE_CREAM_SLAB, "草莓冰淇淋台阶");
		addBlockById(BLUEBERRY_ICE_CREAM_SLAB, "蓝莓冰淇淋台阶");
		addBlockById(ICE_CREAM_STAIRS, "冰淇淋楼梯");
		addBlockById(MINT_ICE_CREAM_STAIRS, "薄荷冰淇淋楼梯");
		addBlockById(STRAWBERRY_ICE_CREAM_STAIRS, "草莓冰淇淋楼梯");
		addBlockById(BLUEBERRY_ICE_CREAM_STAIRS, "蓝莓冰淇淋楼梯");
		addBlockById(MARSHMALLOW_TRAPDOOR, "棉花软糖活版门");
		addBlockById(LIGHT_MARSHMALLOW_TRAPDOOR, "浅色棉花软糖活版门");
		addBlockById(DARK_MARSHMALLOW_TRAPDOOR, "深色棉花软糖活版门");
		addBlockById(JAWBREAKER_BRICK, "基岩硬糖砖");
		addBlockById(JAWBREAKER_LIGHT, "基岩硬糖灯");
		addBlockById(CARAMEL_LIQUID, "熔融焦糖");
	}

	/**
	 * 包含一个玩家名参数
	 */
	private void addDamage(String msgId, String en_us, String zh_cn) {
		add("death.attack." + msgId, en_us, zh_cn);
	}

	/**
	 * 受其他实体影响而死亡,另外包含一个参数：entity.getKillCredit().getDisplayName()
	 *
	 * @see net.minecraft.world.damagesource.DamageSource#getLocalizedDeathMessage(LivingEntity)
	 */
	private void addDamageBy(String msgId, String en_us, String zh_cn) {
		add("death.attack." + msgId + ".player", en_us, zh_cn);
	}

	public void addItemById(ItemEntry<?> ie, String zh_cn) {
		addItem(ie, byId(ie.getId().getPath()), zh_cn);
	}

	public void addItem(ItemEntry<?> ie, String en_us, String zh_cn) {
		add(ie.get().getDescriptionId(), en_us, zh_cn);
	}

	public void addBlockById(BlockEntry<?> be, String zh_cn) {
		addItem(be, byId(be.getId().getPath()), zh_cn);
	}

	public void addItem(BlockEntry<?> be, String en_us, String zh_cn) {
		add(be.get().getDescriptionId(), en_us, zh_cn);
	}

	public void add(String key, String en_us, String zh_cn) {
		assertTrue(existKeys.add(key), () -> "Duplicate key: " + key);
		enProv.add(key, en_us);
		zhProv.add(key, zh_cn);
	}

	/**
	 * 从注册名中获取名称
	 *
	 * @return 将id中的下划线替换为空格，并且每个空格后第一个字母大写
	 * <p>
	 * 例如{@code 输入this_is_a_example 返回 This Is A Example}
	 */

	private static String byId(String id) {
		StringBuilder sb = new StringBuilder();
		String[] s = id.split("_");
		for (String s1 : s) {
			sb.append(s1.substring(0, 1).toUpperCase()).append(s1.substring(1)).append(" ");
		}
		return modifyById(sb.substring(0, sb.length() - 1));
	}

	private static String modifyById(@NotNull String byId) {
		return byId.replace("Pez", "PEZ");
	}

	@Override
	public @NotNull CompletableFuture<?> run(@NotNull CachedOutput output) {
		addTranslations();
		return CompletableFuture.allOf(subs.stream()
				.map(m -> m.run(output))
				.toArray(CompletableFuture[]::new));
	}

	@Override
	public @NotNull String getName() {
		return "CandyCraftCE Languages";
	}
}
