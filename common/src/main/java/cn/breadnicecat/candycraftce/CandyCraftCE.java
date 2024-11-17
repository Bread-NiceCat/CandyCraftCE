package cn.breadnicecat.candycraftce;

import cn.breadnicecat.candycraftce.block.CBlocks;
import cn.breadnicecat.candycraftce.block.CFluids;
import cn.breadnicecat.candycraftce.block.blockentity.CBlockEntities;
import cn.breadnicecat.candycraftce.entity.CEntityTypes;
import cn.breadnicecat.candycraftce.gui.block.CMenus;
import cn.breadnicecat.candycraftce.item.CEnchantments;
import cn.breadnicecat.candycraftce.item.CItems;
import cn.breadnicecat.candycraftce.level.*;
import cn.breadnicecat.candycraftce.misc.CDamageTypes;
import cn.breadnicecat.candycraftce.misc.CEggProject;
import cn.breadnicecat.candycraftce.misc.CGameRules;
import cn.breadnicecat.candycraftce.particle.CParticles;
import cn.breadnicecat.candycraftce.poi.CPoiTypes;
import cn.breadnicecat.candycraftce.recipe.CRecipeTypes;
import cn.breadnicecat.candycraftce.sound.CJukeboxSound;
import cn.breadnicecat.candycraftce.sound.CSoundEvents;
import cn.breadnicecat.candycraftce.utils.CLogUtils;
import cn.breadnicecat.candycraftce.utils.CommonUtils;
import cn.breadnicecat.candycraftce.utils.SimpleEntry;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.profiling.jfr.Environment;
import org.slf4j.Logger;

import java.util.LinkedList;
import java.util.function.Supplier;

import static cn.breadnicecat.candycraftce.utils.CommonUtils.impossibleCode;
import static net.minecraft.core.registries.Registries.*;

public final class CandyCraftCE {
	public static final String MOD_ID = "candycraftce";
	public static final String MOD_NAME = "CandyCraft CE";
	
	private static final Logger LOGGER = CLogUtils.sign();
	
	
	public static final boolean DEV = CommonUtils.isDev();
	public static final Environment ENVIRONMENT = getEnvironment();
	public static final ModPlatform PLATFORM = getPlatform();
	
	
	public static LinkedList<Runnable> bootstrapHooks = new LinkedList<>();
	private static boolean postBootstrap = false;
	private static boolean preBootstrap = false;
	
	/**
	 * bootstrap here
	 */
	public static void bootstrap() {
		if (preBootstrap) {
			LOGGER.error(MOD_ID + " has been bootstrapped");
			Thread.dumpStack();
			return;
		}
		preBootstrap = true;
		
		hookPostBootstrap(() -> LOGGER.info("Post Bootstrap"));
		hookMinecraftSetup(() -> LOGGER.info("Minecraft Setup"));
		
		LOGGER.info("=".repeat(64));
		LOGGER.info(MOD_ID + " Running in {} with {}", ENVIRONMENT, PLATFORM);
		if (DEV) {
			LOGGER.warn("Hey! Here's running in IDE mode!");
			LOGGER.warn("If you 're not a developer, Please report this issue!");
		}
		LOGGER.info("=".repeat(64));

//		Prevent certain classes from not being chained and not being initialized,
//		regardless of order,
//      especially some classes which contains `register` ops.
//      note: some class only contains `RegistryKey<>` (like `CEnchantments`) needn't this!
//      防止某些类不被链式初始化,不计顺序,
//      尤其是一些包含 'register' 操作的类。
//      注意：某些仅包含 'RegistryKey' 的类,如 'CEnchantments',不需要这个！
		
		CItems.init();
		CMenus.init();
		CBlocks.init();
		CFluids.init();
		CDimInit.init();
		CPoiTypes.init();
		CGameRules.init();
		CEggProject.init();
		CEntityTypes.init();
		CRecipeTypes.init();
		CSoundEvents.init();
		CBlockEntities.init();
		if (isClient()) {
			CParticles.init();
		}
		
		bootstrapHooks.forEach(Runnable::run);
		bootstrapHooks = null;
		postBootstrap = true;
		LOGGER.info("Loaded Successfully!");
	}
	
	public static boolean isClient() {
		return ENVIRONMENT == Environment.CLIENT;
	}
	
	@ExpectPlatform
	public static boolean isLoaded(String modid) {
		return impossibleCode();
	}
	
	@ExpectPlatform
	private static Environment getEnvironment() {
		return impossibleCode();
	}
	
	@ExpectPlatform
	private static ModPlatform getPlatform() {
		return impossibleCode();
	}
	
	@ExpectPlatform
	public static <R, S extends R> SimpleEntry<R, S> register(Registry<R> registry, ResourceLocation key, Supplier<S> factory) {
		return impossibleCode();
	}
	
	public static void hookPostBootstrap(Runnable runnable) {
		bootstrapHooks.add(runnable);
	}
	
	/**
	 * Neo/Forge: FMLCommonSetupEvent
	 * <p>
	 * Fabric: onInitialize()
	 */
	@ExpectPlatform
	public static void hookMinecraftSetup(Runnable runnable) {
		impossibleCode();
	}
	
	/**
	 * @return 完成bootstrap后为true
	 */
	public static boolean isBootstrapped() {
		return postBootstrap;
	}
	
	/**
	 * @return 在bootstrap中时为true
	 */
	public static boolean isBootstrapping() {
		return preBootstrap && !postBootstrap;
	}
	
	public static RegistrySetBuilder getDataSetBuilder() {
		return new RegistrySetBuilder()
				.add(DAMAGE_TYPE, CDamageTypes::bootstrap)
				.add(JUKEBOX_SONG, CJukeboxSound::bootstrap)
				.add(ENCHANTMENT, CEnchantments::bootstrap)
				.add(CONFIGURED_CARVER, CConfiguredCarver::bootstrap)
				.add(CONFIGURED_FEATURE, CConfiguredFeatures::bootstrap)
				.add(PLACED_FEATURE, CPlacedFeatures::bootstrap)
				.add(BIOME, CBiomes::bootstrap)
				.add(DIMENSION_TYPE, CDimTypes::bootstrap)
				.add(LEVEL_STEM, CDims::bootstrap);
	}
	
	public enum ModPlatform {
		@Deprecated(forRemoval = true) FORGE,
		FABRIC,
		NEOFORGE,//1.20+
		QUILT//Quilt兼容Fabric,暂时不单独开发
	}
}
