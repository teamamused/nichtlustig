package teamamused.client.gui.chooseCards;

import java.util.Hashtable;
import java.util.List;

import teamamused.common.gui.AbstractModel;
import teamamused.common.interfaces.ITargetCard;

/**
 * Diese Klasse ist für die Datenhaltung zu ChooseCards zuständig.
 * 
 * @author Dani und Michelle
 *
 */
public class ChooseCardsModel extends AbstractModel {

	// Speichert die Zielkarten, wenn es mehrere Möglichkeiten gibt
	Hashtable<Integer, List<ITargetCard>> cardsToChooseOptions;

	List<ITargetCard> cardsChoosen = null;

	/**
	 * Holt die initialen Daten vom Client
	 * 
	 * @param cardOptions Auswahlmöglichkeiten welche der Spieler hat
	 * 
	 */
	public ChooseCardsModel(Hashtable<Integer, List<ITargetCard>> cardOptions) {
		super();
		this.cardsToChooseOptions = cardOptions;
	}

}
