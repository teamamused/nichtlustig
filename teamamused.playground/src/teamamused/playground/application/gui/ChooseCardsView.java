package teamamused.playground.application.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import teamamused.client.libs.Client;
import teamamused.common.gui.AbstractView;
import teamamused.common.interfaces.ITargetCard;

public class ChooseCardsView extends AbstractView<ChooseCardsModel> {

	private final double BUTTON_WIDTH = 110;
	/**
	 * Konstruktor
	 * 
	 * @param stage
	 *            Stage in welcher die Scene angezeigt wird
	 * @param model
	 *            Model für die Datenhaltung
	 */
	public ChooseCardsView(Stage stage, ChooseCardsModel model) {
		super(stage, model);
	}

	@Override
	protected Scene createGUI() {
		BorderPane root = new BorderPane();
		Scene scene = new Scene(root, 1600, 810);
		// Oberer Teil
		HBox topBox = new HBox();
		// Titel label
		Label lTitel = new Label();
		lTitel.setText("Du hast folgende Karten Kombinationen zur Auswahl:");
		// Button zum starten
		topBox.getChildren().addAll(lTitel);
		// Für alle Optionen eine VBox
		VBox options = new VBox();
		for (int i = 0; i < model.cardsToChooseOptions.size(); i++) {
			// Pro Option Karten laden
			options.getChildren().add(drawCards(i));
		}
		// Alle Elemente im Gui anordnen
		root.setTop(topBox);
		root.setCenter(options);
		try {
			// CSS Gestaltungs File laden
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		} catch (Exception e) {
			e.printStackTrace();
		}
		// anzeigen
		return scene;
	}

	/**
	 * Karten pro Option zeichnen
	 */
	protected Button drawCards(int optionNr) {
		Button btn = new Button();
		if (this.model.cardsToChooseOptions != null) {
			btn.setId(optionNr+"");
			HBox box = new HBox(); 
			for (ITargetCard card : model.cardsToChooseOptions.get(optionNr)) {
				ImageView iv = new ImageView(card.getForegroundImage());
				iv.setFitHeight(BUTTON_WIDTH - 5);
				iv.setFitWidth(BUTTON_WIDTH - 5);
				box.getChildren().add(iv);
			}
			btn.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
				Client.getInstance().cardsChoosen(model.cardsToChooseOptions.get(optionNr));
				}
			});
			btn.setGraphic(box);
		}
		return btn;
	}
}
