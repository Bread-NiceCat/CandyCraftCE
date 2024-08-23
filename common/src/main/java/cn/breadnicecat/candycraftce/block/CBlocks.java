package cn.breadnicecat.candycraftce.block;

import cn.breadnicecat.candycraftce.CandyCraftCE;
import cn.breadnicecat.candycraftce.block.blocks.*;
import cn.breadnicecat.candycraftce.block.blocks.JellyBlock.JellyType;
import cn.breadnicecat.candycraftce.level.CDims;
import cn.breadnicecat.candycraftce.mixin.AxeItemAccessor;
import cn.breadnicecat.candycraftce.utils.CLogUtils;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.Fluid;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.HashSet;
import java.util.function.Supplier;

import static cn.breadnicecat.candycraftce.CandyCraftCE.hookMinecraftSetup;
import static cn.breadnicecat.candycraftce.CandyCraftCE.isClient;
import static cn.breadnicecat.candycraftce.block.CBlockBuilder.create;
import static cn.breadnicecat.candycraftce.sound.CSoundTypes.JELLY;
import static cn.breadnicecat.candycraftce.utils.CommonUtils.*;
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
	public static final HashSet<BlockEntry<?>> BLOCKS = new HashSet<>();
	
	public static final BlockEntry<SweetGrassBlock> SWEET_GRASS_0 = create("sweet_grass_0", SweetGrassBlock::new).setProperties(POPPY, null).save();
	public static final BlockEntry<SweetGrassBlock> SWEET_GRASS_1 = create("sweet_grass_1", SweetGrassBlock::new).setProperties(POPPY, null).save();
	public static final BlockEntry<SweetGrassBlock> SWEET_GRASS_2 = create("sweet_grass_2", SweetGrassBlock::new).setProperties(POPPY, null).save();
	public static final BlockEntry<SweetGrassBlock> SWEET_GRASS_3 = create("sweet_grass_3", SweetGrassBlock::new).setProperties(POPPY, null).save();
	public static final BlockEntry<CandyWaterPlantBlock> MINT = create("mint", CandyWaterPlantBlock::new).setProperties(KELP_PLANT, null).save();
	public static final BlockEntry<CandyWaterPlantBlock> ROPE_RASPBERRY = create("rope_raspberry", CandyWaterPlantBlock::new).setProperties(KELP_PLANT, null).save();
	public static final BlockEntry<CandyWaterPlantBlock> BANANA_SEAWEED = create("banana_seaweed", CandyWaterPlantBlock::new).setProperties(KELP_PLANT, null).save();
	public static final BlockEntry<CandyPlantBlock> FRAISE_TAGADA_FLOWER = create("fraise_tagada_flower", CandyPlantBlock::new).setProperties(POPPY, null).save();
	public static final BlockEntry<GoldenSugarFlowerBlock> GOLDEN_SUGAR_FLOWER = create("golden_sugar_flower", GoldenSugarFlowerBlock::new).setProperties(POPPY, null).save();
	public static final BlockEntry<AcidMintFlowerBlock> ACID_MINT_FLOWER = create("acid_mint_flower", AcidMintFlowerBlock::new).setProperties(POPPY, null).save();
	public static final BlockEntry<CandyCropBlock> DRAGIBUS_CROPS = create("dragibus_crops", CandyCropBlock::createL4).setProperties(WHEAT, null).noBlockItem().save();
	public static final BlockEntry<LollipopStemBlock> LOLLIPOP_STEM = create("lollipop_stem", LollipopStemBlock::new).setProperties(WHEAT, null).noBlockItem().save();
	public static final BlockEntry<LollipopFruit> LOLLIPOP_FRUIT = create("lollipop_fruit", LollipopFruit::new).setProperties(WHEAT, null).noBlockItem().save();
	
	public static final BlockEntry<SugarBlock> SUGAR_BLOCK = create("sugar_block", SugarBlock::new).setProperties(Blocks.SAND, Properties::randomTicks).save();
	public static final BlockEntry<Block> CARAMEL_BLOCK = create("caramel_block").setProperties(Blocks.STONE, null).save();
	public static final BlockEntry<Block> CARAMEL_BRICKS = create("caramel_bricks").setProperties(CARAMEL_BLOCK, null).save();
	public static final BlockEntry<Block> CHOCOLATE_CARAMEL_BRICKS = create("chocolate_caramel_bricks").setProperties(CARAMEL_BLOCK, null).save();
	public static final BlockEntry<Block> WHITE_CHOCOLATE_CARAMEL_BRICKS = create("white_chocolate_caramel_bricks").setProperties(CHOCOLATE_CARAMEL_BRICKS, null).save();
