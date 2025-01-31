package cn.breadnicecat.candycraftce.block.blockentity.entities;

import cn.breadnicecat.candycraftce.block.blockentity.CBlockEntities;
import cn.breadnicecat.candycraftce.block.blockentity.ContainerRecipeInput;
import cn.breadnicecat.candycraftce.gui.block.menus.LicoriceFurnaceMenu;
import cn.breadnicecat.candycraftce.item.CSugarFuels;
import cn.breadnicecat.candycraftce.recipe.CRecipeTypes;
import cn.breadnicecat.candycraftce.recipe.recipes.SugarFurnaceRecipe;
import cn.breadnicecat.candycraftce.utils.CDataAccessors;
import cn.breadnicecat.candycraftce.utils.ItemStackList;
import cn.breadnicecat.candycraftce.utils.tools.LambdaAccessor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
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
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

import static cn.breadnicecat.candycraftce.utils.TickUtils.SEC2TICK;
import static java.lang.Math.max;
import static java.lang.Math.min;
import static net.minecraft.world.level.block.AbstractFurnaceBlock.LIT;

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
	
	private final ItemStackList items = new ItemStackList(3);
	private float exp;
	private int litTime;
	private int litTimeTotal;
	private int ticked;
	private int tickedTotal;
	//不能直接实现这个接口Unfixable conflicts
	private final ContainerRecipeInput<LicoriceFurnaceBE> checker = new ContainerRecipeInput<>(this);
	
	private @Nullable SugarFurnaceRecipe recipeUsed = null;
	private final RecipeManager.CachedCheck<ContainerRecipeInput<LicoriceFurnaceBE>, SugarFurnaceRecipe> quickCheck = RecipeManager.createCheck(CRecipeTypes.SUGAR_FURNACE_TYPE.get());
	
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
		boolean legal = tickLegality();
		legal &= tickFuel(legal);
		tickProgress(legal);
	}
	
	private boolean tickLegality() {
		if (items.isEmpty(INPUT_SLOT)) return false;
		SugarFurnaceRecipe ru0=recipeUsed;
		if (recipeUsed == null || !recipeUsed.matches(checker, level)) {
			Optional<RecipeHolder<SugarFurnaceRecipe>> recipe = quickCheck.getRecipeFor(checker, level);
			if (recipe.isEmpty()) return false;
			else {
				recipeUsed = recipe.get().value();
			}
		}
		if(ru0!=recipeUsed)ticked=0;
		return items.simulateInsert(OUTPUT_SLOT, recipeUsed.getResultItem(level.registryAccess())).isEmpty();
	}
	
	private boolean tickFuel(boolean ignite) {
		litTime=max(0,litTime-1);
		if (litTime == 0) {
			if (ignite && !items.isEmpty(FUEL_SLOT)) {
				int nf = CSugarFuels.getBurnDuration(items.extract(FUEL_SLOT, 1));
				if (nf != 0) litTimeTotal = litTime = nf;
			}
		}
		boolean lit = litTime > 0;
		BlockState state = getBlockState();
		if (state.getValue(LIT) != lit) {
			state.setValue(LIT, lit);
			setChanged();
		}
		return lit;
	}
	
	private void tickProgress(boolean promote) {
		if (recipeUsed != null && promote) {
			if (++ticked >= tickedTotal) {
				ticked=0;
				items.extract(INPUT_SLOT, 1);
				items.insert(OUTPUT_SLOT, recipeUsed.assemble(checker, level.registryAccess()));
			}
		} else ticked = max(0, ticked - 2);
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
	protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
		ContainerHelper.saveAllItems(tag, this.items, registries);
		tag.putFloat("Exp", exp);
		tag.putInt("LitTime", litTime);
		tag.putInt("LitTimeTotal", litTimeTotal);
		tag.putInt("Ticked", ticked);
		super.saveAdditional(tag, registries);
	}
	
	@Override
	protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
		ContainerHelper.loadAllItems(tag, this.items, registries);
		exp = tag.getFloat("Exp");
		litTime = tag.getInt("LitTime");
		litTimeTotal = tag.getInt("LitTimeTotal");
		ticked = tag.getInt("Ticked");
		super.loadAdditional(tag, registries);
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
