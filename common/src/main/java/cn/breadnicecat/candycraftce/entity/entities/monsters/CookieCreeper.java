package cn.breadnicecat.candycraftce.entity.entities.monsters;

import cn.breadnicecat.candycraftce.item.CItems;
import cn.breadnicecat.candycraftce.utils.TickUtils;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

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
	private final int explosionRadius = 3;
	public final AnimationState animationState = new AnimationState();

	public CookieCreeper(EntityType<? extends CookieCreeper> entityType, Level level) {
		super(entityType, level);
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
		ItemStack stack = player.getItemInHand(hand);
		if (stack.is(Items.COOKIE)) {
			stack.consume(1, player);
			ignite();
			return InteractionResult.sidedSuccess(player.level().isClientSide);
		}
		if (stack.is(CItems.LOLLIPOP.get())) {
			stack.consume(1, player);
			superIgnite();
			return InteractionResult.sidedSuccess(player.level().isClientSide);
		}
		return InteractionResult.PASS;
	}

	@Override
	public void explodeCreeper() {
		if (level() instanceof ServerLevel level) {
			float modifier = this.isPowered() ? 6F : 1F;
			this.dead = true;
			float radius = (float) this.explosionRadius * modifier;
			level.explode(this, this.getX(), this.getY(), this.getZ(), radius, Level.ExplosionInteraction.MOB);
			this.spawnLingeringCloud(radius);
			this.triggerOnDeathMobEffects(RemovalReason.KILLED);
			if (isPowered()) {
				this.spawnAtLocation(new ItemStack(Items.COOKIE, 32));
			}
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