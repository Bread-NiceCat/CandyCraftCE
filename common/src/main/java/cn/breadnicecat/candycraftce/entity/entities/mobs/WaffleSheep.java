package cn.breadnicecat.candycraftce.entity.entities.mobs;

import cn.breadnicecat.candycraftce.entity.CEntityTypes;
import cn.breadnicecat.candycraftce.entity.ai.WaffleEatBlockGoal;
import cn.breadnicecat.candycraftce.item.CItems;
import cn.breadnicecat.candycraftce.utils.LevelUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.player.Player;
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
		this.eatBlockGoal = new WaffleEatBlockGoal(this);
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.goalSelector.addGoal(1, new PanicGoal(this, 1.25));
		this.goalSelector.addGoal(2, new BreedGoal(this, 1.0));
//		this.goalSelector.addGoal(3, new TemptGoal(this, 1.1, (itemStack) -> itemStack.is(ItemTags.SHEEP_FOOD), false));
		this.goalSelector.addGoal(3, new TemptGoal(this, 1.1, FOOD, false));
		this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.1));
		this.goalSelector.addGoal(5, this.eatBlockGoal);
		this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 1.0F));
		this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
		this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
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
	public @NotNull DyeColor getColor() {
		//disable color system
		return ORANGE;
	}
	
	@Override
	public void setColor(DyeColor dyeColor) {
		//disable color system
	}
	
	@Override
	public boolean hurt(DamageSource source, float amount) {
		boolean hurt = super.hurt(source, amount);
		if (hurt & random.nextBoolean()) {
			LevelUtils.spawnItemEntity(level(), position(), CItems.WAFFLE_NUGGET.getDefaultInstance());
		}
		return hurt;
	}
	
	@Override
	public boolean isSheared() {
		//disable fur system
		//disable color system
		return true;
	}
}
