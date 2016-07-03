package teamamused.common.db;

import java.util.Arrays;
import java.util.Hashtable;
import java.util.Map.Entry;
import java.util.stream.Stream;

import teamamused.common.ServiceLocator;
import teamamused.common.interfaces.IDataBaseContext;
import teamamused.common.interfaces.IPlayer;

/**
 * Sammelstelle für alle Datenbank ab / anfragen welche das Ranking betreffen
 * 
 * @author Daniel
 */
public class RankingRepository {

	/**
	 * Gibt ein Array mit den Rankings zurück anhand des Spieles und einem Hashtable mit den Punkten pro spieler
	 * @param gameId 
	 * 				Id des aktuellen spieles
	 * @param playerPoints
	 * 				HashTable mit den Spielern als Key und den erreichten Punkten als Value
	 * @return
	 */
	public static Ranking[] getInGameRanking(int gameId, Hashtable<IPlayer, Integer> playerPoints) {
		IDataBaseContext db = ServiceLocator.getInstance().getDBContext();
		Ranking[] ranks = new Ranking[playerPoints.size()];
		// HashTable in ein Ranking Array machen 
		int rank = 1;
		for (Entry<IPlayer, Integer> pp : playerPoints.entrySet()) {
			Ranking r = new Ranking();
			r.Username = pp.getKey().getPlayerName();
			r.GameId = gameId;
			r.GameRank = rank;
			ranks[rank -1] = r;
			rank ++;
		}
		// Der DB hinzufügen
		db.addRankings(Arrays.asList(ranks));
		// Von DB auslesen
		Stream<Ranking> ranksFromDB = db.getRankings().stream().filter(x -> x.GameId == gameId);
		if (ranksFromDB.count()> 0) {
			return ranksFromDB.toArray(size -> new Ranking[size]);
		} else {
			return new Ranking[0];
		}
	}
}
