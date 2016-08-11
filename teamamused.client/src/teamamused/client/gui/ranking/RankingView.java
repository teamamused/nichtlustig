package teamamused.client.gui.ranking;

import java.io.FileNotFoundException;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import teamamused.common.LogHelper;
import teamamused.common.ResourceLoader;
import teamamused.common.ServiceLocator;
import teamamused.common.db.Ranking;
import teamamused.common.gui.AbstractView;
import teamamused.common.gui.LangText;
import teamamused.common.gui.Translator;

/**
 * Diese Klasse stellt die grafische Oberfläche für die Ranking-Seite dar.
 * 
 * @author Sandra
 *
 */

public class RankingView extends AbstractView<RankingModel> {

	protected Button btnBack;
	
	private Label labelRanking;

	public RankingView(Stage stage, RankingModel model) {
		super(stage, model);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Scene createGUI() {

		// Label erstellen und ID setzen
		this.labelRanking = new Label("Unsere Besten");
		labelRanking.setId("labelTitle");

		// Tabelle erstellen, Breite und ID setzen
		TableView<Ranking> table = new TableView<Ranking>();
		table.setPrefWidth(750);
		table.setId("tableRanking");

		// Spaltentitel festlegen
		TableColumn<Ranking, Integer> rank = new TableColumn<Ranking, Integer>("Rang");
		rank.setCellValueFactory(new PropertyValueFactory<Ranking, Integer>("TotalRank"));
		
		// Relative Breiten angeben für dynamisches Layout
		rank.prefWidthProperty().bind(table.widthProperty().divide(6));
		rank.getStyleClass().add("tblViewRightCol");

		// Spaltentitel festlegen
		TableColumn<Ranking, Integer> rankInGame = new TableColumn<Ranking, Integer>("im Spiel");
		rankInGame.setCellValueFactory(new PropertyValueFactory<Ranking, Integer>("GameRank"));
		rankInGame.prefWidthProperty().bind(table.widthProperty().divide(6));
		rankInGame.getStyleClass().add("tblViewRightCol");

		// Spaltentitel festlegen
		TableColumn<Ranking, String> name = new TableColumn<Ranking, String>("Name");
		name.setCellValueFactory(new PropertyValueFactory<Ranking, String>("Username"));
		name.prefWidthProperty().bind(table.widthProperty().divide(2.2));

		// Spaltentitel festlegen
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

		// Mittels ResourceLoader das Bild holen, Grösse setzen und der erstellten iview hinzufügen
		ImageView iview = null;
		try {
			iview = new ImageView(ResourceLoader.getImage("Back.png"));
			iview.setFitWidth(30);
			iview.setFitWidth(30);
			iview.setPreserveRatio(true);
		} catch (FileNotFoundException e1) {
			LogHelper.LogException(e1);
		}

		// Back-Button mit Bild erstellen und ID setzen
		btnBack = new Button();
		btnBack.setGraphic(iview);
		btnBack.setId("btnTransparent");
		
		// StackPane erstellen und Button hinzufügen
	    StackPane stackBack = new StackPane();
	    stackBack.setPadding(new Insets(0, 0, 0, 0));
	    stackBack.setAlignment(Pos.TOP_RIGHT);
	    stackBack.getChildren().addAll(btnBack);
		
	    // StackPane erstellen und Label hinzufügen
	    StackPane stackLabelRank = new StackPane();
	    stackLabelRank.setPadding(new Insets(0, 0, 0, 0));
	    stackLabelRank.setAlignment(Pos.TOP_LEFT);
	    stackLabelRank.getChildren().addAll(labelRanking);
	    
	    // StackPane erstellen und Tabelle hinzufügen
	    StackPane stackTable = new StackPane();
	    stackTable.setPadding(new Insets(10, 0, 0, 0));
	    stackTable.setAlignment(Pos.TOP_LEFT);
	    stackTable.getChildren().addAll(table);
	    
	    // BorderPane erstellen und Elemente hinzufügen
	    BorderPane top = new BorderPane();
	    top.setLeft(stackLabelRank);		
	    top.setRight(stackBack);
	    
	    // BorderPane erstellen und Elemente hinzufügen
		BorderPane root = new BorderPane();
		root.setPadding(new Insets(30, 50, 50, 50));
		root.setTop(top);
		root.setLeft(stackTable);

		// Das BorderPane einer Scene hinzufügen
		Scene scene = new Scene(root, 900, 600);

		// Fenstertitel setzen
		stage.setTitle("Nicht Lustig: Ranking");

		// Stylesheet zuweisen
		scene.getStylesheets().add(getClass().getResource("..\\application.css").toExternalForm());

		// Translator-Methode ausführen
		updateTexts();
		
		// Scene widergeben
		return scene;
	}
	
	/**
	 * Aktualisiert die Sprachtexte auf allen GUI-Elementen
	 */
	protected void updateTexts() {
		
		// Translator holen
		Translator tl = ServiceLocator.getInstance().getTranslator();
		
		// Texte holen
		stage.setTitle(tl.getString(LangText.RankingTitel));
		this.labelRanking.setText(tl.getString(LangText.RankingText));
	}

}
