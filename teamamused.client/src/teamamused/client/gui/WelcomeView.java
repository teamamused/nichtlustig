package teamamused.client.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import teamamused.common.gui.AbstractView;

public class WelcomeView extends AbstractView<WelcomeModel> {

	public WelcomeView(Stage stage, WelcomeModel model) {
		super(stage, model);
	
	}

	@Override
	protected Scene createGUI() {

		// Create the labels
		Label labelUser = new Label("Benutzername");
		
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER_RIGHT);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(100, 100, 100, 100));

		grid.add(labelUser, 0, 0);
		
		Scene scene = new Scene(grid, 800, 600);
		
		return scene;
	}

}
