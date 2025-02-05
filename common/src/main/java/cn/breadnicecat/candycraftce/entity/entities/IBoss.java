package cn.breadnicecat.candycraftce.entity.entities;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

/**
 * Created in 2024/10/3 14:45
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 *
 * <p>
 **/
public interface IBoss {
	BossType getType();
	
	boolean shouldShowBar(Entity thisEntity, ServerLevel level, Player player);
	
	enum BossType {
		MiniBoss, Boss
	}
	
	enum BarType {
		BLACK, WHITE, RED, GREEN, BLUE, GREY;
		public static final int WEIGH = 256, HEIGHT = 14;
		
		public static BarType byTier(int tier) {
			return values()[tier];
		}
		
		public int getTier() {
			return ordinal();
		}
		
		public int getTexY() {
			return getTier() * HEIGHT;
		}
	}
	
}
