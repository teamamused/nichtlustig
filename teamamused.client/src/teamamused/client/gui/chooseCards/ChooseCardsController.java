package teamamused.client.gui.chooseCards;

import teamamused.client.libs.IClientListener;
import teamamused.common.gui.AbstractController;
import teamamused.client.libs.Client;

/**
 * Diese Controller-Klasse nimmt die Benutzerinteraktionen der ChooseCards
 * entgegen.
 * 
 * @author Michelle
 *
 */
public class ChooseCardsController extends AbstractController<ChooseCardsModel, ChooseCardsView> implements IClientListener {

	public ChooseCardsController(ChooseCardsModel model, ChooseCardsView view) {
		super(model, view);
		// Das GUI wird für Aktualisierungen registriert.
		Client.getInstance().registerGui(this);
	}

}
