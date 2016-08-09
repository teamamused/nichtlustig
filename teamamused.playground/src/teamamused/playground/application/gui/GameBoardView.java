package teamamused.playground.application.gui;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import teamamused.common.gui.AbstractView;
import teamamused.common.interfaces.ICube;
import teamamused.common.interfaces.IGameCard;
import teamamused.common.interfaces.IPlayer;
import teamamused.common.interfaces.ITargetCard;
import teamamused.server.lib.TextAreaHandler;
import teamamused.client.libs.Client;

public class GameBoardView extends AbstractView<GameBoardModel> {

	private final double BUTTON_WIDTH = 110;
	TilePane todKartenPane;
	TilePane spielKartenPane;
	TilePane spezialKartenPane;
	TextArea txtChat;
	TextArea txtGameMoves;
	TextField txtChatNewMsg;

	HBox wuerfel;
	VBox player;
	Button btnDiceRoll;
	Button btnDiceFix;
	Button btnStartGame;
	Button btnChatSenden;

	CheckBox[] cbsCubesFixed = new CheckBox[7];
	Button[] btnsCubesFixed = new Button[7];
	ArrayList<Button> playerButtons;

	/**
	 * Konstruktor
	 * 
	 * @param stage
	 *            Stage in welcher die Scene angezeigt wird
	 * @param model
	 *            Model für die Datenhaltung
	 */
	public GameBoardView(Stage stage, GameBoardModel model) {
		super(stage, model);
	}

	@Override
	protected Scene createGUI() {
		BorderPane root = new BorderPane();
		Scene scene = new Scene(root, 1600, 810);
		// Oberer Teil
		HBox topBox = new HBox();
		// Titel label
		Label lTitel = new Label();
		lTitel.setText("Hallo " + this.model.player.getPlayerName()
				+ "! Wenn all deine Homies dem Spiel beigetreten sind kannst du das Spiel hier starten:");
		// Button zum starten
		btnStartGame = new Button("Start");
		topBox.getChildren().addAll(lTitel, btnStartGame);
		// Karten laden
		HBox karten = new HBox();
		todKartenPane = new TilePane();
		spielKartenPane = new TilePane();
		spezialKartenPane = new TilePane();
		drawCards();
		// Würfelhinzufügen
		this.wuerfel = new HBox();
		this.btnDiceRoll = new Button("würfeln");
		this.btnDiceFix = new Button("fixieren");
		// Benachrichtigungen
		VBox right = new VBox();
		int rightWidt = 150;
		right.maxWidth(rightWidt);
		Label lSpielzuege = new Label("Spielzüge:");
		txtGameMoves = new TextArea("Starte Gui");
		txtGameMoves.maxWidth(rightWidt);

		// Chat selber
		Label lChat = new Label("Chat:");
		txtChat = new TextArea("");
		txtChat.maxWidth(rightWidt);
		// Nachricht senden
		HBox bottomBox = new HBox();
		txtChatNewMsg = new TextField("Nachricht senden");
		txtChatNewMsg.prefWidthProperty().bind(txtChat.widthProperty().multiply(0.8));
		btnChatSenden = new Button("senden");
		btnChatSenden.prefWidthProperty().bind(txtChat.widthProperty().multiply(0.2));
		bottomBox.getChildren().addAll(txtChatNewMsg, btnChatSenden);
		// Loging
		Label lLogging = new Label("Loging (nur Test):");
		TextArea loggingTxtArea = TextAreaHandler.getInstance().getTextArea();
		loggingTxtArea.setEditable(false);
		// Players
		this.player = new VBox();
		this.playerButtons = new ArrayList<Button>();
		this.drawPlayers();
		right.getChildren().addAll(lSpielzuege, txtGameMoves, lChat, txtChat, bottomBox, lLogging, loggingTxtArea,
				this.player);
		// Alle Elemente im Gui anordnen
		root.setTop(topBox);
		karten.getChildren().addAll(todKartenPane, spielKartenPane, spezialKartenPane);
		root.setCenter(karten);
		root.setRight(right);
		root.setBottom(wuerfel);
		try {
			// CSS Gestaltungs File laden
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		} catch (Exception e) {
			e.printStackTrace();
		}
		// anzeigen
		return scene;
	}

	/**
	 * Alle Karten zeichnen
	 */
	protected void drawCards() {
		if (this.model.spielbrett != null) {
			// Tod Karten laden
			if (model.deadCardsNeedsUpdate) {
				if (this.todKartenPane.getChildren() != null && !this.todKartenPane.getChildren().isEmpty()) {
					this.todKartenPane.getChildren().clear();
				}
				this.addCardsToPane(todKartenPane, this.model.spielbrett.getDeadCards(), 2, 3);
			}
			// Zielkarten laden
			if (model.targetCardsNeedsUpdate) {
				if (this.spielKartenPane.getChildren() != null && !this.spielKartenPane.getChildren().isEmpty()) {
					this.spielKartenPane.getChildren().clear();
				}
				this.addCardsToPane(spielKartenPane, this.model.spielbrett.getTargetCards(), 5, 5);
			}
			// Spezialkarten laden
			if (model.speicalCardsNeedsUpdate) {
				if (this.spezialKartenPane.getChildren() != null && !this.spezialKartenPane.getChildren().isEmpty()) {
					this.spezialKartenPane.getChildren().clear();
				}
				this.addCardsToPane(spezialKartenPane, this.model.spielbrett.getSpecialCards(), 2, 3);
			}
		}
	}

