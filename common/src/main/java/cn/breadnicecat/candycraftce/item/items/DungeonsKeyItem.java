package cn.breadnicecat.candycraftce.item.items;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class DungeonsKeyItem extends BlockItem {
	public DungeonsKeyItem(Block block, Properties properties) {
		super(block, properties);
	}
	
	@Override
	public @NotNull InteractionResult place(BlockPlaceContext context) {
		InteractionResult interactionResult = super.place(context);
		if (interactionResult.consumesAction()) {
			Player player = context.getPlayer();
			if (player != null) {
				player.getCooldowns().addCooldown(this, 200);
			}
		}
		return interactionResult;
	}
	
	@Override
	protected boolean placeBlock(BlockPlaceContext context, BlockState state) {
		Level level = context.getLevel();
		BlockPos pos = context.getClickedPos();
		boolean status = super.placeBlock(context, state);
		if(status){
//			if(level.getBlockEntity(pos) instanceof Block;
			
		}
		return status;
	}
}
