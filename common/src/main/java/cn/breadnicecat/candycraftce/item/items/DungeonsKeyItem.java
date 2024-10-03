package cn.breadnicecat.candycraftce.item.items;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
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
	
	public @NotNull String getDescriptionId() {
		return this.getOrCreateDescriptionId();
	}
	
	public @NotNull String getDescriptionId(ItemStack stack) {
		return this.getDescriptionId();
	}
	
}
