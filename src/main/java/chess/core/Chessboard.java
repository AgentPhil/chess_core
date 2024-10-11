package chess.core;

import chess.core.Exceptions.NullTeamException;
import chess.core.Exceptions.PositionNotOnChessboardException;

/**
 * 2-Dimensional Array containing all Chessman
 * Size is 8 by 8
 * Getter and Setter return values relative to the Team to simplify the isMoveValid Method of a Chessman
 * If you want to get the Chessmen real position, just use Team.BLACK
 * chessboard.getChessman(0, 0, Team.BLACK);
 * Team.BLACK:                 Team.WHITE;
 * X  0  1  2  3  4  5  6  7   X  7  6  5  4  3  2  1  0
 * 0                           7
 * 1                           6
 * 2                           5
 * 3                           4
 * 4                           3
 * 5                           2
 * 6                           1
 * 7                           0
 *
 * Chessboard fills itself with all needed Chessmen
 * X  0  1  2  3  4  5  6  7
 * 0       Team BLACK
 * 1
 * 2
 * 3
 * 4
 * 5
 * 6
 * 7      Team WHITE
 */
public class Chessboard
{
	/**
	 * 2 dimensional Array with the size 8x8
	 * Array[horitional][vertical]
	 */
	private final Chessman[][] field;
	
	/**
	 * creates a new Chessboard with the size 8 by 8
	 * When prefilled is true, it will come filled with Chessman as you would expect a new game
	 * @param prefilled false if you want a empty chessboard
	 */
	public Chessboard(boolean prefilled)
	{
		field = new Chessman[8][8];
		if (prefilled)
		{
			for (Team team : Team.values())
			{
				for (int i = 0; i < this.getSize(); i++)
				{
					new Pawn(this, i, 1, team);
				}
				new Rook(this, 0, 0, team);
				new Rook(this, 7, 0, team);
				new Knight(this, 1, 0, team);
				new Knight(this, 6, 0, team);
				new Bishop(this, 2, 0, team);
				new Bishop(this, 5, 0, team);
			}
			new Queen(this, 3, 0, Team.BLACK);
			new King(this, 4, 0, Team.BLACK);
			new Queen(this, 4, 0, Team.WHITE);
			new King(this, 3, 0, Team.WHITE);
		}
	}
	
	/**
	 * Method to set the Chessman at the relative position of the Team.
	 * When the Chessman is null, nothing happens
	 */
	void setChessman(Chessman chessman)
	{
		if (chessman == null) return;
		switch (chessman.team)
		{
			case BLACK:
				field[chessman.x][chessman.y] = chessman;
				break;
			case WHITE:
				field[this.getSize() - chessman.x - 1][this.getSize() - chessman.y - 1] = chessman;
				break;
		}
	}
	
	/**
	 * Method to move the Chessman to the absolute position on the Chessboard without validation
	 */
	void moveChessmanTo(Chessman chessman, int x, int y) throws PositionNotOnChessboardException
	{
		if (isNotOnChessboard(x, y)) throw new PositionNotOnChessboardException(x, y);
		switch (chessman.team)
		{
			case BLACK:
				field[chessman.x][chessman.y] = null;
				field[x][y] = chessman;
				break;
			case WHITE:
				field[this.getSize() - chessman.x - 1][this.getSize() - chessman.y - 1] = null;
				field[this.getSize() - x - 1][this.getSize() - y - 1] = chessman;
				break;
		}
		chessman.x = x;
		chessman.y = y;
	}
	
	/**
	 *Method to get the Chessman from the absolute position of the Chessboard
	 */
	public Chessman getChessman(int x, int y) throws PositionNotOnChessboardException
	{
		return this.getChessman(x, y, Team.BLACK);
	}
	
	/**
	 * Method to get the Chessman at the relative position of the Team.
	 */
	public Chessman getChessman(int x, int y, Team team) throws PositionNotOnChessboardException, NullTeamException
	{
		if (isNotOnChessboard(x, y)) throw new PositionNotOnChessboardException(x, y);
		switch (team)
		{
			case BLACK:
				return field[x][y];
			case WHITE:
				return field[this.getSize() - x - 1][this.getSize() - y - 1];
			default:
				throw new NullTeamException();
		}
	}
	
	boolean isNotOnChessboard(int x, int y) {
		return x < 0 | x >= getSize() | y < 0 | y >= getSize();
	}
	
	/**
	 * @return size of the Array
	 */
	public int getSize()
	{
		return field.length;
	}
	
	
	/**
	 * returns a text-based visualisation of the Chessman on the Chessboard.
	 * Helpful for debugging
	 * @return String representing the Chessman on the Chessboard
	 */
	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		for (int y = 0; y < this.getSize(); y++) {
			for (int x = 0; x < this.getSize(); x++) {
				char c;
				if (field[x][y] != null) {
					switch (field[x][y].getTeam()) {
						case BLACK: c = 'B'; break;
						case WHITE: c = 'W'; break;
						default: c = '?';
					}
					s.append(c);
					switch (field[x][y].getType()) {
						case BISHOP: c = 'B'; break;
						case PAWN: c = 'P'; break;
						case ROOK: c = 'R'; break;
						case KNIGHT: c = 'K'; break;
						case QUEEN: c = 'Q'; break;
						case KING: c = 'A'; break;
						default: c = '?'; break;
					}
					s.append(c);
				}  else {
					s.append("  ");
				}
			}
			if (y != this.getSize()) {
				s.append("\n");
			}
		}
		return s.toString();
	}
}
