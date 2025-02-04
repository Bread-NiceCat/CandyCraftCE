package cn.breadnicecat.candycraftce.block.neoforge;

import cn.breadnicecat.candycraftce.block.CFluids;
import cn.breadnicecat.candycraftce.block.FlowingFluidEntry;
import cn.breadnicecat.candycraftce.utils.CLogUtils;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.fluids.FluidType;
import org.slf4j.Logger;

import java.lang.reflect.Method;
import java.util.function.Consumer;

@EventBusSubscriber(value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class CFluidsImpl {
	
	private static final Logger log = CLogUtils.getModLogger();
	
	@SubscribeEvent
	private static void initializeClient(RegisterClientExtensionsEvent event) {
		log.info("Init fluid_types...");
		for (FlowingFluidEntry<?, ?> fluid : CFluids.FLUIDS.values()) {
			try {
				FluidType type = fluid.getSource().get().getFluidType();
				Method method = type.getClass().getDeclaredMethod("initializeClient", Consumer.class);
				method.setAccessible(true);
				method.invoke(type, (Consumer<IClientFluidTypeExtensions>) ext -> event.registerFluidType(ext, type));
			} catch (Exception e) {
				log.error("Error during init fluid_type: " + fluid, e);
			}
		}
	}
}