package cn.breadnicecat.candycraftce.mixin.data;

import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

/**
 * Created by NiceCat on 2026/1/17.
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 *
 */
@Mixin(BlockModelGenerators.BlockFamilyProvider.class)
public interface AccessorBlockFamilyProvider {
	@Accessor("fullBlock")
	void setFullBlock(ResourceLocation fullBlock);
}
