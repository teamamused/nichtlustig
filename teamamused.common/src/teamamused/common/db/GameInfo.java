package teamamused.common.db;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Dieses Klasse enthält die wichtigen Spielinformationen.
 * 
 * @author Daniel
 *
 */
public class GameInfo {

	/** Eindeutige Spielerkennung	 */
	public int GameId = 0;
	
	/** Startzeit des Spieles	 */
	public LocalDateTime StartTime = LocalDateTime.MIN;
	
	/** Endzeit des Spieles	 */
	public LocalDateTime EndTime = LocalDateTime.MIN;
	
	/** Spielername der Spielteilnehmer	 */
	public List<String> Players = new ArrayList<String>();
	
	/**
	 * Default Konstruktor
	 */
	public GameInfo() {
		super();
	}
	/**
	 * Konstrukter der ein Objekt mit den übergebenen Parametern erstellt 
	 * 
	 * @param gameId Eindeutige Spielerkennung
	 * @param startTime Startzeit des Spieles
	 * @param endTime Endzeit des Spieles
	 * @param players Spielernamen der Spielteilnehmer
	 */
	public GameInfo(int gameId, LocalDateTime startTime, LocalDateTime endTime, String... players) {
		this();
		this.GameId = gameId;
		this.StartTime = startTime;
		this.EndTime = endTime;
		for (String player : players) {
			this.Players.add(player);
		}
	}
}
