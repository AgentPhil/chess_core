package chess.core;

import chess.core.Exceptions.NullTeamException;
import chess.core.Exceptions.PositionNotOnChessboardException;

public class Knight extends Chessman
{
	
	public Knight(Chessboard chessboard, int x, int y, Team team) throws NullTeamException, PositionNotOnChessboardException
	{
		super(chessboard, x, y, team);
		this.type = ChessmanType.KNIGHT;
	}
	
	
	/**
	 * Method to validate the move of the Knight. Rules:
	 * - rules of Chessman
	 * - can't move to where Chessman with the same team are
	 * - can only move in a |1|/|2| or |2|/|1| way
	 */
	@Override
	public boolean wouldMoveBeValid(int xChessman, int yChessman, int xMoveTo, int yMoveTo) throws PositionNotOnChessboardException
	{
		int xMove = xMoveTo - xChessman;
		int yMove = yMoveTo - yChessman;
		// rules of Chessman
		if (!super.wouldMoveBeValid(xChessman, yChessman, xMoveTo, yMoveTo)) return false;
		if ((xMove == 2 || xMove == -2) && (yMove == -1 || yMove == 1)) return true;
		return (xMove == 1 || xMove == -1) && (yMove == -2 || yMove == 2);
	}
}
