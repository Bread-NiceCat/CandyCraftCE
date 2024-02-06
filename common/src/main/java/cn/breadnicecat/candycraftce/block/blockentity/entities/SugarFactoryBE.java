package cn.breadnicecat.candycraftce.block.blockentity.entities;

import cn.breadnicecat.candycraftce.block.CBlocks;
import cn.breadnicecat.candycraftce.block.blockentity.CBlockEntities;
import cn.breadnicecat.candycraftce.block.blockentity.data.CDataAccessors;
import cn.breadnicecat.candycraftce.block.blockentity.data.ItemStackList;
import cn.breadnicecat.candycraftce.gui.block.menus.SugarFactoryMenu;
import cn.breadnicecat.candycraftce.recipe.CRecipeTypes;
import cn.breadnicecat.candycraftce.recipe.recipes.SugarFactoryRecipe;
import cn.breadnicecat.candycraftce.utils.TickUtils;
import cn.breadnicecat.candycraftce.utils.tools.LambdaAccessor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

import static cn.breadnicecat.candycraftce.utils.ResourceUtils.prefix;

/**
 * Created in 2024/2/3
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-NiceCat">Bread_NiceCat</a>
 * <p>
 */
public class SugarFactoryBE extends BlockEntity implements MenuProvider, WorldlyContainer {
	public static final int[] SLOT_FOR_OTHER = {0};
	public static final int[] SLOT_FOR_DOWN = {1};
	public static final int OUTPUT_SLOT = 1;
	public static final int INPUT_SLOT = 0;
	public static final int TICKED_DATA_SLOT = 0;
	public static final int TICKED_TOTAL_DATA_SLOT = 1;
	public static final int RECIPE_TYPE_DATA_SLOT = 2;

	public static final int NULL_TYPE = 0;
	public static final int COMMON_TYPE = 1;
	public static final int SUGARY_TYPE = 2;

	public static final SugarFactoryRecipe SUGARY = new SugarFactoryRecipe(prefix("__sugary__"), Ingredient.of(CBlocks.SUGAR_BLOCK), Items.SUGAR, 1, false);

	protected static final int COMMON_TICKED_TOTAL = 10 * TickUtils.SEC2TICK;

	private ItemStackList items = new ItemStackList(2);
	private int ticked;
	private int tickedTotal;
	private int recipeType = NULL_TYPE;
	private SugarFactoryRecipe recipeUsed;
	private RecipeManager.CachedCheck<SugarFactoryBE, SugarFactoryRecipe> quickCheck = RecipeManager.createCheck(CRecipeTypes.SUGAR_FACTORY_TYPE.get());

	protected CDataAccessors data = new CDataAccessors(
			LambdaAccessor.of(() -> ticked, (t) -> ticked = t),
			LambdaAccessor.of(() -> tickedTotal, (t) -> tickedTotal = t),
			LambdaAccessor.of(() -> recipeType, (t) -> recipeType = t)
	);

	protected SugarFactoryBE(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
		super(blockEntityType, blockPos, blockState);
	}

	public SugarFactoryBE(BlockPos blockPos, BlockState blockState) {
		this(CBlockEntities.SUGAR_FACTORY_BE.get(), blockPos, blockState);
	}

	@Override
	public @NotNull Component getDisplayName() {
		return getBlockState().getBlock().getName();
	}

	@Override
	public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
		return new SugarFactoryMenu(i, inventory, this, data);
	}

	public void serverTick() {
		check:
		{
			if (items.isEmpty(INPUT_SLOT)) {
				recipeUsed = null;
				break check;
			}
			//检查配方
			if (recipeUsed == null || !recipeUsed.matches(this, level)) {
				Optional<SugarFactoryRecipe> opt = quickCheck.getRecipeFor(this, level);
				if (opt.isEmpty()) {
					if (SUGARY.matches(this, level)) {
						recipeUsed = SUGARY;
					} else {
						recipeUsed = null;
					}
				} else {
					recipeUsed = opt.get();
				}
			}
			//判断输出 输出阻塞
			ItemStack output = items.get(OUTPUT_SLOT);
			if (recipeUsed != null && output.getCount() + recipeUsed.count > output.getMaxStackSize()) {
				recipeUsed = null;
			}
		}

		//更新
		boolean changed = recipeType != (recipeType = getRecipeType(recipeUsed))
				| tickedTotal != (tickedTotal = getTickedTimeTotal(recipeUsed));
		if (recipeUsed != null) {
			if (tickedTotal != 0 && ++ticked > tickedTotal) {
				ticked = 0;
				items.extract(INPUT_SLOT, 1);
				items.insert(OUTPUT_SLOT, recipeUsed.assemble(this, level.registryAccess()));
			}
			changed = true;
		} else if (ticked > 0) {
			ticked = Math.min(0, ticked - 2);
			changed = true;
		}

		if (changed) setChanged();

	}

	@Override
	public void load(CompoundTag tag) {
		super.load(tag);
		ContainerHelper.loadAllItems(tag.getCompound("items"), items);
		ticked = tag.getInt("ticked");
	}

	@Override
	protected void saveAdditional(CompoundTag tag) {
		super.saveAdditional(tag);
		CompoundTag items = new CompoundTag();
		ContainerHelper.saveAllItems(items, this.items);
		tag.put("items", items);
		tag.putInt("ticked", ticked);
	}

	protected int getRecipeType(@Nullable SugarFactoryRecipe recipe) {
		return recipe == null ? NULL_TYPE : (recipe == SUGARY ? SUGARY_TYPE : COMMON_TYPE);
	}

	protected int getTickedTimeTotal(@Nullable SugarFactoryRecipe recipe) {
		return recipe == null ? 0 : COMMON_TICKED_TOTAL;
	}


	@Override
	public int @NotNull [] getSlotsForFace(Direction side) {
		return switch (side) {
			case DOWN -> SLOT_FOR_DOWN;
			default -> SLOT_FOR_OTHER;
		};
	}

	@Override
	public boolean canPlaceItemThroughFace(int index, ItemStack itemStack, @Nullable Direction direction) {
		return direction != null && direction != Direction.DOWN;
	}

	@Override
	public boolean canTakeItemThroughFace(int index, ItemStack stack, Direction direction) {
		return direction == Direction.DOWN;
	}

	@Override
	public int getContainerSize() {
		return items.size();
	}

	@Override
	public boolean isEmpty() {
		return items.isEmpty();
	}

	@Override
	public @NotNull ItemStack getItem(int slot) {
		return items.get(slot);
	}

	@Override
	public @NotNull ItemStack removeItem(int slot, int amount) {
		return items.extract(slot, amount);
	}

	@Override
	public @NotNull ItemStack removeItemNoUpdate(int slot) {
		return items.remove(slot);
	}

	@Override
	public void setItem(int slot, ItemStack stack) {
		items.set(slot, stack);
	}

	@Override
	public boolean stillValid(Player player) {
		return Container.stillValidBlockEntity(this, player);
	}

	@Override
	public void clearContent() {
		items.clear();
	}
}
