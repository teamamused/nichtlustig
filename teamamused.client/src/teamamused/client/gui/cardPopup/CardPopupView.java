package teamamused.client.gui.cardPopup;

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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import teamamused.client.gui.gameboard.GameBoardModel;
import teamamused.common.LogHelper;
import teamamused.common.ResourceLoader;
import teamamused.common.gui.AbstractView;

/**
 * Diese Klasse stellt die grafische Oberfläche für die Anzeige der Gegnerkarten
 * dar.
 * 
 * @author Michelle
 *
 */
public class CardPopupView extends AbstractView<GameBoardModel> {

	protected GridPane root;
	protected VBox titlePane;
	protected HBox cardTxtPane, specialCardTxtPane, buttonPane;
	protected FlowPane cardsFlowPane, specialCardsFlowPane;
	protected Label labelTitle, labelText, cardsRival, specialCardsRival;
	protected Button btnClose;
	protected ScrollPane scrollPane;

	public CardPopupView(Stage stage, GameBoardModel model) {
		super(stage, model);
	}

	protected Scene createGUI() {
		stage.setTitle("Nicht Lustig: Karten der Gegner");

		// Definition der Haupt-Pane
		root = new GridPane();
		root.setPadding(new Insets(20, 20, 20, 20));
		root.setHgap(10);
		root.setVgap(10);
		root.setAlignment(Pos.TOP_LEFT);
		root.setGridLinesVisible(false);
		scrollPane = new ScrollPane();
		scrollPane.setContent(root);

		Scene scene = new Scene(root, 1000, 700);

		// Definition der Titel-Pane inkl. Instanziierung und Zuweisung der
		// Controlls
		titlePane = new VBox();
		labelTitle = new Label("Karten von Spieler " + this.model.getPlayer().getPlayerNumber() + ": "
				+ this.model.getPlayer().getPlayerName());
		labelText = new Label("Hier siehst du die Karten deines Gegners:");
		labelTitle.setId("labelTitle");
		labelText.setId("subtitle");
		titlePane.getChildren().addAll(labelTitle, labelText);
		titlePane.setPrefWidth(1000);

		// Definition der Pane für den Text zu den Zielkarten inkl.
		// Instanziierung und Zuweisung der Controlls
		cardTxtPane = new HBox();
		cardsRival = new Label("Zielkarten");
		cardTxtPane.getChildren().add(cardsRival);

		// TODO: Muss dynamisch gestalten sein
		// Definition der Pane für die Zielkarten inkl. Instanziierung und
		// Zuweisung der Controlls
		cardsFlowPane = new FlowPane();
		cardsFlowPane.setHgap(10);
		cardsFlowPane.setVgap(10);
		cardsFlowPane.getChildren().addAll(getImageView("Dino1Vorne.png"), getImageView("Lemming1Vorne.png"),
				getImageView("Dino1Vorne.png"), getImageView("Lemming1Vorne.png"), getImageView("Dino1Vorne.png"),
				getImageView("Lemming1Vorne.png"), getImageView("Dino1Vorne.png"), getImageView("Lemming1Vorne.png"),
				getImageView("Lemming1Vorne.png"));

		// Definition der Pane für den Text zu den Spezialkarten inkl.
		// Instanziierung und Zuweisung der Controlls
		specialCardTxtPane = new HBox();
		specialCardsRival = new Label("Spezialkarten");
		specialCardTxtPane.getChildren().add(specialCardsRival);

		// TODO: Muss dynamisch gestalten sein
		// Definition der Pane für die Spezialkarten inkl. Instanziierung und
		// Zuweisung der Controlls
		specialCardsFlowPane = new FlowPane();
		specialCardsFlowPane.setHgap(10);
		specialCardsFlowPane.setVgap(10);
		specialCardsFlowPane.getChildren().addAll(getImageView("Killervirus.png"), getImageView("Ente.png"),
				getImageView("Killervirus.png"), getImageView("Ente.png"), getImageView("Killervirus.png"),
				getImageView("Ente.png"), getImageView("Killervirus.png"), getImageView("Ente.png"),
				getImageView("Killervirus.png"), getImageView("Ente.png"));

		// Definition der Pane für die Buttons inkl.
		// Instanziierung und Zuweisung des Controlls
		buttonPane = new HBox();
		btnClose = CardPopupView.initializeButton("schliessen");
		buttonPane.getChildren().add(btnClose);
		buttonPane.setSpacing(10);
		buttonPane.setAlignment(Pos.BASELINE_RIGHT);

		root.add(titlePane, 0, 0);
		root.add(cardTxtPane, 0, 1);
		root.add(cardsFlowPane, 0, 2, 1, 3);
		root.add(specialCardTxtPane, 0, 5);
		root.add(specialCardsFlowPane, 0, 6, 1, 3);
		root.add(buttonPane, 0, 10);

		// Zuweisung des Stylesheets
		try {
			scene.getStylesheets().add(getClass().getResource("..\\application.css").toExternalForm());
		} catch (Exception e) {
			e.printStackTrace();
		}

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
		btn.setPrefSize(180, 40);
		btn.setAlignment(Pos.CENTER);
		return btn;
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
