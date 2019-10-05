package solution;

import noedit.Registers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Cache {

	List<CachedTree> cachedForest = new ArrayList<>();
	Registers registers;

	Cache(Registers registers) {
		this.registers = registers;
		cachedForest.add(new CachedTree(0));
	}

	public void add(int value) {
		// Delete the value from a register if it is already present.
		Optional<Integer> register = containsValue(value);
		register.ifPresent(integer -> cachedForest.get(integer).delete(value));

		// Add value to first register.
		cachedForest.get(0).add(value);
		// Move values over when a register is full.
		int i = 0;
		while (i < cachedForest.size()) {
			if (cachedForest.get(i).size <= registers.registerSize(i)) {
				break;
			} else if (cachedForest.size() == i + 1) {
				if (registers.registerCount() == i + 1) {
					throw new IllegalStateException();
				}
				cachedForest.add(new CachedTree(i + 1));
			}
			CachedNode latest = cachedForest.get(i).oldest;
			cachedForest.get(i).deleteOldest(latest.value);
			cachedForest.get(i + 1).add(latest.value);
			i++;
		}
	}

	public Optional<Integer> containsValue(int value) {
		for (CachedTree tree : cachedForest) {
			if (tree.containsValue(value)) {
				return Optional.of(tree.number);
			}
		}
		return Optional.empty();
	}
}
