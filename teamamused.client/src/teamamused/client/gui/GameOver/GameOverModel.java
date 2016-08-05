package teamamused.client.gui.GameOver;

import teamamused.common.db.Ranking;
import teamamused.common.gui.AbstractModel;

public class GameOverModel extends AbstractModel {
	protected Ranking[] ranking;
	protected Ranking winner;
    
    /**
     * Konstruktor, initial Daten von Client holen
     */
    public GameOverModel(Ranking[] ranking) {
    	super();
    	this.ranking = ranking;
    	
    	winner = ranking[0];
    }
}
