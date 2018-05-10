

public class Bomberman
{
	public static void main(String[] args) throws Exception
	{
		IBomberman bomberman = new IBomberman();
		BombermanListener listener = new BombermanListener(bomberman);
	}
}