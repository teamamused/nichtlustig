package teamamused.playground.application.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import teamamused.common.db.Ranking;
import teamamused.common.gui.AbstractModel;

public class RankingModel extends AbstractModel {
	
    ObservableList<Ranking> ranking = FXCollections.observableArrayList();
	
	public RankingModel(Ranking[] rankings) {
		super();
		for (Ranking r : rankings) {
			ranking.add(r);
		}
	}
}
