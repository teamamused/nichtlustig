package teamamused.common.db;

import java.util.Comparator;

/**
 * Comparar Klasse zum Ranking nach erreichten Punkten.
 * 
 * @author Daniel
 *
 */
public class RankingPointComperator implements Comparator<Ranking> {
	@Override 
	public int compare(Ranking o1, Ranking o2) { 
		return (o2.getPoints() - o1.getPoints()); 
	}
}
