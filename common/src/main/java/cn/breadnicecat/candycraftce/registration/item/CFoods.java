package cn.breadnicecat.candycraftce.registration.item;

import cn.breadnicecat.candycraftce.utils.CLogUtils;
import cn.breadnicecat.candycraftce.utils.CommonUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.food.FoodProperties;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

/**
 * Created in 2023/10/1 12:42
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 */
public class CFoods {
	static {
		CLogUtils.sign();
	}

	private static final HashMap<ResourceLocation, FoodProperties> PROPERTIES = new HashMap<>();
//
//	public static final FoodProperties PEZ = of(builder(1, 1f).build(),
//			CItems.PEZ);

	/**
	 * nutrition          饱食度
	 * saturationModifier 饱和度修饰符,饱和度=2*饱食度*饱和度修饰符
	 *
	 * @see FoodData#eat(int, float)
	 */
	private static FoodProperties.Builder builder(int nutrition, float saturationModifier) {
		return new FoodProperties.Builder().nutrition(nutrition).saturationMod(saturationModifier);
	}

	private static FoodProperties of(FoodProperties prop, ItemEntry<?>... obj) {
		for (ItemEntry<?> o : obj) {
			CommonUtils.assertTrue(PROPERTIES.put(o.getID(), prop) == null);
		}
		return prop;
	}

	public static @Nullable FoodProperties getFoodProperties(ResourceLocation loca) {
		return PROPERTIES.get(loca);
	}

}
