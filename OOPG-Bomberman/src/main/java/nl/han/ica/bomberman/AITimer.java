import java.util.Timer;
import java.util.TimerTask;

public class AITimer extends Timer
{
	Player player;
	Task t;
	
	
	public AITimer(Player p)
	{
		super();
		player = p;
		t = new Task();
		scheduleAtFixedRate(t, 0, 300);
	}
	
	public class Task extends TimerTask
	{
		public void run()
		{
			try
			{
				player.AI(player.getAI());
			}
			catch(Exception e) {};
		}
	}
	
	public void setPlayer(Player player)
	{
		this.player = player;
	}
	
	public Player getPlayer()
	{
		return player;
	}
}