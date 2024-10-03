package cn.breadnicecat.candycraftce.entity.renderers;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.SlimeRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Slime;
import org.jetbrains.annotations.NotNull;

import static cn.breadnicecat.candycraftce.utils.ResourceUtils.entityTex;

/**
 * Created in 2024/10/3 15:51
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 *
 * <p>
 **/
public abstract class RendererJelly extends SlimeRenderer {
	public RendererJelly(EntityRendererProvider.Context context) {
		super(context);
	}
	
	public static @NotNull RendererJelly create(EntityRendererProvider.Context context, ResourceLocation tex) {
		return new RendererJelly(context) {
			@Override
			public @NotNull ResourceLocation getTextureLocation(Slime entity) {
				return tex;
			}
		};
	}
	
	@Override
	public abstract @NotNull ResourceLocation getTextureLocation(Slime entity);
	
	public static RendererJelly createMint(EntityRendererProvider.Context context) {
		return create(context, entityTex("jelly/mint"));
	}
	
	public static RendererJelly createStrawberry(EntityRendererProvider.Context context) {
		return create(context, entityTex("jelly/strawberry"));
	}
	
	public static RendererJelly createSeaBanana(EntityRendererProvider.Context context) {
		return create(context, entityTex("jelly/sea_banana"));
	}
}
