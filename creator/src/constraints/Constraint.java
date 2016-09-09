package constraints;

public interface Constraint {
	public void draw(char[][] board);
	
	public int apply();
}
