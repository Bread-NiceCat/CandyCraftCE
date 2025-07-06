package cn.breadnicecat.candycraftce.entity;

import cn.breadnicecat.candycraftce.block.CBlockTags;
import cn.breadnicecat.candycraftce.entity.entities.misc.CaramelArrow;
import cn.breadnicecat.candycraftce.entity.entities.misc.LicoriceSpear;
import cn.breadnicecat.candycraftce.entity.entities.mobs.*;
import cn.breadnicecat.candycraftce.entity.entities.monsters.CookieCreeper;
import cn.breadnicecat.candycraftce.entity.entities.monsters.ExplorableJelly;
import cn.breadnicecat.candycraftce.entity.models.*;
import cn.breadnicecat.candycraftce.entity.renderers.*;
import cn.breadnicecat.candycraftce.utils.CLogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.LevelAccessor;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.util.HashMap;

import static cn.breadnicecat.candycraftce.entity.CEntityBuilder.create;
import static net.minecraft.world.entity.MobCategory.*;
import static net.minecraft.world.entity.SpawnPlacementTypes.ON_GROUND;
import static net.minecraft.world.level.levelgen.Heightmap.Types.MOTION_BLOCKING_NO_LEAVES;

/**
 * Created in 2024/2/25 8:58
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 * <p>
 */
public class CEntityTypes {
	
	public static final @NotNull HashMap<String, EntityEntry<?>> ENTITIES = new HashMap<>();
	
	private static final Logger LOGGER = CLogUtils.sign();
	//MONSTER
	public static final EntityEntry<CookieCreeper> COOKIE_CREEPER = create("cookie_creeper", CookieCreeper.class, CookieCreeper::new, MONSTER)
			.sized(0.6F, 1.7F)
			.attribute(CookieCreeper::createAttributes)
			.spawnEgg(0xf1c3c3, 0x777777)
			.clientTrackingRange(8)
			.client(b -> b.setRenderer(RendererCookieCreeper::new)
					.addLayer(ModelCookieCreeper.MAIN, ModelCookieCreeper::createBodyLayer))
			.setPlacements(ON_GROUND, MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules)
			.save();

	public static final EntityEntry<ExplorableJelly> STRAWBERRY_JELLY = create("strawberry_jelly", ExplorableJelly.class, ExplorableJelly::new, MONSTER)
			.attribute(ExplorableJelly::createAttributes)
			.spawnEgg(0x555555, 0xff0000)
			.sized(0.52f, 0.52f)
			.clientTrackingRange(10)
			.modify(b -> b.eyeHeight(0.325f))
			.client(b -> b.setRendererForce(RendererJelly::createStrawberry))
			.save();
	
	public static final EntityEntry<ExplorableJelly> SEA_BANANA_JELLY = create("sea_banana_jelly", ExplorableJelly.class, ExplorableJelly::new, MONSTER)
			.attribute(ExplorableJelly::createAttributes)
			.spawnEgg(5592405, 16776960)
			.sized(0.52f, 0.52f)
			.clientTrackingRange(10)
			.modify(b -> b.eyeHeight(0.325f))
			.client(b -> b.setRendererForce(RendererJelly::createSeaBanana))
			.save();
	
	public static final EntityEntry<ExplorableJelly> MINT_JELLY = create("mint_jelly", ExplorableJelly.class, ExplorableJelly::new, MONSTER)
			.attribute(ExplorableJelly::createAttributes)
			.spawnEgg(0x555555, 0xffff)
			.sized(0.52f, 0.52f)
			.clientTrackingRange(10)
			.modify(b -> b.eyeHeight(0.325f))
			.client(b -> b.setRendererForce(RendererJelly::createMint))
			.save();
	
