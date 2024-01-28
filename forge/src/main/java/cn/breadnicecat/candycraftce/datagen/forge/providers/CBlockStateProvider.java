package cn.breadnicecat.candycraftce.datagen.forge.providers;

import cn.breadnicecat.candycraftce.CandyCraftCE;
import cn.breadnicecat.candycraftce.registration.block.blocks.CaramelPortal;
import cn.breadnicecat.candycraftce.registration.block.blocks.PuddingFarm;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.NetherPortalBlock;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.common.data.ExistingFileHelper;

import static cn.breadnicecat.candycraftce.registration.block.CBlocks.*;
import static cn.breadnicecat.candycraftce.utils.CommonUtils.accept;

/**
 * Created in 2023/10/14 22:47
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 * <p>
 */
public class CBlockStateProvider extends BlockStateProvider {
	public CBlockStateProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
		super(output, CandyCraftCE.MOD_ID, existingFileHelper);
	}

	@Override
	protected void registerStatesAndModels() {
		//cubeAll
		accept((b) -> simpleBlockWithItem(b.getBlock(), cubeAll(b.getBlock())),
				SUGAR_BLOCK, CARAMEL_BLOCK, CHOCOLATE_STONE, CHOCOLATE_COBBLESTONE, PUDDING
		);
		//奶皮布丁
		{
			String name = CUSTARD_PUDDING.getID().getPath();
			BlockModelBuilder common = models().cubeBottomTop(name,
					modLoc("block/" + name + "_side"),
					modLoc("block/" + PUDDING.getID().getPath()),
					modLoc("block/" + name + "_top"));
			Block block = CUSTARD_PUDDING.getBlock();
			simpleBlockWithItem(block, common);
		}
		//布丁耕地
		{
			Block block = PUDDING_FARMLAND.getBlock();
			String name = PUDDING_FARMLAND.getID().getPath();
			String base = PUDDING.getID().getPath();
			BlockModelBuilder land = models().withExistingParent(name, "block/template_farmland")
					.texture("dirt", modLoc("block/" + base))
					.texture("top", modLoc("block/" + name + "_top"));
			BlockModelBuilder land_moist = models().withExistingParent(name + "_moist", "block/template_farmland")
					.texture("dirt", modLoc("block/" + base))
					.texture("top", modLoc("block/" + name + "_top_moist"));
			getVariantBuilder(block).forAllStates((s) ->
					ConfiguredModel.builder().modelFile(s.getValue(PuddingFarm.MOISTURE) >= PuddingFarm.MAX_MOISTURE ? land_moist : land).build());
		}
		//传送门
		{
			String name = CARAMEL_PORTAL.getID().getPath();
			CaramelPortal block = CARAMEL_PORTAL.getBlock();
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
}
