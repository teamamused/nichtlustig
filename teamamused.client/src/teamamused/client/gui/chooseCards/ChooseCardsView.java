package teamamused.client.gui.chooseCards;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import teamamused.client.libs.Client;
import teamamused.common.ServiceLocator;
import teamamused.common.gui.AbstractView;
import teamamused.common.gui.LangText;
import teamamused.common.gui.Translator;
import teamamused.common.interfaces.ITargetCard;

/**
 * 
 * Sobald ein Spieler Augenzahlen würfelt, bei welchen ihm mehrere Möglichkeiten
 * der Kartenauswahl zur Verfügung stehen, erscheint ihm ein Fenster, aus
 * welchem er eine Kombination auswählen kann. Diese Klasse stellt dazu die
 * grafische Oberfläche dar.
 * 
 * @author Dani und Michelle
 *
 */
public class ChooseCardsView extends AbstractView<ChooseCardsModel> {

	protected GridPane root;
	protected HBox titlePane;
	protected VBox txtPane;
	protected FlowPane optionPane;
	protected Label titleLabel, explainLabel, choiceLabel;

	private final double BUTTON_WIDTH = 110;

	public ChooseCardsView(Stage stage, ChooseCardsModel model) {
		super(stage, model);
	}

	@Override
	protected Scene createGUI() {

		// Definition der Haupt-Pane
		root = new GridPane();
		root.setPadding(new Insets(20, 20, 20, 20));
		root.setHgap(20);
		root.setVgap(20);
		root.setGridLinesVisible(false);

		Scene scene = new Scene(root, 700, 500);

		// Definition der Pane für die Texte inkl. Instanziierung und Zuweisung
		// der Controlls
		titlePane = new HBox();
		titleLabel = new Label("Karten-Kombinationen");
		titleLabel.setId("labelTitle");
		titlePane.getChildren().add(titleLabel);

		// Definition der Pane für die Texte inkl. Instanziierung und Zuweisung
		// der Controlls
		txtPane = new VBox();
		explainLabel = new Label("Deine gewürfelten Zahlen erlauben mehrere Möglichkeiten.");
		choiceLabel = new Label("Du kannst aus folgenden Karten-Kombinationen wählen:");
		txtPane.getChildren().addAll(explainLabel, choiceLabel);

		// Definition der option-Pane inkl. Instanziierung und Zuweisung der
		// Controlls
		optionPane = new FlowPane();
		optionPane.setVgap(10);
		optionPane.setHgap(10);
		for (int i : model.cardsToChooseOptions.keySet()) {
			// Lädt pro Option die Karten
			optionPane.getChildren().add(drawCards(i));
		}

		// Zuweisung der Elemente zur Haupt-Pane
		root.add(titlePane, 0, 0);
		root.add(txtPane, 0, 1);
		root.add(optionPane, 0, 2);

		// Zuweisung des Stylesheets
		try {
			scene.getStylesheets().add(getClass().getResource("..\\application.css").toExternalForm());
		} catch (Exception e) {
			e.printStackTrace();
		}

		updateTexts();
		
		return scene;
	}

	/**
	 * 
	 * Diese Support-Methode zeichnet die Karten pro Möglichkeit.
	 * 
	 */
	protected Button drawCards(int optionNr) {
		Button btn = new Button();
		if (this.model.cardsToChooseOptions != null) {
			btn.setId(optionNr + "");
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
					stop();
				}
			});
			btn.setGraphic(box);
		}
		return btn;
	}
	
	/**
	 * Aktualisiert die Sprachtexte auf allen GUI-Elementen
	 */
	protected void updateTexts() {

		// Translator holen
		Translator tl = ServiceLocator.getInstance().getTranslator();

		// Texte holen
		this.titleLabel.setText(tl.getString(LangText.ChooseCardsTitle));
		this.explainLabel.setText(tl.getString(LangText.ChooseCardsExplain));
		this.choiceLabel.setText(tl.getString(LangText.ChooseCardsChoice));
	}
}
