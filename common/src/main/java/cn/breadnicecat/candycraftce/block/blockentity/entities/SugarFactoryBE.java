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
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static cn.breadnicecat.candycraftce.utils.tools.LambdaAccessor.of;
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
		check:
		{
			ItemStack output = items.get(OUTPUT_SLOT);
			
			if (items.isEmpty(INPUT_SLOT)) {
				recipeUsed = null;
				break check;
			}
			//如果输入是糖，则取一个糖到右边去
			if (items.get(INPUT_SLOT).is(SUGAR)) {
				if (output.isEmpty()) {
					items.set(OUTPUT_SLOT, items.extract(INPUT_SLOT, 1));
				} else break check;
			}
			//检查配方
			if (recipeUsed == null || !recipeUsed.matches(checker, level)) {
				var opt = quickCheck.getRecipeFor(checker, level);
				recipeUsed = opt.map(RecipeHolder::value).orElse(null);
			}
			//判断输出 输出阻塞
			for (int i = 0; i < 2; i++) {//循环两次
				if (i == 0 && recipeUsed == null) {//没找到匹配的配方
					continue;
				} else if (i == 1) {//第二次进入
					//通过第一次判断
					if (recipeUsed != null) break;
					//验证是否符合制糖模式
					if (SUGARY.matches(checker, level)) {
						recipeUsed = SUGARY;
					} else break;
				}
				//输出内容不是配方输出内容 或者 之和大于最大堆叠数
				if (!output.isEmpty() && (!ItemStack.isSameItemSameComponents(output, recipeUsed.getResult()) || output.getCount() + recipeUsed.getCount() > output.getMaxStackSize())) {
					recipeUsed = null;
				}
			}
		}
		
		//更新
		boolean changed = recipeType != (recipeType = getRecipeType(recipeUsed))
				| tickedTotal != (tickedTotal = getTickedTimeTotal(recipeUsed));
		if (recipeUsed != null) {
			if (tickedTotal != 0 && ++ticked > tickedTotal) {
				ticked = 0;
				items.extract(INPUT_SLOT, 1);
				items.insert(OUTPUT_SLOT, recipeUsed.assemble(checker, level.registryAccess()));
			}
			changed = true;
		} else if (ticked > 0) {
			ticked = Math.min(0, ticked - 2);
			changed = true;
		}
		
		if (changed) setChanged();
		
	}
	
	@Override
	public void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
		super.loadAdditional(tag, registries);
		ContainerHelper.loadAllItems(tag.getCompound("items"), items, registries);
		ticked = tag.getInt("ticked");
	}
	
	@Override
	protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
		super.saveAdditional(tag, registries);
		CompoundTag items = new CompoundTag();
		ContainerHelper.saveAllItems(items, this.items, registries);
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
