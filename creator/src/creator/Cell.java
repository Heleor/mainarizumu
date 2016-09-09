package creator;

import java.util.HashSet;
import java.util.Set;

// row, col are 0-based
// options are 1-size
public class Cell {
	public final int size;
	public final int row;
	public final int col;
	
	private final Set<Integer> options;
	private int value;
	
	public Cell(int size, int row, int col) {
		this.size = size;
		this.row = row;
		this.col = col;
		
		this.options = Sets.options(size);
	}
	
	// Copy accessor.
	public Set<Integer> options() {
		return new HashSet<>(options);
	}
	
	public boolean known() {
		if (options.size() == 1) {
			value = options.iterator().next(); 
		}
		return options.size() == 1;
	}
	
	public int value() {
		if (!known()) {
			throw new RuntimeException(toString() + " is unknown");
		}
		return value;
	}
	
	public int removePossibility(int value) {
		if (!options.contains(value)) {
			return 0;
		}
		options.remove(value);
		if (options.isEmpty()) {
			throw new RuntimeException(toString() + " has no valid values.");
		}
		return 1;
	}
	
	public int constrain(Set<Integer> constraint) {
		int changes = 0;
		for (int i : options()) {
			if (!constraint.contains(i)) {
				changes += removePossibility(i);
			}
		}
		return changes;
	}
	
	public String toString() {
		return "Cell [ c:" + col + ", r:" + row + " ]";
	}

	public char charValue() {
		if (!known()) {
			return ' ';
		} else {
			return (char) (value + '0');
		}
	}
}
