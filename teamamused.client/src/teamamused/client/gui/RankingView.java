package teamamused.client.gui;

import java.io.FileNotFoundException;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import teamamused.common.LogHelper;
import teamamused.common.ResourceLoader;
import teamamused.common.db.Ranking;
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

	@SuppressWarnings("unchecked")
	@Override
	protected Scene createGUI() {

		// Label erstellen
		Label labelRanking = new Label("Unsere Besten");
		labelRanking.setId("labelRanking");

		// Tabelle erstellen
		TableView<Ranking> table = new TableView<Ranking>();
		table.maxWidthProperty().bind(stage.widthProperty().multiply(0.9));
		table.setId("tableRanking");

		// Spaltentitel festlegen
		TableColumn<Ranking, Integer> rank = new TableColumn<Ranking, Integer>("Rang");
		rank.setCellValueFactory(new PropertyValueFactory<Ranking, Integer>("TotalRank"));
		// Relative breiten angaben für dynamisches Layout
		rank.prefWidthProperty().bind(table.widthProperty().divide(6));
		rank.getStyleClass().add("tblViewRightCol");

		TableColumn<Ranking, Integer> rankInGame = new TableColumn<Ranking, Integer>("im Spiel");
		rankInGame.setCellValueFactory(new PropertyValueFactory<Ranking, Integer>("TotalRank"));
		rankInGame.prefWidthProperty().bind(table.widthProperty().divide(6));
		rankInGame.getStyleClass().add("tblViewRightCol");

		TableColumn<Ranking, String> name = new TableColumn<Ranking, String>("Name");
		name.setCellValueFactory(new PropertyValueFactory<Ranking, String>("Username"));
		name.prefWidthProperty().bind(table.widthProperty().divide(2.1));

		TableColumn<Ranking, Integer> points = new TableColumn<Ranking, Integer>("Punkte");
		points.setCellValueFactory(new PropertyValueFactory<Ranking, Integer>("Points"));
		points.prefWidthProperty().bind(table.widthProperty().divide(6));
		points.getStyleClass().add("tblViewRightCol");

		// Datum gibts noch nicht, kommt ev auch nicht mehr (trurigs smilie)
		/*TableColumn<Ranking, LocalDate> date = new TableColumn<Ranking, LocalDate>("Datum");
		date.setCellValueFactory(new PropertyValueFactory<Ranking,
		LocalDate>("Points"));
		date.setMinWidth(150);*/

		table.getColumns().addAll(rank, rankInGame, name, points);//, date);

		table.setItems(this.model.ranking);

		ImageView iview = null;
		try {
			iview = new ImageView(ResourceLoader.getImage("Exit.png"));
			iview.setFitWidth(30);
			iview.setFitWidth(30);
			iview.setPreserveRatio(true);
		} catch (FileNotFoundException e1) {
			LogHelper.LogException(e1);
		}

		ImageView iview2 = null;
		try {
			iview2 = new ImageView(ResourceLoader.getImage("Back.png"));
			iview2.setFitWidth(30);
			iview2.setFitWidth(30);
			iview2.setPreserveRatio(true);
		} catch (FileNotFoundException e1) {
			LogHelper.LogException(e1);
		}

		// Exit-Button mit Bild erstellen
		btnExit = new Button();
		btnExit.setGraphic(iview);
		btnExit.setId("btnTransparent");

		// Back-Button mit Bild erstellen
		btnBack = new Button();
		btnBack.setGraphic(iview2);
		btnBack.setId("btnTransparent");

		BorderPane root = new BorderPane();
		BorderPane top = new BorderPane();
		HBox buttons = new HBox(5);

		buttons.getChildren().addAll(btnBack, btnExit);
		top.setCenter(labelRanking);
		top.setRight(buttons);
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
