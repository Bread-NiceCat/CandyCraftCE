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
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.UniformInt;
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
import static cn.breadnicecat.candycraftce.sound.CSoundTypes.JELLY;
import static cn.breadnicecat.candycraftce.utils.CommonUtils.accept;
import static net.minecraft.world.level.block.Blocks.*;


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

	public static final BlockEntry<SugarBlock> SUGAR_BLOCK = create("sugar_block", SugarBlock::new).setProperties(Blocks.SAND, Properties::randomTicks).save();
	public static final BlockEntry<Block> CARAMEL_BLOCK = create("caramel_block").setProperties(Blocks.STONE, null).save();

	public static final BlockEntry<Block> PUDDING = create("pudding").setProperties(Blocks.DIRT, p -> p.sound(JELLY)).save();
	public static final BlockEntry<CustardPuddingBlock> CUSTARD_PUDDING = create("custard_pudding", CustardPuddingBlock::new).setProperties(Blocks.GRASS_BLOCK, p -> p.sound(JELLY)).save();
	public static final BlockEntry<PuddingFarmBlock> PUDDING_FARMLAND = create("pudding_farmland", PuddingFarmBlock::new).setProperties(Blocks.FARMLAND, p -> p.sound(JELLY)).noBlockItem().save();
	//forestry
	public static final BlockEntry<ChocolateSaplingBlock> CHOCOLATE_SAPLING = create("chocolate_sapling", ChocolateSaplingBlock::new).setProperties(OAK_SAPLING, null).save();
	public static final BlockEntry<WhiteChocolateSaplingBlock> WHITE_CHOCOLATE_SAPLING = create("white_chocolate_sapling", WhiteChocolateSaplingBlock::new).setProperties(CHOCOLATE_SAPLING, null).save();
	public static final BlockEntry<CaramelSaplingBlock> CARAMEL_SAPLING = create("caramel_sapling", CaramelSaplingBlock::new).setProperties(CHOCOLATE_SAPLING, null).save();
	public static final BlockEntry<CandiedCherrySapling> CANDIED_CHERRY_SAPLING = create("candied_cherry_sapling", CandiedCherrySapling::new).setProperties(CHOCOLATE_SAPLING, null).save();

	public static final BlockEntry<RotatedPillarBlock> MARSHMALLOW_LOG = create("marshmallow_log", RotatedPillarBlock::new).setProperties(Blocks.OAK_LOG, null).save();
	public static final BlockEntry<RotatedPillarBlock> DARK_MARSHMALLOW_LOG = create("dark_marshmallow_log", RotatedPillarBlock::new).setProperties(MARSHMALLOW_LOG, null).save();
	public static final BlockEntry<RotatedPillarBlock> LIGHT_MARSHMALLOW_LOG = create("light_marshmallow_log", RotatedPillarBlock::new).setProperties(MARSHMALLOW_LOG, null).save();

	public static final BlockEntry<Block> MARSHMALLOW_PLANKS = create("marshmallow_planks").setProperties(Blocks.OAK_PLANKS, null).save();
	public static final BlockEntry<Block> DARK_MARSHMALLOW_PLANKS = create("dark_marshmallow_planks").setProperties(MARSHMALLOW_PLANKS, null).save();
	public static final BlockEntry<Block> LIGHT_MARSHMALLOW_PLANKS = create("light_marshmallow_planks").setProperties(MARSHMALLOW_PLANKS, null).save();

	public static final BlockEntry<LeavesBlock> CHOCOLATE_LEAVES = create("chocolate_leaves", LeavesBlock::new).setProperties(OAK_LEAVES, null).save();
	public static final BlockEntry<LeavesBlock> WHITE_CHOCOLATE_LEAVES = create("white_chocolate_leaves", LeavesBlock::new).setProperties(CHOCOLATE_LEAVES, null).save();
	public static final BlockEntry<LeavesBlock> CARAMEL_LEAVES = create("caramel_leaves", LeavesBlock::new).setProperties(CHOCOLATE_LEAVES, null).save();
	public static final BlockEntry<LeavesBlock> CANDIED_CHERRY_LEAVES = create("candied_cherry_leaves", LeavesBlock::new).setProperties(CHOCOLATE_LEAVES, null).save();
	public static final BlockEntry<LeavesBlock> MAGIC_LEAVES = create("magic_leaves", LeavesBlock::new).setProperties(CHOCOLATE_LEAVES, null).save();
	//
	public static final BlockEntry<Block> CHOCOLATE_STONE = create("chocolate_stone").setProperties(Blocks.STONE, null).save();
	public static final BlockEntry<Block> CHOCOLATE_COBBLESTONE = create("chocolate_cobblestone").setProperties(Blocks.COBBLESTONE, null).save();
	public static final BlockEntry<DropExperienceBlock> JELLY_ORE = create("jelly_ore", DropExperienceBlock::new).setProperties(IRON_ORE, null).save();
	public static final BlockEntry<DropExperienceBlock> NOUGAT_ORE = create("nougat_ore", p -> new DropExperienceBlock(p, UniformInt.of(1, 5))).setProperties(IRON_ORE, null).save();
	public static final BlockEntry<DropExperienceBlock> LICORICE_ORE = create("licorice_ore", DropExperienceBlock::new).setProperties(COAL_ORE, null).save();
	public static final BlockEntry<DropExperienceBlock> HONEYCOMB_ORE = create("honeycomb_ore", p -> new DropExperienceBlock(p, UniformInt.of(0, 3))).setProperties(IRON_ORE, null).save();
	public static final BlockEntry<DropExperienceBlock> PEZ_ORE = create("pez_ore", DropExperienceBlock::new).setProperties(DIAMOND_ORE, null).save();

	public static final BlockEntry<Block> CANDY_CANE_BLOCK = create("candy_cane_block").setProperties(STONE, null).save();
	public static final BlockEntry<Block> LICORICE_BLOCK = create("licorice_block").setProperties(Blocks.COAL_BLOCK, null).save();
	public static final BlockEntry<Block> LICORICE_BRICK = create("licorice_brick").setProperties(LICORICE_BLOCK, null).save();

	public static final BlockEntry<MarshmallowCraftingTableBlock> MARSHMALLOW_CRAFTING_TABLE = create("marshmallow_crafting_table", MarshmallowCraftingTableBlock::new).setProperties(Blocks.CRAFTING_TABLE, null).save();
	public static final BlockEntry<LicoriceFurnaceBlock> LICORICE_FURNACE = create("licorice_furnace", LicoriceFurnaceBlock::new).setProperties(Blocks.FURNACE, null).save();
	public static final BlockEntry<ChocolateFurnaceBlock> CHOCOLATE_FURNACE = create("chocolate_furnace", ChocolateFurnaceBlock::new).setProperties(LICORICE_FURNACE, null).save();
	public static final BlockEntry<SugarFactoryBlock> SUGAR_FACTORY = create("sugar_factory", SugarFactoryBlock::new).setProperties(CANDY_CANE_BLOCK, null).save();
	public static final BlockEntry<AdvancedSugarFactoryBlock> ADVANCED_SUGAR_FACTORY = create("advanced_sugar_factory", AdvancedSugarFactoryBlock::new).setProperties(SUGAR_FACTORY, null).save();
	////TODO AlchemyMixer functions
	public static final BlockEntry<AlchemyMixerBlock> ALCHEMY_MIXER = create("alchemy_mixer", AlchemyMixerBlock::new).setProperties(ANVIL, null).save();

	public static final BlockEntry<FenceBlock> MARSHMALLOW_FENCE = create("marshmallow_fence", FenceBlock::new).setProperties(MARSHMALLOW_PLANKS, null).save();
	public static final BlockEntry<FenceBlock> LIGHT_MARSHMALLOW_FENCE = create("light_marshmallow_fence", FenceBlock::new).setProperties(LIGHT_MARSHMALLOW_PLANKS, null).save();
	public static final BlockEntry<FenceBlock> DARK_MARSHMALLOW_FENCE = create("dark_marshmallow_fence", FenceBlock::new).setProperties(DARK_MARSHMALLOW_PLANKS, null).save();
	public static final BlockEntry<FenceBlock> CANDY_CANE_FENCE = create("candy_cane_fence", FenceBlock::new).setProperties(CANDY_CANE_BLOCK, null).save();

	public static final BlockEntry<WallBlock> CANDY_CANE_WALL = create("candy_cane_wall", WallBlock::new).setProperties(CANDY_CANE_BLOCK, null).save();
	public static final BlockEntry<WallBlock> LICORICE_WALL = create("licorice_wall", WallBlock::new).setProperties(LICORICE_BLOCK, null).save();
	public static final BlockEntry<WallBlock> LICORICE_BRICK_WALL = create("licorice_brick_wall", WallBlock::new).setProperties(LICORICE_BRICK, null).save();

	public static final BlockEntry<SlabBlock> MARSHMALLOW_SLAB = create("marshmallow_slab", SlabBlock::new).setProperties(MARSHMALLOW_PLANKS, null).save();
	public static final BlockEntry<SlabBlock> LIGHT_MARSHMALLOW_SLAB = create("light_marshmallow_slab", SlabBlock::new).setProperties(LIGHT_MARSHMALLOW_PLANKS, null).save();
	public static final BlockEntry<SlabBlock> DARK_MARSHMALLOW_SLAB = create("dark_marshmallow_slab", SlabBlock::new).setProperties(DARK_MARSHMALLOW_PLANKS, null).save();
	public static final BlockEntry<SlabBlock> CANDY_CANE_SLAB = create("candy_cane_slab", SlabBlock::new).setProperties(CANDY_CANE_BLOCK, null).save();
	public static final BlockEntry<SlabBlock> LICORICE_SLAB = create("licorice_slab", SlabBlock::new).setProperties(LICORICE_BLOCK, null).save();
	public static final BlockEntry<SlabBlock> LICORICE_BRICK_SLAB = create("licorice_brick_slab", SlabBlock::new).setProperties(LICORICE_BRICK, null).save();

	public static final BlockEntry<StairBlock> MARSHMALLOW_STAIRS = stairBlock("marshmallow_stairs", MARSHMALLOW_PLANKS::defaultBlockState).setProperties(MARSHMALLOW_PLANKS, null).save();
	public static final BlockEntry<StairBlock> LIGHT_MARSHMALLOW_STAIRS = stairBlock("light_marshmallow_stairs", LIGHT_MARSHMALLOW_PLANKS::defaultBlockState).setProperties(LIGHT_MARSHMALLOW_PLANKS, null).save();
	public static final BlockEntry<StairBlock> DARK_MARSHMALLOW_STAIRS = stairBlock("dark_marshmallow_stairs", DARK_MARSHMALLOW_PLANKS::defaultBlockState).setProperties(DARK_MARSHMALLOW_PLANKS, null).save();
	public static final BlockEntry<StairBlock> CANDY_CANE_STAIRS = stairBlock("candy_cane_stairs", CANDY_CANE_BLOCK::defaultBlockState).setProperties(CANDY_CANE_BLOCK, null).save();
	public static final BlockEntry<StairBlock> LICORICE_STAIRS = stairBlock("licorice_stairs", LICORICE_BLOCK::defaultBlockState).setProperties(LICORICE_BLOCK, null).save();
	public static final BlockEntry<StairBlock> LICORICE_BRICK_STAIRS = stairBlock("licorice_brick_stairs", LICORICE_BRICK::defaultBlockState).setProperties(LICORICE_BRICK, null).save();

	//invisible
	public static final BlockEntry<CaramelPortalBlock> CARAMEL_PORTAL = create("caramel_portal", CaramelPortalBlock::new).setProperties(Blocks.NETHER_PORTAL, null).noBlockItem().save();
	//火把的BlockItem: cn.breadnicecat.candycraftce.item.CItems.TORCH
	public static final BlockEntry<TorchBlock> HONEYCOMB_TORCH = create("honeycomb_torch", p -> new TorchBlock(p, ParticleTypes.FLAME)).setProperties(Blocks.TORCH, null).noBlockItem().save();
	public static final BlockEntry<WallTorchBlock> WALL_HONEYCOMB_TORCH = create("wall_honeycomb_torch", p -> new WallTorchBlock(p, ParticleTypes.FLAME)).setProperties(WALL_TORCH, p -> p.dropsLike(HONEYCOMB_TORCH.get())).noBlockItem().save();

	@Environment(EnvType.CLIENT)
	private static void declareRendererType() {
		accept((b) -> ItemBlockRenderTypes.TYPE_BY_BLOCK.put(b.get(), RenderType.translucent()),
				CARAMEL_PORTAL, ALCHEMY_MIXER);
		accept((b) -> ItemBlockRenderTypes.TYPE_BY_BLOCK.put(b.get(), RenderType.cutout()),
				HONEYCOMB_TORCH, WALL_HONEYCOMB_TORCH, CHOCOLATE_SAPLING, WHITE_CHOCOLATE_SAPLING, CARAMEL_SAPLING,
				CANDIED_CHERRY_SAPLING);
	}

	public static void init() {
	}


	//Typical
	public static CBlockBuilder<StairBlock> stairBlock(String name, Supplier<BlockState> base) {
		return create(name, p -> _stairBlock(base, p));
	}

	//Platform difference
	@ExpectPlatform
	public static StairBlock _stairBlock(Supplier<BlockState> base, Properties p) {
		return CommonUtils.impossible();
	}
}
