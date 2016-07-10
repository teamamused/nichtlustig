package teamamused.server.gui;

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
import teamamused.common.gui.AbstractView;
import teamamused.server.connect.ClientAwaiter;

public class ServerView extends AbstractView<ServerModel> {
	
	protected GridPane root;
	protected Button restartButton;
	protected Label labelServer, labelLogo, labelConnect, labelPort, labelIP, labelProtocol, labelTeam;
	protected TextArea loggingTxtArea;
	protected Image logo;
	protected ScrollPane scrollTxt;
	protected ChoiceBox<String> language;
	
	public ServerView(Stage stage, ServerModel model){
		super(stage, model);
	}
	
	@Override
	protected void initView() {
		// TODO: Für was ist diese Methode?
	}

	@Override
	protected Scene createGUI() {
		stage.setTitle("Team Amused: Server");
		
		//Definition der Pane
		root = new GridPane();
		root.setPadding(new Insets(20, 20, 20, 20));
		root.setHgap(10);
		root.setVgap(10);
		root.setGridLinesVisible(false);
		
		Scene scene = new Scene (root);
		
		logo = new Image("Logo_1.png", 400, 400, true, true);;
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

		//Hinzufügen der Controlls der Pane
		root.add(restartButton, 0, 0);
		root.add(labelServer, 0, 1);
		root.add(labelLogo, 2, 0);
		root.add(labelConnect, 0, 2);
		root.add(labelPort, 1, 2);
		root.add(labelIP, 1, 3);
		root.add(labelProtocol, 0, 5);
		root.add(language, 2, 6);
		root.add(labelTeam, 0, 6);
		
		//Ausrichtung der Controlls in der Pane
		GridPane.setHalignment(restartButton, HPos.LEFT);
		GridPane.setValignment(restartButton, VPos.CENTER);
		GridPane.setHalignment(labelLogo, HPos.RIGHT);
		GridPane.setValignment(labelLogo, VPos.TOP);
		GridPane.setHalignment(language, HPos.RIGHT);
		GridPane.setValignment(language, VPos.BOTTOM);
		
		//Zuweisung des Stylesheets
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		labelServer.setId("labelServer");
		labelTeam.setId("labelTeam");
		
		return scene;
	}

	/**
	 * Setzt das TextArea-Controll für die Log-Ausgabe
	 * 
	 * @param loggingTxtArea TextArea für die Log-Ausgabe
	 */
	public void setLoggingTxtArea(TextArea loggingTxtArea) {
		this.loggingTxtArea = loggingTxtArea;
		scrollTxt.setContent(loggingTxtArea);
		root.add(loggingTxtArea, 0, 5, 3, 1);
		loggingTxtArea.setEditable(false);
	}

}
