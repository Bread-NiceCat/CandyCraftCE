package cn.breadnicecat.candycraftce.entity.entities.monsters;

import cn.breadnicecat.candycraftce.utils.LevelUtils;
import cn.breadnicecat.candycraftce.utils.TickUtils;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

import static cn.breadnicecat.candycraftce.item.CItems.LOLLIPOP;

/**
 * Created in 2024/10/2 22:28
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 *
 * <p>
 **/
public class CookieCreeper extends Creeper {
	private static final Ingredient FOOD = Ingredient.of(LOLLIPOP);
	private final int explosionRadius = 3;
	public final AnimationState animationState = new AnimationState();
	
	public CookieCreeper(EntityType<? extends CookieCreeper> entityType, Level level) {
		super(entityType, level);
	}
	
	@Override
	protected void dropCustomDeathLoot(ServerLevel level, DamageSource damageSource, boolean recentlyHit) {
		//不掉头
		//`驯服协定`
		if (isPowered()) {
			ItemStack stack = Items.COOKIE.getDefaultInstance();
			stack.setCount(32);
			LevelUtils.spawnItemEntity(level, position(), stack);
		}
	}
	
	private void superIgnite() {
		this.entityData.set(DATA_IS_POWERED, true);
		this.maxSwell = 6 * TickUtils.TICK_PER_SEC;
		ignite();
	}
	
	@Override
	public void tick() {
		super.tick();
		if (level().isClientSide) {
			animationState.startIfStopped(tickCount);
		}
	}
	
	@Override
	protected @NotNull InteractionResult mobInteract(Player player, InteractionHand hand) {
		ItemStack itemStack = player.getItemInHand(hand);
		if (itemStack.is(Items.COOKIE)) {
			ignite();
			return InteractionResult.sidedSuccess(player.level().isClientSide);
			
		}
		if (getHealth() < getMaxHealth() && FOOD.test(itemStack)) {
			itemStack.shrink(1);
			setHealth(getMaxHealth());
			superIgnite();
			return InteractionResult.sidedSuccess(player.level().isClientSide);
		}
		return InteractionResult.PASS;
	}
	
	@Override
	public void explodeCreeper() {
		if (level() instanceof ServerLevel level) {
			boolean powered = this.isPowered();
			float modifier = powered ? 6F : 1F;
			this.dead = true;
			float radius = (float) this.explosionRadius * modifier;
			level.explode(this, this.getX(), this.getY(), this.getZ(), radius, Level.ExplosionInteraction.MOB);
			this.spawnLingeringCloud(radius);
			this.triggerOnDeathMobEffects(RemovalReason.KILLED);
			this.discard();
		}
	}
	
	private void spawnLingeringCloud(float rad) {
		Collection<MobEffectInstance> collection = this.getActiveEffects();
		if (!collection.isEmpty()) {
			AreaEffectCloud cloud = new AreaEffectCloud(this.level(), this.getX(), this.getY(), this.getZ());
			cloud.setRadius(rad + 0.5f);
			cloud.setRadiusOnUse(-0.5f);
			cloud.setWaitTime(10);
			cloud.setDuration(cloud.getDuration() / 2);
			cloud.setRadiusPerTick(-cloud.getRadius() / (float) cloud.getDuration());
			for (MobEffectInstance mobEffectInstance : collection) {
				cloud.addEffect(new MobEffectInstance(mobEffectInstance));
			}
			this.level().addFreshEntity(cloud);
		}
	}
}
