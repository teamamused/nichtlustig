package teamamused.client.gui;

import java.io.FileNotFoundException;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import teamamused.common.ResourceLoader;
import teamamused.common.ServiceLocator;
import teamamused.common.gui.AbstractView;

public class ByeView extends AbstractView<ByeModel> {

	public ByeView(Stage stage, ByeModel model) {
		super(stage, model);
	}

	protected Scene createGUI() {

		// Create the labels
		Label labelTschuess = new Label("Tschüss und auf Wiedersehen");
		
		ImageView iview = null;
		try {
			iview = new ImageView(ResourceLoader.getImage("Tschuess.jpg"));
			iview.setFitWidth(300);
			iview.setFitWidth(300);
			iview.setPreserveRatio(true);
		} catch (FileNotFoundException e1) {
			ServiceLocator.getInstance().getLogger().severe(e1.toString());
		}
		
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER_RIGHT);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(100, 100, 100, 100));

		grid.add(labelTschuess, 0, 0);

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
