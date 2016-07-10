package teamamused.common.db;

import java.util.OptionalInt;

import teamamused.common.ServiceLocator;
import teamamused.common.interfaces.IDataBaseContext;

/**
 * 
 * Sammelstelle für alle Datenbank ab / anfragen welche die GameInfos betreffen
 * 
 * @author Daniel
 *
 */
public class GameInfoRepository {


	/**
	 * Gibt die nächste freie GameId zurück
	 * @return
	 * 		Id des letzten Games plus 1 oder 1 falls noch kein Game vorhanden
	 */
	public static int getNextGameId() {
		IDataBaseContext db = ServiceLocator.getInstance().getDBContext();
		// Höchste Game Id aus DB auslesen
		OptionalInt currId = db.getGameInfos().stream().mapToInt(g -> g.GameId).max();
		// Prüffen ob Ids vorhanden waren
		if (currId.isPresent()) {
			// falls vorhanden waren diese + 1 zurückgeben
			return currId.getAsInt() + 1;
		} else {
			// falls keine vorhanden waren 1 zurückgeben
			return 1;
		}
	}
}
