package teamamused.playground.application.serverKlassen;

import teamamused.common.ServiceLocator;
import teamamused.common.interfaces.ICube;

/**
 * @author Daniel
 *
 */
public class CubeManager {

	// Anzahl gewürfelt
	private static CubeManager instance;
	private int diceCounter = 0;
	private int allowedDicings = 3;
	
	/**
	 * Privater Konstruktor
	 */
	private CubeManager() {
		
	}
	/**
	 * Getter zur Instanz des CubeManagers
	 * @return CubeManager Instanz
	 */
	public static CubeManager getInstance() {
		if (instance == null) {
			instance = new CubeManager();
		}
		return instance;
	}
	
	/**
	 * Beispiel würfeln
	 * @return
	 * 	Wieviel mal noch gewürfelt werden darf
	 */
	public int rollDices() {
		ServiceLocator.getInstance().getLogger().info("CubeManager: rolle Würfel");
		this.diceCounter++;
		for (ICube cube: Boardmanager.getInstance().getGameBoard().getCubes()) {
		    if (!cube.getIsFixed()) {
			cube.dice();			
		    }
		}
		return this.allowedDicings - this.diceCounter;
	}
	/**
	 * Variablen für nächstes Würfeln initialisieren
	 * @param additionalDicings Zusätzliche Würfelversuche (0 oder +/-1)
	 */
	public void initForNextRound(int additionalDicings) {
		this.diceCounter = 0;
		this.allowedDicings = 3 + additionalDicings;
	}
	
}
