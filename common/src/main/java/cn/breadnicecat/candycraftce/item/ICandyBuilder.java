package cn.breadnicecat.candycraftce.item;

import net.minecraft.world.food.FoodData;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

/**
 * Created in 2024/10/2 15:06
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 *
 * <p>
 **/
public interface ICandyBuilder<B> {
	
	/**
	 * @param food 调用properties#food
	 *             NOTE: 以食物为主当然要单独开辟一的API啦！
	 * @see Item.Properties#food(FoodProperties)
	 */
	B setFood(FoodProperties food);
	
	/**
	 * @param duration tick
	 */
	B sugarFuel(int duration);
	
	
	/**
	 * @param nutrition  饱食度
	 * @param saturation 饱和度
	 * @apiNote 注意:不是对现有的FoodProperties修饰,而是直接构建一个新的FoodProperties去setFood
	 * @see FoodData#eat(int, float)
	 */
	default B setFood(int nutrition, float saturation) {
		return setFood(nutrition, saturation, null);
	}
	
	/**
	 * @param nutrition  饱食度
	 * @param saturation 饱和度 # 饱和度=2*饱食度*饱和度修饰符,这里已经进行转化
	 * @see FoodData#eat(int, float)
	 */
	default B setFood(int nutrition, float saturation, @Nullable Consumer<FoodProperties.Builder> modifier) {
		float saturationModifier = saturation / 2f / nutrition;
		FoodProperties.Builder builder = new FoodProperties.Builder()
				.nutrition(nutrition).saturationModifier(saturationModifier);
		if (modifier != null) modifier.accept(builder);
		return setFood(builder.build());
	}
	
}
