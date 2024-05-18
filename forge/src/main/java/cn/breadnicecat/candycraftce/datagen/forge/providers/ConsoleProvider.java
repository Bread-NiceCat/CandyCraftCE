package cn.breadnicecat.candycraftce.datagen.forge.providers;

import com.google.common.hash.HashCode;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.serialization.JsonOps;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.concurrent.CompletableFuture;

import static cn.breadnicecat.candycraftce.block.CBlocks.*;
import static net.minecraft.world.level.levelgen.SurfaceRules.*;
import static net.minecraft.world.level.levelgen.VerticalAnchor.absolute;

/**
 * Created in 2024/5/1 下午4:35
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 * <p>
 */
public class ConsoleProvider implements DataProvider {
	private final PackOutput pack;
	private final ExistingFileHelper efhelper;
	private final Path path;
	private final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

	public ConsoleProvider(PackOutput pack, ExistingFileHelper efhelper) {
		this.pack = pack;
		this.efhelper = efhelper;
		path = pack.getOutputFolder(PackOutput.Target.REPORTS);
	}

	@Override
	public @NotNull CompletableFuture<?> run(@NotNull CachedOutput output) {
		return CompletableFuture.runAsync(() -> {
			try {
				generateSurfaceRules(output);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		});
	}


	private void generateSurfaceRules(CachedOutput output) throws IOException {
		LinkedList<RuleSource> layers = new LinkedList<>();
		//方块
		RuleSource bedrock = state(JAWBREAKER_BRICKS.defaultBlockState());
		RuleSource pudding = state(PUDDING.defaultBlockState());
		RuleSource custardPudding = state(CUSTARD_PUDDING.defaultBlockState());
		RuleSource deepslate = state(BLACK_CHOCOLATE_STONE.defaultBlockState());
		//基岩层
		{
			RuleSource bedrockLayer = ifTrue(verticalGradient(
					"bedrock_floor",
					VerticalAnchor.bottom(),
					VerticalAnchor.aboveBottom(5)
			), bedrock);

			layers.add(bedrockLayer);
		}
		//生成表面土壤
		{
			RuleSource faceSoil = sequence(
					ifTrue(waterBlockCheck(-1, 0),
							custardPudding),
					pudding
			);
			RuleSource soilType = sequence(
					ifTrue(ON_FLOOR, faceSoil),
					ifTrue(UNDER_FLOOR, pudding)
			);
			RuleSource surfaceSoilLayer = ifTrue(abovePreliminarySurface(), soilType);

			layers.add(surfaceSoilLayer);
		}
		//生成深板岩层
		{
			RuleSource deepslateLayer =
					ifTrue(verticalGradient("deepslate",
							absolute(0),
							absolute(8)
					), deepslate);
			layers.add(deepslateLayer);
		}


		RuleSource result = sequence(layers.toArray(RuleSource[]::new));
		String generated = GSON.toJson(RuleSource.CODEC.encodeStart(JsonOps.INSTANCE, result).getOrThrow(false, LOGGER::error));
		byte[] bytes = generated.getBytes(StandardCharsets.UTF_8);
		output.writeIfNeeded(path.resolve("surface_rules.json"), bytes, HashCode.fromBytes(bytes));
	}


	@Override
	public @NotNull String getName() {
		return "Console Provider";
	}
}
