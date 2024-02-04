package cn.breadnicecat.candycraftce.gui.block.menus;

import cn.breadnicecat.candycraftce.block.blockentity.entities.SugarFactoryBE;
import cn.breadnicecat.candycraftce.gui.block.CMenus;
import cn.breadnicecat.candycraftce.gui.block.menus.slot.ResultSlot;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * Created in 2024/2/4
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-NiceCat">Bread_NiceCat</a>
 * <p>
 */
public class SugarFactoryMenu extends AbstractContainerMenu {
	private static final int INV_START = 0;
	private static final int INV_END = 36;
	private static final int INPUT_SLOT = 37;
	private static final int OUTPUT_SLOT = 38;

	public final Inventory inventory;
	public final Container container;
	public final ContainerData data;

	protected SugarFactoryMenu(MenuType<?> type, int id, Inventory inventory, Container container, ContainerData data) {
		super(type, id);
		this.inventory = inventory;
		this.container = container;
		this.data = data;
		//player inv
		for (int i = 0; i < 3; ++i) {
			for (int k = 0; k < 9; ++k) {
				this.addSlot(new Slot(inventory, k + i * 9 + 9, 8 + k * 18, 84 + i * 18));
			}
		}
		for (int i = 0; i < 9; ++i) {
			this.addSlot(new Slot(inventory, i, 8 + i * 18, 142));
		}
		addSlot(new Slot(container, SugarFactoryBE.INPUT_SLOT, 7, 58));
		addSlot(new ResultSlot(container, SugarFactoryBE.INPUT_SLOT, 151, 58));
		addDataSlots(data);
	}

	public SugarFactoryMenu(int id, Inventory inventory, Container container, ContainerData data) {
		this(CMenus.SUGAR_FACTORY_MENU.get(), id, inventory, container, data);
	}

	public SugarFactoryMenu(int id, Inventory inv) {
		this(id, inv, new SimpleContainer(2), new SimpleContainerData(3));
	}

	@Override
	public @NotNull ItemStack quickMoveStack(Player player, int index) {
		ItemStack item = container.getItem(index);
		if (index >= INV_START && index <= INV_END) {
			moveItemStackTo(item, INPUT_SLOT, INPUT_SLOT + 1, false);
		} else {
			moveItemStackTo(item, INV_START, INV_END + 1, false);
		}
		return ItemStack.EMPTY;
	}

	@Override
	public boolean stillValid(Player player) {
		return container.stillValid(player);
	}
}
