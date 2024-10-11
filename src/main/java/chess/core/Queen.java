package chess.core;

import chess.core.Exceptions.NullTeamException;
import chess.core.Exceptions.PositionNotOnChessboardException;

public class Queen extends Chessman
{
	
	public Queen(Chessboard chessboard, int x, int y, Team team) throws NullTeamException, PositionNotOnChessboardException
	{
		super(chessboard, x, y, team);
		this.type = ChessmanType.QUEEN;
	}
	
	
	/**
	 * Method to validate the move of the Queen. Rules:
	 * - rules of Chessman
	 * - rules of Rook
	 * - or rules of Bishop
	 * The reference of this inside the chessboard is being deleted
	 * because the Bishop and the Rook overwrite its reference to validate the move
	 * Because of that, this is set to the chessboard again at the end
	 */
	@Override
	public boolean wouldMoveBeValid(int xChessman, int yChessman, int xMoveTo, int yMoveTo) throws PositionNotOnChessboardException
	{
		// rules of Chessman
		if (!super.wouldMoveBeValid(xChessman, yChessman, xMoveTo, yMoveTo)) return false;
		boolean moveValid = false;
		Chessman oldChessman = chessboard.getChessman(xChessman, yChessman, this.team);
		// rules of Rook
		Rook r = new Rook(chessboard, xChessman, yChessman, this.team);
		if (r.isMoveToValid(xMoveTo, yMoveTo)) moveValid = true;
		// or rules of Bishop
		Bishop b = new Bishop(chessboard, xChessman, this.y, this.team);
		if (b.isMoveToValid(xMoveTo, yMoveTo)) moveValid = true;
		chessboard.setChessman(oldChessman);
		return moveValid;
	}
}
