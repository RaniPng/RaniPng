package GameState;

public class Save {
	private static int _currentLevel;
	private static int _currentCheckPoint;
	
	public static void init() {
		
	}
	
	public static int getLev() { return _currentLevel; }
	public static void setLev(int i) { _currentLevel = i; }
	
	public static int getCehckP() { return _currentCheckPoint; }
	public static void setCheckP(int i) { _currentCheckPoint = i; }


}
