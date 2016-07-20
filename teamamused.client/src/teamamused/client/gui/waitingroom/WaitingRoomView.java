package teamamused.client.gui.waitingroom;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import teamamused.common.gui.AbstractView;

public class WaitingRoomView extends AbstractView<WaitingRoomModel>{
	
	protected Button btnPlayGame;

	public WaitingRoomView(Stage stage, WaitingRoomModel model) {
		super(stage, model);
	
	}

	@Override
	protected Scene createGUI() {
		
		Label labelWait = new Label("Du befindest dich nun im Warteraum...");
		labelWait.setId("labelWait");
		Label labelPlayer = new Label ("Hier werden die Namen der Spieler aufgelistet");
		
		btnPlayGame = new Button();
		btnPlayGame.setText("Spiel starten");
		
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.TOP_LEFT);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(50, 50, 50, 50));
		
		grid.add(labelWait, 0, 0, 20, 1);
		grid.add(labelPlayer, 0, 5);
		grid.add(btnPlayGame, 0, 10);

		// Add the layout pane to a scene
		Scene scene = new Scene(grid, 900, 600);
		
		stage.setTitle("Nicht Lustig: Waiting Room");

		scene.getStylesheets().add(getClass().getResource("..\\application.css").toExternalForm());

		return scene;
	}

	//TODO: Anzeigen, welche Spieler sich verbunden haben (mit Namen). Erst, wenn alle Spieler auf "Spiel starten"
	//geklickt haben, wird das GameBoard initialisiert.
	
}
