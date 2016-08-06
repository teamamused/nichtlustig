package teamamused.playground.application.gui;

import java.text.DateFormat;
import java.util.Hashtable;
import java.util.List;




import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import teamamused.client.libs.IClientListener;
import teamamused.common.LogHelper;
import teamamused.common.db.Ranking;
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
				System.out.println("Button Pressed:");
				System.out.println(model.cubesFixed[0]);
				System.out.println(model.cubesFixed[1]);
				System.out.println(model.cubesFixed[2]);
				System.out.println(model.cubesFixed[3]);
				System.out.println(model.cubesFixed[4]);
				System.out.println(model.cubesFixed[5]);
				System.out.println(model.cubesFixed[6]);
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
		System.out.println("GameBoard Choose Cards");
		Platform.runLater(() -> {
				Stage chooseStage = new Stage();
				ChooseCardsModel model = new ChooseCardsModel(options);
				ChooseCardsView cv = new ChooseCardsView(chooseStage, model);
				new ChooseCardsController(model, cv);
				cv.start();
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
		if (model.spielbrett != null) {
			model.speicalCardsNeedsUpdate = newGameBoard.getSpecialCards().length != model.spielbrett.getSpecialCards().length;
			model.targetCardsNeedsUpdate = newGameBoard.getTargetCards().length != model.spielbrett.getTargetCards().length;
			model.deadCardsNeedsUpdate = newGameBoard.getDeadCards().length != model.spielbrett.getDeadCards().length;

		} else {
			model.speicalCardsNeedsUpdate = true;
			model.targetCardsNeedsUpdate = true;
			model.deadCardsNeedsUpdate = true;
		}
		model.spielbrett = newGameBoard;
		
		Platform.runLater(() -> {
			try {
				view.drawCards();
				view.drawCubes();
				view.drawPlayers();
			} catch (Exception ex) {
				LogHelper.LogException(ex);
			}
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
		Platform.runLater(() -> {
		DateFormat df = DateFormat.getTimeInstance();
		view.txtChat.appendText(df.format(message.getTime()) + " - " + message.getSender() + ": "
				+ message.getMessage());
		view.txtChat.appendText("\n");
		});
	}

	@Override
	public void onNewGameMove(String move) {
		Platform.runLater(() -> {
			view.txtGameMoves.appendText(move + "\n");
		});
	}

	/**
	 * Der Server hat das Spiel für beendet erklärt.
	 * Dem Spieler muss angezeigt werden das das Spiel vorbei ist und das Ranking eingeblendet werden
	 *  
	 * @param rankings Platzierungen der Spielrunde
	 */
	@Override
	public void onGameFinished(Ranking[] rankings) {
		Platform.runLater(() -> {
			Stage rs = new Stage();
			RankingModel model = new RankingModel(rankings);
			RankingView rv = new RankingView(rs, model);
			new RankingController (model, rv);
			rv.start();
		});
	}

	private void sendMessage() {
		String name = "";
		if (model.player != null) {
			name = model.player.getPlayerName();
		}
		TransportableChatMessage message = new TransportableChatMessage(name, view.txtChatNewMsg.getText());
		Client.getInstance().sendChatMessage(message);
		Platform.runLater(() -> {
			view.txtChatNewMsg.setText("");
		});
	}
}
