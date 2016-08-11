package teamamused.common.db;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.Map.Entry;

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
	 * 		Array mit dem Ranking des Spieles
	 */		
	public static Ranking[] getInGameRanking(int gameId, Hashtable<IPlayer, Integer> playerPoints) {
		IDataBaseContext db = ServiceLocator.getInstance().getDBContext();
		ArrayList<Ranking> ranks = new ArrayList<Ranking>();

		// Ranking Objekte erstellen noch ohne Platzierung
		for (Entry<IPlayer, Integer> pp : playerPoints.entrySet()) {
			//System.out.println("Ranking 1 : " + pp.getKey().getPlayerName() + " - " + pp.getValue());
			Ranking r = new Ranking();
			r.setUsername(pp.getKey().getPlayerName());
			r.setGameId(gameId);
			//r.setGameRank(rank);
			r.setPoints(pp.getValue());
			ranks.add(r);
		}
		// Ranking Objekte sortieren und Rang zuteilen 
		int rank = 0;
		int prevPoints = 0;
		Ranking[] inGameranking = ranks.stream().sorted().toArray(x -> new Ranking[x]);
		for (int i = 0; i < inGameranking.length; i++) {
			if (inGameranking[i].getPoints() != prevPoints) {
				prevPoints = inGameranking[i].getPoints();
				rank++;
			}
			//System.out.println("Ranking 2 : " + inGameranking[i].getUsername() + " - " + inGameranking[i].getPoints() + " - " + rank);
			inGameranking[i].setGameRank(rank);
		}
		
		// Der DB hinzufügen
		db.addRankings(Arrays.asList(inGameranking));
		// Alle Rankings in der DB nachführen
		adjustTotalRanking();
		// Rankings wieder aus DB holen (jetzt sind die Toalplatzierungen gesetzt)
		Ranking[] ranksFromDB = db.getRankings().stream().filter(x -> x.getGameId() == gameId).toArray(x -> new Ranking[x]);
		return ranksFromDB;
	}
	
	/**
	 * Gibt die Bestenliste sortiert als Array zurück
	 * @return Array mit dem Ranking aller Spiele
	 */
	public static Ranking[] getTopRanking() {
		return ServiceLocator.getInstance().getDBContext().getRankings().stream().sorted().toArray(x -> new Ranking[x]);
	}
	
	private static void adjustTotalRanking() {
		// Alle Einträge hollen
		List<Ranking> allRanks = ServiceLocator.getInstance().getDBContext().getRankings();//.stream().sorted().toArray(x -> new Ranking[x]);
		// Einträge nach Punkte sortieren
		allRanks.sort(new RankingPointComperator());
		// Ranking Objekte sortieren und Rang zuteilen 
		int rankNr = 0;
		int prevPoints = 0;
		// Einträge durchlaufen und Rang zuteilen
		for (Ranking rank : allRanks) {
			if (rank.getPoints() != prevPoints) {
				prevPoints = rank.getPoints();
				rankNr++;
			}
			rank.setTotalRank(rankNr);
			//System.out.println("Ranking 3 : " + rank.getUsername() + " - " + rank.getPoints() + " - " + rankNr + " ig: " + rank.getGameRank());
		}
	}
}
