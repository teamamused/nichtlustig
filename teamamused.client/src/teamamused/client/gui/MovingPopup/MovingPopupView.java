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

public class MovingPopupView extends AbstractView<MovingPopupModel> {

	public MovingPopupView(Stage stage, MovingPopupModel model) {
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
