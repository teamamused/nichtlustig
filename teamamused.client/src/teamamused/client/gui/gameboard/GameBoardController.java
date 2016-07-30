package teamamused.client.gui.gameboard;

import java.util.Hashtable;
import java.util.List;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import teamamused.client.Main;
import teamamused.client.gui.ChooseCards.ChooseCardsController;
import teamamused.client.gui.ChooseCards.ChooseCardsModel;
import teamamused.client.gui.ChooseCards.ChooseCardsView;
import teamamused.client.gui.cardPopup.CardPopupController;
import teamamused.client.gui.cardPopup.CardPopupView;
import teamamused.client.libs.Client;
import teamamused.client.libs.IClientListener;
import teamamused.common.LogHelper;
import teamamused.common.ServiceLocator;
import teamamused.common.dtos.TransportableChatMessage;
import teamamused.common.gui.AbstractController;
import teamamused.common.interfaces.ITargetCard;
import teamamused.common.models.GameBoard;

/**
 * Diese Controller-Klasse nimmt die Benutzerinteraktionen des GameBoard
 * entgegen.
 * 
 * @author Michelle
 *
 */
public class GameBoardController extends AbstractController<GameBoardModel, GameBoardView> implements IClientListener{

	protected int countDice = 0;
	protected GameBoardModel model;

	public GameBoardController(GameBoardModel model, GameBoardView view) {
		super(model, view);
		this.model = model;
		
		// Registriert das GUI
			Client.getInstance().registerGui(this);

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

		// Bei jedem Klick auf den Button zum Würfeln werden die Würfel neu
		// gewürfelt. Bereits gesetzte Würfel können nicht erneut gewürfelt
		// werden.
		view.btnWuerfeln.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
					model.dice();
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
					model.setBtnPlayerClicked(view.btnArray.indexOf(b) + 1);
					showCardPopup();
				}
			});
		}

		// Sendet die gewürfelten Würfelwerte an den Server
		view.btnUebernehmen.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				// Client.getInstance().setFixedCubes(cubeFixed); TODO
				view.btnBestaetigen.setDisable(false);
			}

		});

		// Sendet die eingegebene Nachricht an den Server
		view.btnSenden.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				try {
					Client.getInstance().sendChatMessage(
							new TransportableChatMessage(model.player.getPlayerName(), view.txtChatInput.getText()));
				} catch (Exception e) {
					LogHelper.LogException(e);
				}
			}
		});
	}

	/**
	 * Die Methode ruft das Fenster mit den Karten des jeweiligen Spielers auf.
	 * 
	 * @throws Exception
	 */
	public void showCardPopup() {
		Stage popupStage = new Stage();
		// CardPopupModel model = new CardPopupModel();
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
		diceControl.getCube().setIsFixed(!diceControl.getCube().getIsFixed());
		boolean[] fixedDices = new boolean[7];
		for (DiceControl diceContr: view.diceControlArray) {
			fixedDices[diceContr.getCube().getCubeNumber()] = diceContr.getCube().getIsFixed();
		}
		view.btnUebernehmen.setDisable(false);
		Client.getInstance().setFixedCubes(fixedDices);
	}
	
	/**
	 * Methode von IClientListener wird hier überschrieben und so die
	 * empfangenen Nachrichten in die ObserverList geschrieben
	 */
	@Override
	public void onChatMessageRecieved(TransportableChatMessage message) {
		model.chatMessages.add(message.getSender() + " : " + message.getMessage());
	}
	
	/**
	 * Das Spielbrett wurde verändert
	 * 
	 * @param newGameBoard Neues Spielbrett
	 */
	@Override
	public void onGameBoardChanged(GameBoard newGameBoard) {
		System.out.println("Aktualisiere Spielbrett");
		if (model.gameBoard != null) {
			model.specialCardsNeedsUpdate = newGameBoard.getSpecialCards().length != model.gameBoard.getSpecialCards().length;
			model.targetCardsNeedsUpdate = newGameBoard.getTargetCards().length != model.gameBoard.getTargetCards().length;
			model.deadCardsNeedsUpdate = newGameBoard.getDeadCards().length != model.gameBoard.getDeadCards().length;
		} else {
			model.specialCardsNeedsUpdate = true;
			model.targetCardsNeedsUpdate = true;
			model.deadCardsNeedsUpdate = true;
		}
		model.gameBoard = newGameBoard;
		Platform.runLater(() -> {
			view.buildCards();
			view.buildDices();
			setEventsOnDices();
			view.buildPlayer();
		});
	}
	
	// Zu den DiceControl-Objekten der View wird ein Handler
	// registriert. Durch den Klick auf einen Würfel, wird dieser gesetzt.
	public void setEventsOnDices() {
		for (DiceControl diceControl : view.diceControlArray) {
			diceControl.setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					DiceControl diceControl = (DiceControl) event.getSource();
					moveDownSelectedDice(diceControl);
				}
			});
		}
	}
	
	/**
	 * Der Spieler muss zwischen den möglichen Zielkarten auswählen
	 * 
	 * @param allowedCards
	 *            Karten welche zur auswahl stehen
	 */
	@Override
	public void onPlayerHasToCooseCards(Hashtable<Integer, List<ITargetCard>> options) {
		System.out.println("Spieler muss Karten wählen");
		Platform.runLater(() -> {
				Stage chooseStage = new Stage();
				ChooseCardsModel model = new ChooseCardsModel(options);
				ChooseCardsView cv = new ChooseCardsView(chooseStage, model);
				new ChooseCardsController(model, cv);
				cv.start();
		});
	}
	
	/**
	 * Der Spieler hat Gewürfelt, der Server sagt Ihm wieviel mal er noch darf
	 * 
	 * @param remDices
	 *            Verbleibende Würfelversuche
	 */
	@Override
	public void onNumberOfRemeiningDicingChanged(int remDices) {
		model.remainingDices = remDices;
	}

}
