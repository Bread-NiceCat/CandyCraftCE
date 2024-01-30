package cn.breadnicecat.candycraftce.registration.block.blocks;

import cn.breadnicecat.candycraftce.registration.block.CBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.CraftingMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CraftingTableBlock;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Created in 2024/1/30 22:32
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 * <p>
 */
public class MarshmallowCraftingTable extends CraftingTableBlock {
	private Component title;

	public MarshmallowCraftingTable(Properties properties) {
		super(properties);
	}

	@Override
	public MenuProvider getMenuProvider(BlockState state, Level level, BlockPos pos) {
		return new SimpleMenuProvider((i, inventory, player) -> new CraftingMenu(i, inventory, ContainerLevelAccess.create(level, pos)),
				title == null ? title = Component.translatable(CBlocks.MARSHMALLOW_CRAFTING_TABLE.getBlock().getDescriptionId()) : title
		);
	}
}