//	public static final BlockEntry<Block> BLACK_CHOCOLATE_CARAMEL_BRICKS = create("black_chocolate_caramel_bricks").setProperties(CHOCOLATE_CARAMEL_BRICKS, null).save();
	
	public static final BlockEntry<Block> PUDDING = create("pudding").setProperties(Blocks.DIRT, p -> p.sound(JELLY)).save();
	public static final BlockEntry<CustardPuddingBlock> CUSTARD_PUDDING = create("custard_pudding", CustardPuddingBlock::new).setProperties(Blocks.GRASS_BLOCK, p -> p.sound(JELLY)).save();
	public static final BlockEntry<PuddingFarmBlock> PUDDING_FARMLAND = create("pudding_farmland", PuddingFarmBlock::new).setProperties(Blocks.FARMLAND, p -> p.sound(JELLY)).save();
	public static final BlockEntry<Block> ICE_CREAM = create("ice_cream", Block::new).setProperties(SNOW_BLOCK, null).save();
	public static final BlockEntry<Block> MINT_ICE_CREAM = create("mint_ice_cream", Block::new).setProperties(ICE_CREAM, null).save();
	public static final BlockEntry<Block> STRAWBERRY_ICE_CREAM = create("strawberry_ice_cream", Block::new).setProperties(ICE_CREAM, null).save();
	public static final BlockEntry<Block> BLUEBERRY_ICE_CREAM = create("blueberry_ice_cream", Block::new).setProperties(ICE_CREAM, null).save();
	
	public static final BlockEntry<Block> MINT_BLOCK = create("mint_block").setProperties(HAY_BLOCK, null).save();
	public static final BlockEntry<Block> RASPBERRY_BLOCK = create("raspberry_block").setProperties(HAY_BLOCK, null).save();
	public static final BlockEntry<Block> BANANA_SEAWEEDS_BLOCK = create("banana_seaweeds_block").setProperties(HAY_BLOCK, null).save();
	public static final BlockEntry<Block> COTTON_CANDY_BLOCK = create("cotton_candy_block").setProperties(HAY_BLOCK, null).save();
	public static final BlockEntry<Block> CANDIED_CHERRY_SACK = create("candied_cherry_sack").setProperties(HAY_BLOCK, null).save();
	public static final BlockEntry<Block> CHEWING_GUM_BLOCK = create("chewing_gum_block").setProperties(SLIME_BLOCK, p -> p.destroyTime(0.6F)).save();
	
	public static final BlockEntry<ChocolateSaplingBlock> CHOCOLATE_SAPLING = create("chocolate_sapling", ChocolateSaplingBlock::new).setProperties(OAK_SAPLING, null).save();
	public static final BlockEntry<WhiteChocolateSaplingBlock> WHITE_CHOCOLATE_SAPLING = create("white_chocolate_sapling", WhiteChocolateSaplingBlock::new).setProperties(CHOCOLATE_SAPLING, null).save();
	public static final BlockEntry<CaramelSaplingBlock> CARAMEL_SAPLING = create("caramel_sapling", CaramelSaplingBlock::new).setProperties(CHOCOLATE_SAPLING, null).save();
	public static final BlockEntry<CandiedCherrySapling> CANDIED_CHERRY_SAPLING = create("candied_cherry_sapling", CandiedCherrySapling::new).setProperties(CHOCOLATE_SAPLING, null).save();
	
	public static final BlockEntry<RotatedPillarBlock> MARSHMALLOW_LOG = create("marshmallow_log", RotatedPillarBlock::new).setProperties(Blocks.OAK_LOG, null).save();
	public static final BlockEntry<RotatedPillarBlock> DARK_MARSHMALLOW_LOG = create("dark_marshmallow_log", RotatedPillarBlock::new).setProperties(MARSHMALLOW_LOG, null).save();
	public static final BlockEntry<RotatedPillarBlock> LIGHT_MARSHMALLOW_LOG = create("light_marshmallow_log", RotatedPillarBlock::new).setProperties(MARSHMALLOW_LOG, null).save();
	public static final BlockEntry<RotatedPillarBlock> STRIPPED_MARSHMALLOW_LOG = create("stripped_marshmallow_log", RotatedPillarBlock::new).setProperties(MARSHMALLOW_LOG, null).save();
	public static final BlockEntry<RotatedPillarBlock> STRIPPED_DARK_MARSHMALLOW_LOG = create("stripped_dark_marshmallow_log", RotatedPillarBlock::new).setProperties(MARSHMALLOW_LOG, null).save();
	public static final BlockEntry<RotatedPillarBlock> STRIPPED_LIGHT_MARSHMALLOW_LOG = create("stripped_light_marshmallow_log", RotatedPillarBlock::new).setProperties(MARSHMALLOW_LOG, null).save();
	
	static {
		hookMinecraftSetup(() -> AxeItemAccessor.setSTRIPPABLES(make(() -> {
			LOGGER.info("Inject Axe Strippable");
			HashMap<Block, Block> map = new HashMap<>(AxeItemAccessor.getSTRIPPABLES());
			map.put(MARSHMALLOW_LOG.get(), STRIPPED_MARSHMALLOW_LOG.get());
			map.put(DARK_MARSHMALLOW_LOG.get(), STRIPPED_DARK_MARSHMALLOW_LOG.get());
			map.put(LIGHT_MARSHMALLOW_LOG.get(), STRIPPED_LIGHT_MARSHMALLOW_LOG.get());
			return map;
		})));
	}
	
	public static final BlockEntry<Block> MARSHMALLOW_PLANKS = create("marshmallow_planks").setProperties(Blocks.OAK_PLANKS, null).save();
	public static final BlockEntry<Block> DARK_MARSHMALLOW_PLANKS = create("dark_marshmallow_planks").setProperties(MARSHMALLOW_PLANKS, null).save();
	public static final BlockEntry<Block> LIGHT_MARSHMALLOW_PLANKS = create("light_marshmallow_planks").setProperties(MARSHMALLOW_PLANKS, null).save();
	
	public static final BlockEntry<LeavesBlock> CHOCOLATE_LEAVES = create("chocolate_leaves", LeavesBlock::new).setProperties(OAK_LEAVES, null).save();
	public static final BlockEntry<LeavesBlock> WHITE_CHOCOLATE_LEAVES = create("white_chocolate_leaves", LeavesBlock::new).setProperties(CHOCOLATE_LEAVES, null).save();
	public static final BlockEntry<CaramelLeavesBlock> CARAMEL_LEAVES = create("caramel_leaves", CaramelLeavesBlock::new).setProperties(CHOCOLATE_LEAVES, null).save();
	public static final BlockEntry<LeavesBlock> CANDIED_CHERRY_LEAVES = create("candied_cherry_leaves", LeavesBlock::new).setProperties(CHOCOLATE_LEAVES, null).save();
	public static final BlockEntry<LeavesBlock> MAGICAL_LEAVES = create("magical_leaves", LeavesBlock::new).setProperties(CHOCOLATE_LEAVES, null).save();
	
	public static final BlockEntry<Block> CHOCOLATE_STONE = create("chocolate_stone").setProperties(Blocks.STONE, null).save();
	//	public static final BlockEntry<Block> BLACK_CHOCOLATE_STONE = create("black_chocolate_stone").setProperties(CHOCOLATE_STONE, null).save();
	public static final BlockEntry<Block> WHITE_CHOCOLATE_STONE = create("white_chocolate_stone").setProperties(CHOCOLATE_STONE, null).save();
	public static final BlockEntry<Block> CHOCOLATE_COBBLESTONE = create("chocolate_cobblestone").setProperties(Blocks.COBBLESTONE, null).save();
	//	public static final BlockEntry<Block> BLACK_CHOCOLATE_COBBLESTONE = create("black_chocolate_cobblestone").setProperties(CHOCOLATE_COBBLESTONE, null).save();
	public static final BlockEntry<Block> WHITE_CHOCOLATE_COBBLESTONE = create("white_chocolate_cobblestone").setProperties(CHOCOLATE_COBBLESTONE, null).save();
	public static final BlockEntry<Block> CHOCOLATE_BRICKS = create("chocolate_bricks").setProperties(CHOCOLATE_STONE, null).save();
	//	public static final BlockEntry<Block> BLACK_CHOCOLATE_BRICKS = create("black_chocolate_bricks").setProperties(BLACK_CHOCOLATE_STONE, null).save();
	public static final BlockEntry<Block> WHITE_CHOCOLATE_BRICKS = create("white_chocolate_bricks").setProperties(WHITE_CHOCOLATE_STONE, null).save();
	public static final BlockEntry<Block> CHOCOLATE_STONE_TILE = create("chocolate_stone_tile").setProperties(CHOCOLATE_STONE, null).save();
	public static final BlockEntry<Block> WHITE_CHOCOLATE_STONE_TILE = create("white_chocolate_stone_tile").setProperties(CHOCOLATE_STONE_TILE, null).save();
