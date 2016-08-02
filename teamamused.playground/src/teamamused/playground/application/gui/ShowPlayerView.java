package teamamused.playground.application.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;
import teamamused.common.gui.AbstractView;
import teamamused.common.interfaces.IDeadCard;
import teamamused.common.interfaces.ISpecialCard;
import teamamused.common.interfaces.ITargetCard;

public class ShowPlayerView extends AbstractView<ShowPlayerModel> {

	private final int BUTTON_WIDTH = 150;

	/**
	 * Konstruktor
	 * 
	 * @param stage
	 *            Stage in welcher die Scene angezeigt wird
	 * @param model
	 *            Model für die Datenhaltung
	 */
	public ShowPlayerView(Stage stage, ShowPlayerModel model) {
		super(stage, model);
	}

	@Override
	protected Scene createGUI() {
		BorderPane root = new BorderPane();
		Scene scene = new Scene(root, 600, 800);
		// Titel label
		Label lTitel = new Label();
		lTitel.setText("Der spieler " + model.player.getPlayerName() + " hat folgende Karten");
		// Button zum starten
		// Für alle Karten eine Tilpane
		VBox karten = new VBox();
		if (this.model.player != null) {
			Label lbl1 = new Label("Zielkarten:");
			TilePane box1 = new TilePane();
			for (ITargetCard card : model.player.getTargetCards()) {
				Button btnTargetCard = new Button();
				btnTargetCard.setGraphic(card.toCanvas(BUTTON_WIDTH - 5));
				btnTargetCard.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						Popup popup = new Popup();
						popup.hideOnEscapeProperty().set(true);
						Button btnSchliessen = new Button("schliessen");
						btnSchliessen.setOnAction(new EventHandler<ActionEvent>() {
							@Override
							public void handle(ActionEvent event) {
								popup.hide();
							}
						});
						VBox box = new VBox(5);
						box.getChildren().addAll(card.toCanvas(BUTTON_WIDTH * 2), btnSchliessen);
						popup.getContent().addAll(box);
						popup.show(stage);
					}
				});
				box1.getChildren().add(btnTargetCard);
			}
			if (box1.getChildren().size() == 0) {
				box1.getChildren().add(new Label("<Keine>"));
			}

			Label lbl2 = new Label("Spezialkarten:");
			TilePane box2 = new TilePane();
			for (ISpecialCard card : model.player.getSpecialCards()) {
				Label lblSpecialCard = new Label();
				ImageView iv = new ImageView(card.getForegroundImage());
				iv.setFitHeight(BUTTON_WIDTH - 5);
				iv.setFitWidth(BUTTON_WIDTH - 5);
				lblSpecialCard.setGraphic(iv);
				Tooltip tooltip = new Tooltip();
				ImageView ivtooltip = new ImageView(card.getForegroundImage());
				ivtooltip.setFitHeight(2* BUTTON_WIDTH);
				ivtooltip.setFitWidth(2* BUTTON_WIDTH);
			      tooltip.setGraphic(ivtooltip);
			      lblSpecialCard.setTooltip(tooltip);
				box2.getChildren().add(iv);
			}
			if (box2.getChildren().size() == 0) {
				box2.getChildren().add(new Label("<Keine>"));
			}
			Label lbl3 = new Label("Todeskarten:");
			TilePane box3 = new TilePane();
			for (IDeadCard card : model.player.getDeadCards()) {
				ImageView iv = new ImageView(card.getForegroundImage());
				iv.setFitHeight(BUTTON_WIDTH - 5);
				iv.setFitWidth(BUTTON_WIDTH - 5);
				box3.getChildren().add(iv);
			}
			if (box3.getChildren().size() == 0) {
				box3.getChildren().add(new Label("<Keine>"));
			}
			karten.getChildren().addAll(lbl1, box1, lbl2, box2, lbl3, box3);
		}

		// Alle Elemente im Gui anordnen
		root.setTop(lTitel);
		root.setCenter(karten);
		try {
			// CSS Gestaltungs File laden
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		} catch (Exception e) {
			e.printStackTrace();
		}
		// anzeigen
		return scene;
	}
}
