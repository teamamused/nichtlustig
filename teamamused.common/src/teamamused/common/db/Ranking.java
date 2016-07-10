package teamamused.common.db;

/**
 * 
 * Datenhaltungsklasse für die Platzierungen der Spieler.
 * Diese werden pro Spiel und gesamthaft geführt
 * 
 * @author Daniel
 *
 */
public class Ranking {
	/** Eindeutige Spielkennungs */
	public int GameId;
	/** Eindeutige Spielerkennung */
	public String Username;
	/** Erreichte Punkte */
	public int Points;
	/** Rang in dem Spiel */
	public int GameRank;
	/** Rang über alle Spiele Spiel */
	public int TotalRank;
	
	/**
	 * Standard Konstruktor
	 */
	public Ranking() {
		super();
	}
	
	/**
	 * Initialisierung eines neuen Ranking Objektes mit den übergebenen parametern
	 * @param gameId Eindeutige Spielkennungs
	 * @param username Eindeutige Spielerkennung
	 * @param points Erreichte Punkte
	 * @param gameRank Rang in dem Spiel
	 * @param totalRank Rang über alle Spiele Spiel
	 */
	public Ranking(int gameId, String username, int points, int gameRank, int totalRank) {
		this();
		GameId = gameId;
		Username = username;
		Points = points;
		GameRank = gameRank;
		TotalRank = totalRank;
	}
	
}