	/**
	 * 
	 * Nur zum Test, bei uns im Spiel anders. Zeigt die übergebenen Karten dort
	 * an wo sonst die Würfel angezeigt werden
	 * 
	 * @param text
	 *            Angezeigter Text vor den Karten
	 * @param cards
	 *            Die Karten selber
	 */
	protected void drawCards(String text, ITargetCard[] cards) {
		// Würfel löschen
		wuerfel.getChildren().clear();
		// Titel label
		Label lblText = new Label();
		lblText.setMinWidth(50);
		lblText.setText(text);
		// Karten laden zeichnen
		TilePane kartenPane = new TilePane();
		this.addCardsToPane(kartenPane, cards, 6, 2);

		// Gui hinzufügen
		this.wuerfel.getChildren().add(lblText);
		this.wuerfel.getChildren().add(kartenPane);
	}

	/**
	 * Würfel zeichnen
	 * 
	 * @param remainingDraws
	 *            Wieviel mal noch gewürfelt werden darf
	 */
	protected void drawCubes() {
		int diceSize = 50;
		ICube[] cubes = this.model.spielbrett.getCubes();
		wuerfel.getChildren().clear();
		wuerfel.minHeight(diceSize + 10);
		for (ICube cube : cubes) {
			this.btnsCubesFixed[cube.getCubeNumber()] = new Button();
			this.btnsCubesFixed[cube.getCubeNumber()].setGraphic(cube.getCurrentValue().toCanvas(diceSize, 10));

			model.cubesFixed[cube.getCubeNumber()] = cube.getIsFixed();

			this.cbsCubesFixed[cube.getCubeNumber()] = new CheckBox();
			this.cbsCubesFixed[cube.getCubeNumber()].setDisable(true);
			this.cbsCubesFixed[cube.getCubeNumber()].setSelected(cube.getIsFixed());
			if (cbsCubesFixed[cube.getCubeNumber()].isSelected()) {
				cbsCubesFixed[cube.getCubeNumber()].setText("fixiert");
			} else {
				cbsCubesFixed[cube.getCubeNumber()].setText("");
			}

			this.btnsCubesFixed[cube.getCubeNumber()].setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					cbsCubesFixed[cube.getCubeNumber()].setSelected(!cbsCubesFixed[cube.getCubeNumber()].isSelected());
					if (cbsCubesFixed[cube.getCubeNumber()].isSelected()) {
						cbsCubesFixed[cube.getCubeNumber()].setText("fixiert");
					} else {
						cbsCubesFixed[cube.getCubeNumber()].setText("");
					}
					model.cubesFixed[cube.getCubeNumber()] = cbsCubesFixed[cube.getCubeNumber()].isSelected();
				}
			});

			VBox wuerfelBtns = new VBox();
			wuerfelBtns.getChildren().addAll(this.btnsCubesFixed[cube.getCubeNumber()],
					this.cbsCubesFixed[cube.getCubeNumber()]);
			wuerfel.getChildren().add(wuerfelBtns);
		}
		Label lblRemainingDraws = new Label("Sie dürfen noch " + model.remainingDices + " mal würfeln");
		wuerfel.getChildren().add(lblRemainingDraws);
		VBox wuerfelButtons = new VBox();
		wuerfelButtons.getChildren().addAll(btnDiceRoll, btnDiceFix);
		wuerfel.getChildren().add(wuerfelButtons);
	}

	protected void drawPlayers() {
		for (Button oldButton : this.playerButtons) {
			this.player.getChildren().remove(oldButton);
			oldButton = null;
		}
		this.playerButtons = new ArrayList<Button>();
		if (model.spielbrett != null) {
			for (IPlayer p : model.spielbrett.getPlayers()) {
				Button btn = new Button(p.getPlayerNumber() + " - " + p.getPlayerName());
				// Aktiven Spieler hervorheben
				if (Client.getInstance().getActivePlayer() != null && p.getPlayerNumber() == Client.getInstance().getActivePlayer().getPlayerNumber()) {
					btn.setStyle("-fx-font: 22 arial; -fx-base: #b6e7c9;");
				}
				btn.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						Stage playerStage = new Stage();
						ShowPlayerModel model = new ShowPlayerModel(p);
						ShowPlayerView view = new ShowPlayerView(playerStage, model);
						new ShowPlayerController(model, view);
						view.start();
					}
				});
				this.playerButtons.add(btn);
				this.player.getChildren().add(btn);
			}
		}
	}

	/**
	 * Fügt die Karten einer TilePane hinzu
	 * 
	 * @param p
	 *            Panel in welches die Karten gezeichnet werden sollen
	 * @param cards
	 *            Die Karten selber
	 * @param anzReihen
	 *            in wieviele Reihen/Spalten
	 * @param anzZeilen
	 *            in wieviel Zeilen
	 */
	private void addCardsToPane(TilePane p, IGameCard[] cards, int anzReihen, int anzZeilen) {
		// abstand zwischen den Buttons
		int gap = 2;
		p.setHgap(gap);
		p.setVgap(gap);
		p.setPrefColumns(anzReihen);
		p.setPrefRows(anzZeilen);
		p.setMinWidth((BUTTON_WIDTH + (2 * gap)) * anzReihen + 4);

		for (IGameCard card : cards) {
			VBox box = new VBox();
			Button btn = new Button();
			btn.setId(cards.toString());
			btn.setMinHeight(BUTTON_WIDTH);
			btn.setMinWidth(BUTTON_WIDTH);
			ImageView iv = new ImageView(card.getForegroundImage());
			iv.setFitHeight(BUTTON_WIDTH - 5);
			iv.setFitWidth(BUTTON_WIDTH - 5);
			Label lblText = new Label(card.toString());
			lblText.setMaxWidth(BUTTON_WIDTH - 5);
			box.getChildren().addAll(iv, lblText);
			btn.setGraphic(box);
			p.getChildren().add(btn);
		}
	}
}
