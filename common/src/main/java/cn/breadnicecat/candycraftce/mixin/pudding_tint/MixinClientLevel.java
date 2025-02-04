package cn.breadnicecat.candycraftce.mixin.pudding_tint;

import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import net.minecraft.client.color.block.BlockTintCache;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.ColorResolver;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Supplier;

import static cn.breadnicecat.candycraftce.misc.PuddingColor.PUDDING_COLOR_RESOLVER;

/**
 * Created in 2024/7/7 上午12:43
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 * required
 * <p>
 **/
@Mixin(ClientLevel.class)
public abstract class MixinClientLevel {
	@Shadow
	@Final
	private Object2ObjectArrayMap<ColorResolver, BlockTintCache> tintCaches;
	
	@Shadow
	public abstract int calculateBlockTint(BlockPos blockPos, ColorResolver colorResolver);
	
	@Inject(method = "<init>", at = @At("TAIL"))
	private void init(ClientPacketListener connection, ClientLevel.ClientLevelData clientLevelData, ResourceKey<?> dimension, Holder<?> dimensionType, int viewDistance, int serverSimulationDistance, Supplier<?> profiler, LevelRenderer levelRenderer, boolean isDebug, long biomeZoomSeed, CallbackInfo ci) {
		tintCaches.put(PUDDING_COLOR_RESOLVER, new BlockTintCache((blockPos) -> calculateBlockTint(blockPos, PUDDING_COLOR_RESOLVER)));
	}
}
