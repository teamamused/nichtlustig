package teamamused.client.gui.GameOver;

import teamamused.common.db.Ranking;
import teamamused.common.gui.AbstractModel;

public class GameOverModel extends AbstractModel {
	Ranking[] ranking;
    
    /**
     * Konstruktor, initial Daten von Client holen
     */
    public GameOverModel(Ranking[] ranking) {
    	super();
    	this.ranking = ranking;
    }
}
