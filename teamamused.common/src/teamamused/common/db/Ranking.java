package teamamused.common.db;

import java.io.Serializable;

/**
 * 
 * Datenhaltungsklasse für die Platzierungen der Spieler.
 * Diese werden pro Spiel und gesamthaft geführt
 * 
 * @author Daniel
 *
 */
public class Ranking implements Serializable, Comparable<Ranking> {
	/** Versionsnummer für Serialisierung	 */
	private static final long serialVersionUID = 1L;
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
	
	/**
	 * Vergleichsmethode Anhand des TotalRankings und wenn diese 0 sind anhand des inGameRankings
	 */
	public int compareTo(Ranking rank2) {
		// wenn Total Ranks noch 0 sind ist es ein in Game Ranking
		if (this.TotalRank == 0 && rank2.TotalRank == 0) {
			return this.GameRank - rank2.GameRank;
		}
		// Sortieren anhand der Rangierungen
		return this.TotalRank - rank2.TotalRank;
		
	}	
	
	public String toString() {
		return this.TotalRank + " - " + this.Username + " Total Punkte: " + this.Points;  
	}
}
