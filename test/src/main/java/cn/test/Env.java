package cn.test;

/**
 * Created in 2023/11/25 18:00
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 * <p>
 */
public class Env {
	public static void main(String[] args) {
		System.out.println(Env.class.getResource("").getProtocol());
	}
}
