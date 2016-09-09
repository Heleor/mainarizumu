package constraints;

import java.util.HashSet;
import java.util.Set;

import creator.Cell;
import creator.Sets;

/**
 * The values in the set must range from 1 .. values.size()
 */
public class SetConstraint implements Constraint {
	final String name;
	final Set<Cell> values;
	
	public SetConstraint(String name, Set<Cell> values) {
		this.name = name;
		this.values = values;
	}
	
	public void draw(char[][] board) {
		// Do nothing.
	}
	
	public int apply() {
		int changes = 0;
		
		// Find all remaining values for this row.
		Set<Cell> unknowns = new HashSet<>();
		Set<Integer> knowns = new HashSet<>();
		int known = 0;
		for (Cell c : values) {
			if (c.known()) {
				known++;
				knowns.add(c.value());
				for (Cell a : values) {
					if (!a.known()) {
						changes += a.removePossibility(c.value());
					}
				}				
			} else {
				unknowns.add(c);
			}
		}
		
		if (known == values.size() && knowns.size() != values.size()) {
			throw new RuntimeException("Contradiction in " + name);
		}
		
		// Find any pairs of equal sets.
		for (Cell a : unknowns) {
			Set<Integer> aOptions = a.options();
			if (aOptions.size() == 2) {
	 			for (Cell b : unknowns) {
	 				if (a == b) continue;
 					if (aOptions.equals(b.options())) {
						for (Cell c : unknowns) {
							if (c != a && c != b) {
								for (int i : aOptions) {
									changes += c.removePossibility(i);
								}
							}
						}
					}
				}
			}
		}
		
		return changes;
	}
}