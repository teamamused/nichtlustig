package teamamused.playground.application.gui;

import java.text.DateFormat;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import teamamused.client.libs.IClientListener;
import teamamused.common.ServiceLocator;
import teamamused.common.dtos.TransportableChatMessage;
import teamamused.common.gui.AbstractController;
import teamamused.common.interfaces.ITargetCard;
import teamamused.common.models.GameBoard;
import teamamused.client.libs.Client;

public class GameBoardController extends AbstractController<GameBoardModel, GameBoardView> implements IClientListener {
	/**
	 * Konstruktor des GameboardControllers
	 * 
	 * @param model
	 *            Instanz des GameBoardModel
	 * @param view
	 *            Instanz der GameBoardView
	 */
	public GameBoardController(GameBoardModel model, GameBoardView view) {
		super(model, view);
		// Dem Client Mitteilen dass man über aktualisierungen informiert werden
		// will
		Client.getInstance().registerGui(this);
		// Im Konstruktor werden alle Gui Events mit den zugehörigen Aktionen
		// verknüpft
		// Button Würfeln
		view.btnDiceRoll.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Client.getInstance().rollDices();
			}
		});
		// Button Würfel fixieren
		view.btnDiceFix.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Client.getInstance().setFixedCubes(model.cubesFixed);
			}
		});
		// Button Spiel starten
		view.btnStartGame.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Client.getInstance().startGame();
			}
		});
		// Button Spiel starten
		view.btnChooseCards.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// Hier müsste dem Spieler eine mölgichkeit gegeben werden die
				// Karten die er will auszuwählen
				// Danach geprüft werden ob mit seinem erwürfeltem die Auswahl
				// legitim ist
				// Dann die ausgewählten zuteilen und nicht einfach alle
				Client.getInstance().cardsChoosen(model.cardsChoosen);
				view.wuerfel.getChildren().clear();
			}
		});
		// Chat Message senden
		view.btnChatSenden.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				sendMessage();
			}
		});
	}

	/**
	 * Der Spieler muss zwischen den möglichen Zielkarten auswählen
	 * 
	 * @param allowedCards
	 *            Karten welche zur auswahl stehen
	 */
	@Override
	public void onPlayerHasToCooseCards(Hashtable<Integer, List<ITargetCard>> options) {
		List<ITargetCard> allowedCards = options.values().stream().findFirst().get();
		model.cardsToChooseOptions = options;
		Platform.runLater(() -> {
			// nicht schön gemacht von mir, es wird nur die erste möglichkeit
			// angezeigt:
			ITargetCard[] karten = Arrays.copyOf(allowedCards.toArray(), allowedCards.size(), ITargetCard[].class);
			ServiceLocator.getInstance().getLogger().info("GameboardController: zeige auszuwählende Karten an");
			this.view.drawCards("Bitte wählen Sie \ndie gewünschten Zielkarten aus:", karten);
		});
	}

	/**
	 * Der Status des Spielers wurde gewechselt
	 * 
	 * @param isActive
	 *            Spieler aktiv Ja/Nein
	 */
	@Override
	public void onPlayerIsActivedChanged(boolean isActive) {
		System.out.println("Player aktiv hat gewechselt, neu: " + isActive);
		this.model.isPlayerActive = isActive;
		if (isActive) {
			Platform.runLater(() -> {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Du bist am Zug");
				alert.setHeaderText(null);
				alert.setContentText("Du bist an der Reihe mit Würfeln!");
				alert.showAndWait();
				view.drawCubes();
			});
		}
	}

	/**
	 * Daten für das Spielbrett aktualisieren
	 * 
	 * @param newGameBoard
	 *            Neues Spielbrett
	 */
	@Override
	public void onGameBoardChanged(GameBoard newGameBoard) {
		model.spielbrett = newGameBoard;
		Platform.runLater(() -> {
			view.drawCards();
			view.drawCubes();
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

	@Override
	public void onChatMessageRecieved(TransportableChatMessage message) {
		DateFormat df = DateFormat.getTimeInstance();
		view.txtChat.appendText(df.format(message.getTime()) + " - " + message.getSender() + ": "
				+ message.getMessage());
		view.txtChat.appendText("\n");
	}

	@Override
	public void onNewGameMove(String move) {
		view.txtGameMoves.appendText(move + "\n");
	}

	private void sendMessage() {
		String name = "";
		if (model.player != null) {
			name = model.player.getPlayerName();
		}
		TransportableChatMessage message = new TransportableChatMessage(name, view.txtChatNewMsg.getText());
		Client.getInstance().sendChatMessage(message);
		view.txtChatNewMsg.setText("");
	}
}
