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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import teamamused.common.ResourceLoader;
import teamamused.common.ServiceLocator;
import teamamused.common.gui.AbstractView;

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

	public RegisterView(Stage stage, RegisterModel model) {
		super(stage, model);

	}

	@Override
	protected Scene createGUI() {
		
		// Labels erstellen
		Label labelTitle = new Label("Hallo Neuling!");
		labelTitle.setId("labelTitle");
		Label labelCopyright = new Label("Copyright © 2016, Team amused (FHNW)");
		labelCopyright.setId("labelCopyright");
		Label labelRegister = new Label("Hier kannst du dich registrieren:");
		Label labelRegUser = new Label("Benutzername");
		Label labelRegPassword = new Label("Passwort");
		Label labelRegPassword2 = new Label("Passwort bestätigen");

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
		grid.setPadding(new Insets(30, 50, 30, 50));

		grid.add(labelTitle, 0, 1);
		grid.add(labelRegister, 0, 3);
		grid.add(labelRegUser, 0, 5);
		grid.add(textRegUser, 0, 6);
		grid.add(labelRegPassword, 0, 7);
		grid.add(password, 0, 8);
		grid.add(labelRegPassword2, 0, 9);
		grid.add(password2, 0, 10);
		
		HBox hbox = new HBox();
		hbox.setPadding(new Insets(0, 50, 50, 50));
		hbox.setAlignment(Pos.CENTER_LEFT);
	    hbox.setSpacing(15);
	    hbox.getChildren().addAll(iview2, cbLang);
	    hbox.getChildren().addAll(btnRegister);
	    
		HBox hbox2 = new HBox();
		hbox2.setPadding(new Insets(0, 0, 0, 0));
		hbox2.setAlignment(Pos.TOP_RIGHT);
	    hbox2.setSpacing(15);
	    hbox2.getChildren().addAll(btnExit);
		
		VBox vbox = new VBox();
		vbox.setPadding(new Insets(50, 80, 0, 50));
		vbox.setAlignment(Pos.TOP_CENTER);
		vbox.setSpacing(20);
		vbox.getChildren().addAll(hbox2);
		vbox.getChildren().addAll(iview);
		vbox.getChildren().addAll(labelCopyright);
	    
		BorderPane border = new BorderPane();
		border.setLeft(grid);
		border.setRight(vbox);
		border.setBottom(hbox);

		// Das Layout Pane einer Scene hinzufügen
		Scene scene = new Scene(border, 900, 600);
		
		// Fenstertitel setzen
		stage.setTitle("Nicht Lustig: Register");

		// Stylesheet zuweisen
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

		return scene;
	}

}
