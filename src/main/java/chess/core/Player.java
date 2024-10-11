package chess.core;

public abstract class Player
{
	/**
	 * the game a player is taking part in
	 */
	private final Game game;
	
	public abstract Move makeMove();
	
	public Player(Game game) {
		this.game = game;
	}
	
	public Game getGame() {
		return this.game;
	}
}
