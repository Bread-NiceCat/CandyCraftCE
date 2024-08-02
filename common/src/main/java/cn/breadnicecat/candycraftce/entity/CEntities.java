package cn.breadnicecat.candycraftce.entity;

import cn.breadnicecat.candycraftce.entity.entities.CandyCanePig;
import cn.breadnicecat.candycraftce.entity.entities.CaramelArrow;
import cn.breadnicecat.candycraftce.entity.entities.GingerbreadMan;
import cn.breadnicecat.candycraftce.entity.entities.LicoriceSpear;
import cn.breadnicecat.candycraftce.entity.models.ModelGingerbreadMan;
import cn.breadnicecat.candycraftce.entity.models.ModelLicoriceSpear;
import cn.breadnicecat.candycraftce.entity.renderers.RendererCandyCanePig;
import cn.breadnicecat.candycraftce.entity.renderers.RendererCaramelArrow;
import cn.breadnicecat.candycraftce.entity.renderers.RendererGingerbreadMan;
import cn.breadnicecat.candycraftce.entity.renderers.RendererLicoriceSpear;
import cn.breadnicecat.candycraftce.utils.CLogUtils;
import net.minecraft.client.renderer.entity.EntityRenderers;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.util.HashMap;

import static cn.breadnicecat.candycraftce.CandyCraftCE.hookMinecraftSetup;
import static cn.breadnicecat.candycraftce.CandyCraftCE.isClient;
import static cn.breadnicecat.candycraftce.entity.CEntityBuilder.create;
import static cn.breadnicecat.candycraftce.entity.CEntityBuilder.registerLayer;
import static net.minecraft.world.entity.MobCategory.CREATURE;
import static net.minecraft.world.entity.MobCategory.MISC;

/**
 * Created in 2024/2/25 8:58
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 * <p>
 */
public class CEntities {
	
	public static final @NotNull HashMap<String, EntityEntry<?>> ENTITIES = new HashMap<>();
	
	private static final Logger LOGGER = CLogUtils.sign();
	
	public static final EntityEntry<GingerbreadMan> GINGERBREAD_MAN = create("gingerbread_man", GingerbreadMan::new, CREATURE)
			.sized(0.5f, 1f)
			.attribute(GingerbreadMan::createAttributes)
			.spawnEgg(0xF1C3C3, 0x61380B)
			.save();
	public static final EntityEntry<CandyCanePig> CANDY_CANE_PIG = CEntityBuilder.<CandyCanePig>create("candy_cane_pig", CandyCanePig::new, CREATURE)
			.sized(0.9f, 0.9f)
			.attribute(CandyCanePig::createAttributes)
			.save();
	
	public static final EntityEntry<CaramelArrow> CARAMEL_ARROW = CEntityBuilder.<CaramelArrow>create("caramel_arrow", CaramelArrow::new, MISC)
			.sized(0.5f, 0.5f)
			.save();
	public static final EntityEntry<LicoriceSpear> LICORICE_SPEAR = CEntityBuilder.<LicoriceSpear>create("licorice_spear", LicoriceSpear::new, MISC)
			.sized(0.5f, 0.5f)
			.save();
	
	
	static {
		hookMinecraftSetup(() -> {
					if (isClient()) {
						EntityRenderers.register(GINGERBREAD_MAN.get(), RendererGingerbreadMan::new);
						EntityRenderers.register(CARAMEL_ARROW.get(), RendererCaramelArrow::new);
						EntityRenderers.register(LICORICE_SPEAR.get(), RendererLicoriceSpear::new);
						EntityRenderers.register(CANDY_CANE_PIG.get(), RendererCandyCanePig::new);
						
						registerLayer(ModelGingerbreadMan.MAIN, ModelGingerbreadMan::createBodyLayer);
						registerLayer(ModelGingerbreadMan.JOB, ModelGingerbreadMan::createBodyLayer);
						registerLayer(ModelLicoriceSpear.MAIN, ModelLicoriceSpear::createBodyLayer);
					}
				}
		);
	}
	
	public static void init() {
	}
}
