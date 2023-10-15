package cn.breadnicecat;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;

/**
 * Created in 2023/8/24 10:49
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 */

//@AT.A
//@AT.B
@AT.AB
class AT {
	public static void main(String[] args) {
		Arrays.stream(AT.class.getAnnotations()).forEach(System.out::println);
		System.out.println(AT.class.isAnnotationPresent(A.class));
	}

	@Retention(RetentionPolicy.RUNTIME)
	public @interface A {
	}

	@Retention(RetentionPolicy.RUNTIME)
	public @interface B {
	}

	@A
	@B
	@Retention(RetentionPolicy.RUNTIME)
	public @interface AB {
	}
}
