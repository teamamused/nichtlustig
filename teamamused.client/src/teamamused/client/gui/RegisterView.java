package teamamused.client.gui;

import java.io.FileNotFoundException;
import java.util.Locale;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
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
 * Diese Klasse stellt die grafische Oberfläche für die Registrierungsseite dar.
 * 
 * @author Sandra
 *
 */

public class RegisterView extends AbstractView<RegisterModel> {
	
	protected Button btnRegister;
	protected Button btnExit;
	protected TextField textRegUser;
	protected PasswordField password;
	protected PasswordField password2;
	protected ChoiceBox<Locale> cbLang;
	
	private Label labelRegister;
	private Label labelCopyright;
	private Label labelRegisterHere;
	private Label labelRegUser;
	private Label labelRegPassword;
	private Label labelRegPassword2;

	public RegisterView(Stage stage, RegisterModel model) {
		super(stage, model);

	}

	@Override
	protected Scene createGUI() {
		
		// Labels erstellen
		this.labelRegister = new Label("Hallo Neuling!");
		this.labelCopyright = new Label("Copyright © 2016, Team amused (FHNW)");
		this.labelRegisterHere = new Label("Hier kannst du dich registrieren:");
		this.labelRegUser = new Label("Benutzername");
		this.labelRegPassword = new Label("Passwort");
		this.labelRegPassword2 = new Label("Passwort bestätigen");
		
		labelRegister.setId("labelTitle");
		labelCopyright.setId("labelCopyright");

		// Textfelder erstellen
		textRegUser = new TextField();
		textRegUser.setPromptText("Dein Benutzername");
		
		//Passwortfelder erstellen
		password = new PasswordField();
		password.setPromptText("Dein Passwort");
		
		//Passwortfelder erstellen
		password2 = new PasswordField();
		password2.setPromptText("Dein Passwort bestätigen");


		// Registrierungs-Button erstellen
		btnRegister = new Button();
		btnRegister.setText("Registrieren");
		
		// ChoiceBox erstellen und Auswahlmöglichkeiten aus dem ServiceLocator holen
		cbLang = new ChoiceBox<>();
		cbLang.setItems(FXCollections.observableArrayList(ServiceLocator.getInstance().getLocales()));
		cbLang.getSelectionModel().selectFirst();

		ImageView iview = null;
		try {
			iview = new ImageView(ResourceLoader.getImage("NichtLustig.png"));
			iview.setFitWidth(380);
			iview.setFitWidth(380);
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
			iview3 = new ImageView(ResourceLoader.getImage("Back.png"));
			iview3.setFitWidth(30);
			iview3.setFitWidth(30);
			iview3.setPreserveRatio(true);
		} catch (FileNotFoundException e1) {
			ServiceLocator.getInstance().getLogger().severe(e1.toString());
		}
		
		// Exit-Button mit Bild erstellen
		btnExit = new Button();
		btnExit.setGraphic(iview3);
		btnExit.setId("btnTransparent");
		
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.TOP_LEFT);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(0, 0, 0, 0));

		grid.add(labelRegister, 0, 1);
		grid.add(labelRegisterHere, 0, 3);
		grid.add(labelRegUser, 0, 4);
		grid.add(textRegUser, 0, 5);
		grid.add(labelRegPassword, 0, 6);
		grid.add(password, 0, 7);
		grid.add(labelRegPassword2, 0, 8);
		grid.add(password2, 0, 9);
		
		HBox hboxLang = new HBox();
		hboxLang.setPadding(new Insets(0, 0, 0, 0));
		hboxLang.setAlignment(Pos.TOP_LEFT);
		hboxLang.setSpacing(15);
		hboxLang.getChildren().addAll(iview2, cbLang);
	    
	    StackPane stackRegister = new StackPane();
	    stackRegister.setPadding(new Insets(0, 0, 0, 0));
	    stackRegister.setAlignment(Pos.TOP_RIGHT);
	    stackRegister.getChildren().addAll(btnRegister);
		
	    VBox vboxLeft = new VBox();
	    vboxLeft.setPadding(new Insets(30, 0, 0, 50));
	    vboxLeft.setAlignment(Pos.TOP_CENTER);
	    vboxLeft.setSpacing(20);
	    vboxLeft.getChildren().addAll(grid);
	    vboxLeft.getChildren().addAll(hboxLang);
	    vboxLeft.getChildren().addAll(stackRegister);
	    
	    StackPane stackExit = new StackPane();
	    stackExit.setPadding(new Insets(0, 50, 0, 0));
	    stackExit.setAlignment(Pos.TOP_RIGHT);
	    stackExit.getChildren().addAll(btnExit);
	    
	    StackPane stackImage = new StackPane();
	    stackImage.setPadding(new Insets(20, 90, 0, 0));
	    stackImage.setAlignment(Pos.TOP_CENTER);
	    stackImage.getChildren().addAll(iview);
	    
	    StackPane stackCopyright = new StackPane();
	    stackCopyright.setPadding(new Insets(0, 90, 0, 0));
	    stackCopyright.setAlignment(Pos.TOP_CENTER);
	    stackCopyright.getChildren().addAll(labelCopyright);
	    
		VBox vboxRight = new VBox();
		vboxRight.setPadding(new Insets(30, 0, 0, 0));
		vboxRight.setAlignment(Pos.TOP_CENTER);
		vboxRight.setSpacing(20);
		vboxRight.getChildren().addAll(stackExit);
		vboxRight.getChildren().addAll(stackImage);
		vboxRight.getChildren().addAll(stackCopyright);
		
		BorderPane border = new BorderPane();
		border.setLeft(vboxLeft);
		border.setRight(vboxRight);

		// Das Layout Pane einer Scene hinzufügen
		Scene scene = new Scene(border, 900, 600);
		
		// Fenstertitel setzen
		stage.setTitle("Nicht Lustig: Registrieren");

		// Stylesheet zuweisen
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

		return scene;
	}
	
	/**
	 * Aktualisiert die Sprachtexte auf allen GUI-Elementen
	 */
	protected void updateTexts() {
		
		// Translator holen
		Translator tl = ServiceLocator.getInstance().getTranslator();
		
		// Texte holen
		stage.setTitle(tl.getString(LangText.RegisterTitel));
		this.labelRegister.setText(tl.getString(LangText.RegisterHello));
		this.labelCopyright.setText(tl.getString(LangText.RegisterCopyright));
		this.labelRegisterHere.setText(tl.getString(LangText.RegisterHere));
		this.labelRegUser.setText(tl.getString(LangText.RegisterUser));
		this.textRegUser.setPromptText(tl.getString(LangText.RegisterTextRegUser));
		this.labelRegPassword.setText(tl.getString(LangText.RegisterLabelPassword));
		this.password.setPromptText(tl.getString(LangText.RegisterPassword));
		this.labelRegPassword2.setText(tl.getString(LangText.RegisterLabelPassword2));
		this.password2.setPromptText(tl.getString(LangText.RegisterPassword2));
	}

}

