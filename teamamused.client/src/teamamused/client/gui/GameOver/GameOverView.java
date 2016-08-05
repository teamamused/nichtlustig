package teamamused.client.gui.GameOver;

import java.io.FileNotFoundException;

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
import teamamused.common.ServiceLocator;
import teamamused.common.gui.AbstractView;
import teamamused.common.gui.LangText;
import teamamused.common.gui.Translator;

/**
 * Die Klasse stellt die grafische Oberfläche für die Schlussseite dar.
 * @author Michelle
 *
 */
public class GameOverView extends AbstractView<GameOverModel> {

	protected GridPane root;
	protected HBox titlePane, txtPane, contentPane, rankingPane;
	protected VBox buttonPane;
	protected Label labelTxt, labelWinner;
	protected Button btnNewStart, btnClose, btnTrophy;
	protected ImageView logo, giftImage, trophyIcon;
	protected ScrollPane scrollPane;

	public GameOverView(Stage stage, GameOverModel model) {
		super(stage, model);
	}

	@Override
	protected Scene createGUI() {
		stage.setTitle("Nicht Lustig: Alles hat ein Ende...");

		// Definition der Haupt-Pane
		root = new GridPane();
		root.setPadding(new Insets(10, 10, 10, 10));
		root.setHgap(10);
		root.setVgap(10);
		root.setGridLinesVisible(false);
		scrollPane = new ScrollPane();
		scrollPane.setContent(root);

		// Definition der Title-Pane inkl. Instanziierung und Zuweisung der
		// Controlls
		titlePane = new HBox();
		try {
			logo = new ImageView(ResourceLoader.getImage("Logo_1.png"));
		} catch (FileNotFoundException e) {
			LogHelper.LogException(e);
		}
		logo.setFitWidth(200);
		logo.setPreserveRatio(true);
		labelTxt = new Label("Das Spiel ist nun zu Ende...");
		labelTxt.setId("subtitle");
		titlePane.setSpacing(200);
		titlePane.setPrefWidth(900);
		titlePane.setAlignment(Pos.CENTER);
		titlePane.setSpacing(420);
		titlePane.getChildren().addAll(labelTxt, logo);

		// Definition der Pane für den Text inkl. Instanziierung und Zuweisung
		// des Controlls
		txtPane = new HBox();
		labelWinner = new Label("Herzliche Gratulation: Spieler " + "Nr" + " gewinnt!");
		txtPane.getChildren().add(labelWinner);

		// Definition der Button-Pane inkl. Instanziierung und Zuweisung der
		// Controlls
		buttonPane = new VBox();
		btnNewStart = GameOverView.initializeButton("Spiel erneut spielen");
		btnClose = GameOverView.initializeButton("Spiel beenden");
		buttonPane.setSpacing(10);
		buttonPane.setAlignment(Pos.BOTTOM_RIGHT);
		buttonPane.getChildren().addAll(btnNewStart, btnClose);

		// Definition der Content-Pane inkl. Instanziierung und Zuweisung der
		// Controlls
		contentPane = new HBox();
		try {
			giftImage = new ImageView(ResourceLoader.getImage("Geschenk.jpg"));
		} catch (FileNotFoundException e) {
			LogHelper.LogException(e);
		}
		giftImage.setFitHeight(350);
		giftImage.setPreserveRatio(true);
		contentPane.setAlignment(Pos.CENTER);
		contentPane.setSpacing(50);
		contentPane.getChildren().addAll(giftImage, buttonPane);

		// Definition der Ranking-Pane inkl. Instanziierung und Zuweisung des
		// Controlls
		rankingPane = new HBox();
		try {
			trophyIcon = new ImageView(ResourceLoader.getImage("Pokal.png"));
		} catch (FileNotFoundException e) {
			LogHelper.LogException(e);
		}
		trophyIcon.setFitHeight(30);
		trophyIcon.setPreserveRatio(true);
		btnTrophy = new Button("", trophyIcon);
		btnTrophy.setId("btnTransparent");
		rankingPane.setAlignment(Pos.CENTER_RIGHT);
		rankingPane.setPadding(new Insets(10, 10, 10, 10));
		rankingPane.getChildren().add(btnTrophy);

		// Zuweisung der Subpanes zur Haupt-Pane
		root.add(titlePane, 0, 0);
		root.add(txtPane, 0, 1);
		root.add(contentPane, 0, 2);
		root.add(rankingPane, 0, 3);

		Scene scene = new Scene(root, 900, 600);

		// Zuweisung des Stylesheets
		try {
			scene.getStylesheets().add(getClass().getResource("..\\application.css").toExternalForm());
		} catch (Exception e) {
			e.printStackTrace();
		}

		updateTexts();
		
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
	
	/**
	 * Aktualisiert die Sprachtexte auf allen GUI-Elementen
	 */
	protected void updateTexts() {

		// Translator holen
		Translator tl = ServiceLocator.getInstance().getTranslator();

		// Texte holen
		stage.setTitle(tl.getString(LangText.GameOverTitle));
		this.labelTxt.setText(tl.getString(LangText.GameOverTxt));
		this.labelWinner.setText(tl.getString(LangText.GameOverWinner));
		this.btnNewStart.setText(tl.getString(LangText.GameOverBtnStart));
		this.btnClose.setText(tl.getString(LangText.GameOverBtnClose));
	}

}
