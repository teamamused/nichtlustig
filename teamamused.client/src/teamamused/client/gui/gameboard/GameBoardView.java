package teamamused.client.gui.gameboard;

import java.io.FileNotFoundException;
import java.util.logging.Level;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import teamamused.common.ResourceLoader;
import teamamused.common.ServiceLocator;
import teamamused.common.gui.AbstractView;

/**
 * Diese Klasse stellt die grafische Oberfläche für das Spielfeld dar.
 * 
 * @author Michelle
 *
 */
public class GameBoardView extends AbstractView<GameBoardModel> {

	protected GridPane root, cardPane, dicePane;
	protected VBox navigation;
	protected ImageView logo;
	protected Button btnGameBoard, btnPlayer1, btnPlayer2;
	protected TextArea txtChatScreen;
	protected TextField txtChatInput;
	protected ScrollPane scrollTxt;
	
	public GameBoardView(Stage stage, GameBoardModel model) {
		super(stage, model);
	}

	@Override
	protected Scene createGUI() {
		stage.setTitle("Team Amused: Spielfeld");

		// TODO: Eventuell keine GridPane als Hauptraster verwenden
		// Definition der Haupt-Pane
		root = new GridPane();
		root.setPadding(new Insets(20, 20, 20, 20));
		root.setHgap(10);
		root.setVgap(10);
		root.setGridLinesVisible(false);

		Scene scene = new Scene(root);
		
		// Instanziierung und Zuweisung der Controlls zur Haupt-Pane
		// TODO
		
		//TODO: txtChatScreen ausrichten -> botton
		// Definition der Pane für die linke Navigationsspalte
		navigation = new VBox();
		navigation.setPadding(new Insets(20, 20, 20, 20));
		try {
			logo = new ImageView(ResourceLoader.getImage("Logo_1.png"));
		} catch (FileNotFoundException e) {
			ServiceLocator.getInstance().getLogger().log(Level.SEVERE, e.getMessage(), e);
		}
		logo.setFitWidth(200);
		logo.setPreserveRatio(true);
		btnGameBoard = new Button("Spielfeld");
		btnGameBoard.setMaxSize(200, 40);
		//TODO: Anzahl Buttons für Spieler dynamisch gestalten - je nach Anzahl Mitspieler
		btnPlayer1 = new Button("Spieler 1");
		btnPlayer1.setMaxSize(200, 40);
		btnPlayer2 = new Button("Spieler 2");
		btnPlayer2.setMaxSize(200, 40);
		txtChatScreen = new TextArea();
		txtChatScreen.setPrefSize(200, 150);
		txtChatInput = new TextField();
		txtChatInput.setPrefWidth(200);
		scrollTxt = new ScrollPane();
		scrollTxt.setContent(txtChatScreen);
		navigation.getChildren().addAll(logo, btnGameBoard, btnPlayer1, btnPlayer2, txtChatScreen, txtChatInput);
		
		root.add(navigation, 0, 0, 1, 10);
		
		// Definition der Pane für die Spielkarten
		cardPane = new GridPane();
		cardPane.setPadding(new Insets(20, 20, 20, 20));
		cardPane.setHgap(10);
		cardPane.setVgap(10);
		cardPane.setGridLinesVisible(false);
		
		// Instanziierung und Zuweisung der Images zur Spielkarten-Pane
		cardPane.add(getImageView("Riebmann1Vorne.png"), 2, 0);
		cardPane.add(getImageView("Riebmann2Vorne.png"), 3, 0);
		cardPane.add(getImageView("Riebmann3Vorne.png"), 4, 0);
		cardPane.add(getImageView("Riebmann4Vorne.png"), 5, 0);
		cardPane.add(getImageView("Riebmann5Vorne.png"), 6, 0);
		cardPane.add(getImageView("Yeti1Vorne.png"), 2, 1);
		cardPane.add(getImageView("Yeti2Vorne.png"), 3, 1);
		cardPane.add(getImageView("Yeti3Vorne.png"), 4, 1);
		cardPane.add(getImageView("Yeti4Vorne.png"), 5, 1);
		cardPane.add(getImageView("Yeti5Vorne.png"), 6, 1);
		cardPane.add(getImageView("Lemming1Vorne.png"), 2, 2);
		cardPane.add(getImageView("Lemming2Vorne.png"), 3, 2);
		cardPane.add(getImageView("Lemming3Vorne.png"), 4, 2);
		cardPane.add(getImageView("Lemming4Vorne.png"), 5, 2);
		cardPane.add(getImageView("Lemming5Vorne.png"), 6, 2);	
		cardPane.add(getImageView("Professoren1Vorne.png"), 2, 3);
		cardPane.add(getImageView("Professoren2Vorne.png"), 3, 3);
		cardPane.add(getImageView("Professoren3Vorne.png"), 4, 3);
		cardPane.add(getImageView("Professoren4Vorne.png"), 5, 3);
		cardPane.add(getImageView("Professoren5Vorne.png"), 6, 3);
		cardPane.add(getImageView("Dino1Vorne.png"), 2, 4);
		cardPane.add(getImageView("Dino2Vorne.png"), 3, 4);
		cardPane.add(getImageView("Dino3Vorne.png"), 4, 4);
		cardPane.add(getImageView("Dino4Vorne.png"), 5, 4);
		cardPane.add(getImageView("Dino5Vorne.png"), 6, 4);
		cardPane.add(getImageView("Tod0Vorne.png"), 7, 1);
		cardPane.add(getImageView("Tod2Vorne.png"), 7, 2);
		cardPane.add(getImageView("Tod4Vorne.png"), 7, 3);
		cardPane.add(getImageView("Tod1Vorne.png"), 8, 1);
		cardPane.add(getImageView("Tod3Vorne.png"), 8, 2);
		cardPane.add(getImageView("Tod5Vorne.png"), 8, 3);
		cardPane.add(getImageView("Ente.png"), 0, 1);
		cardPane.add(getImageView("Clown.png"), 0, 2);
		cardPane.add(getImageView("Zeitmaschine.png"), 0, 3);
		cardPane.add(getImageView("Ufo.png"), 1, 1);
		cardPane.add(getImageView("Roboter.png"), 1, 2);
		cardPane.add(getImageView("Killervirus.png"), 1, 3 );
		
		root.add(cardPane, 1, 0);
		
		// Definition der Pane für den Würfel-Bereich
		dicePane = new GridPane();
		dicePane.initializePane();
		

		// Ausrichtung der Controlls in der Pane
		// GridPane.setHalignment(restartButton, HPos.LEFT);
		// GridPane.setValignment(restartButton, VPos.CENTER);

		// Zuweisung des Stylesheets
		scene.getStylesheets().add(getClass().getResource("..\\application.css").toExternalForm());

		return scene;
	}

	/**
	 * Der Methode kann als String ein Bildname übergeben werden, anhand welchem
	 * der ResourceLoader ein Image-Objekt zurückgibt. Dieses Objekt wird einer
	 * ImageView übergeben und so in der Grösse angepasst und schlussendlich
	 * zurückgegeben.
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
			ServiceLocator.getInstance().getLogger().log(Level.SEVERE, e.getMessage(), e);
		}
		ImageView imageView = new ImageView(image);
		imageView.setFitHeight(100);
		imageView.setPreserveRatio(true);
		return imageView;
	}
	
//	private void initializePane(){
//		pane.setPadding(new Insets(20, 20, 20, 20));
//		pane.setHgap(10);
//		pane.setVgap(10);
//		pane.setGridLinesVisible(false);
//	}
}
