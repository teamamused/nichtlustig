package teamamused.client.gui.gameboard;

import java.util.Arrays;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import teamamused.client.Main;
import teamamused.client.gui.cardPopup.CardPopupController;
import teamamused.client.gui.cardPopup.CardPopupView;
import teamamused.client.libs.Client;
import teamamused.common.LogHelper;
import teamamused.common.ServiceLocator;
import teamamused.common.dtos.TransportableChatMessage;
import teamamused.common.gui.AbstractController;

/**
 * Diese Controller-Klasse nimmt die Benutzerinteraktionen des GameBoard entgegen.
 * 
 * @author Michelle
 *
 */
public class GameBoardController extends AbstractController<GameBoardModel, GameBoardView> {

	protected int countDice = 0;
	protected GameBoardModel model;

	public GameBoardController(GameBoardModel model, GameBoardView view) {
		super(model, view);
		this.model = model;

		// Auf dem Button wird ein MouseEvent registiert, welches den
		// Browser öffnet und das entsprechende HTML-Dokument (Spielregeln)
		// zurückgibt
		view.btnLink.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				ServiceLocator.getInstance().getHostServices().showDocument(view.url);
			}
		});

		// Der Klick auf den Exit-Button führt zur Tschüss-Seite.
		view.btnExit.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				Main.getInstance().startBye3();
			}
		});

		// Zu den DiceControl-Objekten der View wird ein Handler
		// registriert. Durch den Klick auf einen Würfel, wird dieser gesetzt.
		for (DiceControl diceControl : view.diceControlArray) {
			diceControl.setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					DiceControl diceControl = (DiceControl) event.getSource();
					moveDownSelectedDice(diceControl);
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
				} else if (countDice == 4) {
					for (DiceControl diceControl : view.diceControlArray) {
						if (!diceControl.getCube().getIsFixed()) {
							moveDownSelectedDice(diceControl);
						}
					}
				}
//				Client.getInstance().rollDices(); TODO
			}
		});

		// Auf den Spieler-Buttons wird ein Handler registriert, um das Popup
		// aufzurufen. Damit man weiss, welcher Spieler-Button geklickt wurde,
		// wird diese Information in das GameBoardModel geschrieben.
		for (Button btnPlayer : view.btnArray) {
			btnPlayer.setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					Button b = (Button) event.getSource();
					model.setBtnPlayerClicked(view.btnArray.indexOf(b)+1);
					showCardPopup();
				}
			});
		}
		
		// Sendet die gewürfelten Würfelwerte an den Server
		view.btnUebernehmen.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
//				Client.getInstance().setFixedCubes(cubeFixed); TODO
				view.btnBestaetigen.setDisable(false);
			}
			
		});
		
		// Sendet die eingegebene Nachricht an den Server
		try {
		view.btnSenden.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				Client.getInstance().sendChatMessage(new TransportableChatMessage(model.player.getPlayerName(), view.txtChatInput.getText()));
			}
		});} catch (Exception e) {
			LogHelper.LogException(e);
		}
	}

	/**
	 * Die Methode ruft das Fenster mit den Karten des jeweiligen Spielers auf.
	 * 
	 * @throws Exception
	 */
	public void showCardPopup() {
		Stage popupStage = new Stage();
//		CardPopupModel model = new CardPopupModel();
		CardPopupView cardPopupView = new CardPopupView(popupStage, model);
		new CardPopupController(model, cardPopupView);
		cardPopupView.start();
	}

	/**
	 * Diese Methode verschiebt die Würfel nach unten zu den gesetzten Würfeln.
	 * 
	 * @param diceControl
	 */
	private void moveDownSelectedDice(DiceControl diceControl) {
		diceControl.getCube().setIsFixed(true);
		diceControl.hideDice();
		int index = Arrays.asList(view.diceControlArray).indexOf(diceControl);
		DiceControl diceControlChosen = new DiceControl(diceControl.getCube());
		diceControlChosen.showDice();
		view.dicePane.add(diceControlChosen, index, 4);
		view.btnUebernehmen.setDisable(false);
	}

}
