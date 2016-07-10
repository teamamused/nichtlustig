package teamamused.client.gui;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class LogInView {
	
	final private LogInModel model;
	final private Stage stage;
	
	protected LogInView(Stage stage, LogInModel model) {
		this.stage = stage;
		this.model = model;
		
		// Create the labels
		Label labelUser = new Label("Benutzername");
		Label labelPassword = new Label("Passwort");
		Label labelNeu = new Label("Neu bei uns?");
				
		// Create the text fields
		TextField textUser = new TextField ();
		TextField textPassword = new TextField ();
				
		// Create the button
		Button btnLogin = new Button();
		btnLogin.setText("Login");
				
		// Create the hyperlink
		Hyperlink linkReg = new Hyperlink();
		linkReg.setText("Registrieren?");
		linkReg.setOnAction((ActionEvent e) -> {
		System.out.println("This link is clicked");
		});
				
		ChoiceBox cbLang = new ChoiceBox();
		cbLang.setItems(FXCollections.observableArrayList("Deutsch", "English"));
		
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER_RIGHT);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(100, 100, 100, 100));
		
		grid.add(labelUser, 2, 0);
		grid.add(textUser, 2, 1);
		grid.add(labelPassword, 2, 2);
		grid.add(textPassword, 2, 3);
		grid.add(btnLogin, 2, 4);
		grid.add(labelNeu, 2, 8);
		grid.add(linkReg, 2, 9);
		grid.add(cbLang, 2, 10);
		
		// Add the layout pane to a scene
		Scene scene = new Scene(grid, 800, 600);
				
		// Finalize and show the stage
		stage.setScene(scene);
		stage.setTitle("Nicht Lustig");
		stage.show();
	}
	
	public void start() {
		stage.show();
	}
	
	public void stop() {
		stage.hide();
	}

}
