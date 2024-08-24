package cn.breadnicecat.candycraftce.datagen.neoforge.providers.langs;

import cn.breadnicecat.candycraftce.CandyCraftCE;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;


/**
 * Created in 2023/12/30 14:07
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 */
public class EnUsCLanguageProvider extends LanguageProvider {
	public EnUsCLanguageProvider(PackOutput output) {
		super(output, CandyCraftCE.MOD_ID, "en_us");
	}
	
	@Override
	protected void addTranslations() {
	}
}
