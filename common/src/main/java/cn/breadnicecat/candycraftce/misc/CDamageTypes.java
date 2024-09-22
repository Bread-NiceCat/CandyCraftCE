package cn.breadnicecat.candycraftce.misc;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;

import static cn.breadnicecat.candycraftce.utils.ResourceUtils.prefix;

/**
 * Created in 2024/2/14 11:45
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 * <p>
 */
public class CDamageTypes {
	public static final ResourceKey<DamageType> STEP_ON_SPIKES = bind("step_on_spikes");
	
	public static DamageSource stepOnSpikes(RegistryAccess access) {
		return source(access, STEP_ON_SPIKES);
	}
	
	/**
	 * @see DamageSource#getLocalizedDeathMessage(LivingEntity)
	 */
	private static DamageSource source(RegistryAccess access, ResourceKey<DamageType> key) {
		return new DamageSource(type(access, key));
	}
	
	private static DamageSource source(RegistryAccess access, ResourceKey<DamageType> key, @Nullable Entity entity) {
		return new DamageSource(type(access, key), entity);
	}
	
	private static DamageSource source(RegistryAccess access, ResourceKey<DamageType> key, @Nullable Entity causingEntity, @Nullable Entity directEntity) {
		return new DamageSource(type(access, key), causingEntity, directEntity);
	}
	
	
	public static Holder<DamageType> type(RegistryAccess access, ResourceKey<DamageType> key) {
		return registry(access).getHolderOrThrow(key);
	}
	
	public static Registry<DamageType> registry(RegistryAccess access) {
		return access.registryOrThrow(Registries.DAMAGE_TYPE);
	}
	
	private static ResourceKey<DamageType> bind(String name) {
		return ResourceKey.create(Registries.DAMAGE_TYPE, prefix(name));
	}
	
	
	public static void bootstrap(BootstrapContext<DamageType> context) {
		context.register(STEP_ON_SPIKES, new DamageType("step_on_spikes", 0.1f));
	}
}
