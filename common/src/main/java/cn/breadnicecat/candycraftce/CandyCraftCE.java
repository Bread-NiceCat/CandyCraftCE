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
import cn.breadnicecat.candycraftce.misc.CGameRules;
import cn.breadnicecat.candycraftce.misc.egg_project.CEggProject;
import cn.breadnicecat.candycraftce.particle.CParticles;
import cn.breadnicecat.candycraftce.recipe.CRecipeTypes;
import cn.breadnicecat.candycraftce.sound.CJukeboxSound;
import cn.breadnicecat.candycraftce.sound.CSoundEvents;
import cn.breadnicecat.candycraftce.utils.CLogUtils;
import cn.breadnicecat.candycraftce.utils.SimpleEntry;
import dev.architectury.injectables.annotations.ExpectPlatform;
import dev.architectury.injectables.targets.ArchitecturyTarget;
import dev.architectury.platform.Platform;
import dev.architectury.utils.Env;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;

import java.util.LinkedList;
import java.util.function.Supplier;

import static cn.breadnicecat.candycraftce.utils.CommonUtils.impossibleCode;
import static net.minecraft.core.registries.Registries.*;

public final class CandyCraftCE {
	public static final String MOD_ID = "candycraftce";
	public static final String MOD_NAME = "CandyCraft CE";
	
	private static final Logger LOGGER = CLogUtils.sign();
	
	public static LinkedList<Runnable> bootstrapHooks = new LinkedList<>();
	
	private static boolean bootstrapped = false;
	
	/**
	 * bootstrap here
	 */
	public static void bootstrap() {
		if (bootstrapped) {
			LOGGER.error(MOD_ID + " has been bootstrapped");
			Thread.dumpStack();
			return;
		}
		bootstrapped = true;
		
		hookPostBootstrap(() -> LOGGER.info("Post Bootstrap"));
		hookMinecraftSetup(() -> LOGGER.info("Minecraft Setup"));
		LOGGER.info("=".repeat(64));
		LOGGER.info("{}(id:{}) Running in {} with {}", MOD_NAME, MOD_ID, Platform.getEnvironment(), ArchitecturyTarget.getCurrentTarget());
		if (isDev()) {
			LOGGER.warn("Hey! Here's running in IDE mode!");
			LOGGER.warn("If you 're not a developer, Please report this issue!");
		}
		LOGGER.info("=".repeat(64));

//      防止某些类不被链式初始化
//      尤其是一些包含 'register' 操作的类。
//      注意：某些仅包含 'RegistryKey' 的类,如 'CEnchantments',不需要这个！
		
		CItems.init();
		CMenus.init();
		CBlocks.init();
		CFluids.init();
		CDimInit.init();
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
		LOGGER.info("Bootstrap Successfully!");
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
	
	public static boolean isDev() {
		return Platform.isDevelopmentEnvironment();
	}
	
	public static boolean isClient() {
		return Platform.getEnvironment() == Env.CLIENT;
	}
}
