package teamamused.client.gui.gameboard;

import java.util.Arrays;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import teamamused.client.gui.cardPopup.CardPopupController;
import teamamused.client.gui.cardPopup.CardPopupModel;
import teamamused.client.gui.cardPopup.CardPopupView;
import teamamused.common.ServiceLocator;
import teamamused.common.gui.AbstractController;

public class GameBoardController extends AbstractController<GameBoardModel, GameBoardView> {

	int countDice = 0;

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

		// Zu den DiceControl-Objekten der View wird ein Handler
		// registriert. Durch den Klick auf einen Würfel, wird dieser gesetzt.
		for (DiceControl diceControl : view.diceControlArray) {
			diceControl.setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					DiceControl diceControl = (DiceControl) event.getSource();
					diceControl.getCube().setIsFixed(true);
					diceControl.hideDice();
					int index = Arrays.asList(view.diceControlArray).indexOf(diceControl);
					DiceControl diceControlChosen = new DiceControl(diceControl.getCube());
					diceControlChosen.showDice();
					view.dicePane.add(diceControlChosen, index, 10);
				}
			});
		}

		// Bei jedem Klick auf den Button zum Würfeln werden die Würfel neu
		// gewürfelt. Bereits gesetzte Würfel können nicht erneut gewürfelt
		// werden.
		view.btnWuerfeln.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				countDice++;
				if (countDice <= 3) {
					model.dice();
					for (DiceControl diceControl : view.diceControlArray) {
						if (!diceControl.getCube().getIsFixed()) {
							diceControl.showDice();
						}
					}
				}
			}
		});

		view.btnPlayer1.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				showPopup();
			}
		});
	}

	public void showPopup() {
		Stage popupStage = new Stage();
		CardPopupModel model = new CardPopupModel();
		CardPopupView cardPopupView = new CardPopupView(popupStage, model);
		new CardPopupController(model, cardPopupView);
		cardPopupView.start();
	}

}
