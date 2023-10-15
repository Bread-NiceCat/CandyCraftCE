package cn.breadnicecat.candycraftce.datagen.forge;

import cn.breadnicecat.candycraftce.CandyCraftCE;
import cn.breadnicecat.candycraftce.datagen.forge.providers.CLanguageProvider;
import cn.breadnicecat.candycraftce.registration.item.CCTab;
import cn.breadnicecat.candycraftce.registration.item.CItemTags;
import cn.breadnicecat.candycraftce.registration.item.ItemEntry;
import cn.breadnicecat.candycraftce.registration.sound.CSoundEvents;
import cn.breadnicecat.candycraftce.registration.sound.SoundEntry;
import cn.breadnicecat.candycraftce.utils.CLogUtils;
import net.minecraft.tags.ItemTags;

import java.util.function.Function;

import static cn.breadnicecat.candycraftce.registration.item.CItems.*;
import static cn.breadnicecat.candycraftce.utils.ResourceUtils.prefix;

/**
 * Created in 2023/9/9 21:42
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 */
public class CDatagens {
	static {
		CLogUtils.sign();
	}

	public static void init() {
	}

	private static final Function<String, String> PEZ_MODIFIER = (s) -> s.replace("Pez", "PEZ");

	static {
		CLanguageProvider.ENTRIES.add(c -> c.add(CCTab.TITLE_KEY, CandyCraftCE.MOD_NAME));
		items();
		blocks();
		sounds();
	}

	private static void sounds() {
		sound(CSoundEvents.CD_1).setAsMusicDisc(prefix("cd-1"));
		sound(CSoundEvents.CD_2).setAsMusicDisc(prefix("cd-2"));
		sound(CSoundEvents.CD_3).setAsMusicDisc(prefix("cd-3"));
		sound(CSoundEvents.CD_4).setAsMusicDisc(prefix("cd-4"));
		sound(CSoundEvents.CD_WWWOOOWWW).setAsMusicDisc(prefix("wwwooowww"));
		sound(CSoundEvents.JELLY_STEP).setAsStepSound(prefix("jelly1"), prefix("jelly2"), prefix("jelly3"), prefix("jelly4"));
		sound(CSoundEvents.JELLY_DIG).setAsBreakSound(prefix("jelly1"), prefix("jelly2"));
	}

	private static void items() {
		//I18n,Model,Tags
		item(LICORICE).englishAuto().modelGenerated().tags(CItemTags.LICORICE);
		item(HONEYCOMB).englishAuto().modelGenerated().tags(CItemTags.HONEYCOMB);
		item(HONEYCOMB_SHARD).englishAuto().modelGenerated();
		item(PEZ).englishAuto(PEZ_MODIFIER).modelGenerated().tags(CItemTags.PEZ);
		item(MARSHMALLOW_STICK).englishAuto().modelGenerated();
		item(SUGAR_CRYSTAL).englishAuto().modelGenerated();
		item(COTTON_CANDY).englishAuto().modelGenerated();
		item(GUMMY).englishAuto().modelGenerated();
		item(HOT_GUMMY).englishAuto().modelGenerated();
		item(CHOCOLATE_COIN).englishAuto().modelGenerated();
		item(NOUGAT_POWDER).englishAuto().modelGenerated();
		item(PEZ_DUST).englishAuto(PEZ_MODIFIER).modelGenerated();
		item(WAFFLE).englishAuto().modelGenerated();
		item(WAFFLE_NUGGET).englishAuto().modelGenerated();
		item(CANDIED_CHERRY).englishAuto().modelGenerated();
		item(CANDY_CANE).englishAuto().modelGenerated();
		item(CHEWING_GUM).englishAuto().modelGenerated();
		item(LOLLIPOP).englishAuto().modelGenerated();
		item(CRANFISH_SCALE).englishAuto().modelGenerated();
		item(CRANFISH).englishAuto().modelGenerated();
		item(CRANFISH_COOKED).english("Cooked Cranfish").modelGenerated();
		item(JELLY_SENTRY_KEY).englishAuto().modelGenerated();
		item(JELLY_BOSS_KEY).englishAuto().modelGenerated();


		item(RECORD_1).english("Jelly Queen's Secret Record").tags(ItemTags.MUSIC_DISCS).modelGenerated();
		CLanguageProvider.addEntry("item.candycraftce.record_1.desc", "Caution & Crisis C418 - Sweden (Caution & Crisis Remix)");
		item(RECORD_2).english("Suguard's Secret Record").tags(ItemTags.MUSIC_DISCS).modelGenerated();
		CLanguageProvider.addEntry("item.candycraftce.record_2.desc", "Jakim - Every");
		item(RECORD_3).english("Rainbow Record").tags(ItemTags.MUSIC_DISCS).modelGenerated();
		CLanguageProvider.addEntry("item.candycraftce.record_3.desc", "Jean Jacques Perrey - Brazilian Flower");
		item(RECORD_4).english("Licorice beetle's Secret Record").tags(ItemTags.MUSIC_DISCS).modelGenerated();
		CLanguageProvider.addEntry("item.candycraftce.record_4.desc", "Little End - Rain travel in the MineCraft");
		item(RECORD_WWWOOOWWW).modelGenerated();

	}

	private static void blocks() {
//		builder(CBlocks.TEST_BLOCK).model((p, m) -> m.createGenericCube(p.getFirst()))
//				.forItem().englishAuto();
	}

	private static ItemBuilder item(ItemEntry<?> item) {
		return new ItemBuilder(item);
	}

	private static SoundBuilder sound(SoundEntry sound) {
		return new SoundBuilder(sound);
	}

//	static <B extends Block> BlockBuilder<B> block(BlockEntry<B> block) {
//		return new BlockBuilder<>(block);
//	}

}
