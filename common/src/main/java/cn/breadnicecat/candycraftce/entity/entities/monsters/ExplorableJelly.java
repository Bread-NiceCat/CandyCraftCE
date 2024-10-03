package cn.breadnicecat.candycraftce.entity.entities.monsters;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.entity.player.Player;
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
	protected ExplosionDamageCalculator damageCalculator = new ExplosionDamageCalculator() {
		@Override
		public boolean shouldBlockExplode(Explosion explosion, BlockGetter reader, BlockPos pos, BlockState state, float power) {
			return false;
		}
		
		@SuppressWarnings("deprecation")
		@Override
		public float getEntityDamageAmount(Explosion explosion, Entity entity) {
			double distance = entity.distanceToSqr(explosion.center());
			return distance < explosionRad ? damage : (float) (damage * Mth.fastInvSqrt(distance));
		}
	};
	protected float explosionRad = 1.5f;
	protected float damage = 4f;
	protected int size = 1;
	
	public ExplorableJelly(EntityType<? extends ExplorableJelly> entityType, Level level) {
		super(entityType, level);
	}
	
	@Override
	public void tick() {
		super.tick();
		Player player = level().getNearestPlayer(getX(), getY(), getZ(), explosionRad, false);
		if (player != null) {
			DamageSource damageSource = Explosion.getDefaultDamageSource(this.level(), this);
			level().explode(this, damageSource, damageCalculator, getX(), getY(), getZ(), explosionRad, true, Level.ExplosionInteraction.MOB);
			this.playSound(SoundEvents.SLIME_ATTACK, 1.0f, (this.random.nextFloat() - this.random.nextFloat()) * 0.2f + 1.0f);
			this.dead = true;
			this.triggerOnDeathMobEffects(RemovalReason.KILLED);
			discard();
		}
	}
	
	@Override
	public void setSize(int size, boolean resetHealth) {
		if (size != this.size) {
			super.setSize(this.size, resetHealth);
		}
	}
	
	@Override
	protected @NotNull ParticleOptions getParticleType() {
		return super.getParticleType();//todo
	}
	
	@Override
	protected void dealDamage(LivingEntity livingEntity) {
		//do not simply attack
	}
	
	@Override
	protected boolean isDealsDamage() {
		return this.isEffectiveAi();
	}
	
	public static AttributeSupplier.Builder createAttributes() {
		return Monster.createMonsterAttributes();
	}
}
