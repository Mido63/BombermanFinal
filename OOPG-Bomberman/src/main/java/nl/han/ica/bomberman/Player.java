import java.awt.event.KeyEvent;
import java.util.Random;

import javax.swing.JOptionPane;

import com.sun.prism.PhongMaterial.MapType;

public class Player
{
	private int row;
	private int column;
	private int score;
	public BombTimer timer;
	private int dirX; //direction x
	private int dirY; //direction y
	private int keyMove; //keyPress move
	private boolean player2;
	private boolean computer; // voor als we tijd over hebben.
	private int AI = 0; //idem
	private AITimer AITime; //idem
	
	
	public Player(int r, int c)
	{
		row = r;
		column = c;
		timer = new BombTimer(new Bomb(0, 0, -1));
		dirX = 0;
		dirY = 0;
	}
	
	public void playerMove(int direction) throws Exception
	{
		
		dirX = dirY = 0;
		if(direction == KeyEvent.VK_UP)
			dirY = -1;
		else if(direction == KeyEvent.VK_DOWN)
			dirY = 1;
		else if(direction == KeyEvent.VK_RIGHT)
			dirX = 1;
		else if(direction == KeyEvent.VK_LEFT)
			dirX = -1;
		
		Map.Piece[][] temp = IBomberman.getBoard().getBoard();
		
		if(row + dirY >= 0 && row + dirY < 13 && column + dirX >= 0 && column + dirX < 15
				&& temp[row + dirY][column + dirX] != Map.Piece.BLOCK
				&& temp[row + dirY][column + dirX] != Map.Piece.CRATE)
		{
			if(temp[row + dirY][column + dirX] == Map.Piece.EXPLOSION)
			{
				if(player2)
					JOptionPane.showMessageDialog(null, "Player 1 wins");
				else
					JOptionPane.showMessageDialog(null, "Player 2 wins");
				
				IBomberman.resetBoard();
				return;
			}
			else
			{
				boolean set = false;
				
				Player[] p = IBomberman.getPlayers();
				
				for (int i = 0; i < 2; i++) 
				{
					if(p[i].timer.bombs.getRow() == row && p[i].timer.bombs.getColumn() == column && !p[i].timer.bombs.isExploding())
					{
						temp[row][column] = Map.Piece.BOMB;
						IBomberman.setSquare(row, column, Map.Piece.BOMB);
						set = true;
					}
					
					if(p[i].timer.getBombs().isExploding())
					{
						int[] dxx = { 0, 0, 1, 0, -1 };
						int[] dyy = { 0, -1, 0, 1, 0 };
						
						for (int j = 0; j < dxx.length; j++) 
						{
							if(row == (p[i].timer.bombs.getRow() + dyy[j]) && column == p[i].timer.bombs.getColumn() + dxx[j])
							{
								temp[row][column] = Map.Piece.EXPLOSION;
								IBomberman.setSquare(row + dyy[j], column + dxx[j], Map.Piece.EXPLOSION);
								set = true;
							}
						}
					}
					
					if(p[i] != this)
					{
						if(p[i].row == row && p[i].column == column)
						{
							if(p[i].isPlayer2())
							{
								temp[row][column] = Map.Piece.PLAYER2;
								IBomberman.setSquare(row, column, Map.Piece.PLAYER2);
							}
							else
							{
								temp[row][column] = Map.Piece.PLAYER;
								IBomberman.setSquare(row, column, Map.Piece.PLAYER);
							}
							set = true;
						}
					}
				}
				
				if(!set)
				{
					temp[row][column] = Map.Piece.NOTHING;
					IBomberman.setSquare(row, column, Map.Piece.NOTHING);
				}
			}
			
			row += dirY;
			column += dirX;
		}
		
		if(player2)
		{
			IBomberman.setSquare(row, column, Map.Piece.PLAYER2);
			temp[row][column] = Map.Piece.PLAYER2;
		}
		else
		{
			IBomberman.setSquare(row, column, Map.Piece.PLAYER);
			temp[row][column] = Map.Piece.PLAYER;
		}
	}
	
	public void dropBomb()
	{
		
		if(timer.bombs.getStartTime() == 1)
		{
			timer.bombs.setRow(row);
			timer.bombs.setColumn(column);
			timer.bombs.setStartTime(System.currentTimeMillis());
		}
	}
	
	/*
	 * Hier komt het algoritme voor de AI per level van lvl 1 tot lvl 4
	 * 
	 * */
	