//	public static final BlockEntry<Block> BLACK_CHOCOLATE_STONE_TILE = create("black_chocolate_stone_tile").setProperties(CHOCOLATE_STONE_TILE, null).save();
	
	public static final BlockEntry<DropExperienceBlock> JELLY_ORE = create("jelly_ore", DropExperienceBlock::new).setProperties(IRON_ORE, null).save();
	public static final BlockEntry<DropExperienceBlock> NOUGAT_ORE = create("nougat_ore", p -> new DropExperienceBlock(p, UniformInt.of(1, 5))).setProperties(IRON_ORE, null).save();
	public static final BlockEntry<DropExperienceBlock> LICORICE_ORE = create("licorice_ore", DropExperienceBlock::new).setProperties(COAL_ORE, null).save();
	public static final BlockEntry<DropExperienceBlock> HONEYCOMB_ORE = create("honeycomb_ore", p -> new DropExperienceBlock(p, UniformInt.of(0, 3))).setProperties(IRON_ORE, null).save();
	public static final BlockEntry<DropExperienceBlock> PEZ_ORE = create("pez_ore", DropExperienceBlock::new).setProperties(DIAMOND_ORE, p -> p.lightLevel(i -> 8)).save();
	public static final BlockEntry<DropExperienceBlock> WHITE_JELLY_ORE = create("white_jelly_ore", DropExperienceBlock::new).setProperties(JELLY_ORE, null).save();
	public static final BlockEntry<DropExperienceBlock> WHITE_NOUGAT_ORE = create("white_nougat_ore", p -> new DropExperienceBlock(p, UniformInt.of(1, 5))).setProperties(NOUGAT_ORE, null).save();
	public static final BlockEntry<DropExperienceBlock> WHITE_LICORICE_ORE = create("white_licorice_ore", DropExperienceBlock::new).setProperties(LICORICE_ORE, null).save();
	public static final BlockEntry<DropExperienceBlock> WHITE_HONEYCOMB_ORE = create("white_honeycomb_ore", p -> new DropExperienceBlock(p, UniformInt.of(0, 3))).setProperties(HONEYCOMB_ORE, null).save();
	public static final BlockEntry<DropExperienceBlock> WHITE_PEZ_ORE = create("white_pez_ore", DropExperienceBlock::new).setProperties(PEZ_ORE, null).save();
	
	public static final BlockEntry<Block> CANDY_CANE_BLOCK = create("candy_cane_block").setProperties(STONE, null).save();
	public static final BlockEntry<Block> LICORICE_BLOCK = create("licorice_block").setProperties(Blocks.COAL_BLOCK, null).save();
	public static final BlockEntry<Block> LICORICE_BRICKS = create("licorice_bricks").setProperties(LICORICE_BLOCK, null).save();
	public static final BlockEntry<Block> NOUGAT_BLOCK = create("nougat_block").setProperties(IRON_BLOCK, null).save();
	public static final BlockEntry<Block> NOUGAT_HEAD = create("nougat_head").setProperties(NOUGAT_BLOCK, null).save();
	public static final BlockEntry<Block> HONEYCOMB_BLOCK = create("honeycomb_block").setProperties(IRON_BLOCK, null).save();
	public static final BlockEntry<Block> HONEYCOMB_LAMP = create("honeycomb_lamp").setProperties(GLOWSTONE, p -> p.strength(1.5f)).save();
	public static final BlockEntry<Block> PEZ_BLOCK = create("pez_block").setProperties(DIAMOND_BLOCK, p -> p.strength(1.5f)).save();
	
	public static final BlockEntry<MarshmallowCraftingTableBlock> MARSHMALLOW_CRAFTING_TABLE = create("marshmallow_crafting_table", MarshmallowCraftingTableBlock::new).setProperties(Blocks.CRAFTING_TABLE, null).save();
	public static final BlockEntry<LicoriceFurnaceBlock> LICORICE_FURNACE = create("licorice_furnace", LicoriceFurnaceBlock::new).setProperties(Blocks.FURNACE, null).save();
	public static final BlockEntry<ChocolateFurnaceBlock> CHOCOLATE_FURNACE = create("chocolate_furnace", ChocolateFurnaceBlock::new).setProperties(LICORICE_FURNACE, null).save();
	public static final BlockEntry<ChocolateFurnaceBlock> WHITE_CHOCOLATE_FURNACE = create("white_chocolate_furnace", ChocolateFurnaceBlock::new).setProperties(CHOCOLATE_FURNACE, null).save();
	//	public static final BlockEntry<ChocolateFurnaceBlock> BLACK_CHOCOLATE_FURNACE = create("black_chocolate_furnace", ChocolateFurnaceBlock::new).setProperties(CHOCOLATE_FURNACE, null).save();
	public static final BlockEntry<SugarFactoryBlock> SUGAR_FACTORY = create("sugar_factory", SugarFactoryBlock::new).setProperties(CANDY_CANE_BLOCK, null).save();
	public static final BlockEntry<AdvancedSugarFactoryBlock> ADVANCED_SUGAR_FACTORY = create("advanced_sugar_factory", AdvancedSugarFactoryBlock::new).setProperties(SUGAR_FACTORY, null).save();
	//TODO AlchemyMixer functions
	public static final BlockEntry<AlchemyMixerBlock> ALCHEMY_MIXER = create("alchemy_mixer", AlchemyMixerBlock::new).setProperties(ANVIL, Properties::noOcclusion).save();
	
	public static final BlockEntry<FenceBlock> MARSHMALLOW_FENCE = create("marshmallow_fence", FenceBlock::new).setProperties(MARSHMALLOW_PLANKS, null).save();
	public static final BlockEntry<FenceBlock> LIGHT_MARSHMALLOW_FENCE = create("light_marshmallow_fence", FenceBlock::new).setProperties(LIGHT_MARSHMALLOW_PLANKS, null).save();
	public static final BlockEntry<FenceBlock> DARK_MARSHMALLOW_FENCE = create("dark_marshmallow_fence", FenceBlock::new).setProperties(DARK_MARSHMALLOW_PLANKS, null).save();
	public static final BlockEntry<FenceBlock> CANDY_CANE_FENCE = create("candy_cane_fence", FenceBlock::new).setProperties(CANDY_CANE_BLOCK, null).save();
	
	public static final BlockEntry<WallBlock> CANDY_CANE_WALL = create("candy_cane_wall", WallBlock::new).setProperties(CANDY_CANE_BLOCK, null).save();
	public static final BlockEntry<WallBlock> LICORICE_WALL = create("licorice_wall", WallBlock::new).setProperties(LICORICE_BLOCK, null).save();
	public static final BlockEntry<WallBlock> LICORICE_BRICK_WALL = create("licorice_brick_wall", WallBlock::new).setProperties(LICORICE_BRICKS, null).save();
	
	public static final BlockEntry<SlabBlock> MINT_SLAB = create("mint_slab", SlabBlock::new).setProperties(MINT_BLOCK, null).save();
	public static final BlockEntry<SlabBlock> RASPBERRY_SLAB = create("raspberry_slab", SlabBlock::new).setProperties(RASPBERRY_BLOCK, null).save();
	public static final BlockEntry<SlabBlock> BANANA_SEAWEEDS_SLAB = create("banana_seaweeds_slab", SlabBlock::new).setProperties(BANANA_SEAWEEDS_BLOCK, null).save();
	public static final BlockEntry<SlabBlock> COTTON_CANDY_SLAB = create("cotton_candy_slab", SlabBlock::new).setProperties(COTTON_CANDY_BLOCK, null).save();
	public static final BlockEntry<SlabBlock> CANDIED_CHERRY_SLAB = create("candied_cherry_slab", SlabBlock::new).setProperties(CANDIED_CHERRY_SACK, null).save();
	public static final BlockEntry<SlabBlock> CHEWING_GUM_SLAB = create("chewing_gum_slab", SlabBlock::new).setProperties(CHEWING_GUM_BLOCK, null).save();
	public static final BlockEntry<SlabBlock> MARSHMALLOW_SLAB = create("marshmallow_slab", SlabBlock::new).setProperties(MARSHMALLOW_PLANKS, null).save();
	public static final BlockEntry<SlabBlock> LIGHT_MARSHMALLOW_SLAB = create("light_marshmallow_slab", SlabBlock::new).setProperties(LIGHT_MARSHMALLOW_PLANKS, null).save();
	public static final BlockEntry<SlabBlock> DARK_MARSHMALLOW_SLAB = create("dark_marshmallow_slab", SlabBlock::new).setProperties(DARK_MARSHMALLOW_PLANKS, null).save();
	public static final BlockEntry<SlabBlock> CANDY_CANE_SLAB = create("candy_cane_slab", SlabBlock::new).setProperties(CANDY_CANE_BLOCK, null).save();
	public static final BlockEntry<SlabBlock> LICORICE_SLAB = create("licorice_slab", SlabBlock::new).setProperties(LICORICE_BLOCK, null).save();
	public static final BlockEntry<SlabBlock> LICORICE_BRICK_SLAB = create("licorice_brick_slab", SlabBlock::new).setProperties(LICORICE_BRICKS, null).save();
	public static final BlockEntry<SlabBlock> ICE_CREAM_SLAB = create("ice_cream_slab", SlabBlock::new).setProperties(ICE_CREAM, null).save();
	public static final BlockEntry<SlabBlock> MINT_ICE_CREAM_SLAB = create("mint_ice_cream_slab", SlabBlock::new).setProperties(MINT_ICE_CREAM, null).save();
	public static final BlockEntry<SlabBlock> STRAWBERRY_ICE_CREAM_SLAB = create("strawberry_ice_cream_slab", SlabBlock::new).setProperties(STRAWBERRY_ICE_CREAM, null).save();
	public static final BlockEntry<SlabBlock> BLUEBERRY_ICE_CREAM_SLAB = create("blueberry_ice_cream_slab", SlabBlock::new).setProperties(BLUEBERRY_ICE_CREAM, null).save();
	
	public static final BlockEntry<StairBlock> MINT_STAIRS = stairBlock("mint_stairs", MINT_BLOCK::defaultBlockState).setProperties(MINT_BLOCK, null).save();
	public static final BlockEntry<StairBlock> RASPBERRY_STAIRS = stairBlock("raspberry_stairs", RASPBERRY_BLOCK::defaultBlockState).setProperties(RASPBERRY_BLOCK, null).save();
	public static final BlockEntry<StairBlock> BANANA_SEAWEEDS_STAIRS = stairBlock("banana_seaweeds_stairs", BANANA_SEAWEEDS_BLOCK::defaultBlockState).setProperties(BANANA_SEAWEEDS_BLOCK, null).save();
	public static final BlockEntry<StairBlock> COTTON_CANDY_STAIRS = stairBlock("cotton_candy_stairs", COTTON_CANDY_BLOCK::defaultBlockState).setProperties(COTTON_CANDY_BLOCK, null).save();
	public static final BlockEntry<StairBlock> CANDIED_CHERRY_STAIRS = stairBlock("candied_cherry_stairs", CANDIED_CHERRY_SACK::defaultBlockState).setProperties(CANDIED_CHERRY_SACK, null).save();
	public static final BlockEntry<StairBlock> CHEWING_GUM_STAIRS = stairBlock("chewing_gum_stairs", CHEWING_GUM_BLOCK::defaultBlockState).setProperties(CHEWING_GUM_BLOCK, null).save();
	public static final BlockEntry<StairBlock> MARSHMALLOW_STAIRS = stairBlock("marshmallow_stairs", MARSHMALLOW_PLANKS::defaultBlockState).setProperties(MARSHMALLOW_PLANKS, null).save();
	public static final BlockEntry<StairBlock> LIGHT_MARSHMALLOW_STAIRS = stairBlock("light_marshmallow_stairs", LIGHT_MARSHMALLOW_PLANKS::defaultBlockState).setProperties(LIGHT_MARSHMALLOW_PLANKS, null).save();
	public static final BlockEntry<StairBlock> DARK_MARSHMALLOW_STAIRS = stairBlock("dark_marshmallow_stairs", DARK_MARSHMALLOW_PLANKS::defaultBlockState).setProperties(DARK_MARSHMALLOW_PLANKS, null).save();
	public static final BlockEntry<StairBlock> CANDY_CANE_STAIRS = stairBlock("candy_cane_stairs", CANDY_CANE_BLOCK::defaultBlockState).setProperties(CANDY_CANE_BLOCK, null).save();
	public static final BlockEntry<StairBlock> LICORICE_STAIRS = stairBlock("licorice_stairs", LICORICE_BLOCK::defaultBlockState).setProperties(LICORICE_BLOCK, null).save();
	public static final BlockEntry<StairBlock> LICORICE_BRICK_STAIRS = stairBlock("licorice_brick_stairs", LICORICE_BRICKS::defaultBlockState).setProperties(LICORICE_BRICKS, null).save();
	public static final BlockEntry<StairBlock> ICE_CREAM_STAIRS = stairBlock("ice_cream_stairs", ICE_CREAM::defaultBlockState).setProperties(ICE_CREAM, null).save();
	public static final BlockEntry<StairBlock> MINT_ICE_CREAM_STAIRS = stairBlock("mint_ice_cream_stairs", MINT_ICE_CREAM::defaultBlockState).setProperties(MINT_ICE_CREAM, null).save();
	public static final BlockEntry<StairBlock> STRAWBERRY_ICE_CREAM_STAIRS = stairBlock("strawberry_ice_cream_stairs", STRAWBERRY_ICE_CREAM::defaultBlockState).setProperties(STRAWBERRY_ICE_CREAM, null).save();
	public static final BlockEntry<StairBlock> BLUEBERRY_ICE_CREAM_STAIRS = stairBlock("blueberry_ice_cream_stairs", BLUEBERRY_ICE_CREAM::defaultBlockState).setProperties(BLUEBERRY_ICE_CREAM, null).save();
	
	public static final BlockEntry<DoorBlock> MARSHMALLOW_DOOR = create("marshmallow_door", (p) -> new DoorBlock(p, BlockSetType.OAK)).setProperties(OAK_DOOR, null).save();
	public static final BlockEntry<DoorBlock> LIGHT_MARSHMALLOW_DOOR = create("light_marshmallow_door", (p) -> new DoorBlock(p, BlockSetType.DARK_OAK)).setProperties(MARSHMALLOW_DOOR, null).save();
	public static final BlockEntry<DoorBlock> DARK_MARSHMALLOW_DOOR = create("dark_marshmallow_door", (p) -> new DoorBlock(p, BlockSetType.BAMBOO)).setProperties(MARSHMALLOW_DOOR, null).save();
	public static final BlockEntry<TrapDoorBlock> MARSHMALLOW_TRAPDOOR = create("marshmallow_trapdoor", p -> new TrapDoorBlock(p, BlockSetType.OAK)).setProperties(OAK_TRAPDOOR, null).save();
	public static final BlockEntry<TrapDoorBlock> LIGHT_MARSHMALLOW_TRAPDOOR = create("light_marshmallow_trapdoor", p -> new TrapDoorBlock(p, BlockSetType.BAMBOO)).setProperties(MARSHMALLOW_TRAPDOOR, null).save();
	public static final BlockEntry<TrapDoorBlock> DARK_MARSHMALLOW_TRAPDOOR = create("dark_marshmallow_trapdoor", p -> new TrapDoorBlock(p, BlockSetType.DARK_OAK)).setProperties(MARSHMALLOW_TRAPDOOR, null).save();
	public static final BlockEntry<FenceGateBlock> MARSHMALLOW_FENCE_GATE = create("marshmallow_fence_gate", p -> new FenceGateBlock(p, WoodType.OAK)).setProperties(OAK_FENCE_GATE, null).save();
	public static final BlockEntry<FenceGateBlock> LIGHT_MARSHMALLOW_FENCE_GATE = create("light_marshmallow_fence_gate", p -> new FenceGateBlock(p, WoodType.BAMBOO)).setProperties(MARSHMALLOW_FENCE_GATE, null).save();
	public static final BlockEntry<FenceGateBlock> DARK_MARSHMALLOW_FENCE_GATE = create("dark_marshmallow_fence_gate", p -> new FenceGateBlock(p, WoodType.DARK_OAK)).setProperties(MARSHMALLOW_FENCE_GATE, null).save();
	
	public static final BlockEntry<WebBlock> COTTON_CANDY_WEB = create("cotton_candy_web", WebBlock::new).setProperties(COBWEB, null).save();
	public static final BlockEntry<ChewingGumPuddleBlock> CHEWING_GUM_PUDDLE = create("chewing_gum_puddle", ChewingGumPuddleBlock::new).setProperties(SLIME_BLOCK, p -> p.destroyTime(2.5F).noCollission()).save();
	public static final BlockEntry<LadderBlock> MARSHMALLOW_LADDER = create("marshmallow_ladder", LadderBlock::new).setProperties(LADDER, null).save();
	
	public static final BlockEntry<JellyBlock> TRAMPOJELLY = create("trampojelly", (p) -> new JellyBlock(p, JellyType.GREEN)).setProperties(SLIME_BLOCK, p -> p.strength(5F, 2000F)).save();
	public static final BlockEntry<JellyBlock> RED_TRAMPOJELLY = create("red_trampojelly", (p) -> new JellyBlock(p, JellyType.RED)).setProperties(SLIME_BLOCK, p -> p.strength(5F, 2000F)).save();
	public static final BlockEntry<JellyBlock> SOFT_TRAMPOJELLY = create("soft_trampojelly", (p) -> new JellyBlock(p, JellyType.SOFT)).setProperties(SLIME_BLOCK, p -> p.strength(5F, 2000F)).save();
	public static final BlockEntry<JellyBlock> JELLY_SHOCK_ABSORBER = create("jelly_shock_absorber", (p) -> new JellyBlock(p, JellyType.BLUE)).setProperties(SLIME_BLOCK, p -> p.strength(5F, 2000F)).save();
	public static final BlockEntry<SensitiveJellyBlock> SENSITIVE_JELLY = create("sensitive_jelly", SensitiveJellyBlock::new).setProperties(SLIME_BLOCK, p -> p.strength(5F, 2000F)).save();
	
	public static final BlockEntry<SpikesBlock> SUGAR_SPIKES = create("sugar_spikes", SpikesBlock::new).setProperties(PEZ_BLOCK, Properties::noCollission).save();
	public static final BlockEntry<SpikesBlock> CRANBERRY_SPIKES = create("cranberry_spikes", SpikesBlock::new).setProperties(SUGAR_SPIKES, null).save();
	
	public static final BlockEntry<IceBlock> GRENADINE_ICE = create("grenadine_block", IceBlock::new).setProperties(ICE, null).save();
	public static final BlockEntry<GlassBlock> CARAMEL_GLASS = create("caramel_glass", GlassBlock::new).setProperties(GLASS, null).save();
	public static final BlockEntry<GlassBlock> ROUND_CARAMEL_GLASS = create("round_caramel_glass", GlassBlock::new).setProperties(CARAMEL_GLASS, null).save();
	public static final BlockEntry<GlassBlock> DIAMOND_CARAMEL_GLASS = create("diamond_caramel_glass", GlassBlock::new).setProperties(CARAMEL_GLASS, null).save();
	
	public static final BlockEntry<IronBarsBlock> CARAMEL_GLASS_PANE = create("caramel_glass_pane", IronBarsBlock::new).setProperties(GLASS_PANE, null).save();
	public static final BlockEntry<IronBarsBlock> ROUND_CARAMEL_GLASS_PANE = create("round_caramel_glass_pane", IronBarsBlock::new).setProperties(CARAMEL_GLASS_PANE, null).save();
	public static final BlockEntry<IronBarsBlock> DIAMOND_CARAMEL_GLASS_PANE = create("diamond_caramel_glass_pane", IronBarsBlock::new).setProperties(CARAMEL_GLASS_PANE, null).save();
	
	public static final BlockEntry<Block> JAWBREAKER_BRICKS = create("jawbreaker_bricks").setProperties(Blocks.BEDROCK, null).save();
	public static final BlockEntry<Block> JAWBREAKER_LIGHT = create("jawbreaker_light").setProperties(JAWBREAKER_BRICKS, p -> p.lightLevel(b -> 14)).save();
	
	//火把的BlockItem: cn.breadnicecat.candycraftce.item.CItems.TORCH
	public static final BlockEntry<TorchBlock> HONEYCOMB_TORCH = create("honeycomb_torch", p -> new TorchBlock(p, ParticleTypes.FLAME)).setProperties(Blocks.TORCH, null).noBlockItem().save();
	public static final BlockEntry<WallTorchBlock> WALL_HONEYCOMB_TORCH = create("wall_honeycomb_torch", p -> new WallTorchBlock(p, ParticleTypes.FLAME)).setProperties(WALL_TORCH, p -> p.dropsLike(HONEYCOMB_TORCH.get())).noBlockItem().save();
	
	public static final BlockEntry<CaramelPortalBlock> CARAMEL_PORTAL = create("caramel_portal", CaramelPortalBlock::new).setProperties(Blocks.NETHER_PORTAL, null).noBlockItem().save();
	
	//	public static final BlockEntry<LiquidBlock> CARAMEL_LIQUID = create("caramel_liquid", (p) -> new LiquidBlock(CARAMEL.get(), p)).setProperties(WATER, null).noBlockItem().save();
	
	static {
		if (isClient()) {
			CandyCraftCE.hookMinecraftSetup(CBlocks::declareRendererType);
		}
	}
	
	
	/**
	 * 由Mixin调用
	 */
	@Environment(EnvType.CLIENT)
	public static void registerBlockColors(BlockColors colors) {
		LOGGER.info("Register Block Colors");
		colors.register((state, getter, pos, tintindex) -> {
					if (pos != null) {
						return PuddingColor.getEnchantColor(pos.getX(), pos.getZ());
					} else {
						return PuddingColor.getDefaultEnchantColor();
					}
				},
				MAGICAL_LEAVES.get());
		colors.register((state, getter, pos, tintindex) -> {
					ClientLevel level = Minecraft.getInstance().level;
					if (pos != null && getter != null && level != null && level.dimension() == CDims.CANDYLAND) {
						return getter.getBlockTint(pos, BiomeColors.GRASS_COLOR_RESOLVER);
					} else return PuddingColor.getDefaultPuddingColor();
				},
				CUSTARD_PUDDING.get());
		
	}
	
	/**
	 * Minecraft Setup
	 */
	@Environment(EnvType.CLIENT)
	private static void declareRendererType() {
		LOGGER.info("Declare Renderer Type");
		accept((b) -> setRendererType(b.get(), RenderType.translucent()),
				CARAMEL_PORTAL, ALCHEMY_MIXER, TRAMPOJELLY, RED_TRAMPOJELLY, SOFT_TRAMPOJELLY, JELLY_SHOCK_ABSORBER, SENSITIVE_JELLY,
				GRENADINE_ICE
		);
		accept((b) -> setRendererType(b.get(), RenderType.cutoutMipped()),
				HONEYCOMB_TORCH, WALL_HONEYCOMB_TORCH, CHOCOLATE_SAPLING, WHITE_CHOCOLATE_SAPLING, CARAMEL_SAPLING,
				CANDIED_CHERRY_SAPLING, CHEWING_GUM_PUDDLE, COTTON_CANDY_WEB, MARSHMALLOW_LADDER,
				MARSHMALLOW_DOOR, LIGHT_MARSHMALLOW_DOOR, SUGAR_SPIKES, CRANBERRY_SPIKES,
				CARAMEL_GLASS, CARAMEL_GLASS_PANE, ROUND_CARAMEL_GLASS, ROUND_CARAMEL_GLASS_PANE, DIAMOND_CARAMEL_GLASS, DIAMOND_CARAMEL_GLASS_PANE,
				SWEET_GRASS_0, SWEET_GRASS_1, SWEET_GRASS_2, SWEET_GRASS_3, MINT, ROPE_RASPBERRY, BANANA_SEAWEED,
				FRAISE_TAGADA_FLOWER, GOLDEN_SUGAR_FLOWER, ACID_MINT_FLOWER, CUSTARD_PUDDING,
				DRAGIBUS_CROPS, LOLLIPOP_STEM, LOLLIPOP_FRUIT, MARSHMALLOW_TRAPDOOR, LIGHT_MARSHMALLOW_TRAPDOOR, DARK_MARSHMALLOW_TRAPDOOR,
				JELLY_ORE, NOUGAT_ORE, LICORICE_ORE, HONEYCOMB_ORE, PEZ_ORE, WHITE_JELLY_ORE, WHITE_NOUGAT_ORE, WHITE_LICORICE_ORE, WHITE_HONEYCOMB_ORE, WHITE_PEZ_ORE
		);
//		accept((b) -> ItemBlockRenderTypes.TYPE_BY_FLUID.put(b.get(), RenderType.translucent()),
//				CARAMEL
//		);
	}
	
	public static void init() {
	}
	
	@ExpectPlatform
	private static void setRendererType(Block block, RenderType rt) {
		impossibleCode();
	}
	
	@ExpectPlatform
	private static void setRendererType(Fluid fluid, RenderType rt) {
		impossibleCode();
	}
	
	
	private static CBlockBuilder<StairBlock> stairBlock(String name, Supplier<BlockState> base) {
		return create(name, p -> _stairBlock(base, p));
	}
	
	//Platform difference
	@ExpectPlatform
	private static StairBlock _stairBlock(Supplier<BlockState> base, Properties p) {
		return impossibleCode();
	}
}
