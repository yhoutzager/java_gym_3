package solution;

import static java.lang.Math.max;

public class CachedTree {

	CachedNode root;
	CachedNode oldest;
	CachedNode newest;
	int size;
	int number;

	CachedTree(int number) {
		size = 0;
		this.number = number;
	}

	private CachedNode addRecurisive(CachedNode current, int value) {
		if (current == null) {
			CachedNode newNode = new CachedNode(value);
			if (newest != null) {
				newest.next = newNode;
			} else {
				oldest = newNode;
			}
			newest = newNode;
			current = newNode;
		}

		if (value < current.value) {
			current.left = addRecurisive(current.left, value);
			// TODO: 05-10-19 Balance
			if (height(current.left) - height(current.right) == 2)
				if (value < current.left.value)
					current = rotateWithLeftChild(current);
				else
					current = doubleWithLeftChild(current);
		} else if (value > current.value) {
			current.right = addRecurisive(current.right, value);
			if (height(current.right) - height(current.left) == 2)
				if (value > current.right.value)
					current = rotateWithRightChild(current);
				else
					current = doubleWithRightChild(current);
		}
		current.height = max(height(current.left), height(current.right)) + 1;
		return current;
	}

	private CachedNode rotateWithLeftChild(CachedNode k2) {
		CachedNode k1 = k2.left;
		k2.left = k1.right;
		k1.right = k2;
		k2.height = max(height(k2.left), height(k2.right)) + 1;
		k1.height = max(height(k1.left), k2.height) + 1;
		return k1;
	}

	private CachedNode rotateWithRightChild(CachedNode k1) {
		CachedNode k2 = k1.right;
		k1.right = k2.left;
		k2.left = k1;
		k1.height = max(height(k1.left), height(k1.right)) + 1;
		k2.height = max(height(k2.right), k1.height) + 1;
		return k2;
	}

	private CachedNode doubleWithLeftChild(CachedNode k3) {
		k3.left = rotateWithRightChild(k3.left);
		return rotateWithLeftChild(k3);
	}

	private CachedNode doubleWithRightChild(CachedNode k1) {
		k1.right = rotateWithLeftChild(k1.right);
		return rotateWithRightChild(k1);
	}

	private int height(CachedNode node) {

		return node == null ? -1 : node.height;
	}

	public void add(int value) {
		root = addRecurisive(root, value);
		size += 1;
	}

	private boolean containsValueRecursive(CachedNode current, int value) {
		if (current == null) {
			return false;
		}
		if (value == current.value) {
			return true;
		}

		return value < current.value
				? containsValueRecursive(current.left, value)
				: containsValueRecursive(current.right, value);
	}

	public boolean containsValue(int value) {
		return containsValueRecursive(root, value);
	}

	private CachedNode deleteRecursive(CachedNode current, int value) {
		// Height en oldest
		if (current == null) {
			return null;
		}

		if (value == current.value) {
			if (current.left == null && current.right == null) {
				return null;
			}
			if (current.right == null) {
				return current.left;
			}
			if (current.left == null) {
				return current.right;
			}

			int smallestValue = findSmallestValue(current.right);
			current.value = smallestValue;
			current.right = deleteRecursive(current.right, smallestValue);
			return current;
		}
		if (value < current.value) {
			current.left = deleteRecursive(current.left, value);
		} else {
			current.right = deleteRecursive(current.right, value);
		}
		return current;
	}

	private int findSmallestValue(CachedNode root) {
		return root.left == null ? root.value : findSmallestValue(root.left);
	}

	public void delete(int value) {
		root = deleteRecursive(root, value);
		size--;
	}

	public void deleteOldest(int value) {
		if (oldest.value != value) {
			throw new IllegalStateException();
		}

		oldest = oldest.next;
		delete(value);
	}


}
