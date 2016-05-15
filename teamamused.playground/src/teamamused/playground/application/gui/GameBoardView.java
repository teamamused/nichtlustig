package teamamused.playground.application.gui;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import teamamused.common.gui.AbstractView;
import teamamused.common.interfaces.ICube;
import teamamused.common.interfaces.IGameCard;
import teamamused.common.interfaces.ITargetCard;

public class GameBoardView extends AbstractView<GameBoardModel> {

	private final double BUTTON_WIDTH = 125;
	TilePane todKartenPane;
	TilePane spielKartenPane;
	TilePane spezialKartenPane;
	
	HBox wuerfel;
	Button btnDice;
	Button btnStartGame;
	Button btnChooseCards;

	public GameBoardView(Stage stage, GameBoardModel model) {
		super(stage, model);
	}

	@Override
	protected Scene createGUI() {
		BorderPane root = new BorderPane();
		Scene scene = new Scene(root, 1240, 900);
		// Oberer Teil
		HBox topBox = new HBox();
		// Titel label
		Label lTitel = new Label();
		lTitel.setText("Hallo " + this.model.player.getPlayerName() + "! Wenn all deine Homies dem Spiel beigetreten sind kannst du das Spiel hier starten:");
		// Button zum starten
		btnStartGame = new Button("Start");
		topBox.getChildren().addAll(lTitel, btnStartGame);
		// Karten laden
		todKartenPane = new TilePane();	
		spielKartenPane = new TilePane();
		spezialKartenPane = new TilePane();
		drawCards();
		// Würfelhinzufügen
		this.wuerfel = new HBox();
		this.btnDice = new Button("würfeln");
		this.btnChooseCards = new Button("Karten wählen");
		// Alle Elemente im Gui anordnen
		root.setTop(topBox);
		root.setLeft(todKartenPane);
		root.setCenter(spielKartenPane);
		root.setRight(spezialKartenPane);
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
	
	protected void drawCards() {
		// Tod Karten laden	
		this.todKartenPane.getChildren().clear();
		this.addCardsToPane(todKartenPane, this.model.spielbrett.getDeadCards(), 2, 3);
		// Zielkarten laden
		this.spielKartenPane.getChildren().clear();
		this.addCardsToPane(spielKartenPane, this.model.spielbrett.getTargetCards(), 5, 5);
		// Spezialkarten laden
		this.spezialKartenPane.getChildren().clear();
		this.addCardsToPane(spezialKartenPane, this.model.spielbrett.getSpecialCards(), 2, 3);
	}
	
	/**
	 * 
	 * Nur zum Test, bei uns im Spiel anders.
	 * Zeigt die übergebenen Karten dort an wo sonst die Würfel angezeigt werden
	 * 
	 * @param text
	 * @param cards
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
		this.wuerfel.getChildren().add(this.btnChooseCards);
	}

	/**
	 * Würfel zeichnen
	 * @param remainingDraws
	 */
	protected void drawCubes(int remainingDraws) {
		int diceSize = 50;
		ICube[] cubes = this.model.spielbrett.getCubes();
		wuerfel.getChildren().clear();
		wuerfel.minHeight(diceSize + 10);
		for (ICube cube : cubes) {
			wuerfel.getChildren().add(cube.getCurrentValue().toCanvas(diceSize, 10));
		}
		Label lblRemainingDraws = new Label("Sie dürfen noch " + remainingDraws + " mal würfeln");
		wuerfel.getChildren().add(lblRemainingDraws);
		wuerfel.getChildren().add(btnDice);
	}

	/**
	 * Fügt die Karten einer TilePane hinzu
	 * 
	 * @param p
	 * @param cards
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
