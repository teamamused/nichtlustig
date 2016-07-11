package teamamused.client.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import teamamused.common.gui.AbstractView;

public class ByeView extends AbstractView<ByeModel> {

	public ByeView(Stage stage, ByeModel model) {
		super(stage, model);
	}

	protected Scene createGUI() {

		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER_RIGHT);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(100, 100, 100, 100));


		// Add the layout pane to a scene
		Scene scene = new Scene(grid, 800, 600);

		// scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

		return scene;
	}

	public void start() {
		stage.show();
	}

	public void stop() {
		stage.hide();
	}

}
