package constraints;

import creator.Cell;

public abstract class TwoCellConstraint implements Constraint {
	final Cell a;
	final Cell b;
	
	public TwoCellConstraint(Cell a, Cell b) {
		this.a = a;
		this.b = b;
	}
	
	public boolean overlaps(Constraint old) {
		if (old instanceof TwoCellConstraint) {
			TwoCellConstraint other = (TwoCellConstraint) old;

			if (other.a == a && other.b == b) {
				return true;
			}
			if (other.b == a && other.a == b) {
				return true;
			}
		}
		return false;
	}
}
