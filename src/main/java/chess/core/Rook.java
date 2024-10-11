package chess.core;

import chess.core.Exceptions.NullTeamException;
import chess.core.Exceptions.PositionNotOnChessboardException;

public class Rook extends Chessman
{
	
	public Rook(Chessboard chessboard, int x, int y, Team team) throws NullTeamException, PositionNotOnChessboardException
	{
		super(chessboard, x, y, team);
		this.type = ChessmanType.ROOK;
	}
	
	
	/**
	 * Method to validate the move of the Rook. Rules:
	 * - rules of Chessman
	 * - can either move only x wise or only y wise
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
		boolean pathEmpty = true;
		// can either move only x wise or only y wise
		if (xMove == 0 || yMove == 0)
		{
			int xPath = xChessman;
			int yPath = yChessman;
			// can move infinite long distances
			while (xPath != xChessman + xMove || yPath != yChessman + yMove)
			{
				if (xMove > 0) xPath++;
				else if (xMove < 0) xPath--;
				if (yMove > 0) yPath++;
				else if (yMove < 0) yPath--;
				if (xPath == xChessman + xMove && yPath == yChessman + yMove)
				{
					// the last position of the path can be a enemy Chessman
					if (null != chessboard.getChessman(xPath, yPath, this.team))
					{
						if (chessboard.getChessman(xPath, yPath, this.team).team == this.team)
						{
							pathEmpty = false;
						}
					}
				}
				else
				{
					// every other position in between need to be empty
					if (null != chessboard.getChessman(xPath, yPath, this.team))
					{
						pathEmpty = false;
					}
				}
			}
			return pathEmpty;
		}
		else
		{
			return false;
		}
	}
}
