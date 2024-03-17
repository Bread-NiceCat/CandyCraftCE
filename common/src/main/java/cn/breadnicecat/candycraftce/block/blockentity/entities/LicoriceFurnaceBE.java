package cn.breadnicecat.candycraftce.block.blockentity.entities;

import cn.breadnicecat.candycraftce.block.blockentity.CBlockEntities;
import cn.breadnicecat.candycraftce.block.blocks.LicoriceFurnaceBlock;
import cn.breadnicecat.candycraftce.gui.block.menus.LicoriceFurnaceMenu;
import cn.breadnicecat.candycraftce.misc.CSugarFuels;
import cn.breadnicecat.candycraftce.recipe.CRecipeTypes;
import cn.breadnicecat.candycraftce.recipe.recipes.SugarFurnaceRecipe;
import cn.breadnicecat.candycraftce.utils.CDataAccessors;
import cn.breadnicecat.candycraftce.utils.ItemStackList;
import cn.breadnicecat.candycraftce.utils.tools.LambdaAccessor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.block.FurnaceBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

import static cn.breadnicecat.candycraftce.utils.TickUtils.SEC2TICK;

public class LicoriceFurnaceBE extends BlockEntity implements MenuProvider, WorldlyContainer {

	public static final int INPUT_SLOT = 0;
	public static final int FUEL_SLOT = 1;
	public static final int OUTPUT_SLOT = 2;

	public static final int TICKED_DATA = 0;
	public static final int TICKED_TOTAL_DATA = 1;
	public static final int LIT_TIME_DATA = 2;
	public static final int LIT_TIME_TOTAL_DATA = 3;
	public static final int[] SLOT_FOR_UP = {INPUT_SLOT};
	public static final int[] SLOT_FOR_DOWN = {OUTPUT_SLOT};
	public static final int[] SLOT_FOR_SIDE = {FUEL_SLOT};

//	private final RecipeType<?> RECIPE_TYPE = null;
//	private final RecipeManager.CachedCheck<?, ?> CACHE = RecipeManager.createCheck(RECIPE_TYPE);

	private ItemStackList items = new ItemStackList(3);
	private float exp;
	private int litTime;

	private int litTimeTotal;

	private int ticked;
	private int tickedTotal;


	private SugarFurnaceRecipe recipeUsed = null;
	private RecipeManager.CachedCheck<LicoriceFurnaceBE, SugarFurnaceRecipe> quickCheck = RecipeManager.createCheck(CRecipeTypes.SUGAR_FURNACE_TYPE.get());

	public final CDataAccessors data = new CDataAccessors(
			LambdaAccessor.of(() -> this.ticked, (t) -> this.ticked = t),
			LambdaAccessor.of(() -> this.tickedTotal, (t) -> this.tickedTotal = t),
			LambdaAccessor.of(() -> this.litTime, (t) -> this.litTime = t),
			LambdaAccessor.of(() -> this.litTimeTotal, (t) -> this.litTimeTotal = t)
	);

	protected LicoriceFurnaceBE(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState, int tickedTotal) {
		super(blockEntityType, blockPos, blockState);
		this.tickedTotal = tickedTotal;
	}

	public LicoriceFurnaceBE(BlockPos blockPos, BlockState blockState) {
		this(CBlockEntities.LICORICE_FURNACE_BE.get(), blockPos, blockState, (int) (10 * SEC2TICK));
	}

