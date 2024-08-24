package cn.breadnicecat.candycraftce.datagen.neoforge.providers;

import cn.breadnicecat.candycraftce.utils.tools.Accessor;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

/**
 * Created in 2024/2/18 17:02
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 * <p>
 */
public class CTerminalStateProvider implements DataProvider {
	private final Accessor<Boolean> state;
	
	public CTerminalStateProvider(Accessor<Boolean> state) {
		this.state = state;
	}
	
	@Override
	public @NotNull CompletableFuture<?> run(@NotNull CachedOutput output) {
		state.set(true);
		return CompletableFuture.completedFuture(state);
	}
	
	@Override
	public @NotNull String getName() {
		return "Terminal State Provider";
	}
}
