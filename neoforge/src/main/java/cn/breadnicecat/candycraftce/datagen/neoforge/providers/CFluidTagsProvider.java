package cn.breadnicecat.candycraftce.datagen.neoforge.providers;

import cn.breadnicecat.candycraftce.CandyCraftCE;
import cn.breadnicecat.candycraftce.block.FlowingFluidEntry;
import cn.breadnicecat.candycraftce.utils.SimpleEntry;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.FluidTagsProvider;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.concurrent.CompletableFuture;

import static cn.breadnicecat.candycraftce.utils.CommonUtils.accept;
import static cn.breadnicecat.candycraftce.utils.CommonUtils.check;

/**
 * Created in 2024/4/5 下午11:46
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 * <p>
 */
public class CFluidTagsProvider extends FluidTagsProvider {
	public CFluidTagsProvider(PackOutput arg, CompletableFuture<HolderLookup.Provider> completableFuture, @Nullable ExistingFileHelper existingFileHelper) {
		super(arg, completableFuture, CandyCraftCE.MOD_ID, existingFileHelper);
	}
	
	@Override
	protected void addTags(HolderLookup.@NotNull Provider provider) {
//		add(WATER, CARAMEL);
	}
	
	private final Map<TagKey<?>, Set<?>> validator = new HashMap<>();
	
	private void add(TagKey<Fluid> tagKey, SimpleEntry<Fluid, ?>... fe) {
		if (fe.length == 0) return;
		HashSet<SimpleEntry<Fluid, ?>> set = new HashSet<>();
		check(validator.put(tagKey, set) == null, () -> "Duplicate query tag: " + tagKey);
		IntrinsicTagAppender<Fluid> tag = tag(tagKey);
		accept(i -> {
			check(set.add(i), () -> "Duplicate block: " + i + " in Tag: " + tagKey);
			tag.add(i.get());
		}, fe);
	}
	
	@SuppressWarnings({"unchecked", "rawtypes"})
	private void add(TagKey<Fluid> tagKey, FlowingFluidEntry<?, ?>... fe) {
		if (fe.length == 0) return;
		ArrayList<SimpleEntry<Fluid, ?>> fes = new ArrayList<>(fe.length * 2);
		for (FlowingFluidEntry<?, ?> entry : fe) {
			fes.add((SimpleEntry) entry.getSource());
			fes.add((SimpleEntry) entry.getFlowing());
		}
		add(tagKey, fes.toArray(SimpleEntry[]::new));
	}
}
