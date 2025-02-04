package cn.breadnicecat.candycraftce.entity.entities.monsters;

import cn.breadnicecat.candycraftce.particle.CParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

/**
 * Created in 2024/10/3 14:13
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 *
 * <p>
 **/
public class ExplorableJelly extends Slime {
	protected final float explosionRad = 0.5f;
	protected float damage = 2f;
	protected int size = 1;
	protected ExplosionDamageCalculator damageCalculator = new ExplosionDamageCalculator() {
		@Override
		public boolean shouldBlockExplode(Explosion explosion, BlockGetter reader, BlockPos pos, BlockState state, float power) {
			return false;
		}
		
		float radSqr = Mth.square(explosionRad);
		
		@SuppressWarnings("deprecation")
		@Override
		public float getEntityDamageAmount(Explosion explosion, Entity entity) {
			double distanceSqr = entity.distanceToSqr(explosion.center());
			return distanceSqr < radSqr ? damage : (float) (damage * Mth.fastInvSqrt(distanceSqr));
		}
	};
	
	public ExplorableJelly(EntityType<? extends ExplorableJelly> entityType, Level level) {
		super(entityType, level);
	}
	
	@Override
	protected int getJumpDelay() {
		return 4;
	}
	
	@Override
	public void tick() {
		super.tick();
		if (level() instanceof ServerLevel level) {
			if (level.getNearestPlayer(getX(), getY(), getZ(), explosionRad, false) != null) {
				DamageSource damageSource = Explosion.getDefaultDamageSource(level, this);
				level.explode(this, damageSource, damageCalculator, getX(), getY(), getZ(), explosionRad, true, Level.ExplosionInteraction.MOB);
				this.playSound(SoundEvents.SLIME_ATTACK, 1.0f, (this.random.nextFloat() - this.random.nextFloat()) * 0.2f + 1.0f);
				this.dead = true;
				this.triggerOnDeathMobEffects(RemovalReason.KILLED);
				discard();
			}
		}
	}
	
	@Override
	public void setSize(int size, boolean resetHealth) {
		if (size != this.size) {
			super.setSize(this.size = size, resetHealth);
		}
	}
	
	@Override
	protected @NotNull ParticleOptions getParticleType() {
		return CParticles.EMPTY_TYPE.get();
	}
	
	@Override
	protected void dealDamage(LivingEntity livingEntity) {
		//仅爆炸造成伤害
	}
	
	@Override
	protected boolean isDealsDamage() {
		return false;
	}
	
	public static AttributeSupplier.Builder createAttributes() {
		return Monster.createMonsterAttributes();
	}
}
