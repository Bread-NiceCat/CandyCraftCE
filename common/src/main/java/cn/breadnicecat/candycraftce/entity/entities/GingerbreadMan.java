package cn.breadnicecat.candycraftce.entity.entities;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

/**
 * Created in 2024/7/1 上午9:36
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 *
 * <p>
 **/
public class GingerbreadMan extends PathfinderMob {
	private static final EntityDataAccessor<Integer> JOB_CODE = SynchedEntityData.defineId(GingerbreadMan.class, EntityDataSerializers.INT);
	private static final String JOB_KEY = "job";
	
	public GingerbreadMan(EntityType<? extends PathfinderMob> pEntityType, Level pLevel) {
		super(pEntityType, pLevel);
	}
	
	public static AttributeSupplier.Builder createAttributes() {
		return Mob.createMobAttributes().add(Attributes.MOVEMENT_SPEED, 0.45d);
	}
	
	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.goalSelector.addGoal(1, new PanicGoal(this, 1.5d));
		this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, Player.class, 8f, 1d, 1.5d, EntitySelector.NO_CREATIVE_OR_SPECTATOR::test));
		this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 0.8d));
		this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 8F));
		this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
	}
	
	@Override
	public void addAdditionalSaveData(@NotNull CompoundTag pCompound) {
		super.addAdditionalSaveData(pCompound);
		pCompound.putString(JOB_KEY, getJob().toString().toLowerCase());
	}
	
	@Override
	public void readAdditionalSaveData(@NotNull CompoundTag pCompound) {
		super.readAdditionalSaveData(pCompound);
		if (pCompound.contains(JOB_KEY)) setJob(GingerbreadJob.valueOf(pCompound.getString(JOB_KEY).toUpperCase()));
	}
	
	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		entityData.define(JOB_CODE, GingerbreadJob.NONE.ordinal());
	}
	
	@NotNull
	public GingerbreadJob getJob() {
		return GingerbreadJob.valueOf(entityData.get(JOB_CODE));
	}
	
	public void setJob(GingerbreadJob job) {
		entityData.set(JOB_CODE, job.ordinal());
	}
	
	public enum GingerbreadJob {
		NONE, BLACKSMITH, FARMER, TRAVELLER;
		
		public static GingerbreadJob valueOf(int index) {
			return GingerbreadJob.values()[index];
		}
	}
}
