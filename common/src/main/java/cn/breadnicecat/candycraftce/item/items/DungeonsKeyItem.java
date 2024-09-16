package cn.breadnicecat.candycraftce.item.items;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;

public class DungeonsKeyItem extends BlockItem {
    public DungeonsKeyItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public InteractionResult place(BlockPlaceContext context) {
        InteractionResult interactionResult = super.place(context);

        if (interactionResult.consumesAction()) {
            context.getPlayer().getCooldowns().addCooldown(this, 60);
        }
        return interactionResult;
    }

    public String getDescriptionId() {
        return this.getOrCreateDescriptionId();
    }

    public String getDescriptionId(ItemStack stack) {
        return this.getDescriptionId();
    }

}
