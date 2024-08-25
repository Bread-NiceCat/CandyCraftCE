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
	private static final EntityDataAccessor<Integer> RGB_A_ID = SynchedEntityData.defineId(Bunny.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> RGB_B_ID = SynchedEntityData.defineId(Bunny.class, EntityDataSerializers.INT);
	private static final String COLOR_A_KEY = "color_a";
	private static final String COLOR_B_KEY = "color_b";
	private static final Ingredient FOOD = Ingredient.of(LICORICE);
	
	public Bunny(EntityType<? extends Rabbit> entityType, Level level) {
		super(entityType, level);
		setRGB_A(level.random.nextInt(0xffffff));
		setRGB_B(level.random.nextInt(0xffffff));
	}
	
	@Override
	protected void registerGoals() {
//		this.goalSelector.addGoal(3, new TemptGoal(this, 1.0, Ingredient.of(Items.CARROT, Items.GOLDEN_CARROT, Blocks.DANDELION), false));
		goalSelector.removeAllGoals(g -> g instanceof TemptGoal || g instanceof RaidGardenGoal);
		goalSelector.addGoal(3, new TemptGoal(this, 1.0, FOOD, false));
	}
	
	@Override
	public void setVariant(Variant variant) {
		super.setVariant(variant);
	}
	
	@Nullable
	@Override
	public Bunny getBreedOffspring(ServerLevel level, AgeableMob otherParent) {
		Bunny bunny = CEntities.BUNNY.get().create(level);
		bunny.setRGB_A(getRGB_A());
		bunny.setRGB_B(((Bunny) otherParent).getRGB_B());
		return bunny;
	}
	
	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
		builder.define(RGB_A_ID, Color.WHITE.getRGB());
		builder.define(RGB_B_ID, Color.WHITE.getRGB());
	}
	
	
	@Override
	public boolean isFood(ItemStack stack) {
		return FOOD.test(stack);
	}
	
	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putInt(COLOR_A_KEY, getRGB());
		compound.putInt(COLOR_B_KEY, getRGB());
	}
	
	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		if (compound.contains(COLOR_A_KEY)) setRGB_A(compound.getInt(COLOR_A_KEY));
		if (compound.contains(COLOR_B_KEY)) setRGB_B(compound.getInt(COLOR_B_KEY));
	}
	
	public int getRGB() {
		return getRGB_A() & getRGB_B();
	}
	
	public int getRGB_A() {
		return entityData.get(RGB_A_ID);
	}
	
	public int getRGB_B() {
		return entityData.get(RGB_B_ID);
	}
	
	public void setRGB_A(int rgb) {
		entityData.set(RGB_A_ID, rgb);
	}
	
	public void setRGB_B(int rgb) {
		entityData.set(RGB_B_ID, rgb);
	}
}

