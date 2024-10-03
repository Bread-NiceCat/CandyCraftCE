package cn.breadnicecat.candycraftce;

import cn.breadnicecat.candycraftce.block.CBlocks;
import cn.breadnicecat.candycraftce.block.CFluids;
import cn.breadnicecat.candycraftce.block.blockentity.CBlockEntities;
import cn.breadnicecat.candycraftce.entity.CEntityTypes;
import cn.breadnicecat.candycraftce.gui.block.CMenus;
import cn.breadnicecat.candycraftce.item.CItems;
import cn.breadnicecat.candycraftce.level.CDimInit;
import cn.breadnicecat.candycraftce.misc.CEggProject;
import cn.breadnicecat.candycraftce.misc.CGameRules;
import cn.breadnicecat.candycraftce.particle.CParticles;
import cn.breadnicecat.candycraftce.poi.CPoiTypes;
import cn.breadnicecat.candycraftce.recipe.CRecipeTypes;
import cn.breadnicecat.candycraftce.sound.CSoundEvents;
import cn.breadnicecat.candycraftce.utils.CLogUtils;
import cn.breadnicecat.candycraftce.utils.SimpleEntry;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.profiling.jfr.Environment;
import org.slf4j.Logger;

import java.io.File;
import java.util.LinkedList;
import java.util.function.Supplier;

import static cn.breadnicecat.candycraftce.utils.CommonUtils.impossibleCode;

public final class CandyCraftCE {
	public static final String MOD_ID = "candycraftce";
	public static final String MOD_NAME = "CandyCraft CE";
	
	private static final Logger LOGGER = CLogUtils.sign();
	
	
	public static final boolean DEV;
	
	static {
		//检查是否处于Dev环境
		boolean inDev = false;
		try {
			inDev = new File(new File("").getAbsoluteFile().getParentFile(), "src").exists();
		} catch (Exception ignored) {
		} finally {
			DEV = inDev;
		}
	}
	
	public static LinkedList<Runnable> bootstrapHooks = new LinkedList<>();
	private static boolean postBootstrap = false;
	private static boolean preBootstrap = false;
	
	/**
	 * bootstrap here
	 */
	public static void bootstrap() {
		if (preBootstrap) {
			throw new IllegalStateException(MOD_ID + " has been bootstrapped");
		}
		preBootstrap = true;
		Environment environment = getEnvironment();
		ModPlatform platform = getPlatform();
		
		hookPostBootstrap(() -> LOGGER.info("Post Bootstrap"));
		hookMinecraftSetup(() -> LOGGER.info("Minecraft Setup"));
		
		LOGGER.info("=".repeat(64));
		LOGGER.info(MOD_ID + " Running in {} with {}", environment, platform);
		if (DEV) {
			LOGGER.warn("Hey! Here's running in IDE mode!");
			LOGGER.warn("If you 're not a developer, Please report this issue!");
		}
		LOGGER.info("=".repeat(64));

//		Prevent certain classes from not being chained and not being initialized,
//		regardless of order,
//      especially some classes which contains `register` ops.
//      note: some class only contains `RegistryKey<>` (like `CEnchantments`) needn't this!
		
		CItems.init();
		CMenus.init();
		CBlocks.init();
		CFluids.init();
		CDimInit.init();
		CEntityTypes.init();
		CPoiTypes.init();
		CGameRules.init();
		CEggProject.init();
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
		return getEnvironment() == Environment.CLIENT;
	}
	
	@ExpectPlatform
	public static boolean isLoaded(String modid) {
		return impossibleCode();
	}
	
	@ExpectPlatform
	public static Environment getEnvironment() {
		return impossibleCode();
	}
	
	@ExpectPlatform
	public static ModPlatform getPlatform() {
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
	
	public enum ModPlatform {
		@Deprecated FORGE,
		FABRIC,
		NEOFORGE,//1.20+
		QUILT//Quilt兼容Fabric,暂时不单独开发
	}
}
