package cn.breadnicecat.candycraftce.block.forge;

import cn.breadnicecat.candycraftce.block.fluids.CaramelFluid;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
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
			@Override
			public @NotNull FluidType getFluidType() {
				return new FluidType(FluidType.Properties.create()) {
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
			}
		};
	}


}
