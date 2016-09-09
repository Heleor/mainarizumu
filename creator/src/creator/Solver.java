package creator;

import constraints.Constraint;
import constraints.DifferenceConstraint;
import constraints.LessThanConstraint;

public class Solver {
	private static final int SIZE = 6;
	
	final Board board;
	
	public Solver() {
		this.board = new Board(SIZE);
	}
	
	private Constraint lt(int r, int c, int r2, int c2) {
		return new LessThanConstraint(
				board.get(r, c), 
				board.get(r2, c2));
	}
	
	private Constraint d(int r, int c, int r2, int c2, int v) {
		return new DifferenceConstraint(
				board.get(r, c), 
				board.get(r2, c2), v);
	}
	
	public void solve() {
//		board.addConstraint(lt(0,0,0,1));
//		board.addConstraint(lt(1,2,1,3));
//		board.addConstraint(lt(2,1,2,2));
//		board.addConstraint(lt(3,3,3,2));
//		
//		board.addConstraint(lt(2,2,1,2));
//		board.addConstraint(lt(3,2,2,2));
//		
//		board.addConstraint(d(3,0,3,1,2));
//		
//		board.get(0, 1).constrain(Sets.single(3));
		board.addInitialConstraints();
		
		board.print();
		board.solve();
		board.debugPrint();
		board.print();
	}
}
