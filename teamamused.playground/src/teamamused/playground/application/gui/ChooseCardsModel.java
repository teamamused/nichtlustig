package teamamused.playground.application.gui;

import java.util.Hashtable;
import java.util.List;

import teamamused.common.gui.AbstractModel;
import teamamused.common.interfaces.ITargetCard;

public class ChooseCardsModel extends AbstractModel {
    
    // Falls es mehrere Zielkarten gibt zum auswählen, hier die Auswahl
    Hashtable<Integer, List<ITargetCard>> cardsToChooseOptions;
    
	List<ITargetCard> cardsChoosen = null;
    
    
    /**
     * Konstruktor, initial Daten von Client holen
     */
    public ChooseCardsModel(Hashtable<Integer, List<ITargetCard>> cardOptions) {
    	super();
    	this.cardsToChooseOptions = cardOptions;
    }
    
}
