package cn.breadnicecat.candycraftce.registration.item.forge;

import cn.breadnicecat.candycraftce.forge.CandyCraftCEImpl;
import cn.breadnicecat.candycraftce.registration.item.ItemEntry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

/**
 * Created in 2023/9/9 15:26
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 */
public class CItemsItemBuilderImpl {
	public static final DeferredRegister<Item> REGISTER = CandyCraftCEImpl.createRegister(ForgeRegistries.ITEMS);

	public static <I extends Item> ItemEntry<I> register(ResourceLocation name, Supplier<I> sup) {
		RegistryObject<I> object = REGISTER.register(name.getPath(), sup);
		return new ItemEntry<>(name) {
			@Override
			public I getItem() {
				return object.get();
			}
		};
	}
}
