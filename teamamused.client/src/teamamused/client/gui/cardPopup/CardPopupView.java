package teamamused.client.gui.cardPopup;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Popup;
import javafx.stage.Stage;
import teamamused.common.gui.AbstractView;

public class CardPopupView extends AbstractView<CardPopupModel> {
	
	Popup cardPopup;
	
	public CardPopupView(Stage stage, CardPopupModel model) {
		super(stage, model);
	}
	
	protected Scene createGUI() {
		stage.setTitle("Titel");
		GridPane grid = new GridPane();
		Scene scene = new Scene(grid);
		
		cardPopup = new Popup();
		cardPopup.setX(300);
		cardPopup.setY(200);
		cardPopup.getContent().addAll(new Label("Hallo"));
		
		return scene;
		
	}
	
	public Scene getScene() {
		return scene;
	}
	
	public void start() {
		stage.show();
	}

	public void stop() {
		stage.hide();
	}

}
