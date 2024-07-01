package cn.breadnicecat.candycraftce.mixin;

import cn.breadnicecat.candycraftce.entity.CEntityBuilder;
import com.google.common.collect.ImmutableMap;
import net.minecraft.client.model.geom.LayerDefinitions;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Map;

/**
 * Created in 2024/2/25 11:47
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 * <p>
 */
@Mixin(LayerDefinitions.class)
public abstract class MixinLayerDefinitions {
	
	@Inject(method = "createRoots",
			at = @At(value = "INVOKE", target = "Lcom/google/common/collect/ImmutableMap$Builder;build()Lcom/google/common/collect/ImmutableMap;", remap = false),
			locals = LocalCapture.CAPTURE_FAILEXCEPTION)
	private static void createRoots(CallbackInfoReturnable<Map<ModelLayerLocation, LayerDefinition>> info, ImmutableMap.Builder<ModelLayerLocation, LayerDefinition> builder) {
		CEntityBuilder._createRoots(builder);
	}
}
