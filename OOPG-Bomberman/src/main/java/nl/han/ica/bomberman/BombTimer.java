import java.util.Timer;
import java.util.TimerTask;

public class BombTimer extends Timer
{
	public Bomb bombs;
	Task t;
	
	public BombTimer(Bomb b)
	{
		super();
		bombs = b;
		t = new Task();
		scheduleAtFixedRate(t, 0, 50);
	}

	public class Task extends TimerTask
	{
		public void run() {
			// TODO Auto-generated method stub
			if(bombs != null)
			{
				
				if(bombs.checkState() == 1) 
				{
					try 
					{
						bombs.explode();
					}
					catch(Exception e)
					{
						
					}
					
					if(bombs.getStartTime() != 1)
						bombs.setStartTime(System.currentTimeMillis());
				}
				else if(bombs.checkState() == 3)
				{
					
					Map.Piece[][] b = IBomberman.getBoard().getBoard();
					
					int[] dx = { 0, 1, 0, -1, 0 };
					int[] dy = { 0, 0, 1, 0, -1 };
					
					for (int i = 0; i < dx.length; i++) 
					{
						if(b[bombs.getRow() + dy[i]][bombs.getColumn() + dx[i]] == Map.Piece.EXPLOSION)
						{
							b[bombs.getRow() + dy[i]][bombs.getColumn() + dx[i]] = Map.Piece.NOTHING;
							IBomberman.setSquare(bombs.getRow() + dy[i], bombs.getColumn() + dx[i], Map.Piece.NOTHING);
						}
					}
					
					bombs.makeNewBomb();
				}
			}
		}
	}
	
	public void setBombs(Bomb bombs)
	{
		this.bombs = bombs;
	}
	
	public Bomb getBombs()
	{
		return bombs;
	}
}