package teamamused.common.db;

import java.util.List;
import java.util.Optional;
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
	 * @return PlayerInfo Objekt, null wenn Username nicht vorhanden
	 */
	public static PlayerInfo getPlayerInfoByUserName(String username) {
		IDataBaseContext db = ServiceLocator.getInstance().getDBContext();
		// Player nach Username abfragen
		Optional<PlayerInfo> piFound = db.getPlayerInfos().stream().filter(x -> x.Username.equals(username)).findFirst();
		// Prüffen ob ein Player vorhanden ist
		if (piFound.isPresent()) {
			// falls vorhanden waren diesen zurückgeben
			return piFound.get();
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
		// Player nach Username abfragen
		Optional<PlayerInfo> piFound = pis.stream().filter(x -> x.Username.equals(username) && x.PwHash == password.hashCode()).findFirst();
		// Prüffen ob ein Player vorhanden ist
		if (piFound.isPresent()) {
			// PlayerInfo pi = matchedPis.findFirst().get();
			return new Player(username);
		} else {
			return null;
		}
	}
	
	public static boolean isUsernameTaken(String username) {
		IDataBaseContext db = ServiceLocator.getInstance().getDBContext();
		// Player nach Username abfragen
		//System.out.println("es sind " + db.getPlayerInfos().size() + " User vorhanden");
		Stream<PlayerInfo> players = db.getPlayerInfos().stream().filter(x -> x.Username.equals(username));
		// Prüffen ob ein Player vorhanden ist
		if (players.count() > 0) {
			return true;
		}
		return false;
	}
}
