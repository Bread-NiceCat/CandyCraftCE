//package cn.breadnicecat.candycraftce.registration.item.items;
//
//import cn.breadnicecat.candycraftce.block.CBlockEntries;
//import cn.breadnicecat.candycraftce.blockentity.blockentites.BETeleporter;
//import cn.breadnicecat.candycraftce.item.CItemEntries;
//import cn.breadnicecat.candycraftce.level.dungeons.DebugDungeonGenerator;
//import cn.breadnicecat.candycraftce.level.dungeons.IGeneratorProvider;
//import cn.breadnicecat.candycraftce.level.dungeons.JellyDungeonGenerator;
//import cn.breadnicecat.candycraftce.level.dungeons.StructureGenerator;
//import net.minecraft.ChatFormatting;
//import net.minecraft.core.NonNullList;
//import net.minecraft.nbt.CompoundTag;
//import net.minecraft.network.chat.Component;
//import net.minecraft.network.chat.MutableComponent;
//import net.minecraft.network.chat.TextComponent;
//import net.minecraft.network.chat.TranslatableComponent;
//import net.minecraft.util.StringRepresentable;
//import net.minecraft.world.item.*;
//import net.minecraft.world.item.context.BlockPlaceContext;
//import net.minecraft.world.level.Level;
//import net.minecraft.world.level.block.Block;
//import net.minecraft.world.level.block.state.BlockState;
//import org.jetbrains.annotations.NotNull;
//
//import java.util.Map;
//import java.util.function.Supplier;
//
///**
// * Created in 2023/7/5 14:40
// * Project: candycraftce
// *
// * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
// */
//public class ItemDungeonKey extends BlockItem {
//	public static final String GENERATED_KEY_KEY = "tooltip.candycraftce.generated_dungeon";
////	private static final MutableComponent GENERATED_KEY = new TextComponent("(").append(new TranslatableComponent(GENERATED_KEY_KEY)).append(")").withStyle(ChatFormatting.AQUA, ChatFormatting.BOLD);
//	private static final MutableComponent GENERATED_KEY =Component.translatable(GENERATED_KEY_KEY);
//
//	private final DungeonTypes type;
//	public static final String TELEPORTER_DATA_TAG = "teleporter_data";
//
//	public ItemDungeonKey(Properties pProperties, DungeonTypes type) {
//		super(CBlockEntries.TELEPORTER.getBlock(), pProperties);
//		this.type = type;
//	}
//
//	public static void saveToItem(ItemStack pStack, BETeleporter be) {
//		if (be.isInit()) {
//			CompoundTag tag = new CompoundTag();
//			tag.put(TELEPORTER_DATA_TAG, be.saveWithoutMetadata());
//			pStack.setTag(tag);
//		}
//	}
//
//	public DungeonTypes getType() {
//		return type;
//	}
//
//	@Override
//	public @NotNull String getDescriptionId() {
//		return this.getOrCreateDescriptionId();
//	}
//
//	@Override
//	protected boolean placeBlock(@NotNull BlockPlaceContext pContext, @NotNull BlockState pState) {
//		if (type == DungeonTypes.UNDEFINED) return false;
//		if (!super.placeBlock(pContext, pState)) return false;
//		Level level = pContext.getLevel();
//		if (!level.isClientSide()) {
//			if (level.getBlockEntity(pContext.getClickedPos()) instanceof BETeleporter be) {
//				ItemStack item = pContext.getItemInHand();
//				CompoundTag tag = item.getTag();
//				if (tag != null && tag.contains(TELEPORTER_DATA_TAG)) {
//					be.load(tag.getCompound(TELEPORTER_DATA_TAG));
//				}
//			}
//		}
//		return true;
//	}
//
//	@Override
//	public @NotNull Component getName(@NotNull ItemStack pStack) {
//		Component base = super.getName(pStack);
//		if (pStack.getTagElement(TELEPORTER_DATA_TAG) != null) {
//			base = base.copy().append(GENERATED_KEY);
//		}
//		return base;
//	}
//
//	public void fillItemCategory(@NotNull CreativeModeTab pCategory, @NotNull NonNullList<ItemStack> pItems) {
//		if (this.allowdedIn(pCategory)) {
//			pItems.add(new ItemStack(this));
//		}
//	}
//
//	@Override
//	public void registerBlocks(@NotNull Map<Block, Item> pBlockToItemMap, @NotNull Item pItem) {
//	}
//
//	@Override
//	public void removeFromBlockToItemMap(@NotNull Map<Block, Item> blockToItemMap, @NotNull Item itemIn) {
//	}
//
//
//	public enum DungeonTypes implements StringRepresentable, IGeneratorProvider {
//		UNDEFINED(() -> Items.AIR) {
//			@Override
//			public StructureGenerator generate() {
//				return StructureGenerator.NONE;
//			}
//		},
//		BEETLE_DUNGEON(CItemEntries.BEETLE_DUNGEON_KEY) {
//			@Override
//			public StructureGenerator generate() {
//				return new DebugDungeonGenerator();
//			}
//		},
//		JELLY_DUNGEON(CItemEntries.JELLY_DUNGEON_KEY) {
//			@Override
//			public StructureGenerator generate() {
//				return new JellyDungeonGenerator();
//			}
//		},
//		SKY_DUNGEON(CItemEntries.SKY_DUNGEON_KEY) {
//			@Override
//			public StructureGenerator generate() {
//				return null;
//			}
//		},
//		SUGUARD_DUNGEON(CItemEntries.SUGUARD_DUNGEON_KEY) {
//			@Override
//			public StructureGenerator generate() {
//				return null;
//			}
//		};
//
//		private final Supplier<Item> key;
//
//		DungeonTypes(Supplier<Item> key) {
//			this.key = key;
//		}
//
//
//		@Override
//		public @NotNull String getSerializedName() {
//			return name().toLowerCase();
//		}
//
//		public Item getKey() {
//			return key.get();
//		}
//
//	}
//}