	//CREATURE
	public static final EntityEntry<GingerbreadMan> GINGERBREAD_MAN = create("gingerbread_man", GingerbreadMan.class, GingerbreadMan::new, CREATURE)
			.sized(0.5f, 1f)
			.attribute(GingerbreadMan::createAttributes)
			.spawnEgg(0xf1c3c3, 0x61380B)
			.clientTrackingRange(10)
			.client(b -> b
					.setRenderer(RendererGingerbreadMan::new)
					.addLayer(ModelGingerbreadMan.MAIN, ModelGingerbreadMan::createBodyLayer))
			.save();
	public static final EntityEntry<CandyCanePig> CANDY_CANE_PIG = CEntityBuilder.create("candy_cane_pig", CandyCanePig.class, CandyCanePig::new, CREATURE)
			.sized(0.9f, 0.9f)
			.attribute(CandyCanePig::createAttributes)
			.spawnEgg(0xf1c3c3, 0xfb5757)
			.clientTrackingRange(10)
			.setPlacements(ON_GROUND, MOTION_BLOCKING_NO_LEAVES, CEntityTypes::checkCandyAnimalSpawnRules)
			.client(b -> b.setRendererForce(RendererCandyCanePig::new))
			.save();
	public static final EntityEntry<WaffleSheep> WAFFLE_SHEEP = CEntityBuilder.create("waffle_sheep", WaffleSheep.class, WaffleSheep::new, CREATURE)
			.attribute(WaffleSheep::createAttributes)
			.spawnEgg(0xf1c3c3, 0xffc000)
			.sized(0.9f, 1.3f)
			.clientTrackingRange(10)
			.setPlacements(ON_GROUND, MOTION_BLOCKING_NO_LEAVES, CEntityTypes::checkCandyAnimalSpawnRules)
			.client(b -> b.setRendererForce(RendererWaffleSheep::new))
			.save();
	public static final EntityEntry<Bunny> BUNNY = CEntityBuilder.create("bunny", Bunny.class, Bunny::new, CREATURE)
			.attribute(Bunny::createAttributes)
			.spawnEgg(0xf1c3c3, 0xeeff33)
			.sized(0.4f, 0.5f)
			.clientTrackingRange(8)
			.setPlacements(ON_GROUND, MOTION_BLOCKING_NO_LEAVES, CEntityTypes::checkCandyAnimalSpawnRules)
			.client(b -> b
					.setRenderer(RendererBunny::new)
					.addLayer(ModelBunny.MAIN, ModelBunny::createBodyLayer))
			.save();
	
	//WATER_CREATURE
	
	
	//WATER_AMBIENT
	public static final EntityEntry<Cranfish> CRANFISH = CEntityBuilder.create("cranfish", Cranfish.class, Cranfish::new, WATER_AMBIENT)
			.attribute(Cranfish::createAttributes)
			.spawnEgg(0xf1c3c3, 0x3a01df)
			.sized(0.5f, 0.3f)
			.clientTrackingRange(4)
			.setPlacements(ON_GROUND, MOTION_BLOCKING_NO_LEAVES, WaterAnimal::checkSurfaceWaterAnimalSpawnRules)
			.client(b -> b
					.setRenderer(RendererCranfish::new)
					.addLayer(ModelCranfish.MAIN, ModelCranfish::createBodyLayer))
			.save();
	//MISC
	public static final EntityEntry<CaramelArrow> CARAMEL_ARROW = CEntityBuilder.create("caramel_arrow", CaramelArrow.class, CaramelArrow::new, MISC)
			.sized(0.5f, 0.5f)
			.client(b -> b.setRenderer(RendererCaramelArrow::new))
			.save();
	public static final EntityEntry<LicoriceSpear> LICORICE_SPEAR = CEntityBuilder.create("licorice_spear", LicoriceSpear.class, LicoriceSpear::new, MISC)
			.sized(0.5f, 0.5f)
			.client(b -> b
					.setRenderer(RendererLicoriceSpear::new)
					.addLayer(ModelLicoriceSpear.MAIN, ModelLicoriceSpear::createBodyLayer))
			.save();
	
	
	public static boolean checkCandyAnimalSpawnRules(EntityType<? extends Animal> animal, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
		return level.getBlockState(pos.below()).is(CBlockTags.BT_CANDY_ANIMAL_SPAWNABLE_ON) && level.getRawBrightness(pos, 0) > 8;
	}
	
	public static void init() {
	}
}
