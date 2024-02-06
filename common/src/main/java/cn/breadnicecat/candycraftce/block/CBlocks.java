package cn.breadnicecat.candycraftce.block;

import cn.breadnicecat.candycraftce.CandyCraftCE;
import cn.breadnicecat.candycraftce.block.blocks.*;
import cn.breadnicecat.candycraftce.utils.CLogUtils;
import cn.breadnicecat.candycraftce.utils.CommonUtils;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.BlockState;
import org.slf4j.Logger;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import static cn.breadnicecat.candycraftce.CandyCraftCE.isClient;
import static cn.breadnicecat.candycraftce.block.CBlockBuilder.create;
import static cn.breadnicecat.candycraftce.sound.CSoundTypes.JELLY_FOOTSTEP;
import static cn.breadnicecat.candycraftce.utils.CommonUtils.accept;
import static net.minecraft.world.level.block.Blocks.CAULDRON;


/**
 * Created in 2023/11/26 9:41
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 */
public class CBlocks {

	private static final Logger LOGGER = CLogUtils.sign();

	static {
		CandyCraftCE.hookPostBootstrap(() -> BLOCKS = Collections.unmodifiableMap(CBlocks.BLOCKS));
		if (isClient()) {
			CandyCraftCE.hookMinecraftSetup(CBlocks::declareRendererType);
		}
	}

	public static Map<ResourceLocation, BlockEntry<? extends Block>> BLOCKS = new HashMap<>();
	public static final BlockEntry<SugarBlock> SUGAR_BLOCK = create("sugar_block", SugarBlock::new).setProperties(Blocks.SAND, Properties::randomTicks).simpleBlockItem().save();

	public static final BlockEntry<Block> CARAMEL_BLOCK = create("caramel_block").setProperties(Blocks.STONE, null).simpleBlockItem().save();
	public static final BlockEntry<CaramelPortalBlock> CARAMEL_PORTAL = create("caramel_portal", CaramelPortalBlock::new).setProperties(Blocks.NETHER_PORTAL, null).save();

	public static final BlockEntry<Block> CHOCOLATE_STONE = create("chocolate_stone").setProperties(Blocks.STONE, null).simpleBlockItem().save();
	public static final BlockEntry<Block> CHOCOLATE_COBBLESTONE = create("chocolate_cobblestone").setProperties(Blocks.COBBLESTONE, null).simpleBlockItem().save();

	public static final BlockEntry<Block> PUDDING = create("pudding").setProperties(Blocks.DIRT, null).simpleBlockItem().save();
	public static final BlockEntry<CustardPuddingBlock> CUSTARD_PUDDING = create("custard_pudding", CustardPuddingBlock::new).setProperties(Blocks.GRASS_BLOCK, p -> p.sound(JELLY_FOOTSTEP)).simpleBlockItem().save();
	public static final BlockEntry<PuddingFarmBlock> PUDDING_FARMLAND = create("pudding_farmland", PuddingFarmBlock::new).setProperties(Blocks.FARMLAND, p -> p.sound(JELLY_FOOTSTEP)).save();

	public static final BlockEntry<Block> CANDY_CANE_BLOCK = create("candy_cane_block").setProperties(Blocks.STONE, null).simpleBlockItem().save();
	public static final BlockEntry<WallBlock> CANDY_CANE_WALL = wallBlock("candy_cane_wall").setProperties(CANDY_CANE_BLOCK, null).simpleBlockItem().save();
	public static final BlockEntry<FenceBlock> CANDY_CANE_FENCE = fenceBlock("candy_cane_fence").setProperties(CANDY_CANE_BLOCK, null).simpleBlockItem().save();
	public static final BlockEntry<SlabBlock> CANDY_CANE_SLAB = slabBlock("candy_cane_slab").setProperties(CANDY_CANE_BLOCK, null).simpleBlockItem().save();
	public static final BlockEntry<StairBlock> CANDY_CANE_STAIRS = stairBlock("candy_cane_stairs", CANDY_CANE_BLOCK::defaultBlockState).setProperties(CANDY_CANE_BLOCK, null).simpleBlockItem().save();

