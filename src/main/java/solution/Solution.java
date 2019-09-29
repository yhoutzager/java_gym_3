package solution;

import noedit.Data;
import noedit.Registers;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * This solution represents a special CPU cache manager. It can store and lookup data.
 *
 * It should work as follows:
 * <ol>
 * <li>When storing a value, it should go into the first register.
 * <li>If a register is full, the oldest item is pushed to the next register (which may in turn 'overflow').
 * <li>When looking up an item, if the item is in cache, the register number is returned. If it is not in cache
 * (either because it was never encountered, or because it dropped out of the last register), then an empty
 * optional is returned.
 * </ol>
 *
 * Operations have to be fast for large numbers of items (linear at worst). Bonus points for minimizing temporary (heap) data.
 *
 * (This is not really how CPU cache works).
 */
public class Solution {

    @Nonnull
    private final Registers registers;
    private List<CachedTree> cachedTrees;

    public Solution(@Nonnull Registers registers) {
        this.registers = registers;
        cachedTrees = new ArrayList<>();
        cachedTrees.add(new CachedTree(0));
    }

    /**
     * Store the item into the first register, overflowing as necessary.
     *
     * @implNote The maximum worst-case complexity is logarithmic {@code (O(log(n)))}.
     */
    @Nonnull
    public void store(@Nonnull Data storeItem) {
        cachedTrees.get(0).add(storeItem.hashCode());
    }

    /**
     * Return the register that an item if cached in, or empty if not cached.
     *
     * @implNote The maximum complexity is linear {@code (O(n))}.
     */
    @Nonnull
    public Optional<Integer> lookup(@Nonnull Data searchItem) {
        for (CachedTree cachedRegister : cachedTrees) {
            if (cachedRegister.containsValue(searchItem.hashCode())) {
                return Optional.of(cachedRegister.number);
            }
        }
        return Optional.empty();
    }

    /**
     * Queue voor het bijhouden van het oudste item.
     * binary-search-tree voor lookup?
     */
}
