package chess.core;

import chess.core.Exceptions.NullTeamException;
import chess.core.Exceptions.PositionNotOnChessboardException;

/**
 * Abstract Chessman
 * Only the isMoveValid Method needs to be overwritten
 * the move Methods have a standard functionality
 * Chessman needs a Chessboard to exist,
 * also to ensure, that the position in the Chessboard is team relative same as x and y positions
 * Positions of Team Black and Team White are mirrored 0 => 7, 7 => 0
 */
public abstract class Chessman
{
	int x, y;
	
	final Team team;
	
	ChessmanType type = ChessmanType.AbstractChessman;
	
	/**
	 * The Chessman knows the chessboard it is standing on to be able to validate a move on its own
	 */
	Chessboard chessboard;
	
	/**
	 * creates a new Chessman with the specified positions and places itself inside the Chessboard at the
	 * team relative position. The return of the constructor can be 'thrown away'
	 */
	Chessman(Chessboard chessboard, int x, int y, Team team) throws PositionNotOnChessboardException, NullTeamException
	{
		if (chessboard.isNotOnChessboard(x, y)) throw new PositionNotOnChessboardException(x, y);
		if (team == null) throw new NullTeamException();
		this.x = x;
		this.y = y;
		this.team = team;
		this.chessboard = chessboard;
		chessboard.setChessman(this);
	}
	
	/**
	 * validates, if the move would be valid from the position of the Chessman
	 * x and y are team relative
	 */
	boolean isMoveToValid(int x, int y) throws PositionNotOnChessboardException
	{
		return wouldMoveBeValid(this.x, this.y, x, y);
	}
	
	/**
	 * Standard implementation for all Chessman
	 * - Can't jump on spot
	 * - Chessman can't move outside the Chessboard
	 * - can't hit other Chessman from the same team
	 * x and y are relative to the team
	 */
	public boolean wouldMoveBeValid(int xChessman, int yChessman, int xMoveTo, int yMoveTo) throws PositionNotOnChessboardException
	{
		int xMove = xMoveTo - xChessman;
		int yMove = yMoveTo - yChessman;
		// Can't jump on spot
		if (xMove == 0 && yMove == 0) return false;
		// Chessman can't move outside the Chessboard
		if (xMoveTo < 0 | xMoveTo >= chessboard.getSize() | yMoveTo < 0 | yMoveTo >= chessboard.getSize()) return false;
		// can't hit other Chessman from the same team
		Chessman chessmanAtTargetPosition = chessboard.getChessman(xChessman + xMove, yChessman + yMove, this.team);
		return chessmanAtTargetPosition == null || chessmanAtTargetPosition.team != this.team;
	}
	
	/**
	 * Iterates through the Chessboard to determine, if the Chessman is in Danger
	 */
	public boolean inDanger()
	{
		return inDangerAt(this.x, this.y);
	}
	
	/**
	 * Iterates through the Chessboard to determine, if the Chessman would be in danger at the given team relative position
	 */
	public boolean inDangerAt(int x, int y) throws PositionNotOnChessboardException
	{
		boolean inDanger = false;
		int xOriginal = this.x;
		int yOriginal = this.y;
		Chessman chessmanAtTarget = chessboard.getChessman(x, y, this.team);
		chessboard.moveChessmanTo(this, x, y);
		iteration:
		for (int xi = 0; xi < chessboard.getSize(); xi++)
		{
			for (int yi = 0; yi < chessboard.getSize(); yi++)
			{
				if (chessboard.getChessman(xi, yi, this.team) != null)
				{
					if (chessboard.getChessman(xi, yi, this.team).team != this.team)
					{
						if (chessboard.getChessman(xi, yi, this.team).isMoveToValid(Move.mirror(x), Move.mirror(y)))
						{
							inDanger = true;
							break iteration;
						}
					}
				}
			}
		}
		chessboard.moveChessmanTo(this, xOriginal, yOriginal);
		chessboard.setChessman(chessmanAtTarget);
		return inDanger;
	}
	
	public Team getTeam()
	{
		return this.team;
	}
	
	public ChessmanType getType()
	{
		return this.type;
	}
}
