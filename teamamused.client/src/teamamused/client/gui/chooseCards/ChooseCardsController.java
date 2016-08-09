package teamamused.client.gui.chooseCards;

import teamamused.client.libs.IClientListener;
import teamamused.common.gui.AbstractController;
import teamamused.client.libs.Client;

public class ChooseCardsController extends AbstractController<ChooseCardsModel, ChooseCardsView> implements IClientListener {
	/**
	 * Konstruktor des GameboardControllers
	 * 
	 * @param model
	 *            Instanz des GameBoardModel
	 * @param view
	 *            Instanz der GameBoardView
	 */
	public ChooseCardsController(ChooseCardsModel model, ChooseCardsView view) {
		super(model, view);
		// Dem Client Mitteilen dass man über aktualisierungen informiert werden
		// will
		Client.getInstance().registerGui(this);
	}

}
