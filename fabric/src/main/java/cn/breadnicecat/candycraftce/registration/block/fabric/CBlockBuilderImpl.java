package cn.breadnicecat.candycraftce.registration.block.fabric;

import cn.breadnicecat.candycraftce.CandyCraftCE;
import cn.breadnicecat.candycraftce.registration.block.BlockEntry;
import cn.breadnicecat.candycraftce.utils.tools.Accessor;
import cn.breadnicecat.candycraftce.utils.tools.AccessorImpl;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

/**
 * Created in 2023/12/10 10:34
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 * <p>
 */
public class CBlockBuilderImpl {
	public static <B extends Block> BlockEntry<B> register(ResourceLocation name, Supplier<B> sup) {
		Accessor<B> ass = new AccessorImpl<>();
		//适应Forge的注册模式
		CandyCraftCE.hookPostBootstrap(() -> ass.set(Registry.register(BuiltInRegistries.BLOCK, name, sup.get())));
		return new BlockEntry<>(name) {
			@Override
			public B getBlock() {
				return ass.get();
			}
		};
	}
}
