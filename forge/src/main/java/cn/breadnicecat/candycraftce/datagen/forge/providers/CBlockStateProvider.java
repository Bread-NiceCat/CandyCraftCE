package cn.breadnicecat.candycraftce.datagen.forge.providers;

import cn.breadnicecat.candycraftce.CandyCraftCE;
import cn.breadnicecat.candycraftce.block.blocks.*;
import cn.breadnicecat.candycraftce.utils.ResourceUtils;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraftforge.client.model.generators.*;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.Map;

import static cn.breadnicecat.candycraftce.block.CBlocks.*;
import static cn.breadnicecat.candycraftce.item.CItems.HONEYCOMB_TORCH_ITEM;
import static cn.breadnicecat.candycraftce.utils.CommonUtils.accept;

/**
 * Created in 2023/10/14 22:47
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 */
public class CBlockStateProvider extends BlockStateProvider {
	private final ExistingFileHelper exFileHelper;

	public CBlockStateProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
		super(output, CandyCraftCE.MOD_ID, existingFileHelper);
		this.exFileHelper = existingFileHelper;
	}

	@Override
	protected void registerStatesAndModels() {
		//(_)type : textureName
		mappings = new HashMap<>();//makes mappings enabled
		//cubeAll : *
		accept((b) -> simpleBlockWithItem(b.get(), cubeAll(b.get())),
				SUGAR_BLOCK, CARAMEL_BLOCK, CHOCOLATE_STONE, CHOCOLATE_COBBLESTONE, PUDDING,
				SUGAR_FACTORY, ADVANCED_SUGAR_FACTORY, LICORICE_BLOCK, MARSHMALLOW_PLANKS, LIGHT_MARSHMALLOW_PLANKS,
				DARK_MARSHMALLOW_PLANKS, CHOCOLATE_LEAVES, WHITE_CHOCOLATE_LEAVES, CARAMEL_LEAVES, CANDIED_CHERRY_LEAVES,
				MAGIC_LEAVES, JELLY_ORE, NOUGAT_ORE, LICORICE_ORE, HONEYCOMB_ORE, PEZ_ORE
		);
		//column : *_side *_end
		accept(b -> {
			String name = b.getName();
			simpleBlockWithItem(b.get(), models().cubeColumn(name, modLoc("block/" + name + "_side"), modLoc("block/" + name + "_end")));
		}, CANDY_CANE_BLOCK, MARSHMALLOW_CRAFTING_TABLE);
		//wall : *
		zone(() -> {
			mapping("block/" + CANDY_CANE_WALL.getName(), "block/" + CANDY_CANE_BLOCK.getName() + "_side");
			accept(b -> {
						ResourceLocation texture = modLoc("block/" + b.getName());
						wallBlock(b.get(), texture);
						itemModels().wallInventory(b.getName(), texture);
					},
					CANDY_CANE_WALL);
		});
		//fence : *
		zone(() -> {
			mapping("block/" + CANDY_CANE_FENCE.getName(), "block/" + CANDY_CANE_BLOCK.getName() + "_side");
			accept(b -> {
						ResourceLocation texture = modLoc("block/" + b.getName());
						fenceBlock(b.get(), texture);
						itemModels().fenceInventory(b.getName(), texture);
					},
					CANDY_CANE_FENCE);
		});
		//stair : *_side *_end
		zone(() -> {
			{
				String stair = CANDY_CANE_STAIRS.getName();
				String block = CANDY_CANE_BLOCK.getName();
				mapping("block/" + stair + "_side", "block/" + block + "_side");
				mapping("block/" + stair + "_end", "block/" + block + "_end");
			}
			accept(b -> {
				ResourceLocation side = modLoc("block/" + b.getName() + "_side");
				ResourceLocation end = modLoc("block/" + b.getName() + "_end");
				stairsBlock(b.get(), side, end, end);
				itemModels().stairs(b.getName(), side, end, end);
			}, CANDY_CANE_STAIRS);
		});
		//slab : *_side, *_end
		zone(() -> {
			{
				String slab = CANDY_CANE_SLAB.getName();
				String block = CANDY_CANE_BLOCK.getName();
				mapping("block/" + slab + "_side", "block/" + block + "_side");
				mapping("block/" + slab + "_end", "block/" + block + "_end");
			}
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
			}, CANDY_CANE_SLAB);
		});
		//log *, *_top
		accept(b -> {
			RotatedPillarBlock block = b.get();
			logBlock(block);
			simpleBlockItem(block, existModelFile(block));
		}, MARSHMALLOW_LOG, LIGHT_MARSHMALLOW_LOG, DARK_MARSHMALLOW_LOG);
		accept(b -> {
			SaplingBlock block = b.get();
			ResourceLocation cross = blockTexture(block);
			String name = b.getName();
			simpleBlock(block, models().cross(name, cross));
			itemModels().withExistingParent(name, "item/generated").texture("layer0", cross);
		}, CHOCOLATE_SAPLING, WHITE_CHOCOLATE_SAPLING, CARAMEL_SAPLING, CANDIED_CHERRY_SAPLING);
		//================================//
		mappings = Map.of();//makes mapping disabled
		{
			String torchName = HONEYCOMB_TORCH.getName();
			String wallName = WALL_HONEYCOMB_TORCH.getName();
			TorchBlock torchBlock = HONEYCOMB_TORCH.get();
			Item torchItem = torchBlock.asItem();
			WallTorchBlock wallBlock = WALL_HONEYCOMB_TORCH.get();

			ResourceLocation tex = modLoc("block/" + torchName);

			BlockModelBuilder torchModel = models().withExistingParent(torchName, "block/template_torch")
					.texture("torch", tex);
			BlockModelBuilder wallModel = models().withExistingParent(wallName, "block/template_torch_wall")
					.texture("torch", tex);
			simpleBlock(torchBlock, torchModel);
			horizontalBlock(wallBlock, wallModel, 90);
			itemModels().withExistingParent(HONEYCOMB_TORCH_ITEM.getName(), "item/generated").texture("layer0", tex);
		}
		//炼金搅拌器
		{
			String name = ALCHEMY_MIXER.getName();
			AlchemyMixerBlock block = ALCHEMY_MIXER.get();
			ModelFile base = existModelFile(modLoc("block/" + name));

			MultiPartBlockStateBuilder builder = getMultipartBuilder(block)
					.part().modelFile(base)
					.addModel()
					.end()

					.part().modelFile(existModelFile(modLoc("block/" + name + "_rope")))
					.addModel().condition(AlchemyMixerBlock.HANGING, true)
					.end();
			for (int i = 1; i < 5; i++) {
				builder.part().modelFile(existModelFile(modLoc("block/" + name + "_content_" + i)))
						.addModel()
						.condition(AlchemyMixerBlock.LEVEL, i)
						.end();
			}
			simpleBlockItem(block, base);
		}
		//盐甘草糖熔炉
		{
			String name = LICORICE_FURNACE.getName();
			ResourceLocation side = modLoc("block/" + name + "_side");
			ResourceLocation front_on = modLoc("block/" + name + "_front_on");
			ResourceLocation front_off = modLoc("block/" + name + "_front_off");
			ResourceLocation top = modLoc("block/" + name + "_top");
			ResourceLocation bottom = blockTexture(LICORICE_BLOCK.get());
			BlockModelBuilder off = models().orientableWithBottom(name, side, front_off, bottom, top);
			BlockModelBuilder on = models().orientableWithBottom(name + "_on", side, front_on, bottom, top);
			horizontalBlock(LICORICE_FURNACE.get(), (s) -> s.getValue(LicoriceFurnaceBlock.LIT) ? on : off);
			simpleBlockItem(LICORICE_FURNACE.get(), off);
		}
		{
			String name = CHOCOLATE_FURNACE.getName();
			ResourceLocation side = modLoc("block/" + name + "_side");
			ResourceLocation front_on = modLoc("block/" + name + "_front_on");
			ResourceLocation front_off = modLoc("block/" + name + "_front_off");
			ResourceLocation top = modLoc("block/" + name + "_top");
			ResourceLocation bottom = blockTexture(CHOCOLATE_STONE.get());
			BlockModelBuilder off = models().orientableWithBottom(name, side, front_off, bottom, top);
			BlockModelBuilder on = models().orientableWithBottom(name + "_on", side, front_on, bottom, top);
			horizontalBlock(CHOCOLATE_FURNACE.get(), (s) -> s.getValue(ChocolateFurnaceBlock.LIT) ? on : off);
			simpleBlockItem(CHOCOLATE_FURNACE.get(), off);
		}
		//奶皮布丁
		{
			String name = CUSTARD_PUDDING.getName();
			BlockModelBuilder common = models().cubeBottomTop(name,
					modLoc("block/" + name + "_side"),
					blockTexture(PUDDING.get()),
					modLoc("block/" + name + "_top"));
			Block block = CUSTARD_PUDDING.get();
			simpleBlockWithItem(block, common);
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
		}
		//传送门
		{
			String name = CARAMEL_PORTAL.getName();
			CaramelPortalBlock block = CARAMEL_PORTAL.get();
			ResourceLocation tex = blockTexture(block);
			BlockModelBuilder model = models().withExistingParent("block/" + name, "block/nether_portal_ew")
					.texture("portal", tex)
					.texture("particle", tex);
			getVariantBuilder(block).forAllStates(
					state -> ConfiguredModel.builder()
							.modelFile(model)
							.rotationY(state.getValue(NetherPortalBlock.AXIS) == Direction.Axis.Z ? 0 : 90)
							.build()
			);
		}
	}

	public Map<ResourceLocation, ResourceLocation> mappings = Map.of();


	/**
	 * 局部映射
	 */
	public void zone(Runnable m) {
		Map<ResourceLocation, ResourceLocation> global = mappings;
		if (!(global instanceof HashMap<ResourceLocation, ResourceLocation>)) {
			throw new IllegalStateException("Not a valid mapping map");
		}
		mappings = new HashMap<>();
		m.run();
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

	public ModelFile.ExistingModelFile existModelFile(Block block) {
		return existModelFile(ResourceUtils.pathPrefix(ForgeRegistries.BLOCKS.getKey(block), "block/"));
	}

	public ModelFile.ExistingModelFile existModelFile(ResourceLocation location) {
		return new ModelFile.ExistingModelFile(new ResourceLocation(location.getNamespace(), location.getPath()), exFileHelper);
	}

	/**
	 * 贴图位置映射
	 *
	 * @see #modLoc(String)
	 */
	private void mapping(ResourceLocation from, ResourceLocation to) {
		mappings.put(from, to);
	}

	private void mapping(String from, String to) {
		mapping(super.modLoc(from), super.modLoc(to));
	}
}
