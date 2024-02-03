package cn.breadnicecat.candycraftce.item.items;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

/**
 * Created in 2023/10/29 9:01
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 */
public class ItemHoneycombArrow extends ArrowItem {
	public ItemHoneycombArrow(Properties properties) {
		super(properties);
	}

	@Override
	public @NotNull AbstractArrow createArrow(Level level, ItemStack stack, LivingEntity shooter) {
		//TODO 自定义箭实体
		Arrow arrow = new Arrow(level, shooter);
		arrow.setEffectsFromItem(stack);
		return arrow;
	}
}
