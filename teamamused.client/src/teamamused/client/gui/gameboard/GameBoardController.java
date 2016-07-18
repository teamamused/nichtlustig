package teamamused.client.gui.gameboard;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import teamamused.common.ServiceLocator;
import teamamused.common.gui.AbstractController;

public class GameBoardController extends AbstractController<GameBoardModel, GameBoardView> {

	public GameBoardController(GameBoardModel model, GameBoardView view) {
		super(model, view);

		// Auf dem Hyperlink wird ein ActionEvent registiert, welches den
		// Browser öffnet und das entsprechende HTML-Dokument zurückgibt
//		view.linkAnleitung.setOnAction(new EventHandler<ActionEvent>() {
//			@Override
//			public void handle(ActionEvent event) {
//				ServiceLocator.getInstance().getHostServices().showDocument(view.linkAnleitung.getText());
//			}
//		});
	}

}
