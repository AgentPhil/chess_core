package chess.core;

import chess.core.Exceptions.NullTeamException;
import chess.core.Exceptions.PositionNotOnChessboardException;

public class Pawn extends Chessman
{
	
	public Pawn(Chessboard chessboard, int x, int y, Team team) throws NullTeamException, PositionNotOnChessboardException
	{
		super(chessboard, x, y, team);
		this.type = ChessmanType.PAWN;
	}
	
	
	/**
	 * Method to validate the move of the Pawn. Rules:
	 * - rules of Chessman
	 * - First move can be 0/2 if y + 1 == null
	 * - can move one forward if there is no other Chessman
	 * - can move in a side-forward-move if there is a enemy Chessman
	 * - can move to current pos if on the enemy edge to perform a swap of the chessman
	 */
	@Override
	public boolean wouldMoveBeValid(int xChessman, int yChessman, int xMoveTo, int yMoveTo) throws PositionNotOnChessboardException
	{
		int xMove = xMoveTo - xChessman;
		int yMove = yMoveTo - yChessman;
		// rules of Chessman
		if (!super.wouldMoveBeValid(xChessman, yChessman, xMoveTo, yMoveTo)) return false;
		if (xMove == 0)
		{
			//First move can be x0/y2 if the position in front of us is free
			if (yMove == 2 && this.y == 1)
			{
				if (null == chessboard.getChessman(xChessman, yChessman + 1, this.team) && chessboard.getChessman(xChessman, yChessman + yMove, this.team) == null)
				{
					return true;
				}
			}
			//can move one forward if there is no other Chessman
			if (yMove == 1)
			{
				if (chessboard.getChessman(xChessman, yChessman + yMove, this.team) == null)
				{
					return true;
				}
			}
		}
		//can move in a side-forward-move if there is a enemy Chessman
		if ((xMove == 1 || xMove == -1) && yMove == 1)
		{
			Chessman c = chessboard.getChessman(xChessman + xMove, yChessman + yMove, this.team);
			return null != c && this.team != c.team;
		}
		//TODO kill while moving forward in the middle
		//TODO Chessman swap
		return false;
	}
}
