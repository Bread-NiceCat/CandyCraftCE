package cn.breadnicecat.candycraftce;

import cn.breadnicecat.candycraftce.block.CBlocks;
import cn.breadnicecat.candycraftce.block.blockentity.CBlockEntities;
import cn.breadnicecat.candycraftce.entity.CEntities;
import cn.breadnicecat.candycraftce.gui.block.CMenus;
import cn.breadnicecat.candycraftce.item.CItems;
import cn.breadnicecat.candycraftce.level.CDims;
import cn.breadnicecat.candycraftce.misc.CGameRules;
import cn.breadnicecat.candycraftce.recipe.CRecipeTypes;
import cn.breadnicecat.candycraftce.sound.CSoundEvents;
import cn.breadnicecat.candycraftce.utils.CLogUtils;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.util.profiling.jfr.Environment;
import org.slf4j.Logger;

import java.io.File;
import java.util.LinkedList;
import java.util.Objects;

import static cn.breadnicecat.candycraftce.utils.CommonUtils.impossibleCode;

public final class CandyCraftCE {
	public static final String MOD_ID = "candycraftce";
	public static final String MOD_NAME = "CandyCraft CE";

	private static final Logger LOGGER = CLogUtils.sign();


	public static final boolean IS_DEV;
	private static EngineFeatures features;

	static {
		//检查是否处于Dev环境
		boolean _inDev = false;
		try {
			_inDev = new File(new File("").getAbsoluteFile().getParentFile(), "src").exists();
		} catch (Exception ignored) {
		} finally {
			IS_DEV = _inDev;
		}
	}

	private static Environment environment;
	private static ModPlatform platform;

	public static LinkedList<Runnable> bootstrapHooks = new LinkedList<>();

	private static boolean postBootstrap = false;
	private static boolean preBootstrap = false;

	/**
	 * bootstrap here
	 */
	public static void bootstrap(final Environment env, final ModPlatform platform, final EngineFeatures features) {
		if (preBootstrap) {
			throw new IllegalStateException("bootstrap has been ran");
		}
		preBootstrap = true;
		CandyCraftCE.environment = Objects.requireNonNull(env);
		CandyCraftCE.platform = Objects.requireNonNull(platform);
		CandyCraftCE.features = features;
		hookPostBootstrap(() -> LOGGER.info("Post Bootstrap"));
		hookMinecraftSetup(() -> LOGGER.info("Minecraft Setup"));

		LOGGER.info("=".repeat(64));
		LOGGER.info(MOD_ID + " Running in " + getEnvironment() + " with " + getPlatform());
		if (IS_DEV) {
			LOGGER.warn("Hey! Here's running in IDE mode!");
			LOGGER.warn("If you 're not a developer, Please report this issue!");
		}
		LOGGER.info("=".repeat(64));

		//防止某些类未被链式调用导致不会被初始化，不计顺序
		CDims.init();
		CItems.init();
		CMenus.init();
		CBlocks.init();
		CEntities.init();
		CGameRules.init();
		CRecipeTypes.init();
		CSoundEvents.init();
		CBlockEntities.init();
		bootstrapHooks.forEach(Runnable::run);
		bootstrapHooks = null;

		LOGGER.info("Loaded Successfully!");
		postBootstrap = true;
	}

	public static boolean isClient() {
		return getEnvironment() == Environment.CLIENT;
	}

	public static Environment getEnvironment() {
		return Objects.requireNonNull(environment, "too early!");
	}

	public static ModPlatform getPlatform() {
		return Objects.requireNonNull(platform, "too early!");
	}

	public static EngineFeatures getFeatures() {
		return features;
	}

	public static void hookPostBootstrap(Runnable runnable) {
		bootstrapHooks.add(runnable);
	}

	/**
	 * Forge: FMLCommonSetupEvent<p>
	 * Fabric: onInitialize
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
		FORGE,
		NEOFORGE,
		FABRIC,
		QUILT//Quilt兼容Fabric,不单独开发
	}
}
