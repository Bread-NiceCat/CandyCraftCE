package cn.breadnicecat.candycraftce.item;

import cn.breadnicecat.candycraftce.CandyCraftCE;
import cn.breadnicecat.candycraftce.block.BlockEntry;
import com.mojang.datafixers.util.Pair;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static cn.breadnicecat.candycraftce.utils.CommonUtils.assertTrue;
import static cn.breadnicecat.candycraftce.utils.CommonUtils.impossibleCode;
import static cn.breadnicecat.candycraftce.utils.ResourceUtils.prefix;

/**
 * Created in 2023/10/28 23:32
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 */
public class CItemBuilder<I extends Item> {
	private final String name;
	private Function<Properties, I> factory;
	public Properties properties = new Properties();
	public boolean ctab = true;
	
	public static <I extends Item> CItemBuilder<I> create(String name, Function<Properties, I> factory) {
		return new CItemBuilder<>(name, factory);
	}
	
	public static CItemBuilder<Item> create(String name) {
		return create(name, Item::new);
	}
	
	
	protected CItemBuilder(String name, Function<Properties, I> factory) {
		this.name = name;
		this.factory = factory;
	}
	
	public static CItemBuilder<BlockItem> block(BlockEntry<? extends Block> block) {
		ResourceLocation id = block.getId();
		assertTrue(id.getNamespace().equals(CandyCraftCE.MOD_ID), () -> "wrong namespace: " + id.getNamespace() + ", require equ " + CandyCraftCE.MOD_ID);
		return create(id.getPath(), (p) -> new BlockItem(block.get(), p));
	}
	
	
	/**
	 * 新的Properties
	 * 注:默认直接是新的Properties
	 */
	public CItemBuilder<I> setProperties(@NotNull Properties prop) {
		this.properties = Objects.requireNonNull(prop);
		return this;
	}
	
	
	/**
	 * @param food 调用properties#food
	 *             NOTE: 以美食为主当然要单独开辟一的API啦！
	 * @see Properties#food(FoodProperties)
	 */
	public CItemBuilder<I> setFood(FoodProperties food) {
		properties.food(food);
		return this;
	}
	
	/**
	 * @param nutrition  饱食度
	 * @param saturation 饱和度 # 饱和度=2*饱食度*饱和度修饰符,这里已经进行转化
	 * @param modifier   可以为null,对FoodProperties进行最后的修饰
	 *                   <p>注意:不是对现有的FoodProperties修饰,而是直接赋值
	 * @see FoodData#eat(int, float)
	 */
	public CItemBuilder<I> setFood(int nutrition, int saturation, @Nullable Consumer<FoodProperties> modifier) {
		float saturationModifier = saturation / 2f / nutrition;
		FoodProperties food = new FoodProperties.Builder()
				.nutrition(nutrition).saturationMod(saturationModifier).build();
		if (modifier != null) modifier.accept(food);
		return setFood(food);
	}
	
	public CItemBuilder<I> setCtab(boolean ctab) {
		this.ctab = ctab;
		return this;
	}

//	public CItemBuilder<I> setTab(ResourceKey<CreativeModeTab> tab) {
//		return this;
//	}
	
	public ItemEntry<I> save() {
		ItemEntry<I> entry = register(name, () -> factory.apply(properties));
		CItems.ITEMS.add(entry);
		if (ctab) CCTab.add(entry);
		return entry;
	}
	
	private static <I extends Item> @NotNull ItemEntry<I> register(String name, Supplier<I> factory) {
		Pair<ResourceKey<Item>, Supplier<I>> pair = CandyCraftCE.register(BuiltInRegistries.ITEM, prefix(name), factory);
		return new ItemEntry<>(pair);
	}
	
	@SafeVarargs
	@ExpectPlatform
	public static void setTab(ResourceKey<CreativeModeTab> tab, Supplier<ItemStack>... stack){
		impossibleCode();
	}
	
}
