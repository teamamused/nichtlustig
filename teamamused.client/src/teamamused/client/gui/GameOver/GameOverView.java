package teamamused.client.gui.GameOver;

import java.io.FileNotFoundException;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
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
	protected HBox imagePane, buttonPane;
	protected VBox contentPane;
	protected Label labelTxt, labelWinner;
	protected Button btnNewStart, btnClose, btnTrophy;
	protected ImageView logo, giftImage, trophyIcon;
	protected ScrollPane scrollPane;

	public GameOverView(Stage stage, GameOverModel model) {
		super(stage, model);
	}

	@Override
	protected Scene createGUI() {
		stage.setTitle("Nicht Lustig: Alles hat ein Ende - nur die Wurst hat zwei... ;-)");

		// Definition der Haupt-Pane
		root = new GridPane();
		root.setPadding(new Insets(10, 10, 10, 10));
		root.setHgap(10);
		root.setVgap(10);
		root.setGridLinesVisible(true);
		scrollPane = new ScrollPane();
		scrollPane.setContent(root);

		// Instanziierung des Logos
		try {
			logo = new ImageView(ResourceLoader.getImage("Logo_1.png"));
		} catch (FileNotFoundException e) {
			LogHelper.LogException(e);
		}
		logo.setFitWidth(200);
		logo.setPreserveRatio(true);

		// Definition der Content-Pane inkl. Instanziierung und Zuweisung der
		// Controlls
		contentPane = new VBox();
		labelTxt = new Label("Das Spiel ist nun zu Ende...");
		labelTxt.setId("labelGameOver");
		labelWinner = new Label("Herzliche Gratulation: Spieler " + "Nr" + " gewinnt!");
		contentPane.getChildren().addAll(labelTxt, labelWinner);
		contentPane.setPrefWidth(900);

		// Definition der Image-Pane inkl. Instanziierung und Zuweisung des
		// Bildes
		imagePane = new HBox();
		try {
			giftImage = new ImageView(ResourceLoader.getImage("Geschenk.jpg"));
		} catch (FileNotFoundException e) {
			LogHelper.LogException(e);
		}
		giftImage.setFitHeight(300);
		giftImage.setPreserveRatio(true);
		imagePane.getChildren().add(giftImage);
		
		// Definition der Button-Pane inkl. Instanziierung und Zuweisung der
		// Controlls
		buttonPane = new HBox();
		btnNewStart = GameOverView.initializeButton("Spiel erneut spielen");
		btnClose = GameOverView.initializeButton("Spiel beenden");
		try {
			trophyIcon = new ImageView(ResourceLoader.getImage("Pokal.png"));
		} catch (FileNotFoundException e) {
			LogHelper.LogException(e);
		}
		trophyIcon.setFitHeight(30);
		trophyIcon.setPreserveRatio(true);
		btnTrophy = new Button("", trophyIcon);
		btnTrophy.setId("btnTrophy");
		buttonPane.getChildren().addAll(btnNewStart, btnClose, trophyIcon);
		
		// Zuweisung der Subpanes zur Haupt-Pane
		root.add(logo, 0, 0);
		GridPane.setHalignment(logo, HPos.RIGHT);
		root.add(contentPane, 0, 1);
		root.add(imagePane, 0, 2);
		root.add(buttonPane, 0, 3);
		
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
