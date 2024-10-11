package chess.core;

import chess.core.Exceptions.NullTeamException;
import chess.core.Exceptions.PositionNotOnChessboardException;

public class Bishop extends Chessman
{
	public Bishop(Chessboard chessboard, int x, int y, Team team) throws NullTeamException, PositionNotOnChessboardException
	{
		super(chessboard, x, y, team);
		this.type = ChessmanType.BISHOP;
	}
	
	/**
	 * Method to validate the move of the Bishop. Rules:
	 * - rules of Chessman
	 * - can only move diagonal
	 * - can move infinite long distances
	 * - the last position of the path can be a enemy Chessman
	 * - every other position in between needs to be empty
	 */
	@Override
	public boolean wouldMoveBeValid(int xChessman, int yChessman, int xMoveTo, int yMoveTo) throws PositionNotOnChessboardException
	{
		int xMove = xMoveTo - xChessman;
		int yMove = yMoveTo - yChessman;
		// rules of Chessman
		if (!super.wouldMoveBeValid(xChessman, yChessman, xMoveTo, yMoveTo)) return false;
		// can only move diagonal
		if (xMove == yMove || -xMove == yMove)
		{
			boolean pathEmpty = true;
			int xPath = xChessman;
			int yPath = yChessman;
			// can move infinite long distances
			for (; xPath != this.x + xMove; )
			{
                if (xMove > 0) xPath++;
                else xPath--;
                if (yMove > 0) yPath++;
                else yPath--;
				if (xPath == this.x + xMove)
				{
					// the last position of the path can be a enemy Chessman
					if (chessboard.getChessman(xPath, yPath, this.team) != null)
					{
						if (chessboard.getChessman(xPath, yPath, this.team).team == this.team) pathEmpty = false;
					}
				}
				else
				{
					// every other position in between needs to be empty
					if (null != chessboard.getChessman(xPath, yPath, this.team)) pathEmpty = false;
				}
			}
			return pathEmpty;
		}
		else return false;
	}
}
