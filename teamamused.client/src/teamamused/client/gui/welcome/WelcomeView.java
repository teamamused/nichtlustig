package teamamused.client.gui.welcome;

import java.io.FileNotFoundException;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import teamamused.common.LogHelper;
import teamamused.common.ResourceLoader;
import teamamused.common.ServiceLocator;
import teamamused.common.gui.AbstractView;
import teamamused.common.gui.LangText;
import teamamused.common.gui.Translator;

/**
 * Diese Klasse stellt die grafische Oberfläche für die Willkommensseite dar.
 * 
 * @author Sandra
 *
 */

public class WelcomeView extends AbstractView<WelcomeModel> {
	
	protected Button btnSingle;
	protected Button btnMulti;
	protected Button btnTrophy;
	protected Button btnExit;
	
	private Label labelWelcome;
	private Label labelPlay;
	private Label labelFun;

	public WelcomeView(Stage stage, WelcomeModel model) {
		super(stage, model);
	
	}

	@Override
	protected Scene createGUI() {

		// Labels erstellen
		this.labelWelcome = new Label("Herzlich Willkommen " + model.player.getPlayerName());
		this.labelPlay = new Label("Was möchtest du spielen?");
		this.labelFun = new Label("Wir wünschen dir viel Spass!");
		
		labelWelcome.setId("labelWelcome");
		
		// Single-Player-Button erstellen und auf disable stellen
		btnSingle = new Button();
		btnSingle.setText("Single-Player");
		btnSingle.setDisable(true);
		
		// Multi-Player-Button erstellen 
		btnMulti = new Button();
		btnMulti.setText("Multi-Player");
		
		ImageView iview = null;
		try {
			iview = new ImageView(ResourceLoader.getImage("Single-Player.jpg"));
			iview.setFitWidth(260);
			iview.setFitWidth(260);
			iview.setPreserveRatio(true);
		} catch (FileNotFoundException e1) {
			LogHelper.LogException(e1);
		}
		
		ImageView iview2 = null;
		try {
			iview2 = new ImageView(ResourceLoader.getImage("Multi-Player.jpg"));
			iview2.setFitWidth(260);
			iview2.setFitWidth(260);
			iview2.setPreserveRatio(true);
		} catch (FileNotFoundException e1) {
			LogHelper.LogException(e1);
		}
		
		ImageView iview3 = null;
		try {
			iview3 = new ImageView(ResourceLoader.getImage("Pokal.png"));
			iview3.setFitWidth(30);
			iview3.setFitWidth(30);
			iview3.setPreserveRatio(true);
		} catch (FileNotFoundException e1) {
			LogHelper.LogException(e1);
		}
		
		ImageView iview4 = null;
		try {
			iview4 = new ImageView(ResourceLoader.getImage("Exit.png"));
			iview4.setFitWidth(30);
			iview4.setFitWidth(30);
			iview4.setPreserveRatio(true);
		} catch (FileNotFoundException e1) {
			LogHelper.LogException(e1);
		}
		
		// Ranking-Button mit Bild erstellen
		btnTrophy = new Button();
		btnTrophy.setGraphic(iview3);
		btnTrophy.setId("btnTransparent");
		
		// Exit-Button mit Bild erstellen
		btnExit = new Button();
		btnExit.setGraphic(iview4);
		btnExit.setId("btnTransparent");
		
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.TOP_LEFT);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(30, 50, 50, 50));

		grid.add(labelWelcome, 0, 0, 20, 1);
		grid.add(labelPlay, 0, 2);
		grid.add(btnSingle, 0, 4);
		grid.add(btnMulti, 14, 4);
		grid.add(iview, 0, 7);
		grid.add(iview2, 14, 7);
		grid.add(btnTrophy, 24, 10);
		grid.add(labelFun, 0, 10);
		grid.add(btnExit, 24, 0);
		
		// Das Layout Pane einer Scene hinzufügen
		Scene scene = new Scene(grid, 900, 600);
		
		// Fenstertitel setzen
		stage.setTitle("Nicht Lustig: Welcome");
		
		// Stylesheet zuweisen
		scene.getStylesheets().add(getClass().getResource("..\\application.css").toExternalForm());
		
		updateTexts();
		
		return scene;
	}
	
	/**
	 * Aktualisiert die Sprachtexte auf allen GUI-Elementen
	 */
	protected void updateTexts() {
		
		// Translator holen
		Translator tl = ServiceLocator.getInstance().getTranslator();
		
		// Texte holen
		stage.setTitle(tl.getString(LangText.WelcomeTitel));
		this.labelWelcome.setText(String.format(tl.getString(LangText.WelcomeText), model.player.getPlayerName()));
		this.labelPlay.setText(tl.getString(LangText.WelcomePlay));
		this.labelFun.setText(tl.getString(LangText.WelcomeFun));

	}

}
