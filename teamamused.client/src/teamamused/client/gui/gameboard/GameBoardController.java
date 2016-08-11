package teamamused.client.gui.gameboard;

import java.util.Hashtable;
import java.util.List;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import teamamused.client.Main;
import teamamused.client.gui.chooseCards.ChooseCardsController;
import teamamused.client.gui.chooseCards.ChooseCardsModel;
import teamamused.client.gui.chooseCards.ChooseCardsView;
import teamamused.client.gui.cardPopup.CardPopupController;
import teamamused.client.gui.cardPopup.CardPopupView;
import teamamused.client.libs.Client;
import teamamused.client.libs.IClientListener;
import teamamused.common.LogHelper;
import teamamused.common.ServiceLocator;
import teamamused.common.db.Ranking;
import teamamused.common.dtos.TransportableChatMessage;
import teamamused.common.gui.AbstractController;
import teamamused.common.interfaces.IPlayer;
import teamamused.common.interfaces.ITargetCard;
import teamamused.common.models.GameBoard;

/**
 * Diese Controller-Klasse nimmt die Benutzerinteraktionen des GameBoard
 * entgegen.
 * 
 * @author Michelle
 *
 */
public class GameBoardController extends AbstractController<GameBoardModel, GameBoardView> implements IClientListener {

	protected GameBoardModel model;
	protected boolean[] fixedDices;
	protected boolean allowedToMoveDown = false;

