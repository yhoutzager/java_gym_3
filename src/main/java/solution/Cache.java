package solution;

import noedit.Registers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Cache {

	List<MapTree> mapForest = new ArrayList<>();
	Registers registers;

	Cache(Registers registers) {
		this.registers = registers;
		for (int i = 0; i < registers.registerCount(); i++) {
			mapForest.add(new MapTree(i));
		}
	}

	public void add(int value) {
		// Delete the value from a register if it is already present.
		containsValue(value).ifPresent(integer -> mapForest.get(integer).delete(value));

		// Add value to first register.
		mapForest.get(0).add(value);
		// Move values over when a register is full.
		int i = 0;
		while (i < mapForest.size()) {
			if (mapForest.get(i).size() <= registers.registerSize(i)) {
				break;
			}

			Node movedNode = mapForest.get(i).deleteOldest();
			if (mapForest.size() > i + 1) {
				mapForest.get(i + 1).add(movedNode);
			}
			i++;
		}
	}

	public Optional<Integer> containsValue(int value) {
		for (MapTree tree : mapForest) {
			if (tree.containsValue(value)) {
				return Optional.of(tree.index);
			}
		}
		return Optional.empty();
	}
}
