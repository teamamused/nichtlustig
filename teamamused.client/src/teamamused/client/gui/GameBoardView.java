package teamamused.client.gui;

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
import teamamused.common.gui.AbstractView;


public class GameBoardView extends AbstractView<GameBoardModel> {

	protected GridPane root;
	protected GridPane cardPane;
	protected Image imgCard1;

	public GameBoardView(Stage stage, GameBoardModel model) {
		super(stage, model);
	}

	@Override
	protected Scene createGUI() {
		stage.setTitle("Team Amused: Spielfeld");

		//TODO: Eventuell keine GridPane als Hauptraster verwenden
		// Definition der Haupt-Pane
		root = new GridPane();
		root.setPadding(new Insets(20, 20, 20, 20));
		root.setHgap(10);
		root.setVgap(10);
		root.setGridLinesVisible(false);

		Scene scene = new Scene(root);

		//Definition der Sub-Pane für die Spielkarten
		cardPane = new GridPane();
		cardPane.setPadding(new Insets(20, 20, 20, 20));
		cardPane.setHgap(10);
		cardPane.setVgap(10);
		cardPane.setGridLinesVisible(false);
		
//			//Instanziierung der Controlls der Sub-Pane
//			try {
//				imgCard1 = ResourceLoader.getImage("Clown.png");
//			} catch (FileNotFoundException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}

		
		
		// Instanziierung der Controlls
		

		// Hinzufügen der Controlls der Pane
//		root.add(restartButton, 0, 0);


		// Ausrichtung der Controlls in der Pane
//		GridPane.setHalignment(restartButton, HPos.LEFT);
//		GridPane.setValignment(restartButton, VPos.CENTER);

		
		// Zuweisung des Stylesheets
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

		
		return scene;
	}
}
