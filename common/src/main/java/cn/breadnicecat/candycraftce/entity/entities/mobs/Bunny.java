package cn.breadnicecat.candycraftce.entity.entities.mobs;

import cn.breadnicecat.candycraftce.entity.CEntityTypes;
import cn.breadnicecat.candycraftce.utils.CommonUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Rabbit;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.Objects;

import static cn.breadnicecat.candycraftce.item.CItems.LICORICE;

/**
 * Created in 2024/8/5 上午8:19
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 * @see Rabbit
 * <p>
 **/
public class Bunny extends Rabbit {
	private static final EntityDataAccessor<Integer> COLOR_ID = SynchedEntityData.defineId(Bunny.class, EntityDataSerializers.INT);
	public static final String COLOR_KEY = "color";
	public static final Ingredient FOOD = Ingredient.of(LICORICE);
	
	public Bunny(EntityType<? extends Bunny> entityType, Level level) {
		super(entityType, level);
		if (!level.isClientSide) {
			setColor(CommonUtils.RANDOM.nextInt(0xffffff));
		}
	}
	
	public static AttributeSupplier.@NotNull Builder createAttributes() {
		return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 3.0)
				.add(Attributes.MOVEMENT_SPEED, 0.3)
				.add(Attributes.ATTACK_DAMAGE, 3.0);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new FloatGoal(this));
//		this.goalSelector.addGoal(1, new Rabbit.RabbitPanicGoal(this, 2.2));
		this.goalSelector.addGoal(1, new PanicGoal(this, 2.2));
		this.goalSelector.addGoal(2, new BreedGoal(this, 0.8));
		this.goalSelector.addGoal(3, new TemptGoal(this, 1.0, FOOD, false));
		this.goalSelector.addGoal(4, new AvoidEntityGoal<>(this, Player.class, 8.0f, 2.2, 2.2));
		this.goalSelector.addGoal(4, new AvoidEntityGoal<>(this, Monster.class, 4.0F, 2.2, 2.2));
//		this.goalSelector.addGoal(4, new RabbitAvoidEntityGoal(this, Player.class, 8.0F, 2.2, 2.2));
//		this.goalSelector.addGoal(4, new RabbitAvoidEntityGoal(this, Wolf.class, 10.0F, 2.2, 2.2));
//		this.goalSelector.addGoal(4, new RabbitAvoidEntityGoal(this, Monster.class, 4.0F, 2.2, 2.2));
//		this.goalSelector.addGoal(5, new Rabbit.RaidGardenGoal(this));
		this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 0.6));
		this.goalSelector.addGoal(11, new LookAtPlayerGoal(this, Player.class, 10.0F));
	}
	
	
	@Nullable
	@Override
	public Bunny getBreedOffspring(ServerLevel level, AgeableMob otherParent) {
		Bunny bunny = Objects.requireNonNull(CEntityTypes.BUNNY.get().create(level), "create bunny");
		int color = (level.random.nextBoolean() ? this : (Bunny) otherParent).getColor();
		bunny.setColor(color);
		return bunny;
	}
	
	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
		builder.define(COLOR_ID, Color.WHITE.getRGB());
	}
	
	
	@Override
	public boolean isFood(ItemStack stack) {
		return FOOD.test(stack);
	}
	
	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putInt(COLOR_KEY, getColor());
	}
	
	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		if (compound.contains(COLOR_KEY)) setColor(compound.getInt(COLOR_KEY));
	}
	
	
	public int getColor() {
		return entityData.get(COLOR_ID);
	}
	
	public void setColor(int rgb) {
		entityData.set(COLOR_ID, rgb);
	}
}

