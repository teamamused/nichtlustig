package teamamused.server.gui;

import java.io.FileNotFoundException;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import teamamused.common.ResourceLoader;
import teamamused.common.ServiceLocator;
import teamamused.common.gui.AbstractView;
import teamamused.server.TextAreaHandler;
import teamamused.server.connect.ClientAwaiter;

/**
 * Die Klasse ServerView stellt nach dem MVC-Pattern die grafische Oberfläche
 * für den Server dar.
 * 
 * @author Michelle
 *
 */
public class ServerView extends AbstractView<ServerModel> {
	
	protected GridPane root;
	protected Button restartButton;
	protected Label labelServer, labelLogo, labelConnect, labelPort, labelIP, labelProtocol, labelTeam;
	protected Image logo;
	protected ImageView spracheImageView;
	protected ScrollPane scrollTxt;
	protected ChoiceBox<String> language;
	protected TextArea loggingTxtArea;
	
	public ServerView(Stage stage, ServerModel model){
		super(stage, model);
	}

	@Override
	protected Scene createGUI() {
		stage.setTitle("Team Amused: Server");
		
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
		restartButton = new Button("Server neustarten");
		scrollTxt = new ScrollPane();
		language = new ChoiceBox<String>();
		language.getItems().add("Deutsch");
		language.getItems().add("Englisch");
		//Setzt den ersten Wert der Auswahlliste als Default
		language.getSelectionModel().selectFirst();
		// holt das TextArea aus dem TextAreaHandler
		this.loggingTxtArea = TextAreaHandler.getInstance().getTextArea();
		scrollTxt.setContent(loggingTxtArea);
		loggingTxtArea.setEditable(false);
		try {
			spracheImageView = new ImageView(ResourceLoader.getImage("Sprache.png"));
			spracheImageView.setFitWidth(30);
			spracheImageView.setFitWidth(30);
			spracheImageView.setPreserveRatio(true);
		} catch (FileNotFoundException e) {
			ServiceLocator.getInstance().getLogger().severe(e.toString());
		}

		//Hinzufügen der Controlls der Pane
		root.add(restartButton, 0, 0);
		root.add(labelServer, 0, 1);
		root.add(labelLogo, 2, 0);
		root.add(labelConnect, 0, 2);
		root.add(labelPort, 1, 2);
		root.add(labelIP, 1, 3);
		root.add(labelProtocol, 0, 5);
		root.add(language, 2, 6);
		root.add(spracheImageView, 2, 6);
		root.add(labelTeam, 0, 6);
		root.add(loggingTxtArea, 0, 5, 3, 1);
		
		//Ausrichtung der Controlls in der Pane
		GridPane.setHalignment(restartButton, HPos.LEFT);
		GridPane.setValignment(restartButton, VPos.CENTER);
		GridPane.setHalignment(labelLogo, HPos.RIGHT);
		GridPane.setValignment(labelLogo, VPos.TOP);
		GridPane.setHalignment(language, HPos.RIGHT);
		GridPane.setValignment(language, VPos.BOTTOM);
		GridPane.setHalignment(spracheImageView, HPos.LEFT);
		GridPane.setValignment(spracheImageView, VPos.CENTER);
		
		//Zuweisung des Stylesheets
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		labelServer.setId("labelServer");
		labelTeam.setId("labelTeam");
		
		return scene;
	}

}
