package constraints;

import java.util.Set;

import creator.Cell;

// a < b
public class LessThanConstraint extends TwoCellConstraint {
	public LessThanConstraint(Cell a, Cell b) {
		super(a, b);
	}

	public void draw(char[][] board) {
		if (a.row == b.row) {
			// Sanity check that they're next to each other.
			if (a.col == b.col - 1) {
				board[a.row * 2][a.col * 2 + 1] = '<';
			}
			if (a.col == b.col + 1) {
				board[a.row * 2][a.col * 2 - 1] = '>';
			}
		}
		
		if (a.col == b.col) {
			// Sanity check that they're next to each other.
			if (a.row == b.row - 1) {
				board[a.row * 2 + 1][a.col * 2] = '^';
			}
			if (a.row == b.row + 1) {
				board[a.row * 2 - 1][a.col * 2] = 'v';
			}
		}
	}

	// a < b
	public int apply() {
		if (a.known() && b.known()) {
			return 0;
		}
		
		int changes = 0;
		
		int max = 0;
		for (int value : b.options()) {
			if (value > max) {
				max = value;
			}
		}

		int min = Integer.MAX_VALUE;
		for (int value : a.options()) {
			if (value < min) {
				min = value;
			}
			if (value >= max) {
				changes++;
				a.removePossibility(value);
			}
		}
		
		for (int value : b.options()) {
			if (value <= min) {
				changes++;
				b.removePossibility(value);
			}
		}
		
		return changes;
	}
}
