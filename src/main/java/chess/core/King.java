package chess.core;

import chess.core.Exceptions.NullTeamException;
import chess.core.Exceptions.PositionNotOnChessboardException;

public class King extends Chessman
{
	
	public King(Chessboard chessboard, int x, int y, Team team) throws NullTeamException, PositionNotOnChessboardException
	{
		super(chessboard, x, y, team);
		this.type = ChessmanType.KING;
	}
	
	
	/**
	 * Method to validate the move of the King. Rules:
	 * - rules of Chessman
	 * - can walk 1 in each direction
	 */
	@Override
	public boolean wouldMoveBeValid(int xChessman, int yChessman, int xMoveTo, int yMoveTo) throws PositionNotOnChessboardException
	{
		int xMove = xMoveTo - xChessman;
		int yMove = yMoveTo - yChessman;
		// rules of Chessman
		if (!super.wouldMoveBeValid(xChessman, yChessman, xMoveTo, yMoveTo)) return false;
		// can walk 1 in each direction
		return (xMove == 1 || xMove == 0 || xMove == -1) && (yMove == 1 || yMove == 0 || yMove == -1);
	}
}
