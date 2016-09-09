package constraints;

import java.util.HashSet;
import java.util.Set;

import creator.Cell;

// abs(a - b) = difference
public class DifferenceConstraint extends TwoCellConstraint {
	final int difference;
	
	public DifferenceConstraint(Cell a, Cell b, int value) {
		super(a, b);
		this.difference = value;
	}
	
	private char charValue() {
		return (char) ('0' + difference);
	}
	
	public int apply() {
		if (a.known() && b.known()) {
			return 0;
		}

		int changes = 0;
		Set<Integer> aValues = new HashSet<>();
		Set<Integer> bValues = new HashSet<>();
		
		// Constrain a
		for (int value : b.options()) {
			if (value - difference > 0) {
				aValues.add(value - difference);
			}
			if (value + difference <= b.size) {
				aValues.add(value + difference);
			}
		}
		changes += a.constrain(aValues);
		
		// Constrain b
		for (int value : a.options()) {
			if (value - difference > 0) {
				bValues.add(value - difference);
			}
			if (value + difference <= a.size) {
				bValues.add(value + difference);
			}
		}
		changes += b.constrain(bValues);
		
		return changes;
	}
	
	public void draw(char[][] board) {
		if (a.row == b.row) {
			// Sanity check that they're next to each other.
			if (a.col == b.col - 1) {
				board[a.row * 2][a.col * 2 + 1] = charValue();
			}
			if (a.col == b.col + 1) {
				board[a.row * 2][a.col * 2 - 1] = charValue();
			}
		}
		
		if (a.col == b.col) {
			// Sanity check that they're next to each other.
			if (a.row == b.row - 1) {
				board[a.row * 2 + 1][a.col * 2] = charValue();
			}
			if (a.row == b.row + 1) {
				board[a.row * 2 - 1][a.col * 2] = charValue();
			}
		}
	}
}
