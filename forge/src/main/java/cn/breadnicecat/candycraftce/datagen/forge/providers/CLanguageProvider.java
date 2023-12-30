package cn.breadnicecat.candycraftce.datagen.forge.providers;

import cn.breadnicecat.candycraftce.datagen.forge.providers.lang_branches.EnUsCLanguageProvider;
import cn.breadnicecat.candycraftce.datagen.forge.providers.lang_branches.ZhCnCLanguageProvider;
import cn.breadnicecat.candycraftce.registration.item.CCTab;
import cn.breadnicecat.candycraftce.registration.item.ItemEntry;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import static cn.breadnicecat.candycraftce.registration.item.CItems.*;

/**
 * Created in 2023/8/22 21:05
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
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
		addById(LICORICE, "盐甘草糖");
		addById(HONEYCOMB, "蜜蜡");
		addById(HONEYCOMB_SHARD, "蜜蜡碎片");
		addById(PEZ, "皮礼士糖");
		addById(MARSHMALLOW_STICK, "棉花软糖木棍");
		addById(SUGAR_CRYSTAL, "冰糖");
		addById(COTTON_CANDY, "棉花糖");
		addById(GUMMY, "软糖");
		addById(HOT_GUMMY, "熟软糖");
		addById(CHOCOLATE_COIN, "巧克力币");
		addById(NOUGAT_POWDER, "牛轧糖");
		addById(PEZ_DUST, "皮礼士糖粉");
		addById(WAFFLE, "华夫饼");
		addById(WAFFLE_NUGGET, "华夫饼碎屑");
		addById(CANDIED_CHERRY, "蜜饯樱桃");
		addById(CANDY_CANE, "拐杖糖");
		addById(CHEWING_GUM, "口香糖");
		addById(LOLLIPOP, "棒棒糖");
		addById(CRANFISH_SCALE, "蔓越莓鱼鱼鳞");
		addById(CRANFISH, "蔓越莓鱼");
		add(CRANFISH_COOKED, "Cooked Cranfish", "烤蔓越莓鱼");
		addById(JELLY_SENTRY_KEY, "果冻守卫的钥匙");
		addById(JELLY_BOSS_KEY, "果冻国王的钥匙");
		add(RECORD_1, "Jelly Queen's Secret Record", "果冻皇后的私藏唱片");
		add("item.candycraftce.record_1.desc", "Caution & Crisis C418 - Sweden (Caution & Crisis Remix)", null);
		add(RECORD_2, "Suguard's Secret Record", "姜饼人守卫的私藏唱片");
		add("item.candycraftce.record_2.desc", "Jakim - Every", null);
		add(RECORD_3, "Rainbow Record", "彩虹唱片");
		add("item.candycraftce.record_3.desc", "Jean Jacques Perrey - Brazilian Flower", null);
		add(RECORD_4, "Licorice beetle's Secret Record", "盐甘草糖甲虫的私藏唱片");
		add("item.candycraftce.record_4.desc", "Little End - Rain travel in the MineCraft", null);
		addById(GINGERBREAD_EMBLEM, "姜饼人徽章");
		addById(JELLY_EMBLEM, "果冻徽章");
		addById(SKY_EMBLEM, "天空徽章");
		addById(CHEWING_GUM_EMBLEM, "口香糖徽章");
		addById(HONEYCOMB_EMBLEM, "蜜蜡徽章");
		addById(CRANBERRY_EMBLEM, "蔓越莓徽章");
		addById(NESSIE_EMBLEM, "尼斯徽章");
		addById(SUGUARD_EMBLEM, "姜饼人守卫徽章");
		addById(HONEYCOMB_ARROW, "蜜蜡箭");
		addById(CARAMEL_BOW, "焦糖弓");
		addById(MARSHMALLOW_SWORD, "棉花软糖木剑");
		addById(MARSHMALLOW_SHOVEL, "棉花软糖木锹");
		addById(MARSHMALLOW_PICKAXE, "棉花软糖木镐");
		addById(MARSHMALLOW_AXE, "棉花软糖木斧");
		addById(MARSHMALLOW_HOE, "棉花软糖木锄");
		addById(LICORICE_SWORD, "盐甘草糖剑");
		addById(LICORICE_SHOVEL, "盐甘草糖锹");
		addById(LICORICE_PICKAXE, "盐甘草糖镐");
		addById(LICORICE_AXE, "盐甘草糖斧");
		addById(LICORICE_HOE, "盐甘草糖锄");
		addById(HONEYCOMB_SWORD, "蜜蜡剑");
		addById(HONEYCOMB_SHOVEL, "蜜蜡锹");
		addById(HONEYCOMB_PICKAXE, "蜜蜡镐");
		addById(HONEYCOMB_AXE, "蜜蜡斧");
		addById(HONEYCOMB_HOE, "蜜蜡锄");
		addById(PEZ_SWORD, "皮礼士糖剑");
		addById(PEZ_SHOVEL, "皮礼士糖锹");
		addById(PEZ_PICKAXE, "皮礼士糖镐");
		addById(PEZ_AXE, "皮礼士糖斧");
		addById(PEZ_HOE, "皮礼士糖锄");
		addById(LICORICE_HELMET, "盐甘草糖头盔");
		addById(LICORICE_CHESTPLATE, "盐甘草糖胸甲");
		addById(LICORICE_LEGGINGS, "盐甘草糖护腿");
		addById(LICORICE_BOOTS, "盐甘草糖靴子");
		addById(HONEYCOMB_HELMET, "蜜蜡头盔");
		addById(HONEYCOMB_CHESTPLATE, "蜜蜡胸甲");
		addById(HONEYCOMB_LEGGINGS, "蜜蜡护腿");
		addById(HONEYCOMB_BOOTS, "蜜蜡靴子");
		addById(PEZ_HELMET, "皮礼士糖头盔");
		addById(PEZ_CHESTPLATE, "皮礼士糖胸甲");
		addById(PEZ_LEGGINGS, "皮礼士糖护腿");
		addById(PEZ_BOOTS, "皮礼士糖靴子");
		addById(WATER_MASK, "水下面罩");
		addById(JELLY_CROWN, "果冻国王的皇冠");
		addById(TRAMPOJELLY_BOOTS, "果冻靴子");
		add(I_I_DEBUG, "I 爱 DEBUG !!!", null);
	}

	public void addById(ItemEntry<?> ie, String zh_cn) {
		add(ie, byId(ie.getID().getPath()), zh_cn);
	}

	public void add(ItemEntry<?> ie, String en_us, String zh_cn) {
		add(ie.getItem().getDescriptionId(), en_us, zh_cn);
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