	public static final BlockEntry<MarshmallowCraftingTableBlock> MARSHMALLOW_CRAFTING_TABLE = create("marshmallow_crafting_table", MarshmallowCraftingTableBlock::new).setProperties(Blocks.CRAFTING_TABLE, null).simpleBlockItem().save();
	public static final BlockEntry<LicoriceFurnaceBlock> LICORICE_FURNACE = create("licorice_furnace", LicoriceFurnaceBlock::new).setProperties(Blocks.FURNACE, null).simpleBlockItem().save();
	public static final BlockEntry<ChocolateFurnaceBlock> CHOCOLATE_FURNACE = create("chocolate_furnace", ChocolateFurnaceBlock::new).setProperties(LICORICE_FURNACE, null).simpleBlockItem().save();
	public static final BlockEntry<SugarFactoryBlock> SUGAR_FACTORY = create("sugar_factory", SugarFactoryBlock::new).setProperties(CANDY_CANE_BLOCK, null).simpleBlockItem().save();
	public static final BlockEntry<AdvancedSugarFactoryBlock> ADVANCED_SUGAR_FACTORY = create("advanced_sugar_factory", AdvancedSugarFactoryBlock::new).setProperties(SUGAR_FACTORY, null).simpleBlockItem().save();
	//TODO AlchemyMixer functions
	public static final BlockEntry<AlchemyMixerBlock> ALCHEMY_MIXER = create("alchemy_mixer", AlchemyMixerBlock::new).setProperties(CAULDRON, null).simpleBlockItem().save();
//	public static final BlockEntry<CandySaplingBlock> BlockCandySapling = create("alchemy_mixer", AlchemyMixerBlock::new).setProperties(CAULDRON, null).simpleBlockItem().save();
//HELPER.batch((n, a) -> new BlockCandySapling((AbstractTreeGrower) a[0]), (n, b, a) -> simpleBlockItem(b),
//                MODEL_CROSS, VTAG_SAPLINGS, LOOT_DROP_SELF, RENDERER_TYPE_CUTOUT)
//        .addElement(CHOCOLATE_SAPLING, CCTreeGrowers.CHOCOLATE_TREE_GROWER)
//        .addElement(WHITE_CHOCOLATE_SAPLING, CCTreeGrowers.CHOCOLATE_TREE_GROWER)
//        .addElement(CARAMEL_SAPLING, CCTreeGrowers.CHOCOLATE_TREE_GROWER)
//        .addElement(CANDIED_CHERRY_SAPLING, CCTreeGrowers.CHOCOLATE_TREE_GROWER)
//        .register();

	public static final BlockEntry<Block> LICORICE_BLOCK = create("licorice_block").setProperties(Blocks.COAL_BLOCK, null).simpleBlockItem().save();

	@Environment(EnvType.CLIENT)
	private static void declareRendererType() {
		accept((b) -> ItemBlockRenderTypes.TYPE_BY_BLOCK.put(b.get(), RenderType.translucent()),
				CARAMEL_PORTAL, ALCHEMY_MIXER);
//		accept((b) -> ItemBlockRenderTypes.TYPE_BY_BLOCK.put(b.getBlock(), RenderType.cutout()),
//		);
	}

	public static void init() {
		CommonUtils.logInit(LOGGER);
	}

	public static CBlockBuilder<WallBlock> wallBlock(String name) {
		return create(name, WallBlock::new);
	}

	public static CBlockBuilder<FenceBlock> fenceBlock(String name) {
		return create(name, FenceBlock::new);
	}

	public static CBlockBuilder<SlabBlock> slabBlock(String name) {
		return create(name, SlabBlock::new);
	}

	//Models
	public static CBlockBuilder<StairBlock> stairBlock(String name, Supplier<BlockState> base) {
		return create(name, p -> _stairBlock(base, p));
	}

	//  Platform difference
	@ExpectPlatform
	public static StairBlock _stairBlock(Supplier<BlockState> base, Properties p) {
		return CommonUtils.impossible();
	}
}
