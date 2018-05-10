import processing.core.PApplet;

public class Bomberman
{
	public static void main(String[] args) throws Exception
	{
		PApplet.main(new String[] {"nl.han.ica.bomberman.Bomberman"});
		IBomberman bomberman = new IBomberman();
		BombermanListener listener = new BombermanListener(bomberman);
	}
}