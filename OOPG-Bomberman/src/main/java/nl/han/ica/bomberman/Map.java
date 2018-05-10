import java.io.FileReader;
import java.util.Scanner;

public class Map
{
	public enum Piece { BLOCK, BOMB, PLAYER, PLAYER2, EXPLOSION, CRATE, NOTHING, COMPUTER };
	private Piece[][] board;
	
	public Map() throws Exception
	{
		loadMap("defaultMap.txt");
	}
	
	public void loadMap(String fileName) throws Exception
	{
		char[][] map = new char[13][15];
		board = new Piece[13][15];
		Scanner in = new Scanner(new FileReader(fileName));
		
		for (int r = 0; r < 13; r++) 
		{
			map[r] = in.next().toCharArray();
			
			for (int c = 0; c < 15; c++) 
			{
				switch(map[r][c])
				{
				case '1':
					board[r][c] = Piece.PLAYER;
					break;
				case 'C':
					board[r][c] = Piece.CRATE;
					break;
				case 'c':
					board[r][c] = Piece.PLAYER2;
					break;
				case 'B':
					board[r][c] = Piece.BLOCK;
					break;
				case 'b':
					board[r][c] = Piece.BOMB;
					break;
				case 'N':
					board[r][c] = Piece.NOTHING;
					break;
				default:
					break;
				}
			}
		}
		
		for (int r = 0; r < 13; r++) 
			for(int c = 0; c < 15; c++)
				IBomberman.setSquare(r, c, board[r][c]);
	}
	
	public void setBoard(Piece[][] board)
	{
		this.board = board;
	}
	
	public Piece[][] getBoard()
	{
		return board;
	}
}