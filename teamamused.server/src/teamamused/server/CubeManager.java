package teamamused.server;

import java.util.List;

import teamamused.common.ServiceLocator;
import teamamused.common.interfaces.ICube;
import teamamused.common.models.GameBoard;
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
	private GameBoard board = BoardManager.getInstance().getGameBoard();
	private ICube cubes[] = board.getCubes();
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
		ICube[] currentCubes = BoardManager.getInstance().getGameBoard().getCubes();
		for(ICube cube : currentCubes){
			cube.setIsFixed(false);
			cube.resetCubeValues();
		}
	}
	
	/**
	 * Methode, um die Würfel auf dem Spielbrett zu würfeln.
	 * @return 
	 */
	public int rollDices(){
		ServiceLocator.getInstance().getLogger().info("CubeManager: rolle Würfel");
		this.diceCounter++;
		for (ICube cube: BoardManager.getInstance().getGameBoard().getCubes()) {
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
	
	/**
	 * Würfel, welche der Spieler ausgewählt hat, effektiv fixieren lassen.
	 * @param cubesToFix zu fixierende Würfel
	 */
	public void saveFixedDices(List <ICube> cubesToFix){
		for(ICube cube : cubesToFix){
			cube.setIsFixed(true);
			counterFixedCubes++;
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
	
	public CubeValue getCurrentPinkCubeValue(){
		for(ICube cube : cubes){
			if(cube.getCubeColor() == CubeColor.Pink){
				pinkCube = cube;
			}
		}
		return pinkCube.getCurrentValue();
	}
	
	public ICube[] getCubes(){
		return cubes;
	}
}
