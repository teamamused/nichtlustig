package teamamused.client.gui;

import java.io.FileNotFoundException;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import teamamused.client.gui.gameboard.GameBoardView;
import teamamused.common.ResourceLoader;
import teamamused.common.gui.AbstractView;
import teamamused.common.ServiceLocator;

/**
 * Diese Klasse stellt die grafische Oberfläche für das Log-In dar.
 * 
 * @author Sandra
 *
 */

public class LogInView extends AbstractView<LogInModel> {

	protected Button btnLogin;
	protected TextField textUser;
	protected TextField textPassword;
	protected Hyperlink linkReg;

	public LogInView(Stage stage, LogInModel model) {
		super(stage, model);
	}

	protected Scene createGUI() {

		// Labels erstellen
		Label labelUser = new Label("Benutzername");
		Label labelPassword = new Label("Passwort");
		Label labelNeu = new Label("Neu bei uns?");

		// Textfelder erstellen
		textUser = new TextField();
		textPassword = new TextField();
		
		// Login-Button erstellen
		btnLogin = new Button();
		btnLogin.setText("Login");

		// Hyperlink für Registrierung erstellen
		linkReg = new Hyperlink();
		linkReg.setText("Registrieren");

		// ChoiceBox erstellen und Auswahlmöglichkeiten festlegen
		ChoiceBox cbLang = new ChoiceBox();
		cbLang.setItems(FXCollections.observableArrayList("Deutsch", "English"));
		cbLang.getSelectionModel().selectFirst();
		
		ImageView iview = null;
		try {
			iview = new ImageView(ResourceLoader.getImage("Nicht-Lustig.jpg"));
			iview.setFitWidth(300);
			iview.setFitWidth(300);
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
			iview3.setFitWidth(30);
			iview3.setFitWidth(30);
			iview3.setPreserveRatio(true);
		} catch (FileNotFoundException e1) {
			ServiceLocator.getInstance().getLogger().severe(e1.toString());
		}
		
//		GridPane image = new GridPane();
//		image.setAlignment(Pos.CENTER_LEFT);
//		image.setHgap(10);
//		image.setVgap(10);
//		image.setPadding(new Insets(100, 100, 100, 100));
//		image.add(iview, 0, 0);
		
		VBox vbox = new VBox(10);
		vbox.setPadding(new Insets(20, 20, 20, 20));
		vbox.getChildren().addAll(iview);
		
		HBox hbox = new HBox(10);
		hbox.setPadding(new Insets(20, 20, 20, 20));
		hbox.getChildren().addAll(iview3);
		
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER_RIGHT);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(50, 50, 50, 50));
		grid.add(labelUser, 2, 0);
		grid.add(textUser, 2, 1);
		grid.add(labelPassword, 2, 2);
		grid.add(textPassword, 2, 3);
		grid.add(btnLogin, 2, 5);
		grid.add(labelNeu, 2, 7);
		grid.add(linkReg, 2, 8);
		grid.add(iview2, 1, 10);
		grid.add(cbLang, 2, 10);
//		grid.add(iview, 0, 0);
		
//		BorderPane border = new BorderPane();
//		border.setTop(hbox);
//		border.setLeft(vbox);
//		border.setCenter(grid);
		
		GridPane root = new GridPane();
		root.setPadding(new Insets(20, 20, 20, 20));
		root.setHgap(10);
		root.setVgap(10);
		root.setGridLinesVisible(false);
//		root.add(hbox, 0, 0);
//		root.add(vbox, 0, 1);
//		root.add(grid, 1, 2);

		// Das Layout Pane einer Scene hinzufügen
		Scene scene = new Scene(grid, 900, 600);
		
		// Fenstertitel setzen
		stage.setTitle("Nicht Lustig: Log-In");

		// Stylesheet zuweisen
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

		return scene;
	}

	public void start() {
		stage.show();
	}

	public void stop() {
		stage.hide();
	}

}
