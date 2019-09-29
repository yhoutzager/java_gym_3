package solution;

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
			return newest;
		}

		if (value < current.value) {
			current.left = addRecurisive(current.left, value);
		} else if (value > current.value) {
			current.right = addRecurisive(current.right, value);
		}
		return current;
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
	}
}
