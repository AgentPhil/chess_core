package chess.core;

/**
 * Interface for graphical presentation of the Game
 */
public interface ChessUserInterface
{
	/**
	 * paints the chessboard in the beginning and after each Move
	 * @param chessboard new chessboard to be painted
	 * @param playingTeam the team that has the following turn
	 * @param move the last move made right before painting
	 */
	void paint(Chessboard chessboard, Team playingTeam, Move move);
	
	/**
	 * showing information about the team that just won the game, the game wont be painted again after this move
	 * @param winningTeam team that won won the game
	 */
	void won(Team winningTeam);
	
	/**
	 * An Exception caused the end of the game
	 * @param e Exception to be sown
	 */
	void showExceptionStackTrace(Exception e);
}
