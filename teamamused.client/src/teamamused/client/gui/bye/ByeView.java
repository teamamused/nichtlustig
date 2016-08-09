package teamamused.client.gui.bye;

import java.io.FileNotFoundException;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import teamamused.common.LogHelper;
import teamamused.common.ResourceLoader;
import teamamused.common.ServiceLocator;
import teamamused.common.gui.AbstractView;
import teamamused.common.gui.LangText;
import teamamused.common.gui.Translator;

/**
 * Diese Klasse stellt die grafische Oberfläche für die Tschüss-Seite dar.
 * 
 * @author Sandra
 *
 */

public class ByeView extends AbstractView<ByeModel> {
	
	private Label labelTschuess;

	public ByeView(Stage stage, ByeModel model) {
		super(stage, model);
	}

	protected Scene createGUI() {

		// Label erstellen
		this.labelTschuess = new Label("Tschüss und auf Wiedersehen");
		
		labelTschuess.setId("labelTschuess");
		
		ImageView iview = null;
		try {
			iview = new ImageView(ResourceLoader.getImage("Tschuess.jpg"));
			iview.setFitWidth(700);
			iview.setFitWidth(700);
			iview.setPreserveRatio(true);
		} catch (FileNotFoundException e1) {
			LogHelper.LogException(e1);
		}
		
		StackPane stackTschuess = new StackPane();
		stackTschuess.setPadding(new Insets(0, 0, 0, 0));
		stackTschuess.setAlignment(Pos.TOP_CENTER);
		stackTschuess.getChildren().addAll(labelTschuess);
		
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.TOP_CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(30, 50, 50, 50));

		grid.add(stackTschuess, 0, 0);
		grid.add(iview, 0, 3);

		// Das Layout Pane einer Scene hinzufügen
		Scene scene = new Scene(grid, 900, 600);

		// Fenstertitel setzen
		stage.setTitle("Nicht Lustig: Bye");
		
		// Stylesheet zuweisen
		scene.getStylesheets().add(getClass().getResource("..\\application.css").toExternalForm());

		updateTexts();
		
		return scene;
	}

	public void start() {
		stage.show();
	}

	public void stop() {
		stage.hide();
	}
	
	/**
	 * Aktualisiert die Sprachtexte auf allen GUI-Elementen
	 */
	protected void updateTexts() {
		
		// Translator holen
		Translator tl = ServiceLocator.getInstance().getTranslator();
		
		// Texte holen
		stage.setTitle(tl.getString(LangText.ByeTitel));
		this.labelTschuess.setText(tl.getString(LangText.ByeText));

	}
	
}
