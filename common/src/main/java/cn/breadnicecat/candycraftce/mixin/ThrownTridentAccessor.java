package cn.breadnicecat.candycraftce.mixin;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.world.entity.projectile.ThrownTrident;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

/**
 * Created in 2024/7/3 下午1:58
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 *
 * <p>
 **/
@Mixin(ThrownTrident.class)
public interface ThrownTridentAccessor {
	@Accessor
	static EntityDataAccessor<Byte> getID_LOYALTY() {
		throw new UnsupportedOperationException();
	}
	
	@Accessor
	static EntityDataAccessor<Boolean> getID_FOIL() {
		throw new UnsupportedOperationException();
	}
}
