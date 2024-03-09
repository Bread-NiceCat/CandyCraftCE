package cn.breadnicecat.candycraftce.entity;

import cn.breadnicecat.candycraftce.EngineFeatures;
import cn.breadnicecat.candycraftce.utils.CLogUtils;
import com.google.common.collect.ImmutableMap;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import org.slf4j.Logger;

import java.util.function.Supplier;

import static cn.breadnicecat.candycraftce.utils.ResourceUtils.prefix;
import static cn.breadnicecat.candycraftce.utils.ResourceUtils.simplePrefix;

/**
 * Created in 2024/2/25 8:58
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 * <p>
 */
public class CEntities {
	private static final Logger LOGGER = CLogUtils.sign();

//	public static final EntityEntry<LicoriceSpearEntity> LICORICE_SPEAR = register("licorice_spear", () -> EntityType.Builder.<LicoriceSpearEntity>of(LicoriceSpearEntity::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(20));

	public static <E extends Entity> EntityEntry<E> register(String name, Supplier<EntityType.Builder<E>> factory) {
		return EngineFeatures.get().registerEntity(prefix(name), () -> factory.get().build(simplePrefix(name)));
	}

	@Environment(EnvType.CLIENT)
	public static void createRoots(ImmutableMap.Builder<ModelLayerLocation, LayerDefinition> builder) {
		LOGGER.info("create model roots");
//		builder.put(LicoriceSpearModel.MAIN_LAYER, LicoriceSpearModel.createBodyLayer());
//		EntityRenderers.register(LICORICE_SPEAR.get(), LicoriceSpearRenderer::new);
	}

	public static void init() {
	}
}
