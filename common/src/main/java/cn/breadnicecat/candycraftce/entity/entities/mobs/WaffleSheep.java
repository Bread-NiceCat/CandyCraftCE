package cn.breadnicecat.candycraftce.entity.entities.mobs;

import cn.breadnicecat.candycraftce.entity.CEntityTypes;
import cn.breadnicecat.candycraftce.item.CItems;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootTable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static net.minecraft.world.item.DyeColor.ORANGE;

/**
 * Created in 2024/8/4 下午10:25
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 *
 * <p>
 **/
public class WaffleSheep extends Sheep {
	
	private static final Ingredient FOOD = Ingredient.of(CItems.CANDIED_CHERRY);
	
	public WaffleSheep(EntityType<? extends Sheep> entityType, Level level) {
		super(entityType, level);
	}
	
	@Override
	protected void registerGoals() {
		super.registerGoals();
//		this.goalSelector.addGoal(3, new TemptGoal(this, 1.1, Ingredient.of(Items.WHEAT), false));
		goalSelector.removeAllGoals(goal -> goal instanceof TemptGoal);
		this.goalSelector.addGoal(3, new TemptGoal(this, 1.1, FOOD, false));
	}
	
	@Override
	public boolean isFood(ItemStack stack) {
		return FOOD.test(stack);
	}
	
	@Override
	public @NotNull ResourceKey<LootTable> getDefaultLootTable() {
		return this.getType().getDefaultLootTable();
	}
	
	@Nullable
	@Override
	public Sheep getBreedOffspring(ServerLevel level, AgeableMob otherParent) {
		return CEntityTypes.WAFFLE_SHEEP.get().create(level);
	}
	
	@Override
	protected void dropFromLootTable(DamageSource damageSource, boolean hitByPlayer) {
		//disable color system
		super.dropFromLootTable(damageSource, hitByPlayer);
	}
	
	@Override
	public @NotNull DyeColor getColor() {
		//disable color system
		return ORANGE;
	}
	
	@Override
	public void setColor(DyeColor dyeColor) {
		//disable color system
	}
	
	@Override
	public boolean isSheared() {
		//disable fur system
		//disable color system
		return true;
	}
}
