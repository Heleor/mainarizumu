package creator;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import constraints.DifferenceConstraint;
import constraints.LessThanConstraint;

public class Creator {
	private static final int SIZE = 6;
	
	public Creator() {
		this.tries = new HashMap<>();
		this.completes = new HashSet<>();
	}
	
	Map<String, Integer> tries;
	Set<String> completes;
	
	private Board board() {
		Board board = new Board(SIZE);
		board.addInitialConstraints();
		return board;
	}
	
	private void lt(int r, int c, int r2, int c2) {
		Board b = board();
		b.solve();
		b.addConstraint(new LessThanConstraint(
				b.get(r, c), 
				b.get(r2, c2)));
		int cnt = b.solve();
		String s = "< " + r + "," + c + "," + r2 + "," + c2;
		tries.put(s, cnt);
		if (b.complete()) completes.add(s);
	}
	
	private void d(int r, int c, int r2, int c2, int v) {
		Board b = board();
		b.solve();
		b.addConstraint(new DifferenceConstraint(
				b.get(r, c), 
				b.get(r2, c2), v));
		int cnt = b.solve();
		String s = "Difference " + r + "," + c + "," + r2 + "," + c2 +":" + v;
		tries.put(s, cnt);
		if (b.complete()) completes.add(s);
	}
	
	public void solve() {
		// try all <'s
		for (int i = 0; i < SIZE - 1; i++) {
			for (int j = 0; j < SIZE - 1; j++) {
				try {
					lt(i,j,i + 1,j);
				} catch (RuntimeException e) {}
				try {
					lt(i,j,i,j +1);
				} catch (RuntimeException e) {}
				try {
					lt(i + 1,j,i,j);
				} catch (RuntimeException e) {}
				try {
					lt(i,j + 1,i,j);
				} catch (RuntimeException e) {}
				for (int d = 1; d <= SIZE - 1; d++) {
					try {
						d(i, j, i, j + 1, d);
					} catch (RuntimeException e) {}
					try {
						d(i, j, i + 1, j, d);
					} catch (RuntimeException e) {}
				}
			}
		}
		
		
		order();
	}
	
	private void order() {
		String max = null;
		int maxValue = 0;
		for (Entry<String, Integer> e : tries.entrySet()) {
			if (e.getValue() > maxValue) {
				max = e.getKey();
				maxValue = e.getValue();
			}
		}
		System.out.println("Max is " + maxValue + " with " + max);
		if (completes.contains(max)) {
			System.out.println("Puzzle was solved!");
		}
	}
}
