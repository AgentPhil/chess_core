package chess.core;

import chess.core.Exceptions.GameAlreadyRunningException;
import chess.core.Exceptions.NoKingFoundException;
import chess.core.Exceptions.NullTeamException;
import chess.core.Exceptions.PositionNotOnChessboardException;

public class Game
{
	
	protected boolean gameRunning = false;
	
	private Chessboard chessboard;
	
	private Team teamPlaying;
	
	private final ChessUserInterface ui;
	
	private Move lastMove;
	
	/**
	 * creates a new game with a prefilled Chessboard
	 * @param ui userInterface of this game Session
	 */
	public Game(ChessUserInterface ui)
	{
		this.chessboard = new Chessboard(true);
		this.ui = ui;
	}
	
	/**
	 * starts the match and returns the winning Team.
	 *
	 * Player p1 is BLACK and Player p2 is WHITE
	 * @return winning Team, null when there where not all players or the game was already running
	 */
	public Team startMatch(Player p1, Player p2, int backjumps) throws GameAlreadyRunningException
	{
		if (gameRunning)
		{
			throw new GameAlreadyRunningException();
		}
		gameRunning = true;
		this.teamPlaying = Team.WHITE;
		Move move;
		History history = new History(100, backjumps);
		ui.paint(chessboard, teamPlaying, null);
		do
		{
			Player playingPlayer = teamPlaying == Team.WHITE ? p1 : p2;
			while (true)
			{
				try
				{
					Thread.sleep(20);
				}
				catch (InterruptedException ignored) {}
				move = playingPlayer.makeMove();
				if (move.isUndooMove()) {
					HistoricChessboard historic = history.fuckGoBack();
					if (historic != null) {
						chessboard = historic;
						teamPlaying = historic.getPlayingTeam() == Team.BLACK ? Team.WHITE : Team.BLACK;
						break;
					}
				}
				else if (isMoveValid(move))
				{
					history.add(chessboard, teamPlaying, move);
					move(move);
					lastMove = move;
					break;
				}
			}
			ui.paint(chessboard, teamPlaying, move);
			teamPlaying = teamPlaying == Team.WHITE ? Team.BLACK : Team.WHITE;
		}
		while (!checkmated(teamPlaying));
		gameRunning = false;
		Team winningteam = teamPlaying == Team.BLACK ? Team.WHITE : Team.BLACK;
		ui.won(winningteam);
		return winningteam;
	}
	
	
	/**
	 * Moves the Chessman from the first position to the second if valid
	 * The Team of the moving Chessman must match teamPlaying
	 * also changes the state of the playing Team
	 */
	protected void move(Move move) throws PositionNotOnChessboardException
	{
		//eventually redundant
		move = move.convertToTeam(getPlayingTeam());
		Chessman movingChessman = chessboard.getChessman(move.x1, move.y1, getPlayingTeam());
		chessboard.moveChessmanTo(movingChessman, move.x2, move.y2);
	}
	
	
	/**
	 * vaildates the move of the Chessman and if the King would be in Danger after the move
	 * @return true when the move is valid and the king is not in Danger after the move
	 */
	public boolean isMoveValid(Move move) throws PositionNotOnChessboardException
	{
		//eventually reduntant
		move = move.convertToTeam(getPlayingTeam());
		Chessman movingChessman = chessboard.getChessman(move.x1, move.y1, getPlayingTeam());
		if (movingChessman == null) return false;
		if (movingChessman.getTeam() != getPlayingTeam()) return false;
		if (movingChessman.isMoveToValid(move.x2, move.y2)) {
			return !kingInDangerAfterMove(move);
		}
		return false;
	}
	
	private boolean checkSpecialMoves(Move move, Chessman movingChessman) {
		//TODO
		Chessman targetChessman = chessboard.getChessman(move.x2, move.y2, getPlayingTeam());
		return false;
		
	}
	
	
	/**
	 * determines if the King of the playing Team would be in Danger after the Move
	 * @param move is made and then made backwards
	 */
	private boolean kingInDangerAfterMove(Move move) throws PositionNotOnChessboardException
	{
		move = move.convertToTeam(getPlayingTeam());
		//moving chessman has same team as playingTeam
		Chessman movingChessman = chessboard.getChessman(move.x1, move.y1, getPlayingTeam());
		Chessman chessmanAtTarget = chessboard.getChessman(move.x2, move.y2, getPlayingTeam());
		chessboard.moveChessmanTo(movingChessman, move.x2, move.y2);
		boolean kingInDangerAfterMove = kingInDanger(getPlayingTeam());
		chessboard.moveChessmanTo(movingChessman, move.x1, move.y1);
		chessboard.setChessman(chessmanAtTarget);
		return kingInDangerAfterMove;
	}
	
	/**
	 * Iterates through the Chessboard to determine, if the King of the given team is in Danger
	 *
	 * @return true if a Chessman of the other team can move to the position of the king
	 */
	private boolean kingInDanger(Team team) throws NullTeamException
	{
		return getKing(team).inDanger();
	}
	
	/**
	 * Return the first King of the given Team
	 * @throws NoKingFoundException when there is no king of the specific team
	 */
	private King getKing(Team team) throws NullTeamException
	{
		for (int yi = 0; yi < chessboard.getSize(); yi++)
		{
			for (int xi = 0; xi < chessboard.getSize(); xi++)
			{
				//null check is not needed for instanceof
				if (chessboard.getChessman(xi, yi, team) instanceof King)
				{
					King king =  (King) chessboard.getChessman(xi, yi, team);
					if (king.getTeam() == team) {
						return king;
					}
				}
			}
		}
		throw new NoKingFoundException();
	}
	
	
	/**
	 * Iterates through the Chessboard to determine, if the king of the given team is checkmated
	 * Is the same as if no move is allowed anymore
	 */
	private boolean checkmated(Team team) throws NullTeamException
	{
		boolean checkmated = true;
		for (int x = 0; x < chessboard.getSize(); x++)
		{
			for (int y = 0; y < chessboard.getSize(); y++)
			{
				for (int xMoveTo = 0; xMoveTo < chessboard.getSize(); xMoveTo++)
				{
					for (int yMoveTo = 0; yMoveTo < chessboard.getSize(); yMoveTo++)
					{
						if (isMoveValid(new Move(x, y, xMoveTo, yMoveTo, team))) checkmated = false;
					}
				}
			}
		}
		return checkmated;
	}
	
	public Team getPlayingTeam()
	{
		return this.teamPlaying;
	}
	
	public Chessboard getChessboard()
	{
		return chessboard;
	}
	
	protected void setChessboard(Chessboard chessboard) {
		this.chessboard = chessboard;
	}
	
	public ChessUserInterface getUi() {
		return ui;
	}
	
	public Move getLastMove() {
		return lastMove;
	}
	
	protected void setLastMove(Move move) {
		this.lastMove = move;
	}
}
