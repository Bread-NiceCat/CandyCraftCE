package cn.breadnicecat.candycraftce.registration.block;

import cn.breadnicecat.candycraftce.CandyCraftCE;
import cn.breadnicecat.candycraftce.registration.block.blocks.CaramelPortal;
import cn.breadnicecat.candycraftce.registration.block.blocks.CustardPudding;
import cn.breadnicecat.candycraftce.registration.block.blocks.PuddingFarm;
import cn.breadnicecat.candycraftce.registration.block.blocks.SugarBlock;
import cn.breadnicecat.candycraftce.utils.CLogUtils;
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
import static cn.breadnicecat.candycraftce.registration.block.CBlockBuilder.create;
import static cn.breadnicecat.candycraftce.registration.sound.CSoundTypes.JELLY_FOOTSTEP;
import static cn.breadnicecat.candycraftce.utils.CommonUtils.accept;


/**
 * Created in 2023/11/26 9:41
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 * <p>
 */
public class CBlocks {

	private static final Logger LOGGER = CLogUtils.sign();

	static {
		CandyCraftCE.hookMinecraftSetup(() -> {
			BLOCKS = Collections.unmodifiableMap(CBlocks.BLOCKS);
			if (isClient()) {
				declareRendererType();
			}
		});
	}

	public static Map<ResourceLocation, BlockEntry<? extends Block>> BLOCKS = new HashMap<>();
	public static final BlockEntry<SugarBlock> SUGAR_BLOCK = create("sugar_block", SugarBlock::new).setProperties(Blocks.SAND, Properties::randomTicks).save();

	public static final BlockEntry<Block> CARAMEL_BLOCK = create("caramel_block").setProperties(Blocks.STONE, null).save();
	public static final BlockEntry<CaramelPortal> CARAMEL_PORTAL = create("caramel_portal", CaramelPortal::new).setProperties(Blocks.NETHER_PORTAL, null).save();

	public static final BlockEntry<Block> CHOCOLATE_STONE = create("chocolate_stone").setProperties(Blocks.STONE, null).save();
	public static final BlockEntry<Block> CHOCOLATE_COBBLESTONE = create("chocolate_cobblestone").setProperties(Blocks.COBBLESTONE, null).save();

	public static final BlockEntry<Block> PUDDING = create("pudding").setProperties(Blocks.DIRT, null).save();
	public static final BlockEntry<CustardPudding> CUSTARD_PUDDING = create("custard_pudding", CustardPudding::new).setProperties(Blocks.GRASS_BLOCK, p -> p.sound(JELLY_FOOTSTEP)).save();
	public static final BlockEntry<PuddingFarm> PUDDING_FARMLAND = create("pudding_farmland", PuddingFarm::new).setProperties(Blocks.FARMLAND, p -> p.sound(JELLY_FOOTSTEP)).save();

	public static final BlockEntry<Block> CANDY_CANE_BLOCK = create("candy_cane_block").setProperties(Blocks.STONE, null).save();
	public static final BlockEntry<WallBlock> CANDY_CANE_WALL = wallBlock("candy_cane_wall").setProperties(CANDY_CANE_BLOCK, null).save();
	public static final BlockEntry<FenceBlock> CANDY_CANE_FENCE = fenceBlock("candy_cane_fence").setProperties(CANDY_CANE_BLOCK, null).save();
	public static final BlockEntry<SlabBlock> CANDY_CANE_SLAB = slabBlock("candy_cane_slab").setProperties(CANDY_CANE_BLOCK, null).save();
	public static final BlockEntry<StairBlock> CANDY_CANE_STAIRS = stairBlock("candy_cane_stairs", CANDY_CANE_BLOCK::defaultBlockState).setProperties(CANDY_CANE_BLOCK, null).save();

	//HELPER.single(CANDY_CANE_BLOCK, () -> new Block(Properties.copy(Blocks.STONE)), CCBlockManager::simpleBlockItem,
//        MODEL_COLUMN, VTAG_MINEABLE_WITH_PICKAXE, LOOT_DROP_SELF);
//HELPER.single(CANDY_CANE_WALL, () -> new WallBlock(Properties.copy(CANDY_CANE_BLOCK.getBlock())), CCBlockManager::simpleBlockItem,
//        VTAG_MINEABLE_WITH_PICKAXE, VTAG_WALLS, LOOT_DROP_SELF);
//HELPER.single(CANDY_CANE_FENCE, () -> new FenceBlock(Properties.copy(CANDY_CANE_BLOCK.getBlock())), CCBlockManager::simpleBlockItem,
//        VTAG_MINEABLE_WITH_PICKAXE, VTAG_FENCES, LOOT_DROP_SELF);
//HELPER.single(CANDY_CANE_SLAB, () -> new SlabBlock(Properties.copy(CANDY_CANE_BLOCK.getBlock()).noOcclusion()), CCBlockManager::simpleBlockItem,
//        LOOT_DROP_SELF, RENDERER_TYPE_CUTOUT);
//HELPER.single(CANDY_CANE_STAIRS, () -> new StairBlock(CANDY_CANE_BLOCK.getBlock().defaultBlockState(), Properties.copy(CANDY_CANE_BLOCK.getBlock())), CCBlockManager::simpleBlockItem,
//        LOOT_DROP_SELF, RENDERER_TYPE_CUTOUT);
	@Environment(EnvType.CLIENT)
	private static void declareRendererType() {
		accept((b) -> ItemBlockRenderTypes.TYPE_BY_BLOCK.put(b.getBlock(), RenderType.translucent()),
				CARAMEL_PORTAL);
//		accept((b) -> ItemBlockRenderTypes.TYPE_BY_BLOCK.put(b.getBlock(), RenderType.cutout()),
//				CANDY_CANE_SLAB, CANDY_CANE_STAIRS
//		);
	}

	public static void init() {
		LOGGER.info("init");
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
		throw new AssertionError();
	}
}
