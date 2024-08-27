package cn.breadnicecat.candycraftce.item.forge;

import cn.breadnicecat.candycraftce.entity.EntityEntry;
import cn.breadnicecat.candycraftce.item.CItemBuilder;
import cn.breadnicecat.candycraftce.item.ItemEntry;
import cn.breadnicecat.candycraftce.sound.SoundEntry;
import cn.breadnicecat.candycraftce.utils.TickUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.common.ForgeSpawnEggItem;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Supplier;

import static cn.breadnicecat.candycraftce.item.CItems._SPAWN_EGG_TRANS_KEY;

/**
 * Created in 2023/12/29 23:55
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 */
public class CItemsImpl {
	public static RecordItem _recordItem(int analog, @NotNull SoundEntry evt, Item.Properties prop, int lengthInSeconds) {
		return new RecordItem(analog, evt, prop, (int) (lengthInSeconds * TickUtils.SEC2TICK));
	}
	
	public static RecordItem _record_o(int analog, SoundEntry evt, Item.Properties prop, int lengthInSeconds) {
		MutableComponent name = Component.literal("Bread_NiceCat's Secret Record");
		MutableComponent music = Component.literal("J.D.K. - 鉄橋を越えて");
		MutableComponent appendix = Component.literal("To our lost childhood.").withStyle(ChatFormatting.AQUA);
		
		return new RecordItem(analog, evt, prop, (int) (lengthInSeconds * TickUtils.SEC2TICK)) {
			@Override
			public @NotNull MutableComponent getDisplayName() {
				return music;
			}
			
			@Override
			public @NotNull Component getName(@NotNull ItemStack stack) {
				return name;
			}
			
			@Override
			public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, @NotNull List<Component> tooltipComponents, @NotNull TooltipFlag isAdvanced) {
				super.appendHoverText(stack, level, tooltipComponents, isAdvanced);
				tooltipComponents.add(appendix);
			}
		};
	}
	
	public static @NotNull Supplier<ItemEntry<SpawnEggItem>> _spawn_egg(EntityEntry<? extends Mob> entity, int backgroundColor, int highlightColor, @Nullable Item.Properties properties) {
		return () -> {
			CItemBuilder<SpawnEggItem> builder = CItemBuilder.create(entity.getName() + "_spawn_egg", (p) -> new ForgeSpawnEggItem(entity, backgroundColor, highlightColor, p) {
				@Override
				public @NotNull Component getName(@NotNull ItemStack stack) {
					return Component.translatable(_SPAWN_EGG_TRANS_KEY, I18n.get(entity.get().getDescriptionId()));
				}
			});
			if (properties != null) builder.setProperties(properties);
			return builder.save();
		};
	}
	
	public static MobBucketItem _mob_bucket(Supplier<EntityType<? extends Mob>> entityType, Supplier<Fluid> fluid, Supplier<SoundEvent> empty, Item.Properties p) {
		return new MobBucketItem(entityType, fluid, empty, p);
	}
	
}
