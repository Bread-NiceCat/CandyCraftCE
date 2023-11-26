package cn.test;

import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * Created in 2023/4/15 10:22
 * Project: fantasticfighting
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 */
public class UnmodifiableBNodes<K, V> implements Iterable<UnmodifiableBNodes.Node<K, V>> {

	private final Comparator<? super K> comparator;
	private final List<Node<K, V>> nodes;

	public UnmodifiableBNodes(Map<K, ? extends V> map, Comparator<? super K> comparator) {
		this.comparator = Objects.requireNonNull(comparator);
		ArrayList<Node<K, V>> nodes = new ArrayList<>(map.size());
		for (Map.Entry<K, ? extends V> entry : map.entrySet()) {
			nodes.add(new Node<>(entry.getKey(), entry.getValue(), comparator));
		}
		nodes.sort(null);
		Node<K, V> previous = null;
		for (Node<K, V> node : nodes) {
			if (previous != null) {
				node.link(previous);
			}
			previous = node;
		}
		this.nodes = Collections.unmodifiableList(nodes);
	}

	public UnmodifiableBNodes(SortedMap<K, ? extends V> map) {
		this(map, map.comparator());
	}


	public Comparator<? super K> comparator() {
		return comparator;
	}

	public Node<K, V> floor(K k) {
		if (isEmpty()) return null;
		int from = 0, to = nodes.size() - 1;

		Node<K, V> node = nodes.get(from);
		int status = node.compareToKey(k);
		//this?o -> this-o?0 -> >0 =>this>o|| <0 =>this<o
		//判断是否在范围内
		if (status > 0) {//head>key 在范围前
			return null;
		} else if (status == 0) {//刚刚好
			return node;
		}

		node = nodes.get(to);
		status = node.compareToKey(k);
		if (status < 0) {//tail<key 越过范围
			return node;//返回tail
		} else if (status == 0) {//刚刚好
			return node;
		}
		//开始二分判断
		int current;
		while (to - from > 1) {//当中间没有Node后
			current = from + (to - from) / 2;
			node = nodes.get(current);
			status = node.compareToKey(k);
			if (status > 0) {//[current]>key
				to = current;
			} else if (status < 0) {
				from = current;
			} else {
				return node;
			}
		}
		return nodes.get(from);
	}

	public Node<K, V> get(K k) {
		Node<K, V> floor = floor(k);
		return floor.compareToKey(k) == 0 ? floor : null;
	}

	public int size() {
		return nodes.size();
	}

	public boolean isEmpty() {
		return size() == 0;
	}

	@Override
	public String toString() {
		return nodes.toString();
	}

	@NotNull
	@Override
	public ListIterator<Node<K, V>> iterator() {
		return nodes.listIterator();
	}

	public static class Node<K, V> implements Map.Entry<K, V>, Comparable<Node<K, V>> {

		private final K key;
		private final V value;
		private final Comparator<? super K> comparator;
		private Node<K, V> previous, next;

		private Node(K key, V value, Comparator<? super K> comparator) {
			this.key = Objects.requireNonNull(key);
			this.value = value;
			this.comparator = Objects.requireNonNull(comparator);
		}

		private void link(Node<K, V> previous) {
			previous.next = this;
			this.previous = previous;
		}

		@Override
		public K getKey() {
			return key;
		}

		@Override
		public V getValue() {
			return value;
		}

		public Node<K, V> getPrevious() {
			return previous;
		}

		public Node<K, V> getNext() {
			return next;
		}

		public boolean hasNext() {
			return next != null;
		}

		public boolean hasPrevious() {
			return previous != null;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;

			Node<?, ?> node = (Node<?, ?>) o;

			if (!key.equals(node.key)) return false;
			return value.equals(node.value);
		}

		@Override
		public int hashCode() {
			int result = key.hashCode();
			result = 31 * result + value.hashCode();
			return result;
		}

		@Override
		public String toString() {
			return (key == this ? "(this node)" : key) + "->" + (value == this ? "(this node)" : value);
		}

		@Override
		public V setValue(V value) {
			throw new UnsupportedOperationException();
		}

		@Override
		public int compareTo(@NotNull Node<K, V> o) {
			return comparator.compare(key, o.key);
		}

		public int compareToKey(@NotNull K o) {
			return comparator.compare(key, o);
		}

		public boolean equalsToKey(K key) {
			return this.key.equals(key);
		}
	}

}
