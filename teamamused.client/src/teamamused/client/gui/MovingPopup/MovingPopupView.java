package teamamused.client.gui.MovingPopup;

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

public class MovingPopupView extends AbstractView<GameBoardModel> {

	protected Label labelQueue, labelDice, labelCards, labelNext;
	protected Button btnBack;
	
	public MovingPopupView(Stage stage, GameBoardModel model) {
		super(stage, model);
	}

	protected Scene createGUI() {
		stage.setTitle("Nicht Lustig: Spielzug des Gegners");

		// Definition der Pane
		GridPane root = new GridPane();
		root.setPadding(new Insets(50, 50, 50, 50));
		root.setHgap(10);
		root.setVgap(10);
		root.setAlignment(Pos.CENTER);
		root.setGridLinesVisible(true);

		Scene scene = new Scene(root, 900, 600);

		// Instanziierung und Zuweisung der Controlls zur Pane
		labelQueue = new Label("Spieler "+"Nr "+"ist an der Reihe...");
		labelDice = new Label("Der Spieler hat folgendes gewürfelt:");
		labelCards = new Label("Der Spieler hat folgende Karten erwürelt:");
		labelNext = new Label("Spieler "+"Nr "+"ist nun am Zug!");
		btnBack = MovingPopupView.initializeButton("zurück zum Spiel");
		
		root.add(labelQueue, 0, 0);
		root.add(labelDice, 0, 2);
		root.add(labelCards, 0, 5);
		root.add(labelNext, 0, 7);
		root.add(btnBack, 10, 10);
		
		GridPane.setHalignment(btnBack, HPos.RIGHT);
		
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
