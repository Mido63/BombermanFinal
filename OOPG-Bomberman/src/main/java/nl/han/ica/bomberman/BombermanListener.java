import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class BombermanListener implements KeyListener
{
	private IBomberman bomberman;
	
	
	public BombermanListener(IBomberman b)
	{
		this.bomberman = b;
		bomberman.addListener(this);
	}
	
	public void keyPressed(KeyEvent event)
	{
		
		int keycode = event.getKeyCode();
		
		if(keycode== KeyEvent.VK_UP || keycode == KeyEvent.VK_DOWN ||
				keycode == KeyEvent.VK_RIGHT || keycode == KeyEvent.VK_LEFT)
		{
			
			for (int i = 0; i < 2; i++) 
			{
				if(!bomberman.getPlayers()[i].isPlayer2())
				{
					try
					{
						bomberman.getPlayers()[i].playerMove(event.getKeyCode());
					}
					catch(Exception e) {};
				}
				/*
				else if(!bomberman.getPlayers()[i].isComputer())
				{
					try
					{
						bomberman.getPlayers()[i].playerMove(event.getKeyCode());
					}
					catch(Exception e) {};
				}
				*/
			}
		}
		else if(keycode== KeyEvent.VK_W || keycode == KeyEvent.VK_S ||
				keycode == KeyEvent.VK_D || keycode == KeyEvent.VK_A)
		{
			
			for (int i = 0; i < 2; i++) 
			{
				if(bomberman.getPlayers()[i].isPlayer2())
				{
					try
					{
						bomberman.getPlayers()[i].playerMove(event.getKeyCode());
					}
					catch(Exception e) {};
				}
				/*
				else if(!bomberman.getPlayers()[i].isComputer())
				{
					try
					{
						bomberman.getPlayers()[i].playerMove(event.getKeyCode());
					}
					catch(Exception e) {};
				}
				*/
			}
		}
		
		if(keycode == KeyEvent.VK_SPACE)
		{
			for (int i = 0; i < 2; i++) 
			{
				if(!bomberman.getPlayers()[i].isPlayer2())
					bomberman.getPlayers()[i].dropBomb();
			}
		}
		else if(keycode == KeyEvent.VK_0)
		{
			for (int i = 0; i < 2; i++) 
			{
				if(bomberman.getPlayers()[i].isPlayer2())
					bomberman.getPlayers()[i].dropBomb();
			}
		}
	}
	
	public void keyReleased( KeyEvent event )
	{
	}
	
	public void keyTyped( KeyEvent event )
	{
	}
}