package chess.core;

/**
 * Chessboard containing additional information needed to undo a Move
 */
public class HistoricChessboard extends Chessboard
{
	
	/**
	 * Only being used by the Game when going back through the History
	 */
	private final Team playingTeam;
	
	/**
	 * The Move, that follows this state of game
	 * Is used to be able to highlight the move, that has been undone
	 */
	private final Move followingMove;
	
	private final int backjumps;
	
	HistoricChessboard(Team playingTeam, Move followingMove, int backjumps)
	{
		super(false);
		this.playingTeam = playingTeam;
		this.followingMove = followingMove;
		this.backjumps = backjumps;
	}
	
	Team getPlayingTeam() {
		return playingTeam;
	}
	
	public Move getFollowingMove() {
		return followingMove;
	}
	
	public int getBackjumps() {
		return this.backjumps;
	}
}
