package cn.breadnicecat.candycraftce.datagen.forge.providers;

import cn.breadnicecat.candycraftce.CandyCraftCE;
import cn.breadnicecat.candycraftce.block.BlockEntry;
import cn.breadnicecat.candycraftce.block.blocks.*;
import cn.breadnicecat.candycraftce.utils.CLogUtils;
import com.google.common.collect.Sets;
import com.mojang.datafixers.util.Pair;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraftforge.client.model.generators.*;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

import static cn.breadnicecat.candycraftce.block.CBlocks.*;
import static cn.breadnicecat.candycraftce.item.CItems.HONEYCOMB_TORCH_ITEM;
import static cn.breadnicecat.candycraftce.utils.CommonUtils.accept;
import static cn.breadnicecat.candycraftce.utils.CommonUtils.assertTrue;
import static cn.breadnicecat.candycraftce.utils.ResourceUtils.prefix;
import static com.mojang.datafixers.util.Pair.of;

/**
 * Created in 2023/10/14 22:47
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 * 低效，但是好用——无数次想改这个类的架构，改完最后还是觉得这个设计好用。;)
 */
public class CBlockStateProvider extends BlockStateProvider {
	private static final Logger LOGGER = CLogUtils.getModLogger();
	private final ExistingFileHelper exFileHelper;
	
