package teamamused.playground.application.gui;

import java.io.FileNotFoundException;
import java.time.LocalDate;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import teamamused.common.ResourceLoader;
import teamamused.common.ServiceLocator;
import teamamused.common.gui.AbstractView;
import teamamused.common.db.Ranking;

/**
 * Diese Klasse stellt die grafische Oberfläche für die Ranking-Seite dar.
 * 
 * @author Sandra
 *
 */

public class RankingView extends AbstractView<RankingModel> {
	protected Button btnExit;

	public RankingView(Stage stage, RankingModel model) {
		super(stage, model);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Scene createGUI() {

		// Label erstellen
		Label labelRanking = new Label("Unsere Besten:");
		labelRanking.setId("labelRanking");
		
		// Tabelle erstellen
		TableView<Ranking> table = new TableView<Ranking>();
		table.setEditable(false);
		table.setId("tableRanking");
		
		// Spaltentitel festlegen        
        TableColumn<Ranking, Integer> rank = new TableColumn<Ranking, Integer>("Rang");
        rank.setCellValueFactory(new PropertyValueFactory<Ranking, Integer>("TotalRank"));
		rank.prefWidthProperty().bind(table.widthProperty().divide(8));
		rank.setStyle("-fx-alignment: CENTER-RIGHT;");

        TableColumn<Ranking, String> name = new TableColumn<Ranking, String>("Name");
        name.setCellValueFactory(new PropertyValueFactory<Ranking, String>("Username"));
        name.prefWidthProperty().bind(table.widthProperty().divide(2));

        TableColumn<Ranking, Integer> points = new TableColumn<Ranking, Integer>("Punkte");
        points.setCellValueFactory(new PropertyValueFactory<Ranking, Integer>("Points"));
        points.prefWidthProperty().bind(table.widthProperty().divide(8));
        points.setStyle("-fx-alignment: CENTER-RIGHT;");

        TableColumn<Ranking, LocalDate> date = new TableColumn<Ranking, LocalDate>("Datum");
        //date.setCellValueFactory(new PropertyValueFactory<Ranking, LocalDate>("Points"));
        date.prefWidthProperty().bind(table.widthProperty().divide(4));
        date.setStyle("-fx-alignment: CENTER-RIGHT;");
        
        table.getColumns().addAll(
        		rank, 
        		name, 
        		points, 
        		date);
        
		table.setItems(this.model.ranking);

		ImageView iview = null;
		try {
			iview = new ImageView(ResourceLoader.getImage("Exit.png"));
			iview.setFitWidth(30);
			iview.setFitWidth(30);
			iview.setPreserveRatio(true);
		} catch (FileNotFoundException e1) {
			ServiceLocator.getInstance().getLogger().severe(e1.toString());
		}

		// Exit-Button mit Bild erstellen
		btnExit = new Button();
		btnExit.setGraphic(iview);
		
		BorderPane root = new BorderPane();
		BorderPane top = new BorderPane();

		top.setLeft(labelRanking);
		top.setRight(btnExit);		
		
		root.setTop(top);
		root.setCenter(table);

		// Das Layout Pane einer Scene hinzufügen
		Scene scene = new Scene(root, 900, 600);

		// Fenstertitel setzen
		stage.setTitle("Nicht Lustig: Ranking");

		// Stylesheet zuweisen
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

		return scene;
	}

}
