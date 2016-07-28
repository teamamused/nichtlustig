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

/**
 * Diese Klasse stellt die grafische Oberfläche für die Ranking-Seite dar.
 * 
 * @author Sandra
 *
 */

public class RankingView extends AbstractView<RankingModel> {
	
	protected Button btnBack;
	protected Button btnExit;

	public RankingView(Stage stage, RankingModel model) {
		super(stage, model);
	}

	@Override
	protected Scene createGUI() {

		// Label erstellen
		Label labelRanking = new Label("Unsere Besten:");
		labelRanking.setId("labelRanking");
		
		// Tabelle erstellen
		TableView table = new TableView();
		table.setEditable(true);
		table.setId("tableRanking");
		
		// Spaltentitel festlegen
        TableColumn rank = new TableColumn("Rang");
        TableColumn name = new TableColumn("Name");
        TableColumn points = new TableColumn("Punkte");
        TableColumn date = new TableColumn("Datum");
        
        table.getColumns().addAll(rank, name, points, date);

		ImageView iview = null;
		try {
			iview = new ImageView(ResourceLoader.getImage("Exit.png"));
			iview.setFitWidth(30);
			iview.setFitWidth(30);
			iview.setPreserveRatio(true);
		} catch (FileNotFoundException e1) {
			ServiceLocator.getInstance().getLogger().severe(e1.toString());
		}
		
		ImageView iview2 = null;
		try {
			iview2 = new ImageView(ResourceLoader.getImage("Back.png"));
			iview2.setFitWidth(30);
			iview2.setFitWidth(30);
			iview2.setPreserveRatio(true);
		} catch (FileNotFoundException e1) {
			ServiceLocator.getInstance().getLogger().severe(e1.toString());
		}

		// Exit-Button mit Bild erstellen
		btnExit = new Button();
		btnExit.setGraphic(iview);
		btnExit.setId("btnTransparent");
		
		// Back-Button mit Bild erstellen
		btnBack = new Button();
		btnBack.setGraphic(iview2);
		btnExit.setId("btnTransparent");
		
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.TOP_LEFT);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(50, 50, 50, 50));

		grid.add(labelRanking, 0, 0, 20, 1);
		grid.add(table, 0, 2);
		grid.add(btnBack, 41, 0);
		grid.add(btnExit, 43, 0);

		// Das Layout Pane einer Scene hinzufügen
		Scene scene = new Scene(grid, 900, 600);

		// Fenstertitel setzen
		stage.setTitle("Nicht Lustig: Ranking");

		// Stylesheet zuweisen
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

		return scene;
	}

}
