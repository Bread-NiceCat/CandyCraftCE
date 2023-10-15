//package cn.breadnicecat.candycraftce.registration.block.forge;
//
//import cn.breadnicecat.candycraftce.forge.CandyCraftCEImpl;
//import cn.breadnicecat.candycraftce.registration.block.BlockEntry;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.world.level.block.Block;
//import net.minecraftforge.registries.DeferredRegister;
//import net.minecraftforge.registries.ForgeRegistries;
//import net.minecraftforge.registries.RegistryObject;
//
//import java.util.function.Supplier;
//
///**
// * Created in 2023/9/29 15:19
// * Project: candycraftce
// *
// * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
// */
//public class CBlocksBlockBuilderImpl {
//
//	public static final DeferredRegister<Block> REGISTER = CandyCraftCEImpl.createRegister(ForgeRegistries.BLOCKS);
//
//	public static <B extends Block> BlockEntry<B> register(ResourceLocation name, Supplier<B> sup) {
//		String path = name.getPath();
//		RegistryObject<B> b = REGISTER.register(path, sup);
//		return new BlockEntry<>(name) {
//			@Override
//			public B getBlock() {
//				return b.get();
//			}
//		};
//	}
//}
