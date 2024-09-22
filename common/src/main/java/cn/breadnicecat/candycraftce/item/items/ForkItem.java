package cn.breadnicecat.candycraftce.item.items;

import cn.breadnicecat.candycraftce.item.CEnchantments;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static cn.breadnicecat.candycraftce.block.CBlocks.*;
import static cn.breadnicecat.candycraftce.item.CDataComponents.BLOCK_TO_EAT;
import static cn.breadnicecat.candycraftce.utils.CommonUtils.make;

/**
 * Created in 2024/5/18 下午9:35
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 */
public class ForkItem extends Item {
	
	
	private final String POS_KEY = "pos";
	private final String DURATION_KEY = "duration";
	
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
		if (player == null) {
			return farming(level, state, pos, stack, context);
		}
		
		//`eat` or `farm`
		//`eat` first if enabled
		if (!player.isShiftKeyDown()) {
			InteractionResult result = eat(level, state, pos, player, stack, context);
			if (result.consumesAction()) return result;
			return farming(level, state, pos, stack, context);
		} else {
			InteractionResult result = farming(level, state, pos, stack, context);
			if (result.consumesAction()) return result;
			return eat(level, state, pos, player, stack, context);
		}
	}
	
	public @NotNull InteractionResult farming(Level level, BlockState state, BlockPos pos, ItemStack stack, UseOnContext context) {
		if (state.is(PUDDING.get()) || state.is(CUSTARD_PUDDING.get())) {
			if (level.setBlockAndUpdate(pos, PUDDING_FARMLAND.defaultBlockState())) {
				if (level instanceof ServerLevel sl) {
					stack.hurtAndBreak(1, sl, context.getPlayer() instanceof ServerPlayer p ? p : null, i -> {
					});
				}
				return InteractionResult.sidedSuccess(level.isClientSide());
			}
		}
		return InteractionResult.PASS;
	}
	
	public @NotNull InteractionResult eat(Level level, BlockState state, BlockPos pos, Player player, ItemStack stack, UseOnContext context) {
		int eat = EnchantmentHelper.getItemEnchantmentLevel(CEnchantments.getHolder(CEnchantments.DEVOURER, level.registryAccess()), stack);
		if (eat > 0) {
			FoodProperties food = state.getBlock().asItem().components().get(DataComponents.FOOD);
			if (food != null && player.canEat(food.canAlwaysEat())) {
				stack.set(DataComponents.FOOD, food);
				stack.set(BLOCK_TO_EAT.get(), make(new CompoundTag(), t -> {
					t.putLong(POS_KEY, pos.asLong());
					t.putInt(DURATION_KEY, 32);//1.6s,normal food speed
				}));
				player.startUsingItem(context.getHand());
				return InteractionResult.sidedSuccess(level.isClientSide());
			}
		}
		return InteractionResult.PASS;
	}
	
	@Override
	public int getUseDuration(ItemStack stack, @Nullable LivingEntity entity) {
		CompoundTag tag = stack.get(BLOCK_TO_EAT.get());
		if (tag != null) {
			return tag.getInt(DURATION_KEY);
		}
		return 0;
	}
	
	/**
	 * won't eat
	 */
	@Override
	public void releaseUsing(ItemStack stack, Level level, LivingEntity livingEntity, int timeCharged) {
		cleanupEat(stack);
	}
	
	/**
	 * ate
	 */
	@Override
	public @NotNull ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
		CompoundTag tag = stack.get(BLOCK_TO_EAT.get());
		FoodProperties food = stack.get(DataComponents.FOOD);
		if (tag != null && food != null) {
			//eat it
			livingEntity.eat(level, stack, food);
			stack.setCount(1);
			level.destroyBlock(BlockPos.of(tag.getLong(POS_KEY)), false);
			cleanupEat(stack);
			if (level instanceof ServerLevel sl) {
				stack.hurtAndBreak(1, sl, livingEntity instanceof ServerPlayer p ? p : null, i -> {
				});
			}
		}
		return stack;
	}
	
	
	private void cleanupEat(ItemStack stack) {
		stack.remove(DataComponents.FOOD);
		stack.remove(BLOCK_TO_EAT.get());
	}

//Unable to get the block player are looking at

//	/**
//	 * Verify that the current block is the one being eaten
//	 * 检测当前方块是否为正在吃的方块
//	 */
//	@Override
//	public void onUseTick(Level level, LivingEntity livingEntity, ItemStack stack, int remainingUseDuration) {
//		if (livingEntity instanceof Player player) {
//
//		} else {
//			livingEntity.stopUsingItem();
//		}
//	}
	
}
