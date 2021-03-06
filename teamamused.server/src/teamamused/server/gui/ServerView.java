package teamamused.server.gui;

import java.io.FileNotFoundException;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import teamamused.common.LogHelper;
import teamamused.common.ResourceLoader;
import teamamused.common.ServiceLocator;
import teamamused.common.gui.AbstractView;
import teamamused.server.connect.ClientAwaiter;
import teamamused.server.lib.TextAreaHandler;

/**
 * Die Klasse ServerView stellt nach dem MVC-Pattern die grafische Oberfläche
 * für den Server dar.
 * 
 * @author Michelle
 *
 */
public class ServerView extends AbstractView<ServerModel> {
	
	protected GridPane root;
	protected Button restartButton, deployCardsButton;
	protected Label labelServer, labelLogo, labelConnect, labelPort, labelIP, labelProtocol, labelTeam;
	protected Image logo;
	protected ImageView spracheImageView;
	protected ScrollPane scrollTxt;
	protected TextArea loggingTxtArea;
	
	public ServerView(Stage stage, ServerModel model){
		super(stage, model);
	}

	@Override
	protected Scene createGUI() {
		stage.setTitle("Nicht Lustig: Server");
		
		//Definition der Pane
		root = new GridPane();
		root.setPadding(new Insets(50, 50, 50, 50));
		root.setHgap(10);
		root.setVgap(10);
		root.setGridLinesVisible(false);
		
		Scene scene = new Scene (root, 900, 600);
		
		logo = new Image("Logo_1.png", 250, 250, true, true);
		try {
			spracheImageView = new ImageView(ResourceLoader.getImage("Sprache.png"));
			spracheImageView.setFitWidth(30);
			spracheImageView.setFitWidth(30);
			spracheImageView.setPreserveRatio(true);
		} catch (FileNotFoundException e) {
			ServiceLocator.getInstance().getLogger().severe(e.toString());
		}
		labelServer = new Label("Dein Server läuft!");
		labelLogo = new Label("", new ImageView(logo));
		labelConnect = new Label("Spieler können sich verbinden über:");
		labelPort = new Label("\u2022" + "  Port " + ClientAwaiter.PORT_NUMBER);
		labelIP = new Label("\u2022" + "  IP " + ClientAwaiter.IP_ADDRESS);
		labelProtocol = new Label("Protokoll:");
		labelTeam = new Label("Team Amused: IT-Projekt an der FHNW, 2016");
		VBox buttons = new VBox(5);
		restartButton = initializeButton("Server neustarten");
		deployCardsButton = initializeButton("Spiel vorantreiben");
		buttons.getChildren().addAll(restartButton, deployCardsButton);
		scrollTxt = new ScrollPane();
		// holt das TextArea aus dem TextAreaHandler
		loggingTxtArea = TextAreaHandler.getInstance().getTextArea();
		loggingTxtArea.setEditable(false);
		loggingTxtArea.setId("loggingArea");
		scrollTxt.setContent(loggingTxtArea);

		//Hinzufügen der Controlls der Pane
		root.add(buttons, 0, 0);
		root.add(labelServer, 0, 1);
		root.add(labelLogo, 2, 0);
		root.add(labelConnect, 0, 2);
		root.add(labelPort, 1, 2);
		root.add(labelIP, 1, 3, 2, 1);
		root.add(labelProtocol, 0, 5);
		root.add(labelTeam, 0, 6);
		root.add(loggingTxtArea, 0, 5, 3, 1);
		
		//Ausrichtung der Controlls in der Pane
		GridPane.setHalignment(restartButton, HPos.LEFT);
		GridPane.setValignment(restartButton, VPos.CENTER);
		GridPane.setHalignment(labelLogo, HPos.RIGHT);
		GridPane.setValignment(labelLogo, VPos.TOP);

		// Prozentualle Grösse wird definiert für das Verhalten beim Maximieren
	     ColumnConstraints col1 = new ColumnConstraints();
	     col1.setPercentWidth(45);
	     ColumnConstraints col2 = new ColumnConstraints();
	     col2.setPercentWidth(22);
	     ColumnConstraints col3 = new ColumnConstraints();
	     col3.setPercentWidth(33);
	     root.getColumnConstraints().addAll(col1, col2, col3);
	     
		//Zuweisung des Stylesheets
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		labelServer.setId("labelServer");
		labelTeam.setId("labelTeam");
		
		return scene;
	}

	/**
	 * Definiert für das Server-GUI ein Bild für die Anzeige in der Taskleiste
	 */
	@Override
    protected Image getStateImage() {
		try {
			return ResourceLoader.getImage("Server.png");
		} catch (Exception ex) {
			LogHelper.LogException(ex);
		}
		return null;
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
