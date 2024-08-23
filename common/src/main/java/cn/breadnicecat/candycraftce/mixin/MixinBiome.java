package cn.breadnicecat.candycraftce.mixin;

import cn.breadnicecat.candycraftce.block.PuddingColor;
import cn.breadnicecat.candycraftce.level.CDims;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

/**
 * Created in 2024/8/21 上午1:15
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 * 奶皮布丁的颜色
 * <p>
 **/
@Mixin(Biome.class)
public abstract class MixinBiome {
	@Shadow
	public abstract BiomeSpecialEffects getSpecialEffects();
	
	@Inject(method = "getGrassColor", at = @At("HEAD"), cancellable = true)
	void getGrassColor(double posX, double posZ, CallbackInfoReturnable<Integer> cir) {
		//当不在糖果世界的时候的时候禁用
		ClientLevel level = Minecraft.getInstance().level;
		if (level != null && level.dimension() == CDims.CANDYLAND) {
			Optional<Integer> i = getSpecialEffects().getGrassColorOverride();
			if (i.isPresent()) {
				int color = i.get();
				//mcmod上面的id(8526)+index
				if (color == -85261) {
					//附魔森林
					cir.setReturnValue(PuddingColor.getEnchantColor(posX, posZ));
				}
			}
		}
	}
}
