package cn.breadnicecat.candycraftce.datagen.neoforge.providers.builtins;

import cn.breadnicecat.candycraftce.misc.CDamageTypes;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.damagesource.DamageType;

/**
 * Created in 2024/2/14 11:49
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 * <p>
 */
public class CDamageTypesData extends CDamageTypes {
	public static void bootstrap(BootstrapContext<DamageType> context) {
		context.register(STEP_ON_SPIKES, new DamageType("step_on_spikes", 0.1f));
	}
}
