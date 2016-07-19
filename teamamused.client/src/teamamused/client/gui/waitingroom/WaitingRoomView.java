package teamamused.client.gui.waitingroom;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import teamamused.common.gui.AbstractView;

/**
 * TODO: Klasse beschreiben
 * 
 * @author Michelle
 *
 */
public class WaitingRoomView extends AbstractView<WaitingRoomModel>{
	
	protected Button btnPlayGame;

	public WaitingRoomView(Stage stage, WaitingRoomModel model) {
		super(stage, model);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Scene createGUI() {
		
		btnPlayGame = new Button();
		btnPlayGame.setText("Spiel starten");
		
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER_RIGHT);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(100, 100, 100, 100));
		
		grid.add(btnPlayGame, 2, 5);

		// Add the layout pane to a scene
		Scene scene = new Scene(grid, 800, 600);
		
		stage.setTitle("Nicht Lustig: Waiting Room");

		scene.getStylesheets().add(getClass().getResource("..\\application.css").toExternalForm());

		return scene;
	}

	//TODO: Anzeigen, welche Spieler sich verbunden haben (mit Namen). Erst, wenn alle Spieler auf "Spiel starten"
	//geklickt haben, wird das GameBoard initialisiert.
	
}
