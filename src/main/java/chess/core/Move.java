package chess.core;

public class Move
{
	private static final int chessboardSize = 8;
	
	public int x1, y1, x2, y2;
	
	private final Team team;
	
	/**
	 * if this is true, the game will go back one step, playing team will change
	 */
	private boolean undooMove;
	
	/**
	 * Returns a normal Move
	 */
	public Move(int x1, int y1, int x2, int y2, Team team)
	{
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.team = team;
	}
	
	
	/**
	 * Returns a Undoo Move
	 */
	public Move() {
		this.undooMove = true;
		this.team = null;
		x1 = -1;
		x2 = -1;
		y1 = -1;
		y2 = -1;
	}
	
	
	/**
	 * Converts the Move to a static view, this is equivalent to "convertToTeam(Team.BLACK)"
	 * @return a new Move
	 */
	public Move makeStatic() {
		return this.convertToTeam(Team.BLACK);
		
	}
	
	
	/**
	 * Converts the Move to the View of the Team,
	 * if the Team of of the Move equals the parameter a clone of the Move is returned
	 * @return a new Move
	 */
	public Move convertToTeam(Team team) {
		if (this.team != team) {
			int minuend = Move.chessboardSize -1;
			return new Move(minuend - this.x1, minuend - this.y1, minuend - this.x2, minuend - this.y2, team);
		} else {
			return new Move(this.x1, this.y1, this.x2, this.y2, this.team);
		}
	}
	
	
	/**
	 * Mirrirs the number at the center axel of chessboard size
	 * To define the Chessboardsize use the static method setChessboardSize
	 * @param i X or Y starting at 0
	 */
	public static int mirror(int i) {
		int minuend = Move.chessboardSize -1;
		return minuend - i;
	}
	
	
	/**
	 * converts a given number to a Team-Specific View
	 */
	public static int convertToTeam(int i, Team team) {
		if (team == Team.WHITE) {
			int minuend = Move.chessboardSize -1;
			return minuend - i;
		}
		return i;
	}
	
	public Team getTeam()
	{
		return team;
	}
	
	public boolean isUndooMove() {
		return this.undooMove;
	}
	
	public String toString() {
		return x1 + ", " + y1 + ", " + x2 + ", " + y2 + " [" + team + "]";
	}
}
