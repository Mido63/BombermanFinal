import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import nl.han.ica.OOPDProcessingEngineHAN.Engine.GameEngine;
import nl.han.ica.OOPDProcessingEngineHAN.Sound.Sound;

@SuppressWarnings("serial")
public class IBomberman extends GameEngine
{
	private Sound backgroundSound;
	private Sound explotionSound;
	private static JFrame frame;
	private static JLabel[][] squares;
	private static Map board;
	private static Player[] players;
	private static int AIDifficulty; //TODO: Dit variable kan nog worden gebruik mits we tijd over hebben.

	
	public IBomberman() throws Exception
	{
		frame = new JFrame( "Bomberman" );
		JPanel panel = (JPanel) frame.getContentPane();
		
		panel.setLayout( new GridLayout(13, 15));
		
		final GameEngine applet = new GameEngine() {
			
			@Override
			public void update() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void setupGame() {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		};
		
		applet.init();
		panel.add(applet);
		
		squares = new JLabel[13][15];
		
		for (int row = 0; row < 13; row++) 
		{
			for (int column = 0; column < 15; column++) 
			{
				squares[row][column] = new JLabel();
				
				panel.add(squares[row][column]);
			}
		}
		
		frame.setContentPane(panel);
		
		frame.setSize(applet.getSize().width, applet.getSize().height + 200); //750, 650
		
		frame.setVisible(true);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		players = new Player[4];
		players[0] = new Player(0,0);
		players[1] = new Player(0,0);
		
		board = new Map();
		playerSetup();
		initializeSound();
	}
	
//	@Override
//	public void setupGame() {
//		// TODO Auto-generated method stub
//		int worldWidth = 750;
//		int worldHeight = 650;
//		
//		initializeSound();
//		try 
//		{
//			board = new Map();
//		} 
//		catch (Exception e) 
//		{
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		playerSetup();
//		
//		createViewWithoutViewport(worldWidth, worldHeight);
//	}
//	
//	private void createViewWithoutViewport(int screenWidth, int screenHeight)
//	{
//		View view = new View(screenWidth, screenHeight);
//		
//		setView(view);
//		size(screenWidth, screenHeight);
//	}
//	
//	
//
	@Override
	public void setup() {
		// TODO Auto-generated method stub
	}
	
	
	public void addListener( BombermanListener listener )
	{
		frame.addKeyListener( listener );
	}
	
	private void initializeSound() {
        backgroundSound = new Sound(this, "src/main/java/nl/han/ica/bomberman/sounds/background-music.mp3");
        backgroundSound.loop(-1);
        explotionSound = new Sound(this, "src/main/java/nl/han/ica/bomberman/sounds/explosion.mp3");
    }
	
	public static void setSquare(int row, int column, Map.Piece type)
	{
		String s = "images/player-down.jpg";
		
		if(type == Map.Piece.BLOCK)
			s = "images/block.jpg";
		else if(type == Map.Piece.BOMB)
			s = "images/bomb.jpg";
		else if(type == Map.Piece.BLOCK)
			s = "images/block.jpg";
		else if(type == Map.Piece.COMPUTER)
		{
			for (int i = 0; i < 2; i++) 
			{
				
				if(row == players[i].getRow() && column == players[i].getColumn() /*&& players[i].isComputer()*/ )
				{
					if(players[i].getDirX() == 1)
						s = "images/computer-right.jpg";
					else if(players[i].getDirX() == -1)
						s = "images/computer-left.jpg";
					else if(players[i].getDirY() == 1)
						s = "images/computer-down.jpg";
					else
						s = "images/computer-up.jpg";
				}
			}
		}
		else if(type == Map.Piece.CRATE)
			s = "images/crate.jpg";
		else if(type == Map.Piece.EXPLOSION)
			s = "images/explotion.jpg";
		else if(type == Map.Piece.NOTHING)
			s = "images/nothing.jpg";
		else if(type == Map.Piece.PLAYER)
		{
			for (int i = 0; i < 2; i++) 
			{
				if(row == players[i].getRow() && column == players[i].getColumn() /* && !players[i].isComputer()*/ )
				{
					
					if(players[i].getDirX() == 1)
						s = "images/player-right.jpg";
					else if(players[i].getDirX() == -1)
						s = "images/player-left.jpg";
					else if(players[i].getDirY() == 1)
						s = "images/player-down.jpg";
					else
						s = "images/player-up.jpg";
				}
			}
		}
		else if(type == Map.Piece.PLAYER2)
		{
			for (int i = 0; i < 2; i++) 
			{
				if(row == players[i].getRow() && column == players[i].getColumn() /* && !players[i].isComputer()*/ )
				{
					
					if(players[i].getDirX() == 1)
						s = "images/player2-right.jpg";
					else if(players[i].getDirX() == -1)
						s = "images/player2-left.jpg";
					else if(players[i].getDirY() == 1)
						s = "images/player2-down.jpg";
					else
						s = "images/player2-up.jpg";
				}
			}
		}
	}
	
	public static void playerSetup()
	{
		int playerCount = 0;
		
		for (int r = 0; r < 13; r++) 
		{
			for (int c = 0; c < 15; c++) 
			{
				if(board.getBoard()[r][c] == Map.Piece.PLAYER)
				{
					players[playerCount].setRow(r);
					players[playerCount].setColumn(c);
					//players[playerCount].setComputer(false);
					
					players[playerCount].timer.bombs.makeNewBomb();
					
					playerCount++;
				}
				else if(board.getBoard()[r][c] == Map.Piece.PLAYER2)
				{
					players[playerCount].setRow(r);
					players[playerCount].setColumn(c);
					//players[playerCount].setComputer(false);
					
					players[playerCount].timer.bombs.makeNewBomb();
					
					playerCount++;
				}
				else if(board.getBoard()[r][c] == Map.Piece.COMPUTER)
				{
					players[playerCount].setRow(r);
					players[playerCount].setColumn(c);
					players[playerCount].setComputer(true);
					
					players[playerCount].setAI(AIDifficulty);
					AITimer ai = players[playerCount].getAITime();
					ai.setPlayer(players[playerCount]);
					players[playerCount].setAITime(ai);
					
					playerCount++;
				}
			}
		}
	}
	
	public static void resetBoard() throws Exception
	{
		board = new Map();
		playerSetup();
	}
	
	public void setBoard(Map board)
	{
		this.board = board;
	}
	
	public static Map getBoard()
	{
		return board;
	}
	
	public void setPlayers(Player[] players)
	{
		this.players = players;
	}
	
	public static Player[] getPlayers()
	{
		return players;
	}

	@Override
	public void setupGame() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
}