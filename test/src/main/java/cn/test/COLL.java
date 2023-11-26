package cn.test;

import java.util.LinkedHashMap;
import java.util.Objects;
import java.util.Scanner;

/**
 * Created in 2023/8/25 23:39
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 */
public class COLL {
	public static void main(String[] args) {
		LinkedHashMap<Integer, Integer> map = new LinkedHashMap<>();
		Scanner scanner = new Scanner(System.in);
		int i = 0;
		System.out.println("======================");
		while (i != -1) {
			i = scanner.nextInt();
			map.put(i, i);
			System.out.println(map);
			UnmodifiableBNodes<Integer, Integer> nodes = new UnmodifiableBNodes<>(map, Integer::compareTo);
			System.out.println(nodes);
			for (UnmodifiableBNodes.Node<Integer, Integer> node : nodes) {
				if (!Objects.equals(node.getKey(), node.getValue())) {
					throw new IllegalStateException("FAIL");
				}
			}
			System.out.println("PASS");

		}
	}
}
