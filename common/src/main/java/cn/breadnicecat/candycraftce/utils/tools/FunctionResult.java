package cn.breadnicecat.candycraftce.utils.tools;

import com.mojang.datafixers.util.Either;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Created in 2023/7/2 21:10
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 */
public class FunctionResult<R> {
	private Either<R, Throwable> result;

	public FunctionResult(Either<R, Throwable> result) {
		this.result = result;
	}


	public static <R> FunctionResult<R> success(R result) {
		return new FunctionResult<>(Either.left(result));
	}

	public static <R> FunctionResult<R> error(String msg) {
		return new FunctionResult<>(Either.right(new RuntimeException(msg)));
	}

	public static <R> FunctionResult<R> error(Throwable throwable) {
		return new FunctionResult<>(Either.right(throwable));
	}

	public Either<R, Throwable> getResult() {
		return result;
	}

	public R orThrow() {
		return getResult().orThrow();
	}

	public R orThrow(Consumer<Throwable> consumer) {
		return orThrowDefault((c) -> {
			consumer.accept(c);
			return null;
		});
	}

	public R orThrowDefault(Function<Throwable, R> function) {
		return getResult().map(l -> l, function);
	}
}
