package teamamused.server;

import teamamused.common.ServiceLocator;
import teamamused.common.interfaces.ICube;
import teamamused.common.models.cubes.CubeColor;
import teamamused.common.models.cubes.CubeValue;

/**
 * 
 * Die Klasse Cube merkt sich wie oft der aktive Spieler gewürfelt hat und merkt
 * sich die fixierten Würfel.
 * 
 * @author Maja Velickovic
 *
 */

public class CubeManager{
	private static CubeManager instance;
	private int diceCounter = 0;
	private int allowedDicings = 3;
	private int counterFixedCubes = 0;
	private ICube pinkCube;
	
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
	
	/**
	 * Setzt das aktuelle Spiel zurück.
	 * Kein Speichern des Spielstandes
	 * Spiel startet von vorne
	 */
	public static void resetCubeManager() {
		instance = new CubeManager();
	}

	/**
	 * Liest den aktuellen Wert des angegebenen Würfels aus.
	 * @param cube Würfel, von dem man den Wert auslesen möchte
	 * @return Wert von Würfel
	 */
	public CubeValue getCurrentValue(ICube cube){
		return cube.getCurrentValue();
	}
	
	/**
	 * Zähler zurück seten für Würfel. Fixierung aufheben.
	 */
	public void resetCounters(){
		diceCounter = 0;
		allowedDicings = 3;
		counterFixedCubes = 0;
		ICube[] currentCubes = getCubes();
		for(ICube cube : currentCubes){
			cube.setIsFixed(false);
			cube.resetCubeValues();
		}
	}
	
	/**
	 * Methode, um die Würfel auf dem Spielbrett zu würfeln.
	 * @return Anzahl verbleibende Versuche
	 */
	public int rollDices(){
		ServiceLocator.getInstance().getLogger().info("CubeManager: rolle Würfel");
		this.diceCounter++;
		for (ICube cube: getCubes()) {
		    if (!cube.getIsFixed()) {
		    	cube.dice();
		    }
		}
		return this.allowedDicings - this.diceCounter;
	}
	
	/**
	 * Variablen für nächstes Würfeln initialisieren
	 * @param additionalDicings Zusätzliche Würfelversuche (0 oder +/-1)
	 * @return Anzahl Versuche
	 */
	public int initForNextRound(int additionalDicings) {
		ICube[] currentCubes = getCubes();
		for(ICube cube : currentCubes){
			cube.setIsFixed(false);
		}
		this.diceCounter = 0;
		this.allowedDicings = 3 + additionalDicings;
		return this.allowedDicings;
	}
	
	/**
	 * Würfel, welche der Spieler ausgewählt hat, effektiv fixieren lassen.
	 * @param cubesToFix zu fixierende Würfel
	 */
	public void saveFixedDices(boolean[] cubesToFix){
		this.counterFixedCubes = 0;
		for (int i = 0; i<cubesToFix.length; i++) {
			BoardManager.getInstance().getGameBoard().getCubes()[i].setIsFixed(cubesToFix[i]);
			if (cubesToFix[i]) {
				counterFixedCubes++;
			}
		}
	}
	
	/**
	 * Die Methode prüft wieviele Würfel fixiert sind und gibt die entsprechende
	 * Anzahl zurück.
	 * @return gibt die Anzahl fixierter Würfel zurück
	 */
	public int getCountOfFixedDices(){
		return counterFixedCubes;
	}
	
	/**
	 * Liest den pinken Würfel aus und gibt diesen zurück.
	 * @return pinker Würfel
	 */
	public CubeValue getCurrentPinkCube(){
		for(ICube cube : getCubes()){
			if(cube.getCubeColor() == CubeColor.Pink){
				pinkCube = cube;
			}
		}
		return pinkCube.getCurrentValue();
	}
	
	/**
	 * Gibt einen Array von allen aktuellen Würfeln zurück.
	 * @return alle Würfel als Array
	 */
	public ICube[] getCubes(){
		return BoardManager.getInstance().getGameBoard().getCubes();
	}
	
	/**
	 * Prüft ob alle Würfel fixiert sind.
	 * @return true wenn alle fixiert sonst false
	 */
	public boolean getAllCubesFixed(){
		for(ICube cube : getCubes()){
			if(!cube.getIsFixed()){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Getter für die Anzahl verbleibende Würfelversuche des aktiven Players
	 * @return Anzahl Versuche
	 */
	public int getRemainingTries() {
		return this.allowedDicings - this.diceCounter;
	}
}
