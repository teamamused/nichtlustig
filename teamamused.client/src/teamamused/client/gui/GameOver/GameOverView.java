package teamamused.client.gui.GameOver;

import java.io.FileNotFoundException;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import teamamused.common.LogHelper;
import teamamused.common.ResourceLoader;
import teamamused.common.gui.AbstractView;

public class GameOverView extends AbstractView<GameOverModel> {

	protected GridPane root;
	protected HBox titlePane, lastPane;
	protected VBox contentPane;
	protected Label labelTxt, labelWinner;
	protected Button btnNewStart, btnClose, btnTrophy;
	protected Image trophyImage;
	protected ImageView trophyIcon;
	// Zusatzbild fehlt noch

	// HBox für Logo rechts
	// VBox für labelTxt links
	// für labelWinner links
	// für Bild
	// HBox für Buttons und Trophy

	public GameOverView(Stage stage, GameOverModel model) {
		super(stage, model);
	}

	@Override
	protected Scene createGUI() {
		stage.setTitle("Nicht Lustig: Alles hat ein Ende - nur die Wurst hat zwei... ;-)");

		// Definition der Haupt-Pane
		root = new GridPane();
		root.setPadding(new Insets(50, 50, 50, 50));
		root.setHgap(10);
		root.setVgap(10);
		root.setGridLinesVisible(false);

		// Definition der Titel-Pane inkl. Instanziierung und Zuweisung der
		// Controlls
		titlePane = new HBox();
//		titlePane.getChildren().add("logo"); -> siehe Server
		
		labelTxt = new Label("Das Spiel ist nun zu Ende...");
		labelWinner = new Label("Herzliche Gratulation: Spieler " + "Nr" + " gewinnt!");
		btnNewStart = GameOverView.initializeButton("Spiel erneut spielen");
		btnClose = GameOverView.initializeButton("Spiel beenden");
		try {
			trophyImage = ResourceLoader.getImage("Pokal.png");
		} catch (FileNotFoundException e) {
			LogHelper.LogException(e);
		}
		trophyIcon = new ImageView(trophyImage);
		trophyIcon.setFitHeight(30);
		trophyIcon.setPreserveRatio(true);
		btnTrophy = new Button("", trophyIcon);
		btnTrophy.setId("btnTrophy");

		Scene scene = new Scene(root, 900, 600);

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
		btn.setPrefSize(200, 40);
		btn.setAlignment(Pos.CENTER);
		return btn;
	}

}
