package cn.breadnicecat.candycraftce.item;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import org.jetbrains.annotations.NotNull;

import static cn.breadnicecat.candycraftce.utils.ResourceUtils.prefix;
import static net.minecraft.world.item.enchantment.Enchantment.enchantment;

/**
 * Created in 2024/9/22 01:51
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 *
 * <p>
 **/
public class CEnchantments {
	public static final ResourceKey<Enchantment> DEVOURER = create("devourer");
	
	private static ResourceKey<Enchantment> create(String name) {
		return ResourceKey.create(Registries.ENCHANTMENT, prefix(name));
	}
	
	/**
	 * @throws IllegalStateException registry or enchantment not found
	 */
	@NotNull
	public static Holder<Enchantment> getHolder(ResourceKey<Enchantment> key, RegistryAccess registryAccess) {
		return registryAccess.registryOrThrow(Registries.ENCHANTMENT).getHolderOrThrow(key);
	}
	
	public static void bootstrap(BootstrapContext<Enchantment> context) {
		HolderGetter<Item> items = context.lookup(Registries.ITEM);
		register(context, DEVOURER, enchantment(Enchantment.definition(
						HolderSet.direct(items.getOrThrow(CItems.FORK.getKey())),
						1,
						3,
						Enchantment.dynamicCost(12, 7),
						Enchantment.constantCost(50),
						4)
				)
		);
	}
	
	private static void register(BootstrapContext<Enchantment> context, ResourceKey<Enchantment> key, Enchantment.Builder builder) {
		context.register(key, builder.build(key.location()));
	}
}
