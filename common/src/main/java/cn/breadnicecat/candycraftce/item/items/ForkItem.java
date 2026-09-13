package cn.breadnicecat.candycraftce.item.items;

import cn.breadnicecat.candycraftce.item.CEnchantments;
import cn.breadnicecat.candycraftce.item.CItems;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static cn.breadnicecat.candycraftce.advancements.Triggers.EAT_BLOCK;
import static cn.breadnicecat.candycraftce.block.CBlocks.*;
import static cn.breadnicecat.candycraftce.block.CBlockTags.BT_SUGARY;

/**
 * Created in 2024/5/18 下午9:35
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 */
public class ForkItem extends Item {

	public ForkItem(Properties properties) {
		super(properties);
	}

	@Override
	public @NotNull InteractionResult useOn(@NotNull UseOnContext context) {
		Level level = context.getLevel();
		BlockPos pos = context.getClickedPos();
		BlockState state = level.getBlockState(pos);
		ItemStack stack = context.getItemInHand();
		Player player = context.getPlayer();
		if (state.is(PUDDING.get()) || state.is(CUSTARD_PUDDING.get())) {
			if (level.setBlockAndUpdate(pos, PUDDING_FARMLAND.defaultBlockState())) {
				if (level instanceof ServerLevel sl && context.getPlayer() instanceof ServerPlayer sp) {
					stack.hurtAndBreak(1, sl, sp, i -> {});
					sl.playSound(null, pos, SoundEvents.HOE_TILL, SoundSource.BLOCKS, 1.0F, 1.0F);
				}
				return InteractionResult.sidedSuccess(level.isClientSide());
			}
		}
		return InteractionResult.PASS;
	}

	@Override
	public boolean canAttackBlock(BlockState state, Level level, BlockPos pos, Player player) {
		ItemStack stack = player.getMainHandItem();
		if (state.is(BT_SUGARY.bt()) && EnchantmentHelper.getItemEnchantmentLevel(CEnchantments.getHolder(CEnchantments.DEVOURER, level.registryAccess()), stack) > 0) {
			if (!level.isClientSide() && level instanceof ServerLevel sl && player instanceof ServerPlayer sp) {
				stack.hurtAndBreak(1, sl, sp, i -> {});
				sp.getFoodData().eat(1, 1.0F);
				sl.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.PLAYER_BURP, SoundSource.PLAYERS, 1.0F, 1.0F);
				level.removeBlock(pos, false);
				EAT_BLOCK.trigger(sp);
			}
			return false;
		}
		return super.canAttackBlock(state, level, pos, player);
	}

	@Override
	public int getEnchantmentValue() {
		return 10;
	}

	@Override
	public boolean isValidRepairItem(ItemStack stack, ItemStack material) {
		return material.is(CItems.PEZ.get());
	}

	@Override
	public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		return true;
	}

	@Override
	public void postHurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		stack.hurtAndBreak(1, attacker, EquipmentSlot.MAINHAND);
	}
}