package cn.breadnicecat.candycraftce.datagen.forge.providers;

import cn.breadnicecat.candycraftce.block.BlockEntry;
import cn.breadnicecat.candycraftce.datagen.forge.providers.lang_branches.EnUsCLanguageProvider;
import cn.breadnicecat.candycraftce.datagen.forge.providers.lang_branches.ZhCnCLanguageProvider;
import cn.breadnicecat.candycraftce.item.CCTab;
import cn.breadnicecat.candycraftce.item.ItemEntry;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import static cn.breadnicecat.candycraftce.block.CBlocks.*;
import static cn.breadnicecat.candycraftce.item.CItems.*;

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

	public CLanguageProvider(PackOutput output) {
		subs = Set.of(
				enProv = new EnUsCLanguageProvider(output),
				zhProv = new ZhCnCLanguageProvider(output)
		);
	}

	protected void addTranslations() {
		add(CCTab.TITLE_KEY, "CandyCraft CE", "糖果世界非官方版");
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
		add("item.candycraftce.record_1.desc", "Caution & Crisis C418 - Sweden (Caution & Crisis Remix)", null);
		addItem(RECORD_2, "Suguard's Secret Record", "姜饼人守卫的私藏唱片");
		add("item.candycraftce.record_2.desc", "Jakim - Every", null);
		addItem(RECORD_3, "Rainbow Record", "彩虹唱片");
		add("item.candycraftce.record_3.desc", "Jean Jacques Perrey - Brazilian Flower", null);
		addItem(RECORD_4, "Licorice beetle's Secret Record", "盐甘草糖甲虫的私藏唱片");
		add("item.candycraftce.record_4.desc", "Little End - Rain travel in the MineCraft", null);
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
		addItem(I_I_DEBUG, "I 爱 DEBUG !!!", null);
		/*=======================Blocks=======================*/
		addBlockById(SUGAR_BLOCK, "糖块");
		addBlockById(CARAMEL_PORTAL, "焦糖传送门");
		addBlockById(CARAMEL_BLOCK, "焦糖块");
		addBlockById(CHOCOLATE_STONE, "巧克力石头");
		addBlockById(CHOCOLATE_COBBLESTONE, "巧克力圆石");
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


	}

	public void addItemById(ItemEntry<?> ie, String zh_cn) {
		addItem(ie, byId(ie.getID().getPath()), zh_cn);
	}

	public void addItem(ItemEntry<?> ie, String en_us, String zh_cn) {
		add(ie.get().getDescriptionId(), en_us, zh_cn);
	}

	public void addBlockById(BlockEntry<?> be, String zh_cn) {
		addItem(be, byId(be.getID().getPath()), zh_cn);
	}

	public void addItem(BlockEntry<?> be, String en_us, String zh_cn) {
		add(be.get().getDescriptionId(), en_us, zh_cn);
	}

	public void add(String key, String en_us, String zh_cn) {
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

	private static String modifyById(String byId) {
		return byId.replace("Pez", "PEZ");
	}

	@Override
	public @NotNull CompletableFuture<?> run(@NotNull CachedOutput output) {
		addTranslations();
		ArrayList<CompletableFuture<?>> ar = new ArrayList<>(subs.size());
		subs.forEach(m -> ar.add(m.run(output)));
		return CompletableFuture.allOf(ar.toArray(new CompletableFuture[0]));
	}

	@Override
	public @NotNull String getName() {
		return "CandyCraft CE Language Provider";
	}
}
