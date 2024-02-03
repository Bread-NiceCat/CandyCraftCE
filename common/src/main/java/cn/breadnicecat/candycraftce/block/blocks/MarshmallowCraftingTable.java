package cn.breadnicecat.candycraftce.block.blocks;

import cn.breadnicecat.candycraftce.block.CBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.CraftingMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CraftingTableBlock;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Created in 2024/1/30 22:32
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 */
public class MarshmallowCraftingTable extends CraftingTableBlock {
	private Component title;

	public MarshmallowCraftingTable(Properties properties) {
		super(properties);
	}

	@Override
	public MenuProvider getMenuProvider(BlockState state, Level level, BlockPos pos) {
		return new SimpleMenuProvider((i, inventory, player) -> {
			ContainerLevelAccess access = ContainerLevelAccess.create(level, pos);
			return new CraftingMenu(i, inventory, access) {
				@Override
				public boolean stillValid(Player player) {
					return stillValid(access, player, CBlocks.MARSHMALLOW_CRAFTING_TABLE.get());
				}
			};
		}, title == null ? title = Component.translatable(CBlocks.MARSHMALLOW_CRAFTING_TABLE.get().getDescriptionId()) : title);
	}
}
