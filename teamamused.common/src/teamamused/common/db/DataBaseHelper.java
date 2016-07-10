package teamamused.common.db;

import java.time.LocalDateTime;
import java.util.Arrays;
import teamamused.common.ServiceLocator;
import teamamused.common.interfaces.IDataBaseContext;

/**
 * 
 * Diverse Hilfsfunktionen für die Datenverarbeitung
 * 
 * @author Daniel
 *
 */
public class DataBaseHelper {

	/**
	 * Anlegen von Testdaten für Unit Tests und zum pröbeln
	 * @param players Spieler Testdaten anlegen
	 * @param gameInfos Game Testdaten anlegen
	 * @param rankings Rangierungs Testdaten anlegen
	 */
	public static void createDemoData(boolean players, boolean gameInfos, boolean rankings) {
		IDataBaseContext db = ServiceLocator.getInstance().getDBContext();
		if (players) {
			PlayerInfo piMaja = new PlayerInfo("Maja", "lustig".hashCode());
			db.addPlayerInfo(piMaja);

			PlayerInfo pjMichelle = new PlayerInfo("Michelle", "dino".hashCode());
			db.addPlayerInfo(pjMichelle);

			PlayerInfo pjSandra = new PlayerInfo("Sandra", "lemming".hashCode());
			db.addPlayerInfo(pjSandra);

			PlayerInfo piDaniel = new PlayerInfo("Daniel", "geheim".hashCode());
			db.addPlayerInfo(piDaniel);
		}
		
		if (gameInfos) {
			GameInfo gi1 = new GameInfo(1, LocalDateTime.now().minusDays(5), LocalDateTime.now().minusDays(5).plusMinutes(32), "Sandra", "Maja", "Michelle", "Daniel");
			db.addGame(gi1);

			GameInfo gi2 = new GameInfo(2, LocalDateTime.now().minusDays(2).plusHours(10), LocalDateTime.now().minusDays(2).plusHours(10).plusMinutes(26), "Sandra", "Maja", "Michelle");
			db.addGame(gi2);
		}
		
		if (rankings) {
			Ranking[] ranks = new Ranking[4];
			ranks[0] = new Ranking(1, "Michelle", 20, 1, 0);
			ranks[1] = new Ranking(1, "Sandra", 12, 2, 0);
			ranks[2] = new Ranking(1, "Maja", 8, 3, 0);
			ranks[3] = new Ranking(1, "Daniel", -2, 4, 0);
			db.addRankings(Arrays.asList(ranks));

			Ranking[] ranks2 = new Ranking[3];
			ranks2[2] = new Ranking(2, "Maja", 22, 1, 0);
			ranks2[1] = new Ranking(2, "Sandra", 12, 2, 0);
			ranks2[0] = new Ranking(2, "Michelle", 6, 3, 0);
			db.addRankings(Arrays.asList(ranks2));
		}
	}

}