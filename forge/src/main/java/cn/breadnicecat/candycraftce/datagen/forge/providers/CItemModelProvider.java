package cn.breadnicecat.candycraftce.datagen.forge.providers;

import cn.breadnicecat.candycraftce.CandyCraftCE;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * Created in 2023/10/14 22:51
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 * <p>
 */
public class CItemModelProvider extends ItemModelProvider {
	public static final ResourceLocation HANDHELD = new ResourceLocation("item/handheld");

	public CItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
		super(output, CandyCraftCE.MOD_ID, existingFileHelper);
	}

	public static final LinkedHashSet<Consumer<CItemModelProvider>> ENTRIES = new LinkedHashSet<>();

	@Override
	protected void registerModels() {
		ENTRIES.forEach(k -> k.accept(this));
	}

	public ItemModelBuilder handheldItem(Item item) {
		return basicItem(Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(item)));
	}

	public ItemModelBuilder handheldItem(@NotNull ResourceLocation item) {
		return getBuilder(item.toString())
				.parent(new ModelFile.UncheckedModelFile("item/generated"))
				.texture("layer0", new ResourceLocation(item.getNamespace(), "item/" + item.getPath()));
	}

}
