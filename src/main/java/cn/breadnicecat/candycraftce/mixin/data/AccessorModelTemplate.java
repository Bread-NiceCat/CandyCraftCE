package cn.breadnicecat.candycraftce.mixin.data;

import net.minecraft.data.models.model.ModelTemplate;
import net.minecraft.data.models.model.TextureSlot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Set;

/**
 * Created by NiceCat on 2025/12/6.
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 *
 */
@Mixin(ModelTemplate.class)
public interface AccessorModelTemplate {
	@Accessor
	Set<TextureSlot> getRequiredSlots();

}
