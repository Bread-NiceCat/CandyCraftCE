package cn.breadnicecat.candycraftce.mixin;

import net.minecraft.client.model.TridentModel;
import net.minecraft.client.renderer.entity.ThrownTridentRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

/**
 * Created in 2024/7/3 上午2:02
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 *
 * <p>
 **/
@Mixin(ThrownTridentRenderer.class)
public interface ThrownTridentRendererAccessor {
	@Mutable
	@Accessor
	void setModel(TridentModel model);
}
