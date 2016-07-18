package teamamused.client.gui;

import java.io.FileNotFoundException;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import teamamused.common.ResourceLoader;
import teamamused.common.ServiceLocator;
import teamamused.common.gui.AbstractView;

public class RankingView extends AbstractView<RankingModel> {
	
	protected Button btnBack;

	public RankingView(Stage stage, RankingModel model) {
		super(stage, model);
	}

	@Override
	protected Scene createGUI() {

		// Create the labels
		Label labelRanking = new Label("Unsere Besten:");
		
		TableView table = new TableView();
		table.setEditable(true);
		 
        TableColumn rank = new TableColumn("Rang");
        TableColumn name = new TableColumn("Name");
        TableColumn points = new TableColumn("Punkte");
        TableColumn date = new TableColumn("Datum");
        
        table.getColumns().addAll(rank, name, points, date);
        
		// 
		btnBack = new Button();
		btnBack.setText("Zurück");

		ImageView iview = null;
		try {
			iview = new ImageView(ResourceLoader.getImage("Exit.png"));
			iview.setFitWidth(50);
			iview.setFitWidth(50);
			iview.setPreserveRatio(true);
		} catch (FileNotFoundException e1) {
			ServiceLocator.getInstance().getLogger().severe(e1.toString());
		}

		GridPane grid = new GridPane();
		grid.setAlignment(Pos.TOP_LEFT);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(50, 50, 50, 50));

		grid.add(labelRanking, 0, 0);
		grid.add(table, 0, 1);
		grid.add(btnBack, 16, 4);
		grid.add(iview, 16, 0);

		// Add the layout pane to a scene
		Scene scene = new Scene(grid, 800, 600);

		stage.setTitle("Nicht Lustig: Ranking");

		// scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

		return scene;
	}

}
