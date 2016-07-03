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
public abstract class DataBaseContext implements IDataBaseContext {
	
	protected ArrayList<Ranking> rankings;
	protected ArrayList<PlayerInfo> playerInfos;
	protected ArrayList<GameInfo> gameInfos;

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
		this.gameInfos.add(gameInfo);
		return true;
	}

	@Override
	public boolean addPlayerInfo(PlayerInfo newPlayer) {
		this.playerInfos.add(newPlayer);
		return false;
	}

	@Override
	public boolean addRankings(List<Ranking> newRanks) {
		this.rankings.addAll(newRanks);
		return false;
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
