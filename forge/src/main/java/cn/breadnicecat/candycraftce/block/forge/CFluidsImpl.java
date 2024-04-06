package cn.breadnicecat.candycraftce.block.forge;

import cn.breadnicecat.candycraftce.block.fluids.CaramelFluid;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.fluids.FluidType;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

/**
 * Created in 2024/4/5 下午11:05
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 * <p>
 */
public class CFluidsImpl {
	public static CaramelFluid _caramel_fluid(ResourceLocation tex) {
		return new CaramelFluid() {
			public FluidType type = new FluidType(create()
					.sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL)
					.sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY)
					.canHydrate(true)) {
				@Override
				public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer) {
					consumer.accept(new IClientFluidTypeExtensions() {
						@Override
						public ResourceLocation getStillTexture() {
							return tex;
						}

						@Override
						public ResourceLocation getFlowingTexture() {
							return tex;
						}
					});
				}
			};

			@Override
			public @NotNull FluidType getFluidType() {
				return type;
			}
		};
	}

	public static FluidType.Properties create() {
		return FluidType.Properties.create().density(2000).viscosity(10000);
	}

}
