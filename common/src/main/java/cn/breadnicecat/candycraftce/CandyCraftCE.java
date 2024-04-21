package cn.breadnicecat.candycraftce;

import cn.breadnicecat.candycraftce.block.CBlocks;
import cn.breadnicecat.candycraftce.block.CFluids;
import cn.breadnicecat.candycraftce.block.blockentity.CBlockEntities;
import cn.breadnicecat.candycraftce.entity.CEntities;
import cn.breadnicecat.candycraftce.gui.block.CMenus;
import cn.breadnicecat.candycraftce.item.CItems;
import cn.breadnicecat.candycraftce.level.CDimInit;
import cn.breadnicecat.candycraftce.misc.CGameRules;
import cn.breadnicecat.candycraftce.particle.CParticles;
import cn.breadnicecat.candycraftce.recipe.CRecipeTypes;
import cn.breadnicecat.candycraftce.sound.CSoundEvents;
import cn.breadnicecat.candycraftce.utils.CLogUtils;
import com.mojang.datafixers.util.Pair;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
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


	public static final boolean INDEV;

	static {
		//检查是否处于Dev环境
		boolean _inDev = false;
		try {
			_inDev = new File(new File("").getAbsoluteFile().getParentFile(), "src").exists();
		} catch (Exception ignored) {
		} finally {
			INDEV = _inDev;
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

		hookPostBootstrap(() -> LOGGER.info("Post Bootstrap"));
		hookMinecraftSetup(() -> LOGGER.info("Minecraft Setup"));
		LOGGER.info("=".repeat(64));
		LOGGER.info(MOD_ID + " Running in {} with {}", getEnvironment(), getPlatform());
		if (INDEV) {
			LOGGER.warn("Hey! Here's running in IDE mode!");
			LOGGER.warn("If you 're not a developer, Please report this issue!");
		}
		LOGGER.info("=".repeat(64));

//		防止某些类未被链式调用导致不会被初始化，不计顺序
		CItems.init();
		CMenus.init();
		CFluids.init();
		CBlocks.init();
		CDimInit.init();
		CEntities.init();
		CParticles.init();
		CGameRules.init();
		CRecipeTypes.init();
		CSoundEvents.init();
		CBlockEntities.init();
		bootstrapHooks.forEach(Runnable::run);
		bootstrapHooks = null;
		postBootstrap = true;
		LOGGER.info("Loaded Successfully!");
	}

	public static boolean isClient() {
		return getEnvironment() == Environment.CLIENT;
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
	public static <R, S extends R> Pair<ResourceKey<R>, Supplier<S>> register(Registry<R> registry, ResourceLocation key, Supplier<S> factory) {
		return impossibleCode();
	}

	public static void hookPostBootstrap(Runnable runnable) {
		bootstrapHooks.add(runnable);
	}

	/**
	 * Forge: FMLCommonSetupEvent
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
		FORGE,
		FABRIC,
		NEOFORGE,//1.20+
		QUILT//Quilt兼容Fabric,暂时不单独开发
	}
}