	public CBlockStateProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
		super(output, CandyCraftCE.MOD_ID, existingFileHelper);
		this.exFileHelper = existingFileHelper;
	}
	
	@SuppressWarnings("CodeBlock2Expr")
	@Override
	protected void registerStatesAndModels() {
		//(_)type : textureName
		mappings = new HashMap<>();//make mappings enabled
		//exist : (model) *
		{
			accept(b -> simpleBlockWithItem(b.get(), existModelFile(b.get())),
					CUSTARD_PUDDING, MAGICAL_LEAVES);
		}
		
		//cubeAll : *
		{
			accept((b) -> simpleBlockWithItem(b.get(), cubeAll(b.get())),
					SUGAR_BLOCK, CARAMEL_BLOCK, PUDDING,
//					BLACK_CHOCOLATE_STONE, BLACK_CHOCOLATE_COBBLESTONE, BLACK_CHOCOLATE_BRICKS,BLACK_CHOCOLATE_STONE_TILE,
					CHOCOLATE_STONE, CHOCOLATE_COBBLESTONE, WHITE_CHOCOLATE_STONE, WHITE_CHOCOLATE_COBBLESTONE,
					SUGAR_FACTORY, ADVANCED_SUGAR_FACTORY, MARSHMALLOW_PLANKS, LIGHT_MARSHMALLOW_PLANKS,
					DARK_MARSHMALLOW_PLANKS, CHOCOLATE_LEAVES, WHITE_CHOCOLATE_LEAVES, CARAMEL_LEAVES, CANDIED_CHERRY_LEAVES,
					JELLY_ORE, NOUGAT_ORE, LICORICE_ORE, HONEYCOMB_ORE, PEZ_ORE, LICORICE_BLOCK, LICORICE_BRICKS,
					NOUGAT_BLOCK, NOUGAT_HEAD, HONEYCOMB_BLOCK, HONEYCOMB_LAMP, PEZ_BLOCK,
					TRAMPOJELLY, RED_TRAMPOJELLY, SOFT_TRAMPOJELLY, JELLY_SHOCK_ABSORBER,
					CARAMEL_GLASS, ROUND_CARAMEL_GLASS, DIAMOND_CARAMEL_GLASS, MINT_BLOCK,
					RASPBERRY_BLOCK, BANANA_SEAWEEDS_BLOCK, COTTON_CANDY_BLOCK, CHEWING_GUM_BLOCK, GRENADINE_ICE,
					ICE_CREAM, MINT_ICE_CREAM, STRAWBERRY_ICE_CREAM, BLUEBERRY_ICE_CREAM, JAWBREAKER_BRICKS, JAWBREAKER_LIGHT,
					CARAMEL_BRICKS, CHOCOLATE_BRICKS, WHITE_CHOCOLATE_BRICKS, CHOCOLATE_STONE_TILE, WHITE_CHOCOLATE_STONE_TILE
			);
		}
		
		//cubeBottomTop : *_side *_bottom *_top
		zone(() -> {
//			mapping(modLoc("block/" + CUSTARD_PUDDING.getName() + "_bottom"), blockTexture(PUDDING.get()));
			accept(b -> {
				String name = b.getName();
				BlockModelBuilder common = models().cubeBottomTop(name,
						modLoc("block/" + name + "_side"),
						modLoc("block/" + name + "_bottom"),
						modLoc("block/" + name + "_top"));
				simpleBlockWithItem(b.get(), common);
			}, /*CUSTARD_PUDDING,*/ CANDIED_CHERRY_SACK);
		});
		
		//column : *_side *_end
		accept(b -> {
			String name = b.getName();
			simpleBlockWithItem(b.get(), models().cubeColumn(name, modLoc("block/" + name + "_side"), modLoc("block/" + name + "_end")));
		}, CANDY_CANE_BLOCK, MARSHMALLOW_CRAFTING_TABLE);
		
		//wall : *
		zone(() -> {
			mapping("block/" + CANDY_CANE_WALL.getName(), "block/" + CANDY_CANE_BLOCK.getName() + "_side");
			mapping("block/" + LICORICE_WALL.getName(), "block/" + LICORICE_BLOCK.getName());
			mapping("block/" + LICORICE_BRICK_WALL.getName(), "block/" + LICORICE_BRICKS.getName());
			accept(b -> {
						ResourceLocation texture = modLoc("block/" + b.getName());
						wallBlock(b.get(), texture);
						itemModels().wallInventory(b.getName(), texture);
					},
					CANDY_CANE_WALL, LICORICE_WALL, LICORICE_BRICK_WALL);
		});
		
		//fence : *
		zone(() -> {
			mapping("block/" + CANDY_CANE_FENCE.getName(), "block/" + CANDY_CANE_BLOCK.getName() + "_side");
			mapping(blockTexture(MARSHMALLOW_FENCE.get()), blockTexture(MARSHMALLOW_PLANKS.get()));
			mapping(blockTexture(LIGHT_MARSHMALLOW_FENCE.get()), blockTexture(LIGHT_MARSHMALLOW_PLANKS.get()));
			mapping(blockTexture(DARK_MARSHMALLOW_FENCE.get()), blockTexture(DARK_MARSHMALLOW_PLANKS.get()));
			accept(b -> {
						ResourceLocation texture = modLoc("block/" + b.getName());
						fenceBlock(b.get(), texture);
						itemModels().fenceInventory(b.getName(), texture);
					},
					CANDY_CANE_FENCE, MARSHMALLOW_FENCE, LIGHT_MARSHMALLOW_FENCE, DARK_MARSHMALLOW_FENCE);
		});
		
		//stair : *_side *_end
		zone(() -> {
			{
				String stair = CANDY_CANE_STAIRS.getName();
				String block = CANDY_CANE_BLOCK.getName();
				mapping("block/" + stair + "_side", "block/" + block + "_side");
				mapping("block/" + stair + "_end", "block/" + block + "_end");
			}
			BiConsumer<String, String> consumer = (stair, block) -> {
				mapping("block/" + stair + "_side", "block/" + block);
				mapping("block/" + stair + "_end", "block/" + block);
			};
			consumer.accept(LICORICE_STAIRS.getName(), LICORICE_BLOCK.getName());
			consumer.accept(LICORICE_BRICK_STAIRS.getName(), LICORICE_BRICKS.getName());
			consumer.accept(MARSHMALLOW_STAIRS.getName(), MARSHMALLOW_PLANKS.getName());
			consumer.accept(LIGHT_MARSHMALLOW_STAIRS.getName(), LIGHT_MARSHMALLOW_PLANKS.getName());
			consumer.accept(DARK_MARSHMALLOW_STAIRS.getName(), DARK_MARSHMALLOW_PLANKS.getName());
			consumer.accept(COTTON_CANDY_STAIRS.getName(), COTTON_CANDY_BLOCK.getName());
			consumer.accept(MINT_STAIRS.getName(), MINT_BLOCK.getName());
			consumer.accept(RASPBERRY_STAIRS.getName(), RASPBERRY_BLOCK.getName());
			consumer.accept(BANANA_SEAWEEDS_STAIRS.getName(), BANANA_SEAWEEDS_BLOCK.getName());
			consumer.accept(CANDIED_CHERRY_STAIRS.getName(), CANDIED_CHERRY_SACK.getName() + "_top");
			consumer.accept(CHEWING_GUM_STAIRS.getName(), CHEWING_GUM_BLOCK.getName());
			consumer.accept(ICE_CREAM_STAIRS.getName(), ICE_CREAM.getName());
			consumer.accept(MINT_ICE_CREAM_STAIRS.getName(), MINT_ICE_CREAM.getName());
			consumer.accept(STRAWBERRY_ICE_CREAM_STAIRS.getName(), STRAWBERRY_ICE_CREAM.getName());
			consumer.accept(BLUEBERRY_ICE_CREAM_STAIRS.getName(), BLUEBERRY_ICE_CREAM.getName());
			accept(b -> {
						ResourceLocation side = modLoc("block/" + b.getName() + "_side");
						ResourceLocation end = modLoc("block/" + b.getName() + "_end");
						stairsBlock(b.get(), side, end, end);
						itemModels().stairs(b.getName(), side, end, end);
					}, CANDY_CANE_STAIRS, LICORICE_STAIRS, LICORICE_BRICK_STAIRS,
					MARSHMALLOW_STAIRS, LIGHT_MARSHMALLOW_STAIRS, DARK_MARSHMALLOW_STAIRS,
					COTTON_CANDY_STAIRS, MINT_STAIRS, RASPBERRY_STAIRS, BANANA_SEAWEEDS_STAIRS,
					CANDIED_CHERRY_STAIRS, CHEWING_GUM_STAIRS, ICE_CREAM_STAIRS,
					MINT_ICE_CREAM_STAIRS, STRAWBERRY_ICE_CREAM_STAIRS, BLUEBERRY_ICE_CREAM_STAIRS
			);
		});
		//slab : *_side, *_end
		zone(() -> {
			{
				String slab = CANDY_CANE_SLAB.getName();
				String block = CANDY_CANE_BLOCK.getName();
				mapping("block/" + slab + "_side", "block/" + block + "_side");
				mapping("block/" + slab + "_end", "block/" + block + "_end");
			}
			BiConsumer<String, String> consumer = (slab, block) -> {
				String ori = "block/" + block;
				mapping("block/" + slab + "_side", ori);
				mapping("block/" + slab + "_end", ori);
			};
			consumer.accept(LICORICE_SLAB.getName(), LICORICE_BLOCK.getName());
			consumer.accept(LICORICE_BRICK_SLAB.getName(), LICORICE_BRICKS.getName());
			consumer.accept(MARSHMALLOW_SLAB.getName(), MARSHMALLOW_PLANKS.getName());
			consumer.accept(LIGHT_MARSHMALLOW_SLAB.getName(), LIGHT_MARSHMALLOW_PLANKS.getName());
			consumer.accept(DARK_MARSHMALLOW_SLAB.getName(), DARK_MARSHMALLOW_PLANKS.getName());
			consumer.accept(COTTON_CANDY_SLAB.getName(), COTTON_CANDY_BLOCK.getName());
			consumer.accept(MINT_SLAB.getName(), MINT_BLOCK.getName());
			consumer.accept(RASPBERRY_SLAB.getName(), RASPBERRY_BLOCK.getName());
			consumer.accept(BANANA_SEAWEEDS_SLAB.getName(), BANANA_SEAWEEDS_BLOCK.getName());
			consumer.accept(CANDIED_CHERRY_SLAB.getName(), CANDIED_CHERRY_SACK.getName() + "_top");
			consumer.accept(CHEWING_GUM_SLAB.getName(), CHEWING_GUM_BLOCK.getName());
			consumer.accept(ICE_CREAM_SLAB.getName(), ICE_CREAM.getName());
			consumer.accept(MINT_ICE_CREAM_SLAB.getName(), MINT_ICE_CREAM.getName());
			consumer.accept(STRAWBERRY_ICE_CREAM_SLAB.getName(), STRAWBERRY_ICE_CREAM.getName());
			consumer.accept(BLUEBERRY_ICE_CREAM_SLAB.getName(), BLUEBERRY_ICE_CREAM.getName());
			accept(b -> {
						String name = b.getName();
						ResourceLocation side = modLoc("block/" + name + "_side");
						ResourceLocation end = modLoc("block/" + name + "_end");
						BlockModelBuilder slab = models().slab(name, side, end, end);
						BlockModelBuilder slabTop = models().slabTop(name + "_top", side, end, end);
						BlockModelBuilder full = models().cubeColumn(name + "_full", side, end);
						SlabBlock block = b.get();
						slabBlock(block, slab, slabTop, full);
						simpleBlockItem(block, slab);
					}, CANDY_CANE_SLAB, LICORICE_SLAB, LICORICE_BRICK_SLAB, MARSHMALLOW_SLAB, LIGHT_MARSHMALLOW_SLAB,
					DARK_MARSHMALLOW_SLAB, COTTON_CANDY_SLAB, MINT_SLAB, RASPBERRY_SLAB, BANANA_SEAWEEDS_SLAB, CANDIED_CHERRY_SLAB,
					CHEWING_GUM_SLAB, ICE_CREAM_SLAB, MINT_ICE_CREAM_SLAB, STRAWBERRY_ICE_CREAM_SLAB, BLUEBERRY_ICE_CREAM_SLAB);
		});
		//trapdoor *
		zone(() -> accept(b -> {
			TrapDoorBlock block = b.get();
			ResourceLocation tex = blockTexture(block);
			trapdoorBlock(block, tex, true);
			itemModels().trapdoorBottom(b.getName(), tex);
		}, MARSHMALLOW_TRAPDOOR, LIGHT_MARSHMALLOW_TRAPDOOR, DARK_MARSHMALLOW_TRAPDOOR));
		//log *, *_top
		accept(b -> {
			RotatedPillarBlock block = b.get();
			logBlock(block);
			simpleBlockItem(block, existModelFile(block));
		}, MARSHMALLOW_LOG, LIGHT_MARSHMALLOW_LOG, DARK_MARSHMALLOW_LOG);
		//cross *
		{
			accept(b -> {
						Block block = b.get();
						ResourceLocation cross = blockTexture(block);
						String name = b.getName();
						simpleBlock(block, models().cross(name, cross));
						generatedItem(name, cross);
					}, CHOCOLATE_SAPLING, WHITE_CHOCOLATE_SAPLING, CARAMEL_SAPLING, CANDIED_CHERRY_SAPLING,
					COTTON_CANDY_WEB, SUGAR_SPIKES, CRANBERRY_SPIKES,
					SWEET_GRASS_0, SWEET_GRASS_1, SWEET_GRASS_2, SWEET_GRASS_3, MINT, ROPE_RASPBERRY, BANANA_SEAWEED,
					FRAISE_TAGADA_FLOWER, GOLDEN_SUGAR_FLOWER, ACID_MINT_FLOWER, LOLLIPOP_FRUIT
			);
		}
		
		//door *_bottom *_top
		accept(b -> {
			String name = b.getName();
			doorBlock(b.get(), modLoc("block/" + name + "_bottom"), modLoc("block/" + name + "_top"));
			itemModels().basicItem(b.getId());
		}, MARSHMALLOW_DOOR, LIGHT_MARSHMALLOW_DOOR, DARK_MARSHMALLOW_DOOR);
		
		//fence_gate *
		zone(() -> {
			mapping(blockTexture(MARSHMALLOW_FENCE_GATE.get()), blockTexture(MARSHMALLOW_PLANKS.get()));
			mapping(blockTexture(LIGHT_MARSHMALLOW_FENCE_GATE.get()), blockTexture(LIGHT_MARSHMALLOW_PLANKS.get()));
			mapping(blockTexture(DARK_MARSHMALLOW_FENCE_GATE.get()), blockTexture(DARK_MARSHMALLOW_PLANKS.get()));
			accept(b -> {
				FenceGateBlock block = b.get();
				ResourceLocation tex = blockTexture(block);
				fenceGateBlock(block, tex);
				itemModels().fenceGate(b.getName(), tex);
			}, MARSHMALLOW_FENCE_GATE, LIGHT_MARSHMALLOW_FENCE_GATE, DARK_MARSHMALLOW_FENCE_GATE);
		});
		
		//glass_pane * *_edge
		zone(() -> {
			mapping(blockTexture(CARAMEL_GLASS_PANE.get()), blockTexture(CARAMEL_GLASS.get()));
			mapping(blockTexture(ROUND_CARAMEL_GLASS_PANE.get()), blockTexture(ROUND_CARAMEL_GLASS.get()));
			mapping(blockTexture(DIAMOND_CARAMEL_GLASS_PANE.get()), blockTexture(DIAMOND_CARAMEL_GLASS.get()));
			ResourceLocation edge = modLoc("block/caramel_glass_pane_edge");
			accept(b -> {
				mapping(modLoc("block/" + b.getName() + "_edge"), edge);
			}, CARAMEL_GLASS_PANE, ROUND_CARAMEL_GLASS_PANE, DIAMOND_CARAMEL_GLASS_PANE);
			accept(b -> {
				IronBarsBlock block = b.get();
				ResourceLocation pane = blockTexture(block);
				paneBlock(block, pane, modLoc("block/" + b.getName() + "_edge"));
				generatedItem(b.getName(), pane);
			}, CARAMEL_GLASS_PANE, ROUND_CARAMEL_GLASS_PANE, DIAMOND_CARAMEL_GLASS_PANE);
		});
		
		//mixed_cube *_1 *_2
		zone(() -> {
			ResourceLocation parent = prefix("mixed_cube");
			ResourceLocation caramel = blockTexture(CARAMEL_BLOCK.get());
			mapping(blockTexture(CHOCOLATE_CARAMEL_BRICKS.get(), "_1"), caramel);
			mapping(blockTexture(CHOCOLATE_CARAMEL_BRICKS.get(), "_2"), blockTexture(CHOCOLATE_BRICKS.get()));
			mapping(blockTexture(WHITE_CHOCOLATE_CARAMEL_BRICKS.get(), "_1"), caramel);
			mapping(blockTexture(WHITE_CHOCOLATE_CARAMEL_BRICKS.get(), "_2"), blockTexture(WHITE_CHOCOLATE_BRICKS.get()));
//			mapping(blockTexture(BLACK_CHOCOLATE_CARAMEL_BRICKS.get(), "_1"), caramel);
//			mapping(blockTexture(BLACK_CHOCOLATE_CARAMEL_BRICKS.get(), "_2"), blockTexture(BLACK_CHOCOLATE_BRICKS.get()));
			accept(b -> {
				Block block = b.get();
				ResourceLocation location = blockTexture(block);
				ResourceLocation _1 = postfix(location, "_1");
				ResourceLocation _2 = postfix(location, "_2");
				simpleBlockWithItem(block, models().withExistingParent(b.getName(), parent)
						.texture("1", _1)
						.texture("2", _2)
				);
			}, CHOCOLATE_CARAMEL_BRICKS, WHITE_CHOCOLATE_CARAMEL_BRICKS/*, BLACK_CHOCOLATE_CARAMEL_BRICKS*/);
		});
		
		mappings = Map.of();//making mapping disabled
		/*================CUSTOM PART================*/
//		{
//			BlockEntry<CustardPuddingBlock> be = CUSTARD_PUDDING;
//			CustardPuddingBlock b = be.get();
//			//"particle": "block/dirt",
//			//"bottom": "block/dirt",
//			//"top": "block/grass_block_top",
//			//"side": "block/grass_block_side",
//			//"overlay": "block/grass_block_side_overlay"
//			ResourceLocation bottom = blockTexture(PUDDING.get());
//			BlockModelBuilder model = models().withExistingParent(be.getName(), "block/grass_block")
//					.texture("particle", bottom)
//					.texture("top", modLoc("block/" + be.getName() + "_top"))
//					.texture("side", modLoc("block/" + be.getName() + "_side"))
//					.texture("overlay", modLoc("block/" + be.getName() + "_overlay_side"));
//			getVariantBuilder(b).partialState().addModels(
//					new ConfiguredModel(model, 0, 0, false),
//					new ConfiguredModel(model, 0, 90, false),
//					new ConfiguredModel(model, 0, 180, false),
//					new ConfiguredModel(model, 0, 270, false)
//			);
//			simpleBlockItem(b, model);
//		}
		//Dragibus软糖
		{
			CandyCropBlock block = DRAGIBUS_CROPS.get();
			String name = DRAGIBUS_CROPS.getName();
			ConfiguredModel[] m0 = ConfiguredModel.builder().modelFile(models().crop(name + "_0", modLoc("block/" + name + "_0"))).build();
			ConfiguredModel[] m1 = ConfiguredModel.builder().modelFile(models().crop(name + "_1", modLoc("block/" + name + "_1"))).build();
			ConfiguredModel[] m2 = ConfiguredModel.builder().modelFile(models().crop(name + "_2", modLoc("block/" + name + "_2"))).build();
			ConfiguredModel[] m3 = ConfiguredModel.builder().modelFile(models().crop(name + "_3", modLoc("block/" + name + "_3"))).build();
			getVariantBuilder(block).forAllStates((bs) -> switch (block.getStage(bs)) {
						case 0 -> m0;
						case 1 -> m1;
						case 2 -> m2;
						case 3 -> m3;
						default -> throw new IllegalArgumentException();
					}
			);
		}
		//棒棒糖
		{
			LollipopStemBlock block = LOLLIPOP_STEM.get();
			String name = LOLLIPOP_STEM.getName();
			ConfiguredModel[] m0 = ConfiguredModel.builder().modelFile(models().cross(name + "_0", modLoc("block/" + name + "_0"))).build();
			ConfiguredModel[] m1 = ConfiguredModel.builder().modelFile(models().cross(name + "_1", modLoc("block/" + name + "_1"))).build();
			ConfiguredModel[] m2 = ConfiguredModel.builder().modelFile(models().cross(name + "_2", modLoc("block/" + name + "_2"))).build();
			getVariantBuilder(block).forAllStates((bs) -> switch (block.getStage(bs)) {
						case 0 -> m0;
						case 1 -> m1;
						case 2 -> m2;
						default -> throw new IllegalArgumentException();
					}
			);
		}
		//敏感果冻
		{
			String name = SENSITIVE_JELLY.getName();
			SensitiveJellyBlock block = SENSITIVE_JELLY.get();
			ResourceLocation texture = blockTexture(block);
			BlockModelBuilder simple = models().cubeAll(name, texture);
			BlockModelBuilder active = models().cubeAll(name + "_powered", postfix(texture, "_powered"));
			
			getVariantBuilder(block)
					.forAllStates(s -> ConfiguredModel.builder()
							.modelFile(s.getValue(SensitiveJellyBlock.POWERED) ? active : simple)
							.build());
			simpleBlockItem(block, simple);
		}
		//梯子
		{
			String name = MARSHMALLOW_LADDER.getName();
			ResourceLocation tex = modLoc("block/" + name);
			BlockModelBuilder m = models().withExistingParent(name, "block/ladder")
					.texture("texture", tex)
					.texture("particle", tex);
			horizontalBlock(MARSHMALLOW_LADDER.get(), m);
			generatedItem(name, tex);
		}
		//口香糖片
		{
			ChewingGumPuddleBlock block = CHEWING_GUM_PUDDLE.get();
			simpleBlock(block, existModelFile(block));
			generatedItem(CHEWING_GUM_PUDDLE.getName(), blockTexture(block));
		}
		//火把
		{
			String torchName = HONEYCOMB_TORCH.getName();
			String wallName = WALL_HONEYCOMB_TORCH.getName();
			TorchBlock torchBlock = HONEYCOMB_TORCH.get();
			WallTorchBlock wallBlock = WALL_HONEYCOMB_TORCH.get();
			ResourceLocation tex = modLoc("block/" + torchName);
			
			BlockModelBuilder torchModel = models().withExistingParent(torchName, "block/template_torch")
					.texture("torch", tex);
			BlockModelBuilder wallModel = models().withExistingParent(wallName, "block/template_torch_wall")
					.texture("torch", tex);
			simpleBlock(torchBlock, torchModel);
			horizontalBlock(wallBlock, wallModel, 90);
			generatedItem(HONEYCOMB_TORCH_ITEM.getName(), tex);
		}
		//炼金搅拌器
		{
			String name = ALCHEMY_MIXER.getName();
			AlchemyMixerBlock block = ALCHEMY_MIXER.get();
			ResourceLocation baseLoc = modLoc("block/" + name);
			ModelFile base = existModelFile(baseLoc);
			ModelFile liquid = existModelFile(baseLoc.withSuffix("_liquid"));
			
			MultiPartBlockStateBuilder builder = getMultipartBuilder(block)
					.part().modelFile(base)
					.addModel()
					.end()
					
					.part().modelFile(liquid)
					.addModel()
					.condition(AlchemyMixerBlock.FULL, true)
					.end();
			
			simpleBlockItem(block, base);
		}
		//盐甘草糖熔炉
		{
			String name = LICORICE_FURNACE.getName();
			ResourceLocation front_on = modLoc("block/" + name + "_front_on");
			ResourceLocation front_off = modLoc("block/" + name + "_front_off");
			ResourceLocation block = blockTexture(LICORICE_BLOCK.get());
			BlockModelBuilder off = models().orientableWithBottom(name, block, front_off, block, block);
			BlockModelBuilder on = models().orientableWithBottom(name + "_on", block, front_on, block, block);
			horizontalBlock(LICORICE_FURNACE.get(), (s) -> s.getValue(LicoriceFurnaceBlock.LIT) ? on : off);
			simpleBlockItem(LICORICE_FURNACE.get(), off);
		}
		//巧克力熔炉
		{
			for (Pair<BlockEntry<ChocolateFurnaceBlock>, BlockEntry<Block>> pair : List.of(
					of(CHOCOLATE_FURNACE, CHOCOLATE_STONE),
					of(WHITE_CHOCOLATE_FURNACE, WHITE_CHOCOLATE_STONE)
//					of(BLACK_CHOCOLATE_FURNACE, BLACK_CHOCOLATE_STONE)
			)) {
				BlockEntry<ChocolateFurnaceBlock> block = pair.getFirst();
				BlockEntry<Block> bottomRef = pair.getSecond();
				
				String name = block.getName();
				ResourceLocation side = modLoc("block/" + name + "_side");
				ResourceLocation front_on = modLoc("block/" + name + "_front_on");
				ResourceLocation front_off = modLoc("block/" + name + "_front_off");
				ResourceLocation top = modLoc("block/" + name + "_top");
				ResourceLocation bottom = blockTexture(bottomRef.get());
				BlockModelBuilder off = models().orientableWithBottom(name, side, front_off, bottom, top);
				BlockModelBuilder on = models().orientableWithBottom(name + "_on", side, front_on, bottom, top);
				horizontalBlock(block.get(), (s) -> s.getValue(ChocolateFurnaceBlock.LIT) ? on : off);
				simpleBlockItem(block.get(), off);
			}
			
		}
		//布丁耕地
		{
			Block block = PUDDING_FARMLAND.get();
			String name = PUDDING_FARMLAND.getName();
			String base = PUDDING.getName();
			BlockModelBuilder land = models().withExistingParent(name, "block/template_farmland")
					.texture("dirt", modLoc("block/" + base))
					.texture("top", modLoc("block/" + name + "_top"));
			BlockModelBuilder land_moist = models().withExistingParent(name + "_moist", "block/template_farmland")
					.texture("dirt", modLoc("block/" + base))
					.texture("top", modLoc("block/" + name + "_top_moist"));
			getVariantBuilder(block).forAllStates((s) ->
					ConfiguredModel.builder().modelFile(s.getValue(PuddingFarmBlock.MOISTURE) >= PuddingFarmBlock.MAX_MOISTURE ? land_moist : land).build());
			simpleBlockItem(block, land);
		}
		//传送门
		{
			String name = CARAMEL_PORTAL.getName();
			CaramelPortalBlock block = CARAMEL_PORTAL.get();
			ResourceLocation tex = blockTexture(block);
			String baseName = "block/" + name;
			BlockModelBuilder x = models().withExistingParent(baseName + "_x", "block/nether_portal_ew")
					.texture("portal", tex)
					.texture("particle", tex);
			ModelFile.ExistingModelFile y = existModelFile(modLoc("block/caramel_portal_y"));
			getMultipartBuilder(block).part().modelFile(x).addModel().condition(CaramelPortalBlock.X, true).end()
					.part().modelFile(x).rotationY(90).addModel().condition(CaramelPortalBlock.Z, true).end()
					.part().modelFile(y).addModel().condition(CaramelPortalBlock.Y, true).end();
		}
	}
	
	private ItemModelBuilder generatedItem(String name, ResourceLocation... tex) {
		ItemModelBuilder builder = itemModels().withExistingParent(name, "item/generated");
		for (int i = 0; i < tex.length; i++) {
			builder.texture("layer" + i, tex[i]);
		}
		return builder;
	}
	
	public Map<ResourceLocation, ResourceLocation> mappings = Map.of();
	
	
	/**
	 * 局部映射
	 */
	public void zone(Runnable m) {
		StackTraceElement element = Thread.currentThread().getStackTrace()[2];
		LOGGER.info("{}zone({}:{}){}", "=".repeat(32), element.getFileName(), element.getLineNumber(), "=".repeat(32));
		Map<ResourceLocation, ResourceLocation> global = mappings;
		if (!(global instanceof HashMap<ResourceLocation, ResourceLocation>)) {
			throw new IllegalStateException("Not a valid mapping map");
		}
		var used = new HashSet<>();
		mappings = new HashMap<>(global) {
			@Override
			public ResourceLocation get(Object key) {
				ResourceLocation v = super.get(key);
				if (v != null) {
					LOGGER.info("mapping: {}\t->\t{}", key, v);
					used.add(key);
				}
				return v;
			}
			
			@Override
			public ResourceLocation getOrDefault(Object key, ResourceLocation defaultValue) {
				ResourceLocation v = get(key);
				return v != null ? v : defaultValue;
			}
		};
		m.run();
		if (used.size() != mappings.size()) {
			Sets.SetView<Object> view = Sets.difference(new HashSet<>(mappings.keySet()), used);
			throw new IllegalStateException("Unused mappings in zone:\n" + view);
		}
		mappings = global;
	}
	
	@Override
	public ResourceLocation modLoc(String name) {
		ResourceLocation loc = super.modLoc(name);
		return mappings.getOrDefault(loc, loc);
	}
	
	@Override
	public ResourceLocation mcLoc(String name) {
		ResourceLocation loc = super.mcLoc(name);
		return mappings.getOrDefault(loc, loc);
	}
	
	@Override
	public ResourceLocation blockTexture(Block block) {
		ResourceLocation loc = super.blockTexture(block);
		return mappings.getOrDefault(loc, loc);
	}
	
	public ResourceLocation blockTexture(Block block, String postfix) {
		return postfix(blockTexture(block), postfix);
	}
	
	public ResourceLocation postfix(ResourceLocation ori, String app) {
		ResourceLocation postfix = ori.withSuffix(app);
		return mappings.getOrDefault(postfix, postfix);
	}
	
	public ModelFile.ExistingModelFile existModelFile(Block block) {
		return existModelFile(ForgeRegistries.BLOCKS.getKey(block).withPrefix("block/"));
	}
	
	public ModelFile.ExistingModelFile existModelFile(ResourceLocation location) {
		return new ModelFile.ExistingModelFile(new ResourceLocation(location.getNamespace(), location.getPath()), exFileHelper);
	}
	
	/**
	 * 贴图位置映射
	 * ori->dest
	 *
	 * @see #modLoc(String)
	 */
	private void mapping(ResourceLocation ori, ResourceLocation dest) {
		assertTrue(mappings.put(ori, dest) == null, () -> "Duplicate mapping: " + ori + " -> " + dest);
	}
	
	private void mapping(String ori, String dest) {
		mapping(super.modLoc(ori), super.modLoc(dest));
	}
}
