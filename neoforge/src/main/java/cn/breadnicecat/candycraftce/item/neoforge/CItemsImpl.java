package cn.breadnicecat.candycraftce.item.neoforge;

import cn.breadnicecat.candycraftce.entity.EntityEntry;
import cn.breadnicecat.candycraftce.item.CItemBuilder;
import cn.breadnicecat.candycraftce.item.ItemEntry;
import cn.breadnicecat.candycraftce.level.PuddingColor;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.*;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

import static cn.breadnicecat.candycraftce.item.CItems.I18_SPAWN_EGG;

/**
 * Created in 2023/12/29 23:55
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 * <p>
 */
@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class CItemsImpl {
	public static Item _recordItem(ResourceKey<JukeboxSong> jukeSong, Item.Properties prop) {
//		return new RecordItem(analog, evt, prop, (int) (lengthInSeconds * TickUtils.SEC2TICK));
		return new Item(prop.jukeboxPlayable(jukeSong));
	}
	
	public static @NotNull Supplier<ItemEntry<SpawnEggItem>> _spawn_egg(EntityEntry<? extends Mob> entity, int backgroundColor, int highlightColor, @Nullable Item.Properties properties) {
		return () -> {
			CItemBuilder<SpawnEggItem> builder = CItemBuilder.create(entity.getName() + "_spawn_egg", (p) -> new DeferredSpawnEggItem(entity, backgroundColor, highlightColor, p) {
				private Component name;
				
				@Override
				public @NotNull Component getName(@NotNull ItemStack stack) {
					return name == null ? name = Component.translatable(I18_SPAWN_EGG, I18n.get(entity.get().getDescriptionId())) : name;
				}
			});
			if (properties != null) builder.setProperties(properties);
			return builder.save();
		};
	}
	
	public static MobBucketItem _mob_bucket(Supplier<EntityType<? extends Mob>> entityType, Supplier<Fluid> fluid, Supplier<SoundEvent> empty, Item.Properties p) {
		return new MobBucketItem(entityType.get(), fluid.get(), empty.get(), p);
	}
	
	@SubscribeEvent
	public static void onItemColor(RegisterColorHandlersEvent.Item event) {
		PuddingColor._registerItemColors(event.getBlockColors(), event.getItemColors());
	}
	
}
