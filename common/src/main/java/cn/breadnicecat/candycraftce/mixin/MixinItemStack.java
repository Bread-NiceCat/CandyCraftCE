package cn.breadnicecat.candycraftce.mixin;

import cn.breadnicecat.candycraftce.item.CDataComponents;
import cn.breadnicecat.candycraftce.utils.TickUtils;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.PatchedDataComponentMap;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Created in 2024/10/3 21:10
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 *
 * <p>
 **/
@Mixin(ItemStack.class)
public abstract class MixinItemStack {
	@Shadow
	public abstract Item getItem();
	
	@Shadow
	@Nullable
	public abstract <T> T set(DataComponentType<? super T> component, @Nullable T value);
	
	@Inject(method = "<init>(Lnet/minecraft/world/level/ItemLike;ILnet/minecraft/core/component/PatchedDataComponentMap;)V", at = @At("TAIL"))
	void init(ItemLike item, int count, PatchedDataComponentMap components, CallbackInfo ci) {
		if (getItem() == Items.SUGAR) {
			set(CDataComponents.SUGAR_BURN_TIME.get(), TickUtils.TICK_PER_SEC);
		}
	}
}
