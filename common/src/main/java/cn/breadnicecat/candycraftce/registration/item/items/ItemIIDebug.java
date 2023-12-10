package cn.breadnicecat.candycraftce.registration.item.items;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

/**
 * Created in 2023/7/30 14:12
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 */
public class ItemIIDebug extends Item {
	public ItemIIDebug() {
		super(new Properties().stacksTo(1).rarity(Rarity.EPIC));
	}

	@Override
	public @NotNull InteractionResult useOn(UseOnContext pContext) {
		if (!pContext.getLevel().isClientSide()) {
			BlockPos pos = pContext.getClickedPos();
			Player player = pContext.getPlayer();
			if (player != null) {

				int[] zero = pContext.getItemInHand().getOrCreateTag().getIntArray("zero");
				if (zero.length != 0) {
					player.sendSystemMessage(Component.literal("[坐标测算]: 坐标:  %d,%d,%d".formatted(pos.getX() - zero[0], pos.getY() - zero[1], pos.getZ() - zero[2])).withStyle(ChatFormatting.YELLOW));
				} else {
					player.sendSystemMessage(Component.literal("[坐标测算]: 未调零").withStyle(ChatFormatting.DARK_RED));
				}

			}
		}
		return InteractionResult.sidedSuccess(pContext.getLevel().isClientSide());
	}

	public boolean canAttackBlock(@NotNull BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos, @NotNull Player pPlayer) {
		if (!pLevel.isClientSide()) {
			ItemStack item = pPlayer.getItemInHand(InteractionHand.MAIN_HAND);

			item.getOrCreateTag().putIntArray("zero", new int[]{pPos.getX(), pPos.getY(), pPos.getZ()});
			pPlayer.sendSystemMessage(Component.literal("[坐标测算]: 调零: [%s]".formatted(pPos.toShortString())).withStyle(ChatFormatting.GREEN));
		}
		return false;
	}

	public boolean isFoil(@NotNull ItemStack pStack) {
		return true;
	}
}
