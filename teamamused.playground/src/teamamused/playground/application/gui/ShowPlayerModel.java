package teamamused.playground.application.gui;

import teamamused.common.gui.AbstractModel;
import teamamused.common.interfaces.IPlayer;

public class ShowPlayerModel extends AbstractModel {
    
    // Falls es mehrere Zielkarten gibt zum auswählen, hier die Auswahl
	IPlayer player;
    
    /**
     * Konstruktor, initial Daten von Client holen
     */
    public ShowPlayerModel(IPlayer player) {
    	super();
    	this.player = player;
    }
    
}
