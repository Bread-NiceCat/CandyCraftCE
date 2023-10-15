package cn.breadnicecat.candycraftce.datagen.forge.providers;

import cn.breadnicecat.candycraftce.CandyCraftCE;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Consumer;

/**
 * Created in 2023/8/22 21:05
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 * <p>
 * 只生成en_us,剩下的手动补全
 */
public class CLanguageProvider extends LanguageProvider {


	public static final Set<Consumer<CLanguageProvider>> ENTRIES = new LinkedHashSet<>();

	public CLanguageProvider(PackOutput output) {
		super(output, CandyCraftCE.MOD_ID, "en_us");
	}

	public static void addEntry(String key, String value) {
		ENTRIES.add(p -> p.add(key, value));
	}

	@Override
	protected void addTranslations() {
		ENTRIES.forEach(k -> k.accept(this));
	}

	/**
	 * 从注册名中获取名称
	 *
	 * @return 将id中的下划线替换为空格，并且每个空格后第一个字母大写
	 * <p>
	 * 例如{@code 输入this_is_a_example 返回 This Is A Example}
	 */

	public static String byId(String id) {
		StringBuilder sb = new StringBuilder();
		String[] s = id.split("_");
		for (String s1 : s) {
			sb.append(s1.substring(0, 1).toUpperCase()).append(s1.substring(1)).append(" ");
		}
		return sb.substring(0, sb.length() - 1);
	}
}
