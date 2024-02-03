package cn.breadnicecat.candycraftce;

import cn.breadnicecat.candycraftce.block.CBlocks;
import cn.breadnicecat.candycraftce.block.blockentity.CBlockEntities;
import cn.breadnicecat.candycraftce.gui.block.CMenus;
import cn.breadnicecat.candycraftce.item.CItems;
import cn.breadnicecat.candycraftce.misc.CGameRules;
import cn.breadnicecat.candycraftce.recipe.CRecipeTypes;
import cn.breadnicecat.candycraftce.sound.CSoundEvents;
import cn.breadnicecat.candycraftce.utils.CLogUtils;
import net.minecraft.util.profiling.jfr.Environment;
import org.slf4j.Logger;

import java.io.File;
import java.util.LinkedList;
import java.util.Objects;

public final class CandyCraftCE {
	public static final String MOD_ID = "candycraftce";
	public static final String MOD_NAME = "CandyCraft CE";

	private static final Logger LOGGER = CLogUtils.sign();


	public static final boolean IS_DEV;

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
	public static LinkedList<Runnable> mcSetupHooks = new LinkedList<>();

	private static boolean postBootstrap = false;
	private static boolean preBootstrap = false;

	/**
	 * bootstrap here
	 */
	public static void runBootstrap(final Environment env, final ModPlatform platform) {
		if (preBootstrap) {
			throw new IllegalStateException("bootstrap ran");
		}
		preBootstrap = true;
		CandyCraftCE.environment = Objects.requireNonNull(env);
		CandyCraftCE.platform = Objects.requireNonNull(platform);

		LOGGER.info("=".repeat(50));
		LOGGER.info(MOD_ID + " Running in " + getEnvironment() + " with " + getPlatform());
		if (IS_DEV) {
			LOGGER.warn("Hey! Here's running in IDE mode!");
			LOGGER.warn("If you 're not a developer, Please report this issue!");
		}
		LOGGER.info("=".repeat(50));

		CItems.init();
		CMenus.init();
		CBlocks.init();
		CGameRules.init();
		CRecipeTypes.init();
		CSoundEvents.init();
		CBlockEntities.init();

		bootstrapHooks.forEach(Runnable::run);
		bootstrapHooks = null;

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

	public static void hookPostBootstrap(Runnable runnable) {
		bootstrapHooks.add(runnable);
	}

	/**
	 * Forge: FMLCommonSetupEvent<p>
	 * Fabric: onInitialize
	 */
	public static void hookMinecraftSetup(Runnable runnable) {
		mcSetupHooks.add(runnable);
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
		FORGE, FABRIC, QUILT//Quilt兼容Fabric,不单独开发
	}
}
