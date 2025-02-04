package cn.breadnicecat.candycraftce.datagen.neoforge.providers;

import com.google.common.hash.HashCode;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.serialization.JsonOps;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.concurrent.CompletableFuture;

import static cn.breadnicecat.candycraftce.CandyCraftCE.MOD_ID;
import static cn.breadnicecat.candycraftce.block.CBlocks.*;
import static cn.breadnicecat.candycraftce.level.CBiomes.*;
import static net.minecraft.world.level.levelgen.SurfaceRules.*;
import static net.minecraft.world.level.levelgen.VerticalAnchor.absolute;

/**
 * Created in 2024/5/1 下午4:35
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 * <p>
 */
public class CNoisingSettingsProvider implements DataProvider {
	private final Gson GSON = new GsonBuilder().create();
	private final Path output;
	
	public CNoisingSettingsProvider(PackOutput pack) {
		this.output = pack.getOutputFolder(PackOutput.Target.DATA_PACK).resolve(MOD_ID + "/worldgen/noise_settings");
	}
	
	@Override
	public @NotNull CompletableFuture<?> run(@NotNull CachedOutput output) {
		return CompletableFuture.runAsync(() -> {
					try {
						RuleSource rules = candylandSurfaceRules();
						String rulesStr = GSON.toJson(RuleSource.CODEC.encodeStart(JsonOps.INSTANCE, rules).getOrThrow());
						String out = MODEL.replace("${surface_rule_generated_data}", rulesStr);
						byte[] data = out.getBytes(StandardCharsets.UTF_8);
						output.writeIfNeeded(this.output.resolve("candyland_settings.json"), data, HashCode.fromBytes(data));
					} catch (IOException ioe) {
						throw new RuntimeException(ioe);
					}
				}
		);
	}
	
	private RuleSource candylandSurfaceRules() {
		
		LinkedList<RuleSource> layers = new LinkedList<>();
		//方块
		RuleSource bedrock = state(JAWBREAKER_BRICKS.defaultBlockState());
		RuleSource pudding = state(PUDDING.defaultBlockState());
		RuleSource custardPudding = state(CUSTARD_PUDDING.defaultBlockState());
		RuleSource iceCream = state(ICE_CREAM.defaultBlockState());
		RuleSource white = state(WHITE_CHOCOLATE_STONE.defaultBlockState());
		RuleSource black = state(CHOCOLATE_STONE.defaultBlockState());
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
			RuleSource soilType = ifTrue(abovePreliminarySurface(), sequence(
					ifTrue(ON_FLOOR, sequence(
							ifTrue(isBiome(ICE_CREAM_PLAINS),
									iceCream),
							ifTrue(waterBlockCheck(0, 0),
									custardPudding),
							pudding
					)),
					ifTrue(UNDER_FLOOR,
							pudding)
			));
			layers.add(soilType);
		}
		{
			RuleSource middleLayer = sequence(
					ifTrue(isBiome(ICE_CREAM_PLAINS, ICE_CREAM_FOREST),
							white
					),
					ifTrue(isBiome(CHOCOLATE_FOREST),
							black
					)
			);
			layers.add(middleLayer);
		}
		//生成深板岩层
		{
			RuleSource deepslateLayer =
					ifTrue(verticalGradient("deepslate",
							absolute(0),
							absolute(8)
					), white);
			layers.add(deepslateLayer);
		}
		
