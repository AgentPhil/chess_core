package chess.core.Exceptions;

public class ChessmanNotCopyableException extends RuntimeException
{
	private String classname;
	
	@Override
	public void printStackTrace()
	{
		System.out.println("The Class " + classname + "couldn't be copied. It needs to be defined in the History class");
		super.printStackTrace();
	}
	
	public ChessmanNotCopyableException(String classname) {
		this.classname = classname;
	}
}