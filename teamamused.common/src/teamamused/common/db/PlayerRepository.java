package teamamused.common.db;

import java.util.List;
import java.util.stream.Stream;

import teamamused.common.ServiceLocator;
import teamamused.common.interfaces.IDataBaseContext;
import teamamused.common.models.Player;

/**
 * Sammelstelle für alle Datenbank ab / anfragen welche den Spieler betreffen
 *  
 * @author Daniel
 *
 */
public class PlayerRepository {

	/**
	 * gibt die PlayerInformationen anhand des Benutzernamens zurück
	 * @param username Benutzername
	 * @return Neues PlayerInfo Objekt
	 */
	public static PlayerInfo getPlayerInfoByUserName(String username) {
		IDataBaseContext db = ServiceLocator.getInstance().getDBContext();
		// Player nach Username abfragen
		Stream<PlayerInfo> players = db.getPlayerInfos().stream().filter(x -> x.Username == username);
		// Prüffen ob ein Player vorhanden ist
		if (players.count() > 0) {
			// falls vorhanden waren diesen zurückgeben
			return players.findFirst().get();
		} else {
			// falls keiner vorhanden null zurückgeben
			return null;
		}
	}

	/**
	 * Prüft ob ein Spieler mit den übergebenen anmeldeinformationen vorhanden ist
	 * 
	 * @param username Benutzername
	 * @param password Passwort
	 * @return falls vorhanden Spieler, ansonsten null
	 * @throws Exception Exception wird geworfen wenn der Benutzername bereits vorhanden ist.
	 */
	public static Player createPlayer(String username, String password) throws Exception {
		// Prüfen ob der Benutzername bereits vorhanden ist
		PlayerInfo pi = PlayerRepository.getPlayerInfoByUserName(username);
		if (pi != null) {
			throw new Exception("Der Benutzername ist bereits vorhanden");
		}
		// Benutzer anlegen
		pi = new PlayerInfo(username, password.hashCode());
		ServiceLocator.getInstance().getDBContext().addPlayerInfo(pi);
		// Player Objekt zurückgeben
		return new Player(username);
	}

	/**
	 * Prüft ob ein Spieler mit den übergebenen anmeldeinformationen vorhanden ist
	 * 
	 * @param username Benutzername
	 * @param password Passwort
	 * @return falls vorhanden Spieler, ansonsten null
	 */
	public static Player validatePlayerLogin(String username, String password) {
		List<PlayerInfo> pis = ServiceLocator.getInstance().getDBContext().getPlayerInfos();
		Stream<PlayerInfo> matchedPis = pis.stream().filter(x -> x.Username == username && x.PwHash == password.hashCode());
		if (matchedPis.count()>0) {
			// PlayerInfo pi = matchedPis.findFirst().get();
			return new Player(username);
		} else {
			return null;
		}
	}
}
