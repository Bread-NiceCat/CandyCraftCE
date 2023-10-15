package cn.breadnicecat.candycraftce.datagen.forge.providers;

import cn.breadnicecat.candycraftce.CandyCraftCE;
import net.minecraft.data.PackOutput;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.LinkedHashSet;
import java.util.function.Consumer;

/**
 * Created in 2023/10/14 22:51
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 * <p>
 */
public class CItemModelProvider extends ItemModelProvider {
	public CItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
		super(output, CandyCraftCE.MOD_ID, existingFileHelper);
	}

	public static final LinkedHashSet<Consumer<CItemModelProvider>> ENTRIES = new LinkedHashSet<>();

	@Override
	protected void registerModels() {
		ENTRIES.forEach(k -> k.accept(this));
	}
}