		return sequence(layers.toArray(RuleSource[]::new));
	}
	
	
	@Override
	public @NotNull String getName() {
		return "Candyland NoisingSetting Provider";
	}
	
	private static final String MODEL = """
			{
				"sea_level":63,"disable_mob_generation":false,"aquifers_enabled":true,"ore_veins_enabled":true,"legacy_random_source":false,"default_block":{"Name":"candycraftce:chocolate_stone"},"default_fluid":{"Name":"minecraft:water","Properties":{"level":"0"}},"noise":{"min_y":-64,"height":384,"size_horizontal":1,"size_vertical":2},"spawn_target":[{"temperature":[-1,1],"humidity":[-1,1],"continentalness":[-0.11,1],"erosion":[-1,1],"weirdness":[-1,-0.16],"depth":0,"offset":0},{"temperature":[-1,1],"humidity":[-1,1],"continentalness":[-0.11,1],"erosion":[-1,1],"weirdness":[0.16,1],"depth":0,"offset":0}],"noise_router":{"barrier":{"type":"minecraft:noise","noise":"minecraft:aquifer_barrier","xz_scale":1.0,"y_scale":0.5},"continents":"minecraft:overworld/continents","depth":"minecraft:overworld/depth","erosion":"minecraft:overworld/erosion","final_density":{"type":"minecraft:min","argument1":{"type":"minecraft:squeeze","argument":{"type":"minecraft:mul","argument1":0.64,"argument2":{"type":"minecraft:interpolated","argument":{"type":"minecraft:blend_density","argument":{"type":"minecraft:add","argument1":0.1171875,"argument2":{"type":"minecraft:mul","argument1":{"type":"minecraft:y_clamped_gradient","from_value":0.0,"from_y":-64,"to_value":1.0,"to_y":-40},"argument2":{"type":"minecraft:add","argument1":-0.1171875,"argument2":{"type":"minecraft:add","argument1":-0.078125,"argument2":{"type":"minecraft:mul","argument1":{"type":"minecraft:y_clamped_gradient","from_value":1.0,"from_y":240,"to_value":0.0,"to_y":256},"argument2":{"type":"minecraft:add","argument1":0.078125,"argument2":{"type":"minecraft:range_choice","input":"minecraft:overworld/sloped_cheese","max_exclusive":1.5625,"min_inclusive":-1000000.0,"when_in_range":{"type":"minecraft:min","argument1":"minecraft:overworld/sloped_cheese","argument2":{"type":"minecraft:mul","argument1":5.0,"argument2":"minecraft:overworld/caves/entrances"}},"when_out_of_range":{"type":"minecraft:max","argument1":{"type":"minecraft:min","argument1":{"type":"minecraft:min","argument1":{"type":"minecraft:add","argument1":{"type":"minecraft:mul","argument1":4.0,"argument2":{"type":"minecraft:square","argument":{"type":"minecraft:noise","noise":"minecraft:cave_layer","xz_scale":1.0,"y_scale":8.0}}},"argument2":{"type":"minecraft:add","argument1":{"type":"minecraft:clamp","input":{"type":"minecraft:add","argument1":0.27,"argument2":{"type":"minecraft:noise","noise":"minecraft:cave_cheese","xz_scale":1.0,"y_scale":0.6666666666666666}},"max":1.0,"min":-1.0},"argument2":{"type":"minecraft:clamp","input":{"type":"minecraft:add","argument1":1.5,"argument2":{"type":"minecraft:mul","argument1":-0.64,"argument2":"minecraft:overworld/sloped_cheese"}},"max":0.5,"min":0.0}}},"argument2":"minecraft:overworld/caves/entrances"},"argument2":{"type":"minecraft:add","argument1":"minecraft:overworld/caves/spaghetti_2d","argument2":"minecraft:overworld/caves/spaghetti_roughness_function"}},"argument2":{"type":"minecraft:range_choice","input":"minecraft:overworld/caves/pillars","max_exclusive":0.03,"min_inclusive":-1000000.0,"when_in_range":-1000000.0,"when_out_of_range":"minecraft:overworld/caves/pillars"}}}}}}}}}}}}},"argument2":"minecraft:overworld/caves/noodle"},"fluid_level_floodedness":{"type":"minecraft:noise","noise":"minecraft:aquifer_fluid_level_floodedness","xz_scale":1.0,"y_scale":0.67},"fluid_level_spread":{"type":"minecraft:noise","noise":"minecraft:aquifer_fluid_level_spread","xz_scale":1.0,"y_scale":0.7142857142857143},"initial_density_without_jaggedness":{"type":"minecraft:add","argument1":0.1171875,"argument2":{"type":"minecraft:mul","argument1":{"type":"minecraft:y_clamped_gradient","from_value":0.0,"from_y":-64,"to_value":1.0,"to_y":-40},"argument2":{"type":"minecraft:add","argument1":-0.1171875,"argument2":{"type":"minecraft:add","argument1":-0.078125,"argument2":{"type":"minecraft:mul","argument1":{"type":"minecraft:y_clamped_gradient","from_value":1.0,"from_y":240,"to_value":0.0,"to_y":256},"argument2":{"type":"minecraft:add","argument1":0.078125,"argument2":{"type":"minecraft:clamp","input":{"type":"minecraft:add","argument1":-0.703125,"argument2":{"type":"minecraft:mul","argument1":4.0,"argument2":{"type":"minecraft:quarter_negative","argument":{"type":"minecraft:mul","argument1":"minecraft:overworld/depth","argument2":{"type":"minecraft:cache_2d","argument":"minecraft:overworld/factor"}}}}},"max":64.0,"min":-64.0}}}}}}},"ridges":"minecraft:overworld/ridges","temperature":{"type":"minecraft:shifted_noise","noise":"minecraft:temperature","shift_x":"minecraft:shift_x","shift_y":0.0,"shift_z":"minecraft:shift_z","xz_scale":0.25,"y_scale":0.0},"vegetation":{"type":"minecraft:shifted_noise","noise":"minecraft:vegetation","shift_x":"minecraft:shift_x","shift_y":0.0,"shift_z":"minecraft:shift_z","xz_scale":0.25,"y_scale":0.0},"lava":0,"vein_gap":0,"vein_ridged":0,"vein_toggle":0},
				"surface_rule": ${surface_rule_generated_data}
			}
			""";
}
