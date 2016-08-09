package teamamused.client.gui.movingPopup;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import teamamused.client.gui.gameboard.GameBoardModel;
import teamamused.common.gui.AbstractController;

/**
 * Die Controller-Klasse nimmt Benutzerinteraktionen der MovingPopupView entgegen.
 * 
 * @author Michelle
 *
 */
public class MovingPopupController extends AbstractController<GameBoardModel, MovingPopupView> {

	public MovingPopupController(GameBoardModel model, MovingPopupView view) {
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