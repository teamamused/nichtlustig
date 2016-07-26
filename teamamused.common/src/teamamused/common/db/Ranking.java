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
	private int gameId;
	/** Eindeutige Spielerkennung */
	private String username;
	/** Erreichte Punkte */
	private int points;
	/** Rang in dem Spiel */
	private int gameRank;
	/** Rang über alle Spiele Spiel */
	private int totalRank;
	
	
	/**
	 * @return the gameId
	 */
	public int getGameId() {
		return gameId;
	}

	/**
	 * @param gameId the gameId to set
	 */
	public void setGameId(int gameId) {
		this.gameId = gameId;
	}
	/**
	 * @return the gameId
	 */
	public String getUsername() {
		return this.username;
	}

	/**
	 * @param gameId the gameId to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the points
	 */
	public int getPoints() {
		return this.points;
	}

	/**
	 * @param points the points to set
	 */
	public void setPoints(int points) {
		this.points = points;
	}

	/**
	 * @return the gameRank
	 */
	public int getGameRank() {
		return this.gameRank;
	}

	/**
	 * @param gameRank the gameRank to set
	 */
	public void setGameRank(int gameRank) {
		this.gameRank = gameRank;
	}

	/**
	 * @return the totalRank
	 */
	public int getTotalRank() {
		return this.totalRank;
	}

	/**
	 * @param totalRank the totalRank to set
	 */
	public void setTotalRank(int totalRank) {
		this.totalRank = totalRank;
	}
	
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
		this.gameId = gameId;
		this.username = username;
		this.points = points;
		this.gameRank = gameRank;
		this.totalRank = totalRank;
	}
	
	/**
	 * Vergleichsmethode Anhand des TotalRankings und wenn diese 0 sind anhand des inGameRankings
	 */
	public int compareTo(Ranking rank2) {
		// wenn Total Ranks noch 0 sind ist es ein in Game Ranking
		if (this.totalRank == 0 && rank2.totalRank == 0) {
			return this.gameRank - rank2.gameRank;
		}
		// Sortieren anhand der Rangierungen
		return this.totalRank - rank2.totalRank;
		
	}	
	
	public String toString() {
		return this.totalRank + " - " + this.username + " Total Punkte: " + this.points;  
	}
}
