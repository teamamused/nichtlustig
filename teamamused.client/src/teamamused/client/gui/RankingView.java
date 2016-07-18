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

public class RankingView extends AbstractView<RankingModel> {

	public RankingView(Stage stage, RankingModel model) {
		super(stage, model);
	}

	@Override
	protected Scene createGUI() {

		// Create the labels
		Label labelRanking = new Label("Unsere Besten:");

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
		grid.add(iview, 16, 0);
		
		return null;
	}

}
