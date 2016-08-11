package teamamused.client.gui.gameOver;

import teamamused.common.db.Ranking;
import teamamused.common.gui.AbstractModel;

public class GameOverModel extends AbstractModel {
	protected Ranking[] ranking;
	protected Ranking winner;
    
    /**
     * Der Konstruktor holt die initialen Daten vom Client
     */
    public GameOverModel(Ranking[] ranking) {
    	super();
    	this.ranking = ranking;
    	
    	// Der Gewinner befindet sich an Index 0 des Ranking-Arrays
    	winner = ranking[0];
    }
}
