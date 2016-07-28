package teamamused.client.gui.MovingPopup;

import java.io.FileNotFoundException;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import teamamused.client.gui.gameboard.GameBoardModel;
import teamamused.common.LogHelper;
import teamamused.common.ResourceLoader;
import teamamused.common.gui.AbstractView;

/**
 * Diese Klasse stellt die grafische Oberfläche für die Benachrichtigung über
 * den Spielzug des Gegners dar.
 * 
 * @author Michelle
 *
 */
public class MovingPopupView extends AbstractView<GameBoardModel> {

	protected GridPane root;
	protected HBox titlePane, diceTxtPane, cardTxtPane, nextPlayerPane;
	protected FlowPane dicePane, cardPane;
	protected Label labelQueue, labelDice, labelCards, labelNext;
	protected Button btnClose;
	protected ScrollPane scrollPane;

	public MovingPopupView(Stage stage, GameBoardModel model) {
		super(stage, model);
	}

	protected Scene createGUI() {
		stage.setTitle("Nicht Lustig: Spielzug des Gegners");

		// Definition der Haupt-Pane
		root = new GridPane();
		root.setPadding(new Insets(50, 50, 50, 50));
		root.setHgap(10);
		root.setVgap(10);
		root.setAlignment(Pos.TOP_LEFT);
		root.setGridLinesVisible(false);
		scrollPane = new ScrollPane();
		scrollPane.setContent(root);

		Scene scene = new Scene(root, 1000, 700);

		// TODO: Nr. des aktuellen Spielers
		// Definition der Titel-Pane inkl. Instanziierung und Zuweisung der
		// Controlls
		titlePane = new HBox();
		labelQueue = new Label("Spieler " + "Nr" + " ist an der Reihe...");
		labelQueue.setId("subtitle");
		titlePane.getChildren().add(labelQueue);
		titlePane.setPrefWidth(1000);

		// Definition der Pane für den Text zu den Würfeln inkl.
		// Instanziierung und Zuweisung der Controlls
		diceTxtPane = new HBox();
		labelDice = new Label("Der Spieler hat folgendes gewürfelt:");
		diceTxtPane.getChildren().add(labelDice);

		// TODO: Muss dynamisch gestalten sein / WuerfelDummy löschen
		// Definition der Pane für die Würfel inkl. Instanziierung und
		// Zuweisung der Controlls
		dicePane = new FlowPane();
		dicePane.setHgap(10);
		dicePane.setVgap(10);
		dicePane.getChildren().addAll(getImageView("WuerfelDummy.jpg"));

		// Definition der Pane für den Text zu den Karten inkl.
		// Instanziierung und Zuweisung der Controlls
		cardTxtPane = new HBox();
		labelCards = new Label("Der Spieler hat folgende Karten erwürfelt:");
		cardTxtPane.getChildren().add(labelCards);

		// TODO: Muss dynamisch gestalten sein
		// Definition der Pane für die Spezialkarten inkl. Instanziierung und
		// Zuweisung der Controlls
		cardPane = new FlowPane();
		cardPane.setHgap(10);
		cardPane.setVgap(10);
		cardPane.getChildren().addAll(getImageView("Killervirus.png"), getImageView("Ente.png"),
				getImageView("Killervirus.png"), getImageView("Ente.png"), getImageView("Killervirus.png"),
				getImageView("Ente.png"), getImageView("Killervirus.png"), getImageView("Ente.png"),
				getImageView("Killervirus.png"), getImageView("Ente.png"));

		// TODO: Nr. des nächsten Spielers
		// Definition der Schluss-Pane inkl.
		// Instanziierung und Zuweisung der Controlls
		nextPlayerPane = new HBox();
		labelNext = new Label("Spieler " + "Nr" + " ist nun am Zug!");
		btnClose = new Button("schliessen");
		btnClose.setPrefSize(180, 40);
		btnClose.setAlignment(Pos.CENTER);
		nextPlayerPane.getChildren().addAll(labelNext, btnClose);
		nextPlayerPane.setAlignment(Pos.BASELINE_LEFT);
		nextPlayerPane.setSpacing(470);

		root.add(titlePane, 0, 0);
		root.add(diceTxtPane, 0, 2);
		root.add(dicePane, 0, 4);
		root.add(cardTxtPane, 0, 6);
		root.add(cardPane, 0, 8);
		root.add(nextPlayerPane, 0, 10);

		// Zuweisung des Stylesheets
		scene.getStylesheets().add(getClass().getResource("..\\application.css").toExternalForm());

		return scene;

	}

	/**
	 * 
	 * Dieser Support-Methode kann als String ein Bildname übergeben werden,
	 * anhand welchem der ResourceLoader ein Image-Objekt zurückgibt. Dieses
	 * Objekt wird einer ImageView übergeben und so in der Grösse für dem
	 * Spielfeld entsprechend angepasst und schlussendlich zurückgegeben.
	 * 
	 * @param imageName
	 *            Methode nimmt den Bildnamen (inkl. Endung) als String entgegen
	 * @return Methode gibt eine ImageView des gewünschten Bildes zurück
	 */
	private ImageView getImageView(String imageName) {
		Image image = null;
		try {
			image = ResourceLoader.getImage(imageName);
		} catch (FileNotFoundException e) {
			LogHelper.LogException(e);
		}
		ImageView imageView = new ImageView(image);
		imageView.setFitHeight(100);
		imageView.setPreserveRatio(true);
		return imageView;
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
