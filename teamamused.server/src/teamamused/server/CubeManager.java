package teamamused.server;

public class CubeManager{
	private static CubeManager instance;
	
	public CubeManager(){
		
	}
	
	/**
	 * Statischer Getter für Instanz (Singleton Pattern)
	 * @return Instanz des CubeManager
	 */
	public static CubeManager getInstance() {
		if (instance == null) {
			instance = new CubeManager();
		}
		return instance;
	}
	
	
}
