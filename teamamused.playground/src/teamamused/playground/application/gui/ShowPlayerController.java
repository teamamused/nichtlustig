package teamamused.playground.application.gui;

import teamamused.client.libs.IClientListener;
import teamamused.common.gui.AbstractController;
import teamamused.client.libs.Client;

public class ShowPlayerController extends AbstractController<ShowPlayerModel, ShowPlayerView> implements IClientListener {
	/**
	 * Konstruktor des GameboardControllers
	 * 
	 * @param model
	 *            Instanz des GameBoardModel
	 * @param view
	 *            Instanz der GameBoardView
	 */
	public ShowPlayerController(ShowPlayerModel model, ShowPlayerView view) {
		super(model, view);
		// Dem Client Mitteilen dass man über aktualisierungen informiert werden
		// will
		Client.getInstance().registerGui(this);
	}

}