	public void randomMove() throws Exception
	{
		
		int dir = new Random().nextInt(4);
		dirX = dirY = 0;
		switch(dir)
		{
		case 0:
			keyMove = KeyEvent.VK_UP;
			dirY = 1;
			break;
		case 1:
			keyMove = KeyEvent.VK_DOWN;
			dirY = -1;
			break;
		case 2:
			keyMove = KeyEvent.VK_RIGHT;
			dirX = 1;
			break;
		case 3:
			keyMove = KeyEvent.VK_LEFT;
			dirX = -1;
			break;
		default:
			break;
		}
	}
	
	/*AI*/
	public void AI(int level) throws Exception
	{
		if(level == 1)
			level1();
		else if(level == 2)
			level2();
		else if(level == 3)
			level3();
		else if(level == 4)
			level4();
	}
	
	/*Level 1*/
	public void level1() throws Exception
	{
		randomMove();
		playerMove(keyMove);
	}
	
	/*Level 2*/
	public void level2() throws Exception
	{
		
		randomMove();
		
		if(IBomberman.getBoard().getBoard()[row + dirY][column + dirX] != Map.Piece.EXPLOSION)
		{
			playerMove(keyMove);
		}
	}
	
	/*Level 3*/
	public void level3() throws Exception
	{
		
		randomMove();
		
		if(IBomberman.getBoard().getBoard()[row + dirY][column + dirX] == Map.Piece.EXPLOSION)
		{
			playerMove(keyMove);
		}
		else if(IBomberman.getBoard().getBoard()[row + dirY][column + dirX] == Map.Piece.BOMB)
		{
			
			dirX *= -1;
			dirY *= -1;
			
			if(dirX == 1)
				playerMove(KeyEvent.VK_RIGHT);
			else if(dirX == -1)
				playerMove(KeyEvent.VK_LEFT);
			else if(dirY == -1)
				playerMove(KeyEvent.VK_UP);
			else if(dirY == 1)
				playerMove(KeyEvent.VK_DOWN);
		}
		
		int bomb = new Random().nextInt(100);
		if(bomb < 5)
			dropBomb();
	}
	
	/*Level 4*/
	public void level4() throws Exception
	{
		
		boolean foundMove = false;
		
		int[] dxArray = { 0, 1, 0, -1 };
		int[] dyArray = { -1, 0, 1, 0 };
		int[] keyMoves = { KeyEvent.VK_UP, KeyEvent.VK_RIGHT, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT };
		int[] keyOpposites = { KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_UP, KeyEvent.VK_RIGHT };
		
		for (int r = 0; r < 13; r++) 
		{
			for (int c = 0; c < 15; c++) 
			{
				if(IBomberman.getBoard().getBoard()[r][c] == Map.Piece.BOMB) 
				{
					
					for (int i = 0; i < dxArray.length; i++) 
					{
						if(r == row + dyArray[i] && c == column + dxArray[i])
						{
							dirX = -dxArray[i];
							dirY = -dyArray[i];
							keyMove = keyOpposites[i];
							foundMove = true;
							break;
						}
					}
				}
			}
		}
		
		if(foundMove)
			playerMove(keyMove);
		else
		{
			randomMove();
			if(IBomberman.getBoard().getBoard()[row + dirY][column + dirX] != Map.Piece.EXPLOSION)
			{
				playerMove(keyMove);
			}
		}
		
		for (int i = 0; i < dxArray.length; i++) 
		{
			if(IBomberman.getBoard().getBoard()[row + dyArray[i]][column + dxArray[i]] == Map.Piece.CRATE)
			{
				dropBomb();
				break;
			}
		}
	}
	
	/**/
	
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
	
	public void setScore(int score)
	{
		
	}
	
	public int getScore()
	{
		return score;
	}
	
	public void setPlayer2(boolean player2)
	{
		this.player2 = player2;
	}
	
	public boolean isPlayer2()
	{
		return player2;
	}
	
	public void setComputer(boolean computer)
	{
		this.computer = computer;
	}
	
	public boolean isComputer()
	{
		return computer;
	}
	
	public void setAI(int aI)
	{
		AI = aI;
	}
	
	public int getAI()
	{
		return AI;
	}
	
	public void setAITime(AITimer aITime)
	{
		AITime = aITime;
	}
	
	public AITimer getAITime()
	{
		return AITime;
	}
	
	public void setDirX(int dirX)
	{
		this.dirX = dirX;
	}
	
	public int getDirX()
	{
		return dirX;
	}
	
	public void setDirY(int dirY)
	{
		this.dirY = dirY;
	}
	
	public int getDirY()
	{
		return dirY;
	}
}




















