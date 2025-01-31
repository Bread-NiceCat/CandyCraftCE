package cn.breadnicecat.candycraftce.block.blockentity.entities;

import cn.breadnicecat.candycraftce.block.CBlockTags;
import cn.breadnicecat.candycraftce.block.blockentity.CBlockEntities;
import cn.breadnicecat.candycraftce.block.blockentity.ContainerRecipeInput;
import cn.breadnicecat.candycraftce.gui.block.menus.SugarFactoryMenu;
import cn.breadnicecat.candycraftce.recipe.CRecipeTypes;
import cn.breadnicecat.candycraftce.recipe.recipes.SugarFactoryRecipe;
import cn.breadnicecat.candycraftce.utils.CDataAccessors;
import cn.breadnicecat.candycraftce.utils.ItemStackList;
import cn.breadnicecat.candycraftce.utils.TickUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
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
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static cn.breadnicecat.candycraftce.utils.tools.LambdaAccessor.of;
import static java.lang.Math.max;
import static net.minecraft.world.item.Items.SUGAR;

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
	
	public static final SugarFactoryRecipe SUGARY = new SugarFactoryRecipe(Ingredient.of(CBlockTags.BT_SUGARY.it()), SUGAR.getDefaultInstance(), false);
	
	protected static final int COMMON_TICKED_TOTAL = (int) (10 * TickUtils.SEC2TICK);
	
	private final ItemStackList items = new ItemStackList(2);
	private int ticked;
	private int tickedTotal;
	private int recipeType = NULL_TYPE;
	private @Nullable SugarFactoryRecipe recipeUsed;
	
	private final ContainerRecipeInput<SugarFactoryBE> checker = new ContainerRecipeInput<>(this);
	private final RecipeManager.CachedCheck<ContainerRecipeInput<SugarFactoryBE>, SugarFactoryRecipe> quickCheck = RecipeManager.createCheck(CRecipeTypes.SUGAR_FACTORY_TYPE.get());
	
	protected CDataAccessors data = new CDataAccessors(
			of(() -> ticked, (t) -> ticked = t),
			of(() -> tickedTotal, (t) -> tickedTotal = t),
			of(() -> recipeType, (t) -> recipeType = t)
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
	boolean legal = tickLegality();
	tickProgress(legal);
}
	
	private boolean tickLegality() {
		if (items.isEmpty(INPUT_SLOT)) return false;
		//如果放入糖，则进入制糖模式
		if(items.get(INPUT_SLOT).is(SUGAR)){
			if(items.get(OUTPUT_SLOT).isEmpty()) {
				items.transfer(INPUT_SLOT, OUTPUT_SLOT, 1);
			}
			return false;
		}
		final SugarFactoryRecipe ru0=recipeUsed;
		//检查配方
		if(items.is(OUTPUT_SLOT,SUGAR)&&SUGARY.matches(checker,level)){
			//如果输出栏有糖，则优先进入制糖模式
			recipeUsed=SUGARY;
		}else if (recipeUsed == null
				//注意：如果是制糖模式的话，每次都应该检查是否有其他的配方，防止出现输入被替换后仍然符合制糖模式要求的情况
				|| recipeUsed==SUGARY
				|| !recipeUsed.matches(checker, level))//不匹配
		{
			//匹配
			var recipe = quickCheck.getRecipeFor(checker, level).orElse(null);
			if (recipe==null) {
				//备选，制糖模式
				if (SUGARY.matches(checker, level)) {
					recipeUsed = SUGARY;
				}else return false;
			} else {
				recipeUsed = recipe.value();
			}
		}
		if(recipeUsed!=ru0) {
			//如果配方改变了
			tickedTotal = getTickTimeTotal(recipeUsed);
			recipeType = getRecipeType(recipeUsed);
				ticked = 0;
		}
		return items.simulateInsert(OUTPUT_SLOT, recipeUsed.getResultItem(level.registryAccess())).isEmpty();
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
	public void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
		super.loadAdditional(tag, registries);
		ContainerHelper.loadAllItems(tag.getCompound("Items"), items, registries);
		ticked = tag.getInt("Ticked");
	}
	
	@Override
	protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
		super.saveAdditional(tag, registries);
		CompoundTag items = new CompoundTag();
		ContainerHelper.saveAllItems(items, this.items, registries);
		tag.put("Items", items);
		tag.putInt("Ticked", ticked);
	}
	
	protected int getRecipeType(@Nullable SugarFactoryRecipe recipe) {
		return recipe == null ? NULL_TYPE : (recipe == SUGARY ? SUGARY_TYPE : COMMON_TYPE);
	}
	
	protected int getTickTimeTotal(@Nullable SugarFactoryRecipe recipe) {
		return recipe == null ? 0 : COMMON_TICKED_TOTAL;
	}
	
	
	@Override
	public int @NotNull [] getSlotsForFace(Direction side) {
		return side == Direction.DOWN ? SLOT_FOR_DOWN : SLOT_FOR_OTHER;
	}
	
	@Override
	public boolean canPlaceItemThroughFace(int index, ItemStack itemStack, @Nullable Direction direction) {
		return direction != null && direction != Direction.DOWN;
	}
	
	@Override
	public boolean canTakeItemThroughFace(int index, ItemStack stack, Direction direction) {
		return direction == Direction.DOWN && (!stack.is(SUGAR) || stack.getCount() > 1);
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
