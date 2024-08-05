package cn.breadnicecat.candycraftce.entity.entities.mobs;

import cn.breadnicecat.candycraftce.entity.CEntities;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.animal.Rabbit;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.awt.*;

import static cn.breadnicecat.candycraftce.item.CItems.LICORICE;

/**
 * Created in 2024/8/5 上午8:19
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 *
 * <p>
 **/
public class Bunny extends Rabbit {
	private static final EntityDataAccessor<Integer> RGB_ID = SynchedEntityData.defineId(Bunny.class, EntityDataSerializers.INT);
	private static final String COLOR_KEY = "color";
	private static final Ingredient FOOD = Ingredient.of(LICORICE);
	
	public Bunny(EntityType<? extends Rabbit> entityType, Level level) {
		super(entityType, level);
	}
	
	@Override
	protected void registerGoals() {
//		this.goalSelector.addGoal(3, new TemptGoal(this, 1.0, Ingredient.of(Items.CARROT, Items.GOLDEN_CARROT, Blocks.DANDELION), false));
		goalSelector.removeAllGoals(g -> g instanceof TemptGoal);
		goalSelector.addGoal(3, new TemptGoal(this, 1.0, FOOD, false));
	}
	
	@Override
	public void setVariant(Variant variant) {
		super.setVariant(variant);
	}
	
	@Nullable
	@Override
	public Bunny getBreedOffspring(ServerLevel level, AgeableMob otherParent) {
		return CEntities.BUNNY.get().create(level);
	}
	
	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(RGB_ID, Color.MAGENTA.getRGB());
	}
	
	@Override
	public boolean isFood(ItemStack stack) {
		return FOOD.test(stack);
	}
	
	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putInt(COLOR_KEY, getRGB());
	}
	
	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		if (compound.contains(COLOR_KEY)) setRGB(compound.getInt(COLOR_KEY));
	}
	
	public int getRGB() {
		return entityData.get(RGB_ID);
	}
	
	public void setRGB(int red, int green, int blue) {
		setRGB(red << 16 | green << 8 | blue);
	}
	
	public void setRGB(int rgb) {
		entityData.set(RGB_ID, rgb);
	}
}

