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
		labelTschuess.setId("labelTschuess");
		
		ImageView iview = null;
		try {
			iview = new ImageView(ResourceLoader.getImage("Tschuess.jpg"));
			iview.setFitWidth(700);
			iview.setFitWidth(700);
			iview.setPreserveRatio(true);
		} catch (FileNotFoundException e1) {
			ServiceLocator.getInstance().getLogger().severe(e1.toString());
		}
		
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.TOP_CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(50, 50, 50, 50));

		grid.add(labelTschuess, 0, 0);
		grid.add(iview, 0, 3);

		// Add the layout pane to a scene
		Scene scene = new Scene(grid, 900, 600);

		stage.setTitle("Nicht Lustig: Bye");
		
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
