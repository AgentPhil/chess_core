package chess.core;

/**
 * Contains the types of different Chessman as well as its "tier"(Queen(9) is worth more than a Pawn(1))
 */
public enum ChessmanType
{
	AbstractChessman(0),
	BISHOP(3),
	KING(Integer.MAX_VALUE),
	KNIGHT(3),
	PAWN(1),
	QUEEN(9),
	ROOK(5);
	
	/**
	 * represents the value of the Chessman, the higher, the better
	 */
	private final int tier;
	
	ChessmanType(int tier)
	{
		this.tier = tier;
	}
	
	public int getTier()
	{
		return tier;
	}
}
