package solution;

public class CachedNode {
	int value;
	CachedNode left;
	CachedNode right;
	CachedNode next;
	int height;

	CachedNode(int value) {
		this.value = value;
		left = null;
		right = null;
		next = null;
		height = 0;
	}


}