	public GameBoardController(GameBoardModel model, GameBoardView view) {
		super(model, view);
		this.model = model;

		// Registriert das GUI
		Client.getInstance().registerGui(this);
		Client.getInstance().joinGame();

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
				Main.getInstance().startBye(view);
			}
		});

		// Bei jedem Klick auf den Button zum Würfeln werden die Würfel neu
		// gewürfelt. Bereits gesetzte Würfel können nicht erneut gewürfelt
		// werden.
		view.btnWuerfeln.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				model.dice();
				allowedToMoveDown = true;
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
				view.txtChatInput.clear();
			}
		});

		// Startet das Spiel
		view.btnStart.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				Client.getInstance().startGame();
				view.btnStart.setDisable(true);
			}

		});
		
		// Text Nachricht schicken wenn Enter betätigt wurde
		view.txtChatInput.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode().equals(KeyCode.ENTER)) {
					try {
						Client.getInstance().sendChatMessage(
								new TransportableChatMessage(model.player.getPlayerName(), view.txtChatInput.getText()));
					} catch (Exception e) {
						LogHelper.LogException(e);
					}
					view.txtChatInput.clear();
				}
			}
		});
	}

	/**
	 * Diese Methode verschiebt die Würfel nach unten zu den gesetzten Würfeln.
	 * 
	 * @param diceControl
	 */
	private void moveDownSelectedDice(DiceControl diceControl) {
		diceControl.getCube().setIsFixed(!diceControl.getCube().getIsFixed());
		boolean[] fixedDices = new boolean[7];
		for (DiceControl diceContr : view.diceControlArray) {
			fixedDices[diceContr.getCube().getCubeNumber()] = diceContr.getCube().getIsFixed();
		}
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
	 * @param newGameBoard
	 *            Neues Spielbrett
	 */
	@Override
	public void onGameBoardChanged(GameBoard newGameBoard) {
		if (model.gameBoard != null) {
			model.specialCardsNeedsUpdate |= newGameBoard.getSpecialCards().length != model.gameBoard
					.getSpecialCards().length;
			model.targetCardsNeedsUpdate |= newGameBoard.getTargetCards().length != model.gameBoard
					.getTargetCards().length;
			model.deadCardsNeedsUpdate |= newGameBoard.getDeadCards().length != model.gameBoard.getDeadCards().length;
		} else {
			model.specialCardsNeedsUpdate = true;
			model.targetCardsNeedsUpdate = true;
			model.deadCardsNeedsUpdate = true;
		}

		model.gameBoard = newGameBoard;
		Platform.runLater(() -> {
			if (model.gameBoard.getGameStartet() && view.navigation.getChildren().contains(view.btnStart)) {
				view.navigation.getChildren().remove(view.btnStart);
			}
			view.buildCards();
			view.buildDices();
			setEventOnDices();
			view.buildPlayer();
			setEventsOnPlayerButton();
		});
	}

	/**
	 * 
	 * Zu den DiceControl-Objekten der View wird ein Handler registriert. DUrch
	 * den Klick auf einen Würfel, wird dieser fixiert und erscheint bei den
	 * gesetzten Würfeln.
	 * 
	 */
	public void setEventOnDices() {
		for (DiceControl diceControl : view.diceControlArray) {
			if (!diceControl.getCube().getIsFixed()) {
				diceControl.setOnMouseClicked(new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent event) {
						if (allowedToMoveDown) {
							DiceControl diceControl = (DiceControl) event.getSource();
							moveDownSelectedDice(diceControl);
						}
					}
				});
			}
		}
	}

	public void setEventsOnPlayerButton() {
		for (Button btnPlayer : view.btnArray) {
			if (btnPlayer.getOnMouseClicked() == null) {
				btnPlayer.setOnMouseClicked(new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent event) {
						Stage playerStage = new Stage();
						// getUserData() holt den Player, welcher an den Button
						// gebunden ist
						GameBoardModel gameBoardModel = new GameBoardModel((IPlayer) btnPlayer.getUserData());
						CardPopupView cardPopupView = new CardPopupView(playerStage, gameBoardModel);
						new CardPopupController(gameBoardModel, cardPopupView);
						cardPopupView.start();
					}
				});
			}
		}
	}

	/**
	 * Der Spieler muss zwischen den möglichen Zielkarten auswählen, da ihm
	 * aufgrund der gewürfelten Augenzahlen mehrere Kombinationen zur Auswahl
	 * stehen.
	 * 
	 * @param allowedCards
	 *            Karten welche zur Auswahl stehen
	 */
	@Override
	public void onPlayerHasToCooseCards(Hashtable<Integer, List<ITargetCard>> cardOptions) {
		Platform.runLater(() -> {
			Stage chooseStage = new Stage();
			ChooseCardsModel chooseCardsModel = new ChooseCardsModel(cardOptions);
			ChooseCardsView chooseCardsView = new ChooseCardsView(chooseStage, chooseCardsModel);
			new ChooseCardsController(chooseCardsModel, chooseCardsView);
			chooseCardsView.start();
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
		Platform.runLater(() -> {
			view.updateTextOnLabelRollDices();
			allowedToDice();
		});
	}

	/**
	 * Die Methode teilt mit, ob der Spieler an der Reihe ist oder nicht. Wenn
	 * der Spieler am Zug ist, wird der Button zum Würfeln freigeschalten.
	 * 
	 * @param isActive
	 *            Spieler aktiv Ja / Nein
	 */
	@Override
	public void onPlayerIsActivedChanged(boolean isActive) {
		model.playerIsActive = isActive;
		Platform.runLater(() -> {
			allowedToDice();
			if (isActive) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Du bist am Zug");
				alert.setHeaderText(null);
				alert.setContentText("Du bist an der Reihe mit Würfeln!");
				alert.showAndWait();
			}
		});
	}

	/**
	 * Methode erhält vom Server die beiden Events und konsolidiert diese, um
	 * die disabled Würfel zu steuern.
	 */
	private void allowedToDice() {
		if (model.playerIsActive && model.remainingDices > 0) {
			view.btnWuerfeln.setDisable(false);
		} else if (model.playerIsActive && model.remainingDices <= 0) {
			view.btnWuerfeln.setDisable(true);
		} else {
			view.btnWuerfeln.setDisable(true);
			allowedToMoveDown = false;
		}
	}

	/**
	 * Der Server hat das Spiel für beendet erklärt. Dem Spieler wird angezeigt,
	 * dass das Spiel vorbei ist und der Sieger eingeblendet
	 * 
	 * @param rankings
	 *            Platzierungen der Spielrunde
	 */
	@Override
	public void onGameFinished(Ranking[] rankings) {
		Platform.runLater(() -> {
			Main.getInstance().startGameOver(rankings);
		});

	}

	/**
	 * Gibt auf dem TextArea die Spielzüge aus.
	 */
	@Override
	public void onNewGameMove(String move) {
		Platform.runLater(() -> {
			view.txtGameMove.appendText(move + "\n");
		});
	}
}
