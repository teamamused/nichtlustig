package teamamused.client.gui.gameboard;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;
import teamamused.common.ServiceLocator;
import teamamused.common.gui.AbstractController;

public class GameBoardController extends AbstractController<GameBoardModel, GameBoardView> {

	public GameBoardController(GameBoardModel model, GameBoardView view) {
		super(model, view);

		// Auf dem Hyperlink wird ein ActionEvent registiert, welches den
		// Browser öffnet und das entsprechende HTML-Dokument zurückgibt
		view.linkAnleitung.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				ServiceLocator.getInstance().getHostServices().showDocument(view.linkAnleitung.getText());
			}
		});
		
		// TODO: Richtigen handler implementieren
		for(Canvas c : view.cubeCanvas) {
			c.setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					ServiceLocator.getInstance().getLogger().info("Dice clicked");
				}
			});
		}
		
		view.btnWuerfeln.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				model.dice();
			}
		});
	}
	

}
