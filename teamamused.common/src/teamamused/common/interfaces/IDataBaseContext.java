package teamamused.common.interfaces;

import java.util.List;

import teamamused.common.db.*;

/**
 * Schnittstele für die Datenbankinteraktionen
 * 
 * Durch das separieren mit dem Interface können wir zur Laufzeit die Daten aus einer XML Datei laden
 * und für die Unit-Tests aus dem Memory
 * 
 * @author Daniel
 *
 */
public interface IDataBaseContext {
	
	/**
	 * Gibt eine Liste sämtlicher Platzierungen zurück 
	 * @return  Liste mit allen Rankings
	 */
	public List<Ranking> getRankings();

	/**
	 * Gibt eine Liste sämtlicher Spieler Informationen zurück 
	 * @return Liste mit allen PlayerInfos
	 */
	public List<PlayerInfo> getPlayerInfos();

	/**
	 * Gibt eine Liste sämtlicher Spielinferomationen zurück 
	 * @return Liste mit allen GameInfos
	 */
	public List<GameInfo> getGameInfos();
	
	/**
	 * Fügt ein neue Spielinformationen hinzu
	 * @param gameInfo spielinformationen
	 * @return hinzufügen erfolgreich
	 */
	boolean addGame(GameInfo gameInfo);

	/**
	 * Fügt einen neuen Spieler hinzu
	 * @param newPlayer Spieler zum hinzufügen
	 * @return hinzufügen erfolgreich
	 */
	boolean addPlayerInfo(PlayerInfo newPlayer);

	/**
	 * Fügt neue Platzierungen hinzu
	 * @param newRanks Platzierungen hinzufügen
	 * @return hinzufügen erfolgreich
	 */
	boolean addRankings(List<Ranking> newRanks);

	/**
	 * Lädt die Daten aus der Quelle in den Speichern
	 * @return erfolgreich
	 */
	boolean loadContext();
	
	/**
	 * speichert die Daten aus dem Speicher in die Quelle zurück
	 * @return erfolgreich
	 */
	boolean saveContext();
}
