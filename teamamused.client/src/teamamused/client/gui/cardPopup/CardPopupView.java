package teamamused.client.gui.cardPopup;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import teamamused.client.gui.gameboard.GameBoardModel;
import teamamused.common.gui.AbstractView;

public class CardPopupView extends AbstractView<GameBoardModel> {

	protected Label labelText, cardsRival, specialCardsRival;
	protected Button btnTakeCard, btnClose;

	public CardPopupView(Stage stage, GameBoardModel model) {
		super(stage, model);
	}

	protected Scene createGUI() {
		stage.setTitle("Nicht Lustig: Karten der Gegner");

		// Definition der Pane
		GridPane root = new GridPane();
		root.setPadding(new Insets(50, 50, 50, 50));
		root.setHgap(10);
		root.setVgap(10);
		root.setAlignment(Pos.CENTER);
		root.setGridLinesVisible(true);

		Scene scene = new Scene(root, 900, 600);

		// Instanziierung und Zuweisung der Controlls zur Pane
		labelText = new Label(
				"Hier kannst du die Karten deines Gegners sehen und allenfalls\nvon ihm Karten stibitzen.");
		// Erste Variante funktioniert nicht - wieso? TODO
		cardsRival = new Label("Karten von Spieler " + model.getBtnPlayerClicked());
		// cardsRival = new Label("Karten von Spieler " + "Nr");
		specialCardsRival = new Label("Sonderkarten von Spieler " + "Nr");
		btnTakeCard = CardPopupView.initializeButton("Karte(n) nehmen");
		btnClose = CardPopupView.initializeButton("schliessen");

		root.add(labelText, 0, 0, 5, 1);
		root.add(cardsRival, 0, 2);
		root.add(specialCardsRival, 0, 3);
		root.add(btnTakeCard, 5, 4);
		root.add(btnClose, 5, 7);

		GridPane.setHalignment(btnTakeCard, HPos.RIGHT);
		GridPane.setHalignment(btnClose, HPos.RIGHT);

		// Zuweisung des Stylesheets
		scene.getStylesheets().add(getClass().getResource("..\\application.css").toExternalForm());

		return scene;

	}

	/**
	 * Die Support-Methode instanziiert einen Button und gibt diesen formatiert
	 * zurück (Wiederverwendbarkeit von Code)
	 * 
	 * @param buttonText
	 *            Bezeichnung des Buttons als String
	 * @return formatiertes Button-Objekt
	 */
	private static Button initializeButton(String buttonText) {
		Button btn = new Button(buttonText);
		btn.setMaxSize(200, 40);
		btn.setAlignment(Pos.CENTER);
		return btn;
	}

	public Scene getScene() {
		return scene;
	}

	public void start() {
		stage.show();
	}

	public void stop() {
		stage.hide();
	}

}
