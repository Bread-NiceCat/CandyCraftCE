package cn.breadnicecat.candycraftce.gui.block.menus;

import cn.breadnicecat.candycraftce.block.blockentity.entities.LicoriceFurnaceBE;
import cn.breadnicecat.candycraftce.gui.block.CMenus;
import cn.breadnicecat.candycraftce.gui.block.menus.slot.ResultSlot;
import cn.breadnicecat.candycraftce.gui.block.menus.slot.SugarFuelSlot;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class LicoriceFurnaceMenu extends AbstractContainerMenu {

	private static final int INV_START = 0;
	private static final int INV_END = 36;
	private static final int INPUT_SLOT = 37;
	private static final int FUEL_SLOT = 38;
	private static final int OUTPUT_SLOT = 39;

	private final Inventory inventory;
	private final Container container;
	public final ContainerData containerData;

	public LicoriceFurnaceMenu(int i, Inventory inventory) {
		this(i, inventory, new SimpleContainer(3), new SimpleContainerData(4));
	}

	public LicoriceFurnaceMenu(int containerId, Inventory inventory, Container container, ContainerData containerData) {
		this(CMenus.LICORICE_FURNACE_MENU.get(), containerId, inventory, container, containerData);
	}

	protected LicoriceFurnaceMenu(MenuType<?> type, int containerId, Inventory inventory, Container container, ContainerData containerData) {
		super(type, containerId);
		checkContainerSize(container, 3);
		checkContainerDataCount(containerData, 4);
		this.inventory = inventory;
		this.container = container;
		this.containerData = containerData;
		//player inv
		for (int i = 0; i < 3; ++i) {
			for (int k = 0; k < 9; ++k) {
				this.addSlot(new Slot(inventory, k + i * 9 + 9, 8 + k * 18, 84 + i * 18));
			}
		}
		for (int i = 0; i < 9; ++i) {
			this.addSlot(new Slot(inventory, i, 8 + i * 18, 142));
		}
		addSlot(new Slot(container, LicoriceFurnaceBE.INPUT_SLOT, 56, 17));
		addSlot(new SugarFuelSlot<>(container, LicoriceFurnaceBE.FUEL_SLOT, 56, 53));
		addSlot(new ResultSlot(container, LicoriceFurnaceBE.OUTPUT_SLOT, 116, 35) {
			@Override
			public void onTake(@NotNull Player pPlayer, @NotNull ItemStack pStack) {
				if (pPlayer instanceof ServerPlayer sp) {
					if (container instanceof LicoriceFurnaceBE b) {
						b.dropExp(sp.position());
					}
				}
				super.onTake(pPlayer, pStack);
			}
		});
		this.addDataSlots(containerData);
	}

	@Override
	public @NotNull ItemStack quickMoveStack(Player player, int index) {
		Slot slot = slots.get(index);
		ItemStack item = slot.getItem();
		if (index >= INV_START && index <= INV_END) {
			moveItemStackTo(item, INPUT_SLOT, FUEL_SLOT + 1, true);
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