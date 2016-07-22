package teamamused.client.gui;

import java.io.FileNotFoundException;
import javafx.collections.FXCollections;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
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
	protected TextField textRegUser;
	protected TextField textRegPassword;
	protected TextField textRegPassword2;

	public RegisterView(Stage stage, RegisterModel model) {
		super(stage, model);

	}

	@Override
	protected Scene createGUI() {
		
		// Labels erstellen
		Label labelTitle = new Label("Hallo Neuling!");
		labelTitle.setId("labelTitle");
		Label labelRegister = new Label("Hier kannst du dich registrieren:");
		Label labelRegUser = new Label("Benutzername");
		Label labelRegPassword = new Label("Passwort");
		Label labelRegPassword2 = new Label("Passwort bestätigen");

		// Textfelder erstellen
		textRegUser = new TextField();
		textRegPassword = new TextField();
		textRegPassword2 = new TextField();

		// Registrierungs-Button erstellen
		btnRegister = new Button();
		btnRegister.setText("Registrieren");

		// ChoiceBox erstellen und Auswahlmöglichkeiten festlegen
		ChoiceBox cbLang = new ChoiceBox();
		cbLang.setItems(FXCollections.observableArrayList("Deutsch", "English"));
		cbLang.getSelectionModel().selectFirst();

		ImageView iview = null;
		try {
			iview = new ImageView(ResourceLoader.getImage("Nicht-Lustig.jpg"));
			iview.setFitWidth(400);
			iview.setFitWidth(400);
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
		
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.TOP_LEFT);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(50, 50, 50, 50));

		grid.add(labelTitle, 0, 1);
		grid.add(labelRegister, 0, 3);
		grid.add(labelRegUser, 0, 5);
		grid.add(textRegUser, 0, 6);
		grid.add(labelRegPassword, 0, 7);
		grid.add(textRegPassword, 0, 8);
		grid.add(labelRegPassword2, 0, 9);
		grid.add(textRegPassword2, 0, 10);
		grid.add(btnRegister, 0, 12);
		grid.add(iview2, 5, 6);
		grid.add(cbLang, 6, 6);

		// Das Layout Pane einer Scene hinzufügen
		Scene scene = new Scene(grid, 900, 600);
		
		// Fenstertitel setzen
		stage.setTitle("Nicht Lustig: Register");

		// Stylesheet zuweisen
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

		return scene;
	}

}
