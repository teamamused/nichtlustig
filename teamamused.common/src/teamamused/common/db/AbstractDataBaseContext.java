package teamamused.common.db;

import java.util.ArrayList;
import java.util.List;

import teamamused.common.interfaces.IDataBaseContext;

/**
 * Superklasse aller spezifizierten Datenbank Kontext klassen
 * Hier ist die Grundfunktionalität definiert.
 * Das Laden und zurückschreiben der Daten muss dann für jede spezifizierung selber implementiert werden.
 * 
 * @author Daniel
 *
 */
public abstract class AbstractDataBaseContext implements IDataBaseContext {
	
	protected ArrayList<Ranking> rankings = null;
	protected ArrayList<PlayerInfo> playerInfos = null;
	protected ArrayList<GameInfo> gameInfos = null;

	@Override
	public ArrayList<Ranking> getRankings() {
		return this.rankings;
	}

	@Override
	public ArrayList<PlayerInfo> getPlayerInfos() {
		return this.playerInfos;
	}

	@Override
	public ArrayList<GameInfo> getGameInfos() {
		return this.gameInfos;
	}

	@Override
	public boolean addGame(GameInfo gameInfo) {
		return this.gameInfos.add(gameInfo);
	}

	@Override
	public boolean addPlayerInfo(PlayerInfo newPlayer) {
		return this.playerInfos.add(newPlayer);
	}

	@Override
	public boolean addRankings(List<Ranking> newRanks) {
		return this.rankings.addAll(newRanks);
	}

	@Override
	public abstract boolean loadContext();

	@Override
	public abstract boolean saveContext();

	
	protected void initEmptyLists() {
		if (this.playerInfos == null) {
			this.playerInfos = new ArrayList<PlayerInfo>();
		}
		if (this.gameInfos == null) {
			this.gameInfos = new ArrayList<GameInfo>();
		}
		if (this.rankings == null) {
			this.rankings = new ArrayList<Ranking>();
		}		
	}

}
