package cn.breadnicecat.candycraftce.entity;

import cn.breadnicecat.candycraftce.entity.entities.entity.CaramelArrow;
import cn.breadnicecat.candycraftce.entity.entities.entity.LicoriceSpear;
import cn.breadnicecat.candycraftce.entity.entities.mobs.*;
import cn.breadnicecat.candycraftce.entity.models.ModelBunny;
import cn.breadnicecat.candycraftce.entity.models.ModelCranfish;
import cn.breadnicecat.candycraftce.entity.models.ModelGingerbreadMan;
import cn.breadnicecat.candycraftce.entity.models.ModelLicoriceSpear;
import cn.breadnicecat.candycraftce.entity.renderers.*;
import cn.breadnicecat.candycraftce.utils.CLogUtils;
import net.minecraft.client.renderer.entity.EntityRenderers;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.util.HashMap;

import static cn.breadnicecat.candycraftce.CandyCraftCE.hookMinecraftSetup;
import static cn.breadnicecat.candycraftce.CandyCraftCE.isClient;
import static cn.breadnicecat.candycraftce.entity.CEntityBuilder.create;
import static cn.breadnicecat.candycraftce.entity.CEntityBuilder.registerLayer;
import static net.minecraft.world.entity.MobCategory.*;

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
	//CREATURE
	public static final EntityEntry<GingerbreadMan> GINGERBREAD_MAN = create("gingerbread_man", GingerbreadMan.class, GingerbreadMan::new, CREATURE)
			.sized(0.5f, 1f)
			.attribute(GingerbreadMan::createAttributes)
			.spawnEgg(0xf1c3c3, 0x61380B)
			.clientTrackingRange(10)
			.save();
	public static final EntityEntry<CandyCanePig> CANDY_CANE_PIG = CEntityBuilder.create("candy_cane_pig", CandyCanePig.class, CandyCanePig::new, CREATURE)
			.sized(0.9f, 0.9f)
			.attribute(CandyCanePig::createAttributes)
			.spawnEgg(0xf1c3c3, 0xfb5757)
			.clientTrackingRange(10)
			.save();
	public static final EntityEntry<WaffleSheep> WAFFLE_SHEEP = CEntityBuilder.create("waffle_sheep", WaffleSheep.class, WaffleSheep::new, CREATURE)
			.attribute(WaffleSheep::createAttributes)
			.spawnEgg(0xf1c3c3, 0xffc000)
			.sized(0.9f, 1.3f)
			.clientTrackingRange(10)
			.save();
	public static final EntityEntry<Bunny> BUNNY = CEntityBuilder.create("bunny", Bunny.class, Bunny::new, CREATURE)
			.attribute(Bunny::createAttributes)
			.spawnEgg(0xf1c3c3, 0xeeff33)
			.sized(0.4f, 0.5f)
			.clientTrackingRange(8)
			.save();
	
	//WATER_CREATURE
	
	
	//WATER_AMBIENT
	public static final EntityEntry<Cranfish> CRANFISH = CEntityBuilder.create("cranfish", Cranfish.class, Cranfish::new, WATER_AMBIENT)
			.attribute(Cranfish::createAttributes)
			.spawnEgg(0xf1c3c3, 0x3a01df)
			.sized(0.5f, 0.3f)
			.clientTrackingRange(4)
			.save();
	//MISC
	public static final EntityEntry<CaramelArrow> CARAMEL_ARROW = CEntityBuilder.create("caramel_arrow", CaramelArrow.class, CaramelArrow::new, MISC)
			.sized(0.5f, 0.5f)
			.save();
	public static final EntityEntry<LicoriceSpear> LICORICE_SPEAR = CEntityBuilder.create("licorice_spear", LicoriceSpear.class, LicoriceSpear::new, MISC)
			.sized(0.5f, 0.5f)
			.save();
	
	
	static {
		hookMinecraftSetup(() -> {
					if (isClient()) {
						EntityRenderers.register(GINGERBREAD_MAN.get(), RendererGingerbreadMan::new);
						EntityRenderers.register(CARAMEL_ARROW.get(), RendererCaramelArrow::new);
						EntityRenderers.register(LICORICE_SPEAR.get(), RendererLicoriceSpear::new);
						EntityRenderers.register(CANDY_CANE_PIG.get(), RendererCandyCanePig::new);
						EntityRenderers.register(CRANFISH.get(), RendererCranfish::new);
						EntityRenderers.register(WAFFLE_SHEEP.get(), RendererWaffleSheep::new);
						EntityRenderers.register(BUNNY.get(), RendererBunny::new);
						
						registerLayer(ModelGingerbreadMan.MAIN, ModelGingerbreadMan::createBodyLayer);
						registerLayer(ModelLicoriceSpear.MAIN, ModelLicoriceSpear::createBodyLayer);
						registerLayer(ModelCranfish.MAIN, ModelCranfish::createBodyLayer);
						registerLayer(ModelBunny.MAIN, ModelBunny::createBodyLayer);
					}
				}
		);
	}
	
	public static void init() {
	}
}
