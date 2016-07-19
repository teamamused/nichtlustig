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

public class RegisterView extends AbstractView<RegisterModel> {
	
	protected Button btnRegister;
	protected TextField textUser;
	protected TextField textPassword;
	protected TextField textPassword2;

	public RegisterView(Stage stage, RegisterModel model) {
		super(stage, model);

	}

	@Override
	protected Scene createGUI() {
		
		// Create the labels
		Label labelTitle = new Label("Hallo Neuling!");
		Label labelRegister = new Label("Hier kannst du dich registrieren:");
		Label labelUser = new Label("Benutzername");
		Label labelPassword = new Label("Passwort");
		Label labelPassword2 = new Label("Passwort bestätigen");

		textUser = new TextField();
		textPassword = new TextField();
		textPassword2 = new TextField();

		// Create the Button
		btnRegister = new Button();
		btnRegister.setText("Registrieren");

		ChoiceBox cbLang = new ChoiceBox();
		cbLang.setItems(FXCollections.observableArrayList("Deutsch", "English"));

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

		grid.add(labelTitle, 1, 1);
		grid.add(labelRegister, 1, 3);
		grid.add(labelUser, 1, 5);
		grid.add(textUser, 1, 6);
		grid.add(labelPassword, 1, 7);
		grid.add(textPassword, 1, 8);
		grid.add(labelPassword2, 1, 9);
		grid.add(textPassword2, 1, 10);
		grid.add(btnRegister, 1, 12);
		grid.add(iview2, 0, 14);
		grid.add(cbLang, 1, 14);
		//grid.add(iview, 8, 2);

		// Add the layout pane to a scene
		Scene scene = new Scene(grid, 800, 600);
		
		stage.setTitle("Nicht Lustig: Register");

		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

		return scene;
	}

}