	public void serverTick() {
		boolean isLit = litTime > 0;
		//检查工作状态(配方,燃料,输出)
		check:
		{
			if (items.get(INPUT_SLOT).isEmpty()) {
				recipeUsed = null;
				break check;
			}

			//检查配方
			if (recipeUsed == null || !recipeUsed.matches(this, level)) {
				//寻找新配方
				Optional<SugarFurnaceRecipe> recipe = quickCheck.getRecipeFor(this, level);
				if (recipe.isEmpty()) {
					recipeUsed = null;
					break check;
				} else {
					recipeUsed = recipe.get();
				}
			}

			//检查输出 输出堵塞
			ItemStack out = items.get(OUTPUT_SLOT);
			ItemStack resultItem = recipeUsed.getResultItem(level.registryAccess());
			if (!out.isEmpty() && (recipeUsed.getCount() + out.getCount() > out.getMaxStackSize() || !out.is(resultItem.getItem()))) {
				recipeUsed = null;
			}
		}
		//方块更新
		boolean changed = false;
		if (isLit) {
			litTime--;
			changed = true;
		}
		//有东西烧且燃料接近耗尽
		//补充燃料
		if (!items.isEmpty(FUEL_SLOT) && recipeUsed != null && litTime <= 1) {
			int fu = CSugarFuels.getBurnDuration(items.extract(FUEL_SLOT, 1).getItem());
			if (fu != 0) {
				litTime += fu;
				litTimeTotal = fu;
				changed = true;
			}
		}
//:: 原版在无燃料燃烧状态下状态下不会主动从燃烧状态(lit=true)变成非燃烧状态
		//燃料耗尽
		if (litTime < 1) {
			isLit = false;
			litTimeTotal = 0;
		} else {
			isLit = true;
		}
		//不工作进度条倒退而不是清零
		if (!isLit || (recipeUsed == null && ticked > 0)) {
			ticked = Math.max(ticked - 2, 0);
			changed = true;
		} else if (recipeUsed != null) {
			//已经完成
			if (++ticked >= tickedTotal) {
				items.extract(INPUT_SLOT, 1);
				items.insert(OUTPUT_SLOT, recipeUsed.assemble(this, level.registryAccess()));
				exp += recipeUsed.getExp();
				ticked = 0;
			}
			changed = true;
		}
		//更新状态 燃烧状态有改变
		if (isLit != getBlockState().getValue(LicoriceFurnaceBlock.LIT)) {
			level.setBlock(worldPosition, getBlockState().setValue(FurnaceBlock.LIT, isLit), 2);
		}
		if (changed) setChanged();
	}


	@Override
	public @NotNull Component getDisplayName() {
		return getBlockState().getBlock().getName();
	}

	@Nullable
	@Override
	public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
		return new LicoriceFurnaceMenu(i, inventory, this, data);
	}

	public void dropExp(Vec3 pos) {
		if (exp > 0f) {
			ExperienceOrb orb = new ExperienceOrb(level, pos.x, pos.y, pos.z, Mth.floor(exp));
			this.exp = 0f;
			setChanged();
			level.addFreshEntity(orb);
		}
	}


	@Override
	protected void saveAdditional(CompoundTag tag) {
		ContainerHelper.saveAllItems(tag, this.items);
		tag.putFloat("exp", exp);
		tag.putInt("litTime", litTime);
		tag.putInt("litTimeTotal", litTimeTotal);
		tag.putInt("ticked", ticked);
		super.saveAdditional(tag);
	}

	@Override
	public void load(CompoundTag tag) {
		ContainerHelper.loadAllItems(tag, this.items);
		exp = tag.getFloat("exp");
		litTime = tag.getInt("litTime");
		litTimeTotal = tag.getInt("litTimeTotal");
		ticked = tag.getInt("ticked");
		super.load(tag);
	}

	@Override
	public int @NotNull [] getSlotsForFace(Direction side) {
		return switch (side) {
			case UP -> SLOT_FOR_UP;
			case DOWN -> SLOT_FOR_DOWN;
			default -> SLOT_FOR_SIDE;
		};
	}

	@Override
	public boolean canPlaceItemThroughFace(int index, ItemStack itemStack, @Nullable Direction direction) {
		return direction != null && switch (direction) {
			case DOWN -> false;
			default -> true;
		};
	}

	@Override
	public boolean canTakeItemThroughFace(int index, ItemStack stack, Direction direction) {
		return switch (direction) {
			case DOWN -> true;
			default -> false;
		};
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
		return ContainerHelper.removeItem(items, slot, amount);
	}

	@Override
	public @NotNull ItemStack removeItemNoUpdate(int slot) {
		return ContainerHelper.takeItem(items, slot);

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
