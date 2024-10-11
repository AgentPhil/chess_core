package chess.core.Exceptions;

public class PositionNotOnChessboardException extends RuntimeException
{
	private int x;
	
	private int y;
	
	@Override
	public void printStackTrace()
	{
		System.out.println("The Position x: " + x + ", y: " + y + "is outside the Chessboard. Usally 0 - 7)");
		super.printStackTrace();
	}
	
	public PositionNotOnChessboardException(int x, int y) {
		this.x = x;
		this.y = y;
	}
}
