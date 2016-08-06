package teamamused.client.gui;

import java.io.FileNotFoundException;
import java.util.Locale;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import teamamused.common.ResourceLoader;
import teamamused.common.ServiceLocator;
import teamamused.common.gui.AbstractView;
import teamamused.common.gui.LangText;
import teamamused.common.gui.Translator;

/**
 * Diese Klasse stellt die grafische Oberfläche für das Log-In dar.
 * 
 * @author Sandra
 *
 */

public class LogInView extends AbstractView<LogInModel> {

	protected Button btnConnectServer;
	protected Button btnLogin;
	protected TextField textServer;
	protected TextField textPort;
	protected TextField textUser;
	protected PasswordField password;
	protected Hyperlink linkReg;
	protected ChoiceBox<Locale> cbLang;
	protected Label labelFailLogIn;
	
	private Label labelCopyright;
	private Label labelConnect;
	private Label labelServer;
	private Label labelPort;
	private Label labelUser;
	private Label labelPassword;
	private Label labelNeu;


	public LogInView(Stage stage, LogInModel model) {
		super(stage, model);
	}

	protected Scene createGUI() {
		
		// Labels erstellen
		this.labelCopyright = new Label("Copyright © 2016, Team amused (FHNW)");
		this.labelConnect = new Label ("Bitte verbinden Sie sich als erstes mit dem Server:");
		this.labelServer = new Label("Server:");
		this.labelPort = new Label("Port:");
		this.labelUser = new Label("Benutzername");
		this.labelPassword = new Label("Passwort");
		this.labelNeu = new Label("Neu bei uns?");
		this.labelFailLogIn = new Label();

		labelCopyright.setId("labelCopyright");
		labelConnect.setId("labelConnect");
		labelFailLogIn.setId("labelFailLogIn");

		
		// Textfelder erstellen
		textServer = new TextField();
		textServer.setPrefWidth(140);
		textServer.setText("localhost");
		textPort = new TextField();
		textPort.setPrefWidth(80);
		textPort.setText("9636");
		textUser = new TextField();
		textUser.setPromptText("Dein Benutzername");
		password = new PasswordField();
		password.setPromptText("Dein Passwort");

		// Login-Button erstellen
		btnConnectServer = new Button();
		btnConnectServer.setText("Server verbinden");
		
		// Login-Button erstellen
		btnLogin = new Button();
		btnLogin.setText("Login");
		btnLogin.setDisable(true);

		// Hyperlink für Registrierung erstellen
		linkReg = new Hyperlink();
		linkReg.setText("Registrieren");

		// ChoiceBox erstellen und Auswahlmöglichkeiten aus dem ServiceLocator holen
		cbLang = new ChoiceBox<>();
		cbLang.setItems(FXCollections.observableArrayList(ServiceLocator.getInstance().getLocales()));
		cbLang.getSelectionModel().selectFirst();
		
		ImageView iview = null;
		try {
			iview = new ImageView(ResourceLoader.getImage("NichtLustig.png"));
			iview.setFitWidth(360);
			iview.setFitWidth(360);
			iview.setPreserveRatio(true);
		} catch (FileNotFoundException e1) {
			ServiceLocator.getInstance().getLogger().severe(e1.toString());
		}
		
		ImageView iview2 = null;
		try {
			iview2 = new ImageView(ResourceLoader.getImage("Sprache.png"));
			iview2.setFitWidth(30);
			iview2.setFitWidth(30);
			iview2.setPreserveRatio(true);
		} catch (FileNotFoundException e1) {
			ServiceLocator.getInstance().getLogger().severe(e1.toString());
		}
		
		ImageView iview3 = null;
		try {
			iview3 = new ImageView(ResourceLoader.getImage("Logo_1.png"));
			iview3.setFitWidth(140);
			iview3.setFitWidth(140);
			iview3.setPreserveRatio(true);
		} catch (FileNotFoundException e1) {
			ServiceLocator.getInstance().getLogger().severe(e1.toString());
		}
		
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER_RIGHT);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(10, 50, 50, 0));
		grid.add(labelUser, 2, 0);
		grid.add(textUser, 2, 1);
		grid.add(labelPassword, 2, 2);
		grid.add(password, 2, 3);
		grid.add(btnLogin, 2, 5);
		grid.add(labelFailLogIn, 2, 6);
		grid.add(labelNeu, 2, 7);
		grid.add(linkReg, 2, 8);
		grid.add(iview2, 1, 10);
		grid.add(cbLang, 2, 10);
		
		StackPane stackConnect = new StackPane();
		stackConnect.setPadding(new Insets(30, 0, 10, 50));
		stackConnect.setAlignment(Pos.TOP_LEFT);
		stackConnect.getChildren().addAll(labelConnect);
		
		HBox hboxConnect = new HBox();
		hboxConnect.setPadding(new Insets(0, 50, 0, 50));
		hboxConnect.setAlignment(Pos.TOP_LEFT);
		hboxConnect.setSpacing(15);
		hboxConnect.getChildren().addAll(labelServer, textServer, labelPort, textPort, btnConnectServer);
		hboxConnect.setId("hboxConnect");
	    
	    HBox hboxLogo = new HBox();
	    hboxLogo.setPadding(new Insets(30, 50, 0, 0));
	    hboxLogo.setAlignment(Pos.TOP_RIGHT);
	    hboxLogo.setSpacing(15);
	    hboxLogo.getChildren().addAll(iview3);
	    
		VBox vbox = new VBox();
		vbox.setPadding(new Insets(40, 0, 0, 90));
		vbox.setAlignment(Pos.TOP_CENTER);
		vbox.setSpacing(10);
		vbox.getChildren().addAll(iview);
		vbox.getChildren().addAll(labelCopyright);

		BorderPane borderConnect = new BorderPane();
		borderConnect.setTop(stackConnect);
		borderConnect.setLeft(hboxConnect);
		
		BorderPane border = new BorderPane();
		border.setLeft(borderConnect);
		border.setRight(hboxLogo);
	    
		BorderPane border2 = new BorderPane();
		border2.setTop(border);
		border2.setLeft(vbox);
		border2.setCenter(grid);

		// Das Layout Pane einer Scene hinzufügen
		Scene scene = new Scene(border2, 900, 600);
		
		// Fenstertitel setzen
		stage.setTitle("Nicht Lustig: Log-In");

		// Stylesheet zuweisen
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

		updateTexts();
		
		return scene;
	}

	public void start() {
		stage.show();
	}

	public void stop() {
		stage.hide();
	}

	/**
	 * Aktualisiert die Sprachtexte auf allen GUI-Elementen
	 */
	protected void updateTexts() {
		
		// Translator holen
		Translator tl = ServiceLocator.getInstance().getTranslator();
		
		// Texte holen
		stage.setTitle(tl.getString(LangText.LogInTitel));
		this.labelCopyright.setText(tl.getString(LangText.LogInCopyright));
		this.labelConnect.setText(tl.getString(LangText.LogInConnect));
		this.labelServer.setText(tl.getString(LangText.LogInServer));
		this.labelPort.setText(tl.getString(LangText.LogInPort));
		this.labelUser.setText(tl.getString(LangText.LogInUser));
		this.textUser.setPromptText(tl.getString(LangText.LogInTextUser));
		this.labelPassword.setText(tl.getString(LangText.LogInLabelPassword));
		this.password.setPromptText(tl.getString(LangText.LogInPassword));
		this.labelNeu.setText(tl.getString(LangText.LogInNeu));
		this.btnConnectServer.setText(tl.getString(LangText.LogInButtonServer));
		this.linkReg.setText(tl.getString(LangText.LogInLinkReg));
	}

}
