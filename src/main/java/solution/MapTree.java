package solution;

import java.util.HashMap;

class Node {

	Node previous;
	Node next;
	int value;

	Node(Node previous, int value) {
		this.previous = previous;
		this.value = value;
	}

	public void setNext(Node next) {
		this.next = next;
	}
}


public class MapTree{

	private HashMap<Integer, Node> hashMap;
	public Node oldest;
	public Node newest;
	public int index;

	MapTree(int index) {
		hashMap = new HashMap<>();
		this.index = index;
	}

	public void add(int value) {
		Node node = new Node(oldest, value);
		add(node);
	}

	public void add(Node node) {
		if (newest == null) {
			newest = node;
		} else {
			newest.setNext(node);
			newest = node;
		}
		if (oldest == null) {
			oldest = node;
		}
		hashMap.put(node.value, node);
	}

	public boolean containsValue(int value) {
		return hashMap.containsKey(value);
	}

	public Node delete(int value) {
		return hashMap.remove(value);
	}

	public Node deleteOldest() {
		Node removed = delete(oldest.value);
		oldest = removed.next;
		return removed;
	}

	public int size() {
		return hashMap.size();
	}
}
