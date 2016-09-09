package creator;

import java.util.HashSet;
import java.util.Set;

import constraints.Constraint;
import constraints.DifferenceConstraint;
import constraints.LessThanConstraint;
import constraints.SetConstraint;
import constraints.TwoCellConstraint;

public class Board {
	private final boolean DEBUG = false;
	
	final int size;
	
	// cell[row][col];
	private final Cell[][] board;
	
	public Cell get(int r, int c) {
		return board[r][c];
	}
	
	private final Set<Constraint> constraints;
	
	public void addConstraint(TwoCellConstraint c) {
		for (Constraint old : constraints) {
			if (c.overlaps(old)) {
				throw new RuntimeException("This is not a valid constraint.");
			}
		}
		constraints.add(c);
	}
	
	public Board(int size) {
		this.size = size;
		
		this.board = new Cell[size][size];
		// Initialize the board.
		for (int r = 0; r < size; r++) {
			for (int c = 0; c < size; c++) {
				board[r][c] = new Cell(size, r, c);
			}
		}
		
		this.constraints = new HashSet<>();
		// Initialize the 'sudoku-constraints'
		for (int i = 0; i < size; i++) {
			Set<Cell> row = new HashSet<>();
			Set<Cell> col = new HashSet<>();
			for (int j = 0; j < size; j++) {
				row.add(board[i][j]);
				col.add(board[j][i]);
			}
			constraints.add(new SetConstraint("row " + i, row));
			constraints.add(new SetConstraint("col " + i, col));
		}
	}
	
	public void addInitialConstraints() {
		constraints.add(new DifferenceConstraint(board[1][0], board[1][1], 1));
		constraints.add(new DifferenceConstraint(board[1][1], board[1][2], 2));
		constraints.add(new DifferenceConstraint(board[1][2], board[1][3], 3));
		constraints.add(new DifferenceConstraint(board[1][3], board[1][4], 4));
		constraints.add(new DifferenceConstraint(board[1][4], board[1][5], 5));
		
		constraints.add(new DifferenceConstraint(board[4][2], board[4][3], 1));
		
		constraints.add(new LessThanConstraint(board[4][1], board[4][0]));
		constraints.add(new LessThanConstraint(board[4][2], board[4][1]));
		constraints.add(new DifferenceConstraint(board[4][2], board[4][3], 1));
		constraints.add(new LessThanConstraint(board[4][3], board[4][4]));
		constraints.add(new LessThanConstraint(board[4][4], board[4][5]));
		
		// Experiments
		constraints.add(new DifferenceConstraint(board[3][2], board[4][2], 5));
		constraints.add(new DifferenceConstraint(board[3][3], board[3][4], 3));
		constraints.add(new DifferenceConstraint(board[2][4], board[2][5], 2));
		constraints.add(new DifferenceConstraint(board[0][4], board[0][5], 3));
	}
	
	public boolean complete() {
		boolean complete = true;
		for (int r = 0; r < size; r++) {
			for (int c = 0; c < size; c++) {
				if (!board[r][c].known()) {
					complete = false;
				}
			}
		}
		return complete;
	}
	
	public int solve() {
		int sumChanges = 0;
		int changes = 0;
		do {
			sumChanges += changes;
			changes = 0;
			for (Constraint c : constraints) {
				int newChanges = c.apply();
				if (DEBUG && newChanges > 0) {
					System.out.println(c + " removed " + newChanges + " possibilities.");
				}
				changes += newChanges;
			}
			debugPrint();
		} while (changes > 0);
		return sumChanges;
	}
	
	public void debugPrint() {
		if (!DEBUG) {
			return;
		}
		System.err.println("---");
		for (int r = 0; r < size; r++) {
			for (int c = 0; c < size; c++) {
				if (!board[r][c].known()) { 
					System.err.print(board[r][c].options() + ",");
				} else {
					System.err.print(board[r][c].value() +",");
				}
			}
			System.err.println("");
		}
		
	}
	
	public void print() {
		char[][] board = new char[size * 2][size * 2];
		for (int r = 0; r < size; r++) {
			for (int c = 0; c < size; c++) {
				board[r * 2][c * 2] = this.board[r][c].charValue(); 
			}
		}
		
		for (Constraint c : constraints) {
			c.draw(board);
		}
		
		System.out.println("-----------");
		for (int i = 0; i < size * 2; i++) {
			System.out.println(board[i]);
		}
		System.out.println("-----------");
	}
}
