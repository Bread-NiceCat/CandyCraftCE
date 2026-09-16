package cn.breadnicecat.candycraftce.item.items;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import java.util.List;

public class EmblemItem extends Item {
	private final String name;

	public EmblemItem(String name, Properties properties) {
		super(properties);
		this.name=name;
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flags) {
		tooltip.add(Component.translatable("tooltip.candycraftce." + name).withStyle(ChatFormatting.AQUA));
		tooltip.add(Component.translatable("tooltip.candycraftce.emblem").withStyle(ChatFormatting.GREEN));
	}
}