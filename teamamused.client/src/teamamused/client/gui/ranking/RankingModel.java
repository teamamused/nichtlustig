package teamamused.client.gui.ranking;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import teamamused.common.db.Ranking;
import teamamused.common.gui.AbstractModel;

public class RankingModel extends AbstractModel {

	ObservableList<Ranking> ranking = FXCollections.observableArrayList();
	boolean isGameFinished = false;

	Ranking[] rankings;
	
	public RankingModel(Ranking[] rankings, boolean isGameFinished) {
		super();
		this.isGameFinished = isGameFinished;
		for (Ranking r : rankings) {
			ranking.add(r);
		}
	}


}
