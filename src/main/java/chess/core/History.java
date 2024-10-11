package chess.core;

import chess.core.Exceptions.ChessmanNotCopyableException;

/**
 * Class containing multiple copies of the Chessboard at differnt times
 */
class History
{
	/**
	 * Array of multiple copies of the Chessboard
	 */
	private final HistoricChessboard[] chessboards;
	
	/**
	 * number of moves that are gone back
	 */
	private final int backjumps;
	
	/**
	 * points at the index of the next free(or overwriteable) historic chessboard
	 */
	private int pointer = 0;
	
	/**
	 * Array of multiple copies of the Chessboard
	 * @param size steps you can max go backwards
	 */
	History(int size, int backjumps) {
		this.chessboards =  new HistoricChessboard[size];
		this.backjumps = backjumps;
	}
	
	
	/**
	 * Creates a copy of the chessboard and adds the copy to the field chesssboards
	 * @param chessboard Chessboard that should be copied
	 * @param playingTeam the Team that would make the next move
	 */
	void add(Chessboard chessboard, Team playingTeam, Move followingMove) {
		HistoricChessboard historic = new HistoricChessboard(playingTeam, followingMove, backjumps);
		for (int x = 0; x < chessboard.getSize(); x++) {
			for (int y = 0; y < chessboard.getSize(); y++) {
				Chessman chessman = chessboard.getChessman(x, y);
				if (chessman != null) {
					int cX = chessman.x; //Team relative Positiions of the Chessman
					int cY = chessman.y;
					switch (chessman.getType()) {
						case PAWN: new Pawn(historic, cX, cY, chessman.getTeam()); break;
						case ROOK: new Rook(historic, cX, cY, chessman.getTeam()); break;
						case BISHOP: new Bishop(historic, cX, cY, chessman.getTeam()); break;
						case KNIGHT: new Knight(historic, cX, cY, chessman.getTeam()); break;
						case QUEEN: new Queen(historic, cX, cY, chessman.getTeam()); break;
						case KING: new King(historic, cX, cY, chessman.getTeam()); break;
						default: throw new ChessmanNotCopyableException(chessman.getType().name());
					}
				}
			}
		}
		chessboards[pointer] = historic;
		pointer = ++pointer % chessboards.length;
	}
	
	
	/**
	 * returns a historic Chessboard
	 * @return The chessboard that lies the number of backjumps back in time
	 */
	HistoricChessboard fuckGoBack() {
		if (chessboards[(pointer - backjumps + chessboards.length) % chessboards.length] != null) {
			pointer = (pointer - backjumps + chessboards.length) % chessboards.length;
			HistoricChessboard historic = chessboards[pointer];
			chessboards[pointer] = null;
			return historic;
		} else {
			return null;
		}
	}
}
