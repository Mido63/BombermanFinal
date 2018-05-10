import javax.swing.JOptionPane;

public class Bomb
{
	private long startTime;
	private int row;
	private int column;
	private boolean exploding;
	
	public Bomb(int r, int c, long time)
	{
		row = r;
		column = c;
		startTime = time;
		exploding = false;
	}
	
	public int checkState()
	{
		
		long time;
		int state;
		
		if(exploding)
		{
			time = 1500;
			state = 3;
		}
		else
		{
			time = 2500;
			state = 1;
		}
		
		if(startTime != -1 && System.currentTimeMillis() - startTime > time)
			return state;
		else
			return state -1;
	}
	
	public void explode() throws Exception
	{
		
		Player[] p = IBomberman.getPlayers();
		Map.Piece[][] b = IBomberman.getBoard().getBoard();
		int[] dx = { 0, 1, 0, -1, 0 };
		int[] dy = { 0, 0, 1, 0, -1 };
		
		for (int i = 0; i < dx.length; i++) 
		{
			if(b[row + dy[i]][column + dx[i]] != Map.Piece.BLOCK)
			{
				if(b[row + dy[i]][column + dx[i]] == Map.Piece.PLAYER)
				{
					JOptionPane.showMessageDialog(null, "BOOM! Player 1 loses");
					IBomberman.resetBoard();
					return;
				}
				else if(b[row + dy[i]][column + dx[i]] == Map.Piece.PLAYER2)
				{
					JOptionPane.showMessageDialog(null, "Player 1 wins!");
					IBomberman.resetBoard();
					return;
				}
				else if(b[row + dy[i]][column + dx[i]] == Map.Piece.PLAYER2)
				{
					JOptionPane.showMessageDialog(null, "BOOM! Player 2 loses");
					IBomberman.resetBoard();
					return;
				}
				else if(b[row + dy[i]][column + dx[i]] == Map.Piece.PLAYER)
				{
					JOptionPane.showMessageDialog(null, "Player 2 wins!");
					IBomberman.resetBoard();
					return;
				}
				b[row + dy[i]][column + dx[i]] = Map.Piece.EXPLOSION;
				IBomberman.setSquare(row, column, Map.Piece.EXPLOSION);
			}
		}
		exploding = true;
	}
	
	public void makeNewBomb()
	{
		row = 0;
		column = 0;
		startTime = -1;
		exploding = false;
	}
	
	public void setStartTime(long startTime)
	{
		this.startTime = startTime;
	}
	
	public long getStartTime()
	{
		return startTime;
	}
	
	public void setRow(int row)
	{
		this.row = row;
	}
	
	public int getRow()
	{
		return row;
	}
	
	public void setColumn(int column)
	{
		this.column = column;
	}
	
	public int getColumn()
	{
		return column;
	}
	
	public void setExploding(boolean exploding)
	{
		this.exploding = exploding;
	}
	
	public boolean isExploding()
	{
		return exploding;
	}
}





























