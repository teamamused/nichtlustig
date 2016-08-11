package teamamused.client.gui.cardPopup;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import teamamused.client.gui.gameboard.GameBoardModel;
import teamamused.common.gui.AbstractController;

/**
 * Diese Controller-Klasse nimmt die Benutzerinteraktionen des CardPopups
 * entgegen.
 * 
 * @author Michelle
 *
 */
public class CardPopupController extends AbstractController<GameBoardModel, CardPopupView> {

	public CardPopupController(GameBoardModel model, CardPopupView view) {
		super(model, view);
		
		// Der Klick auf den Schliessen-Button schliesst das Fenster
		view.btnClose.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				view.stop();
			}
		});

	}
}