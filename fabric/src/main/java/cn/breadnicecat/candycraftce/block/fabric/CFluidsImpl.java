//package cn.breadnicecat.candycraftce.block.fabric;
//
//import cn.breadnicecat.candycraftce.block.CBlocks;
//import cn.breadnicecat.candycraftce.block.fluids.CaramelFluid;
//import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
//import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler;
//import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
//import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariantAttributeHandler;
//import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariantAttributes;
//import net.minecraft.network.chat.Component;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.sounds.SoundEvent;
//import net.minecraft.world.level.Level;
//import org.jetbrains.annotations.Nullable;
//
//import java.util.Optional;
//
//import static cn.breadnicecat.candycraftce.utils.ResourceUtils.prefix;
//import static net.minecraft.sounds.SoundEvents.BUCKET_EMPTY;
//import static net.minecraft.sounds.SoundEvents.BUCKET_FILL;
//
///**
// * Created in 2024/4/5 下午11:05
// * Project: candycraftce
// *
// * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
// * <p>
// */
//public class CFluidsImpl {
//    @SuppressWarnings("UnstableApiUsage")
//    public static CaramelFluid _caramel_fluid(ResourceLocation tex) {
//        CaramelFluid fluid = new CaramelFluid() {
//        };
//
//        FluidVariantAttributes.register(fluid, new FluidVariantAttributeHandler() {
//                    @Override
//                    public Optional<SoundEvent> getFillSound(FluidVariant variant) {
//                        return Optional.of(BUCKET_FILL);
//                    }
//
//                    @Override
//                    public Optional<SoundEvent> getEmptySound(FluidVariant variant) {
//                        return Optional.of(BUCKET_EMPTY);
//                    }
//
//                    @Override
//                    public int getViscosity(FluidVariant variant, @Nullable Level world) {
//                        return 10000;
//                    }
//
//
//                    @Override
//                    public Component getName(FluidVariant fluidVariant) {
//                        return CBlocks.CARAMEL_LIQUID.get().getName();
//                    }
//                }
//        );
//        FluidRenderHandlerRegistry.INSTANCE.register(fluid, new SimpleFluidRenderHandler(tex, tex));
//        return fluid;
//    }
//}
