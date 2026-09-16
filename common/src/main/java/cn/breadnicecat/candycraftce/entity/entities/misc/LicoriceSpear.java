package cn.breadnicecat.candycraftce.entity.entities.misc;

import cn.breadnicecat.candycraftce.entity.CEntityTypes;
import cn.breadnicecat.candycraftce.item.CItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created in 2024/7/2 21:15
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 *
 * <p>
 **/
public class LicoriceSpear extends AbstractArrow {
	private static final EntityDataAccessor<Byte> ID_LOYALTY = SynchedEntityData.defineId(LicoriceSpear.class, EntityDataSerializers.BYTE);
	private static final EntityDataAccessor<Boolean> ID_FOIL = SynchedEntityData.defineId(LicoriceSpear.class, EntityDataSerializers.BOOLEAN);
	private boolean dealtDamage;
	public int clientSideReturnTridentTickCount;

	public LicoriceSpear(EntityType<? extends LicoriceSpear> type, Level worldIn) {
		super(type, worldIn);
	}

	public LicoriceSpear(Level world, LivingEntity owner, ItemStack stack) {
		super(CEntityTypes.LICORICE_SPEAR.get(), owner, world, stack, null);
		this.entityData.set(ID_LOYALTY, this.getLoyaltyFromItem(stack));
		this.entityData.set(ID_FOIL, stack.hasFoil());
	}

	public LicoriceSpear(Level world, double x, double y, double z, ItemStack stack) {
		super(CEntityTypes.LICORICE_SPEAR.get(), x, y, z, world, stack, stack);
		this.entityData.set(ID_LOYALTY, this.getLoyaltyFromItem(stack));
		this.entityData.set(ID_FOIL, stack.hasFoil());
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
		builder.define(ID_LOYALTY, (byte)0);
		builder.define(ID_FOIL, false);
	}

	@Override
	public void tick() {
		if (this.inGroundTime > 4) {
			this.dealtDamage = true;
		}

		Entity entity = this.getOwner();
		int i = this.entityData.get(ID_LOYALTY);
		if (i > 0 && (this.dealtDamage || this.isNoPhysics()) && entity != null) {
			if (!this.isAcceptibleReturnOwner()) {
				if (!this.level().isClientSide && this.pickup == AbstractArrow.Pickup.ALLOWED) {
					this.spawnAtLocation(this.getPickupItem(), 0.1F);
				}

				this.discard();
			} else {
				this.setNoPhysics(true);
				Vec3 vec3 = entity.getEyePosition().subtract(this.position());
				this.setPosRaw(this.getX(), this.getY() + vec3.y * 0.015 * (double)i, this.getZ());
				if (this.level().isClientSide) {
					this.yOld = this.getY();
				}

				double d0 = 0.05 * (double)i;
				this.setDeltaMovement(this.getDeltaMovement().scale(0.95).add(vec3.normalize().scale(d0)));
				if (this.clientSideReturnTridentTickCount == 0) {
					this.playSound(SoundEvents.TRIDENT_RETURN, 10.0F, 1.0F);
				}

				this.clientSideReturnTridentTickCount++;
			}
		}

		super.tick();
	}

	private boolean isAcceptibleReturnOwner() {
		Entity entity = this.getOwner();
		return entity == null || !entity.isAlive() ? false : !(entity instanceof ServerPlayer) || !entity.isSpectator();
	}

	public boolean isFoil() {
		return this.entityData.get(ID_FOIL);
	}

	@Nullable
	@Override
	protected EntityHitResult findHitEntity(Vec3 start, Vec3 end) {
		return this.dealtDamage ? null : super.findHitEntity(start, end);
	}

	@Override
	protected void onHitEntity(EntityHitResult result) {
		Entity entity = result.getEntity();
		float f = 6.0F;
		Entity entity1 = this.getOwner();
		DamageSource damagesource = this.damageSources().trident(this, (Entity)(entity1 == null ? this : entity1));
		if (this.level() instanceof ServerLevel serverlevel) {
			f = EnchantmentHelper.modifyDamage(serverlevel, this.getWeaponItem(), entity, damagesource, f);
		}

		this.dealtDamage = true;
		if (entity.hurt(damagesource, f)) {
			if (entity.getType() == EntityType.ENDERMAN) {
				return;
			}

			if (this.level() instanceof ServerLevel serverlevel1) {
				EnchantmentHelper.doPostAttackEffectsWithItemSource(serverlevel1, entity, damagesource, this.getWeaponItem());
			}

			if (entity instanceof LivingEntity livingentity) {
				this.doKnockback(livingentity, damagesource);
				this.doPostHurtEffects(livingentity);
			}
		}

		this.setDeltaMovement(this.getDeltaMovement().multiply(-0.01, -0.1, -0.01));
		this.playSound(SoundEvents.TRIDENT_HIT, 1.0F, 1.0F);
	}

	@Override
	protected void hitBlockEnchantmentEffects(ServerLevel serverLevel, BlockHitResult blockHitResult, ItemStack stack) {
		Vec3 vec3 = blockHitResult.getBlockPos().clampLocationWithin(blockHitResult.getLocation());
		EnchantmentHelper.onHitBlock(
			serverLevel,
			stack,
			this.getOwner() instanceof LivingEntity livingentity ? livingentity : null,
			this,
			null,
			vec3,
			serverLevel.getBlockState(blockHitResult.getBlockPos()),
			flag -> this.kill()
		);
	}

	@Override
	public ItemStack getWeaponItem() {
		return this.getPickupItemStackOrigin();
	}

	@Override
	protected boolean tryPickup(Player player) {
		return super.tryPickup(player) || this.isNoPhysics() && this.ownedBy(player) && player.getInventory().add(this.getPickupItem());
	}
	
	@Override
	protected @NotNull ItemStack getDefaultPickupItem() {
		return CItems.LICORICE_SPEAR.getDefaultInstance();
	}

	@Override
	protected SoundEvent getDefaultHitGroundSoundEvent() {
		return SoundEvents.TRIDENT_HIT_GROUND;
	}

	@Override
	public void playerTouch(Player player) {
		if (this.ownedBy(player) || this.getOwner() == null) {
			super.playerTouch(player);
		}
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		this.dealtDamage = compound.getBoolean("DealtDamage");
		this.entityData.set(ID_LOYALTY, this.getLoyaltyFromItem(this.getPickupItemStackOrigin()));
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putBoolean("DealtDamage", this.dealtDamage);
	}

	private byte getLoyaltyFromItem(ItemStack stack) {
		return this.level() instanceof ServerLevel serverlevel ? (byte)Mth.clamp(EnchantmentHelper.getTridentReturnToOwnerAcceleration(serverlevel, stack, this), 0, 127) : 0;
	}

	@Override
	public void tickDespawn() {
		int i = this.entityData.get(ID_LOYALTY);
		if (this.pickup != AbstractArrow.Pickup.ALLOWED || i <= 0) {
			super.tickDespawn();
		}
	}

	@Override
	protected float getWaterInertia() {
		return 0.99F;
	}

	@Override
	public boolean shouldRender(double x, double y, double z) {
		return true;
	}
}