//package cn.breadnicecat.candycraftce.registration.block.fabric;
//
//import net.minecraft.core.registries.BuiltInRegistries;
//import net.minecraft.resources.ResourceLocation;
//
///**
// * Created in 2023/9/29 15:19
// * Project: candycraftce
// *
// * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
// */
//public class CBlocksBlockBuilderImpl {
//	public static <B extends Block> BlockEntry<B> register(ResourceLocation name, Supplier<B> sup) {
//		B b = Registry.register(BuiltInRegistries.BLOCK, name, sup.get());
//		return new BlockEntry<>(name) {
//			@Override
//			public B getBlock() {
//				return b;
//			}
//		};
//	}
//}
