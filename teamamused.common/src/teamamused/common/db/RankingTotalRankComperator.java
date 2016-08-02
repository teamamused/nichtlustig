package teamamused.common.db;

import java.util.Comparator;

public class RankingTotalRankComperator implements Comparator<Ranking> {
	@Override 
	public int compare(Ranking o1, Ranking o2) { 
		if (o1.getTotalRank() == o2.getTotalRank()) {
			return o1.getGameRank() - o2.getGameRank();
		}
		return (o1.getTotalRank() - o2.getTotalRank()); 
	}
}
