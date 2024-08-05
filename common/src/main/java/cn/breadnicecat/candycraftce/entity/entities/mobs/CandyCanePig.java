package cn.breadnicecat.candycraftce.entity.entities.mobs;

import cn.breadnicecat.candycraftce.entity.CEntities;
import cn.breadnicecat.candycraftce.item.CItems;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

/**
 * Created in 2024/8/1 上午8:50
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 *
 * <p>
 **/
public class CandyCanePig extends Pig {
	private static final Ingredient FOOD_ITEMS = Ingredient.of(CItems.DRAGIBUS);
	
	public CandyCanePig(EntityType<? extends Pig> entityType, Level level) {
		super(entityType, level);
	}
	
	@Override
	protected void registerGoals() {
		super.registerGoals();
//		this.goalSelector.addGoal(4, new TemptGoal(this, 1.2, Ingredient.of(Items.CARROT_ON_A_STICK), false));
		goalSelector.removeAllGoals(goal -> goal instanceof TemptGoal);
		this.goalSelector.addGoal(4, new TemptGoal(this, 1.2, FOOD_ITEMS, false));
	}
	
	@Override
	public boolean isFood(ItemStack stack) {
		return FOOD_ITEMS.test(stack);
	}
	
	@Nullable
	@Override
	public CandyCanePig getBreedOffspring(ServerLevel level, AgeableMob otherParent) {
		return CEntities.CANDY_CANE_PIG.get().create(level);
	}
	
	@Override
	public void thunderHit(ServerLevel level, LightningBolt lightning) {
		//no zombified_piglin
	}
}
